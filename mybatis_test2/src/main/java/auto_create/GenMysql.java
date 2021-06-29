package auto_create;

import auto_create.tabel.GenTableServiceImpl;

import java.util.List;

public class GenMysql {

    public static String authorName = "cjt";//作者名字
    public static String tablename = "test";//表名
    public static String tabledesc = "测试表";//实体类备注
    public static String modelName = "test";//模块名

    public static boolean f_util = true; // 是否需要导入包java.util.*
    public static boolean f_sql = false; // 是否需要导入包java.sql.*

//    private String projectName = "cloud";

//    private String packageOutPath = "cn.com."+projectName+".core.po";//指定实体生成所在包的路径
//    private String packageDaoOutPath = "cn.com."+projectName+".core.dao";//指定实体生成所在包的路径
    public static String packagePath = "com.module."+modelName;//指定模块生成所在包的路径
    public static String packageOutPath = "com.module."+modelName+".po";//指定实体生成所在包的路径
    public static String packageDaoOutPath = "com.module."+modelName+".mapper";//指定实体生成所在包的路径

    public static void main(String[] args) {
        GenTableServiceImpl gen = new GenTableServiceImpl();
        gen.generatorCode(tablename);
    }
}
