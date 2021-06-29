package auto_create;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerDemo {

 	private static String TEMPLATE_PATH = "src/main/java/auto_create/templates";
 	private static String CONTROLLER_CLASS_PATH = "src/main/java/lanlyc/module/???/controller";
    private static String SERVICE_CLASS_PATH = "src/main/java/lanlyc/module/???/service";
//    private static final String className = "UserController";
//    private static final String root = "userinfo";
    public static void main(String[] args) {
        
    }
	/**
	 * 按模板生成controller
	 * @param className
	 * @param root
	 * @param tlName 模板文件
	 */
    public static void createFile(String className,String root,String tlName,String model) {
    	// step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("model", model);
            dataMap.put("class", className);
            dataMap.put("className", className);
            dataMap.put("root", root);
            // step4 加载模版文件
            Template template = configuration.getTemplate(tlName+".ftl");
            // step5 生成数据
            File docFile = new File(CONTROLLER_CLASS_PATH.replace("???", model) );
            if(!docFile.exists()){//如果文件夹不存在
            	docFile.mkdirs();//创建文件夹
    		}
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile+ "\\" +className+".java")));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^"+className+".java 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    /**
	 * 按模板生成service
	 * @param className
	 * @param root
	 * @param tlName 模板文件
	 */
    public static void createService(String className,String poName,String daoName,String tlName,String modelName) {
    	// step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("poName", poName);
            dataMap.put("daoName", daoName);
            dataMap.put("modelName", modelName);
            // step4 加载模版文件
            Template template = configuration.getTemplate(tlName+".ftl");
            // step5 生成数据
            File docFile = new File(SERVICE_CLASS_PATH.replace("???", modelName) );
            if(!docFile.exists()){//如果文件夹不存在
            	docFile.mkdirs();//创建文件夹
    		}
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile+ "\\"+ className+".java")));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^"+className+".java 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
