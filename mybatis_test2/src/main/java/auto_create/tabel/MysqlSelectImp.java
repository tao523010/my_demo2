package auto_create.tabel;

import auto_create.GenMysql;
import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MysqlSelectImp implements MysqlSelect {

    //数据库连接
    private static final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf8";
    private static final String NAME = "root";
    private static final String PASS = "123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATANAME = "test";

//    private static final String URL = "jdbc:dm://192.168.188.3:5236";
//    private static final String NAME = "SYSDBA";
//    private static final String PASS = "SYSDBA";
//    private static final String DRIVER = "dm.jdbc.driver.DmDriver";
//    private static final String DATANAME = "inspect_platform";

    private static Connection con = null;


    public GenTable selectDbTableByName(String tableName) {
        GenTable table =  new GenTable();
        table.setTableComment(GenMysql.tabledesc);
        table.setTableName(tableName);

        return table;
    }

    public List<GenTableColumn> selectDbTableColumnsByName(String tableName) {
        if(DRIVER.contains("mysql")){
            return  selectMysqlTableColumnsByName(tableName);
        }else if (DRIVER.contains("DmDriver")){
            List<GenTableColumn> cols = selectDMDbTableColumnsByName(tableName);
            Map<String,String> comments = selectDMDbColumnCommonsByName(tableName).stream().filter(x -> x.getColumnComment()!=null).collect(Collectors.toMap(GenTableColumn::getColumnName,GenTableColumn::getColumnComment));
            cols.forEach(x -> {
                if(comments.containsKey(x.getColumnName())) {
                    x.setColumnComment(comments.get(x.getColumnName()));
                }else{
                    x.setColumnComment(x.getColumnName());
                }
            });
            return  cols;
        }else {
            return  new ArrayList<>();
        }
    }

    public List<GenTableColumn> selectDMDbTableColumnsByName(String tableName) {
        String sql = "select COLUMN_NAME column_name,DATA_TYPE column_type from user_tab_columns where Table_Name='"+tableName+"';";
        ResultSet rs = getResult(sql);

        return convertList(rs,GenTableColumn.class);
    }
    public List<GenTableColumn> selectDMDbColumnCommonsByName(String tableName) {
        String sql = "select COLUMN_NAME column_name,COMMENTS columnComment from user_col_comments where Table_Name='"+tableName+"';";
        ResultSet rs = getResult(sql);

        return convertList(rs,GenTableColumn.class);
    }
    public List<GenTableColumn> selectMysqlTableColumnsByName(String tableName) {
        String sql = "select column_name, (case when (is_nullable = 'no' and column_key != 'PRI') then '1' else null end) as is_required, (case when column_key = 'PRI' then '1' else '0' end) as is_pk, ordinal_position as sort, column_comment, (case when extra = 'auto_increment' then '1' else '0' end) as is_increment, column_type" +
                " from information_schema.columns where table_schema = (select database()) and table_name = '" + tableName + "'" +
                " order by ordinal_position";
        ResultSet rs = getResult(sql);

        return convertList(rs,GenTableColumn.class);
    }

    private Connection getConnection() {
        if (con == null) {
            try {
                Class.forName(DRIVER);
                con = DriverManager.getConnection(URL, NAME, PASS);
            } catch (Exception e) {
            }
        }
        return con;
    }

    public ResultSet getResult(String sql) {
        ResultSet rs = null;
        try {
            PreparedStatement pStemt = getConnection().prepareStatement(sql);
            ResultSetMetaData rsmd = pStemt.getMetaData();  //获取列的类型和名称

            rs = pStemt.executeQuery(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;

    }


    private  static<T extends Serializable> List<T> convertList(ResultSet rs,Class<T> clazz) {

        List<T> list = new ArrayList();
        try {
            ResultSetMetaData md = rs.getMetaData();

            int columnCount = md.getColumnCount(); //Map rowData;

            while (rs.next()) { //rowData = new HashMap(columnCount);

                Map<String, Object> rowData = new HashMap();

                for (int i = 1; i <= columnCount; i++) {

                    rowData.put(md.getColumnName(i), rs.getObject(i));

                }
                T temp = JSON.parseObject(JSON.toJSONString(rowData), clazz);

                list.add(temp);

            }
        }catch (SQLException e) {

        }
        return list;

    }


}
