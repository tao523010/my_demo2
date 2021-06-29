//package auto_create;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class GenEntityMysql {
//
//    private String authorName = "lq";//作者名字
//    private String tablename = "environment_push_warning_status";//表名
//    private String tabledesc = "环境监测--推送--报警记录表";//实体类备注
//    private String modelName = "environment";//模块名
//    private String[] colnames; // 列名数组
//    private String[] colTypes; //列名类型数组
//    List<String> columnComments;//列名注释集合
//    private int[] colSizes; //列名大小数组
//    private boolean f_util = true; // 是否需要导入包java.util.*
//    private boolean f_sql = false; // 是否需要导入包java.sql.*
//
//    //数据库连接
//    private static final String URL ="jdbc:mysql://101.133.219.73:3006/inspect_platform_hy?serverTimezone=UTC&characterEncoding=utf8";
//    private static final String NAME = "root";
//    private static final String PASS = "Jk88888888";
//    private static final String DRIVER ="com.mysql.jdbc.Driver";
//    private static final String DATANAME ="inspect_platform";
////    private String projectName = "cloud";
//
////    private String packageOutPath = "cn.com."+projectName+".core.po";//指定实体生成所在包的路径
////    private String packageDaoOutPath = "cn.com."+projectName+".core.dao";//指定实体生成所在包的路径
//
//    private String packageOutPath = "lanlyc.module."+modelName+".po";//指定实体生成所在包的路径
//    private String packageDaoOutPath = "lanlyc.module."+modelName+".dao";//指定实体生成所在包的路径
//    /*
//     * 构造函数
//     */
//    public void autoGenEntityMysql(Connection con,String tablename){
//        //创建连接
////        Connection con;
//        //查要生成实体类的表
//        String sql = "select * from " + tablename;
//        PreparedStatement pStemt = null;
//        ResultSet rs = null;
//        try {
//            try {
//                Class.forName(DRIVER);
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            con = DriverManager.getConnection(URL,NAME,PASS);
//            pStemt = con.prepareStatement(sql);
//            ResultSetMetaData rsmd = pStemt.getMetaData();  //获取列的类型和名称
//
//            rs = pStemt.executeQuery("show full columns from " + tablename);
//
//            int size = rsmd.getColumnCount();   //统计列
//            colnames = new String[size];
//            colTypes = new String[size];
//            colSizes = new int[size];
//            columnComments = new ArrayList<>();
//
//            while (rs.next()) {
//
//            	columnComments.add(rs.getString("Comment"));
//            }
//            System.out.println(columnComments);
//            for (int i =0 ; i < size; i++) {
//
//                colnames[i] = rsmd.getColumnName(i + 1);
//                System.out.println(" *	 \t\""+colnames[i]+"\" "+columnComments.get(i));
//                colTypes[i] = rsmd.getColumnTypeName(i + 1);
//
//                if(colTypes[i].equalsIgnoreCase("datetime")){
//                    f_util = true;
//                }
//                if(colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")){
//                    f_sql = true;
//                }
//                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
//            }
////            List<ExcelDto> dtos = new ArrayList<>();
////            for (int i =0 ; i < size; i++) {
////            	System.err.print((i+1)+"\t");
////            	System.err.print(columnComments.get(i)+"\t");
////            	System.err.print(rsmd.getColumnName(i + 1)+"\t");
////            	System.err.print(rsmd.getColumnTypeName(i + 1)+"\t");
////            	System.err.print(rsmd.getColumnDisplaySize(i + 1)+"\n");
////            	ExcelDto dto = new ExcelDto();
////            	dto.setSort((i+1)+"");
////            	dto.setComment(columnComments.get(i));
////            	dto.setFieldName(rsmd.getColumnName(i + 1));
////            	dto.setType(rsmd.getColumnTypeName(i + 1));
////            	dto.setLength(rsmd.getColumnDisplaySize(i + 1)+"");
////            	if(i == 0) {
////            		dto.setEnlName(tablename);
////            		dto.setCnaName(tabledesc);
////            	}
////            	dtos.add(dto);
////            }
////            ExcelUtil.writeExcel(tabledesc, dtos, ExcelDto.class, false);
//            String content = parse(colnames,colTypes,colSizes,tablename);
//            String contentDao = parseDao(colnames, colTypes, colSizes, tablename);
//            try {
//                File directory = new File("");
//                //System.out.println("绝对路径："+directory.getAbsolutePath());
//                //System.out.println("相对路径："+directory.getCanonicalPath());
//                String path=this.getClass().getResource("").getPath();
//
//                System.out.println(path);
//              //項目特殊需要 去_换大写
//                //tablename2为类名  tablename为表名
//                String tablename2 = changecap(tablename,"");
//
////                System.out.println("src/?/"+path.substring(path.lastIndexOf("/com/", path.length())) );
////              String outputPath = directory.getAbsolutePath()+ "/src/"+path.substring(path.lastIndexOf("/com/", path.length()), path.length()) + initcap(tablename) + ".java";
//                String outputPath = directory.getAbsolutePath()+ "/src/main/java/"+this.packageOutPath.replace(".", "/")+"/" ;
//                String outputDaoPath = directory.getAbsolutePath()+ "/src/main/java/"+this.packageDaoOutPath.replace(".", "/")+"/" ;
//                File file=new File(outputPath);
//        		if(!file.exists()){//如果文件夹不存在
//        			file.mkdirs();//创建文件夹
//        		}
//        		File file2=new File(outputDaoPath);
//        		if(!file2.exists()){//如果文件夹不存在
//        			file2.mkdirs();//创建文件夹
//        		}
//                FileWriter fw = new FileWriter(outputPath+initcap(tablename2)+ ".java");
//                PrintWriter pw = new PrintWriter(fw);
//                pw.println(content);
//                pw.flush();
//                pw.close();
//
//                FileWriter fwDao = new FileWriter(outputDaoPath+initcap(tablename2)+ "Dao.java");
//                PrintWriter pwDao = new PrintWriter(fwDao);
//                pwDao.println(contentDao);
//                pwDao.flush();
//                pwDao.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally{
////          try {
////              con.close();
////          } catch (SQLException e) {
////              // TODO Auto-generated catch block
////              e.printStackTrace();
////          }
//        }
//    }
//
//    /**
//     * 功能：生成实体类主体代码
//     * @param colnames
//     * @param colTypes
//     * @param colSizes
//     * @return
//     */
//    private String parse(String[] colnames, String[] colTypes, int[] colSizes,String tablename) {
//        StringBuffer sb = new StringBuffer();
//
//
//        sb.append("package " + this.packageOutPath + ";\r\n");
//        sb.append("\r\n");
//        //判断是否导入工具包
//        if(f_util){
//            sb.append("import java.util.Date;\r\n");
//        }
//        if(f_sql){
//            sb.append("import java.sql.*;\r\n");
//        }
//        sb.append("import java.io.Serializable;\r\n");
//        sb.append("import lanlyc.base.persistence.PrimaryKeyMapper;\r\n");
//        sb.append("import lanlyc.base.persistence.TableMapper;\r\n");
//        sb.append("import lanlyc.annotation.ExcelField;\r\n");
//        //注释部分
//        sb.append("   /**\r\n");
//      //項目特殊需要 去_换大写
//        //tablename2为类名  tablename为表名
//        String tablename2 = changecap(tablename,"");
//        sb.append("    * "+tablename2+" "+tabledesc+"\r\n");
//        sb.append("    * "+new Date()+" "+this.authorName+"\r\n");
//        sb.append("    */ \r\n");
//        //实体部分
//
//        sb.append("\r\n@TableMapper(\""+tablename+"\")");
//        sb.append("\r\npublic class " + initcap(tablename2) + "  implements Serializable{\r\n");
//
//        processAllAttrs(sb);//属性
//        processAllMethod(sb);//get set方法
//        sb.append("}\r\n");
//
//        //System.out.println(sb.toString());
//        return sb.toString();
//    }
//
//    private String parseDao(String[] colnames, String[] colTypes, int[] colSizes,String tablename) {
//        StringBuffer sb = new StringBuffer();
//      //項目特殊需要 去_换大写
//        //tablename2为类名  tablename为表名
//        String tablename2 = changecap(tablename,"");
//
//        sb.append("package " + this.packageDaoOutPath + ";\r\n");
//        sb.append("\r\n");
//        //判断是否导入工具包
//        if(f_util){
//            sb.append("import java.util.Date;\r\n");
//        }
//        if(f_sql){
//            sb.append("import java.sql.*;\r\n");
//        }
//        sb.append("import org.springframework.stereotype.Service;\r\n");
//        sb.append("import lanlyc.base.persistence.MySQLMapper;\r\n");
//        sb.append("import "+this.packageOutPath+"."+initcap(tablename2)+";\r\n");
//
//        //实体部分
//
//        sb.append("\r\n@Service");
//        sb.append("\r\npublic class " + initcap(tablename2) + "Dao  extends MySQLMapper<"+initcap(tablename2)+">{\r\n");
//
//        sb.append("}\r\n");
//
//        //System.out.println(sb.toString());
//        return sb.toString();
//    }
//    /**
//     * 功能：生成所有属性
//     * @param sb
//     */
//    private void processAllAttrs(StringBuffer sb) {
//
//        for (int i = 0; i < colnames.length; i++) {
//        	if (i==0) {
//        		sb.append("\r\n\tprivate static final long serialVersionUID = 1L; \r\n");
//        		//注释部分
//                sb.append("\r\n   /**\r\n");
//                sb.append("    * 唯一ID\r\n");
//                sb.append("    */ \r\n");
//                sb.append("\t@PrimaryKeyMapper");
//
//        	}else {
//        		//注释部分
//                sb.append("\r\n   /**\r\n");
//                sb.append("    * "+columnComments.get(i)+"\r\n");
//                sb.append("    */ \n");
//                sb.append("\t@ExcelField(title = \""+columnComments.get(i)+"\")");
//        	}
//            sb.append("\r\tprivate " + sqlTypeJavaType(colTypes[i]) + " " + colnames[i] + ";\r\n");
//        }
//
//    }
//
//    /**
//     * 功能：生成所有方法
//     * @param sb
//     */
//    private void processAllMethod(StringBuffer sb) {
//
//        for (int i = 0; i < colnames.length; i++) {
//            sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlTypeJavaType(colTypes[i]) + " " +
//                    colnames[i] + "){\r\n");
//            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
//            sb.append("\t}\r\n");
//            sb.append("\tpublic " + sqlTypeJavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
//            sb.append("\t\treturn " + colnames[i] + ";\r\n");
//            sb.append("\t}\r\n");
//        }
//
//    }
//
//    /**
//     * 功能：将输入字符串的首字母改成大写
//     * @param str
//     * @return
//     */
//    private String initcap(String str) {
//
//        char[] ch = str.toCharArray();
//        if(ch[0] >= 'a' && ch[0] <= 'z'){
//            ch[0] = (char)(ch[0] - 32);
//        }
//
//        return new String(ch);
//    }
//
//    /**
//     * 将下划线改为下一个字母大写
//     */
//    private String changecap(String str,String com) {
//    	String res = str;
//    	while(res.contains("_")) {
//        	int order = res.indexOf("_");
////        	String start = tablename2.charAt(order-1)+"";
//        	String end = res.charAt(order+1)+"";
//        	res = res.replace("_"+end, end.toUpperCase());
//
//        }
//
//    	return res;
//    }
//    /**
//     * 功能：获得列的数据类型
//     * @param sqlType
//     * @return
//     */
//    private String sqlTypeJavaType(String sqlType) {
//
//        if(sqlType.equalsIgnoreCase("bit")){
//            return "boolean";
//        }else if(sqlType.equalsIgnoreCase("tinyint")){
//            return "byte";
//        }else if(sqlType.equalsIgnoreCase("longblob")){
//            return "byte[]";
//        }else if(sqlType.equalsIgnoreCase("smallint")){
//            return "short";
//        }else if(sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("INT UNSIGNED")){
//            return "Integer";
//        }else if(sqlType.equalsIgnoreCase("bigint")){
//            return "Long";
//        }else if(sqlType.equalsIgnoreCase("float")){
//            return "Float";
//        }else if(sqlType.equalsIgnoreCase("double")){
//            return "Double";
//        }else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
//                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
//                || sqlType.equalsIgnoreCase("smallmoney")){
//            return "BigDecimal";
//        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
//                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
//                || sqlType.equalsIgnoreCase("text")){
//            return "String";
//        }else if(sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")){
//            return "Date";
//        }else if(sqlType.equalsIgnoreCase("image")){
//            return "Blod";
//        }
//
//        return null;
//    }
//
//    /**
//     * 出口
//     * TODO
//     * @param args
//     */
//    public static void main(String[] args) {
//
////        new GenEntityMysql();
//    	GenEntityMysql gm = new GenEntityMysql();
//    	try {
//    	   Connection con = DriverManager.getConnection(URL,NAME,PASS);
////    	   gm.autoGenEntityMysql(con, "pos_cardtype");
//		   java.sql.DatabaseMetaData meta =  con.getMetaData();
//		   PreparedStatement pStemt = con.prepareStatement("select table_comment from information_schema.tables where table_type='BASE TABLE' and table_schema=\""+DATANAME+"\"");
//
//
////           ResultSet conmentrs = pStemt.executeQuery();
////		   ResultSet rs = meta.getTables(null, null, null,
////		     new String[] { "TABLE" });
////		   while (rs.next()) {
////			   conmentrs.next();
////			 System.out.println(conmentrs.getString(1));//表注释
////			 gm.tabledesc = conmentrs.getString(1);
////		     System.out.println("表名：" + rs.getString(3)); //
////		     if(rs.getString(3).contains(gm.tablename)) {
//		    	 gm.autoGenEntityMysql(con, gm.tablename);
//		    	 String tnm = gm.tablename;
////		    	 new FreeMarkerDemo().createFile(gm.changecap(gm.initcap(tnm), "")+"Controller", tnm.replace("_", ""),"controller",gm.modelName);
////		    	 new FreeMarkerDemo().createService(gm.changecap(gm.initcap(tnm), "")+"Service", gm.changecap(gm.initcap(tnm), ""),gm.changecap(tnm, ""),"service",gm.modelName);
////		    	 new FreeMarkerDemo().createService2(gm.changecap(gm.initcap(tnm), "")+"Service", gm.changecap(gm.initcap(tnm), ""),gm.changecap(tnm, ""),"bankserviceinterface");
////		     }
////		   }
//		   con.close();
//	   } catch (Exception e) {
//		   // TODO Auto-generated catch block
//		   e.printStackTrace();
//	   }
//
//
//    }
//
//    public boolean DeleteFolder(String sPath) {
//        boolean flag = false;
//        File file = new File(sPath);
//        // 判断目录或文件是否存在
//        if (!file.exists()) {  // 不存在返回 false
//            return flag;
//        } else {
//            // 判断是否为文件
//            if (file.isFile()) {  // 为文件时调用删除文件方法
//                return deleteFile(sPath);
//            } else {  // 为目录时调用删除目录方法
//                return deleteDirectory(sPath);
//            }
//        }
//    }
//    public boolean deleteFile(String sPath) {
//        boolean flag = false;
//        File file = new File(sPath);
//        // 路径为文件且不为空则进行删除
//        if (file.isFile() && file.exists()) {
//            file.delete();
//            flag = true;
//        }
//        return flag;
//    }
//    public boolean deleteDirectory(String sPath) {
//        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
//        if (!sPath.endsWith(File.separator)) {
//            sPath = sPath + File.separator;
//        }
//        File dirFile = new File(sPath);
//        //如果dir对应的文件不存在，或者不是一个目录，则退出
//        if (!dirFile.exists() || !dirFile.isDirectory()) {
//            return false;
//        }
//        boolean flag = true;
//        //删除文件夹下的所有文件(包括子目录)
//        File[] files = dirFile.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            //删除子文件
//            if (files[i].isFile()) {
//                flag = deleteFile(files[i].getAbsolutePath());
//                if (!flag) break;
//            } //删除子目录
//            else {
//                flag = deleteDirectory(files[i].getAbsolutePath());
//                if (!flag) break;
//            }
//        }
//        if (!flag) return false;
//        //删除当前目录
//        if (dirFile.delete()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//
//
//
//
//}
