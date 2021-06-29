package auto_create.tabel;

import java.util.List;

public interface MysqlSelect {

    /**
     * 查询据库列表
     *
     * @param tableName 表名称组
     * @return 数据库表集合
     */
    public GenTable selectDbTableByName(String tableName);

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName);


}
