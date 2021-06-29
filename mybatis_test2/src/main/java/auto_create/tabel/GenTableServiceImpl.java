package auto_create.tabel;

import auto_create.GenMysql;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.utils.Constants;
import com.utils.GenConstants;
import com.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 业务 服务层实现
 *
 */
@Service
public class GenTableServiceImpl
{
    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    MysqlSelect mysqlSelectImp = new MysqlSelectImp();



    /**
     * 导入表结构
     *
     * @param tableName 导入表列表
     */
    public GenTable selectGenTableByName(String tableName)
    {
        GenTable table = mysqlSelectImp.selectDbTableByName(tableName);
        table.setColumns(mysqlSelectImp.selectDbTableColumnsByName(tableName));
        GenUtils.initTable(table, GenMysql.packagePath);
        return table;

    }



    /**
     * 查询表信息并生成代码
     */
    public void generatorCode(String tableName)
    {
        // 查询表信息
        GenTable table = selectGenTableByName(tableName);
        // 查询列信息
        List<GenTableColumn> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try
            {
                String filePath = VelocityUtils.getFileName(template, table);
                String filePath2 = filePath.substring(0,filePath.lastIndexOf("/"));
                File docFile = new File(filePath2);
                if(!docFile.exists()){//如果文件夹不存在
                    docFile.mkdirs();//创建文件夹
                }
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), fileOutputStream, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            catch (IOException e)
            {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }


    /**
     * 设置主键列信息
     *
     * @param table 业务表信息
     * @param columns 业务字段列表
     */
    public void setPkColumn(GenTable table, List<GenTableColumn> columns)
    {
        for (GenTableColumn column : columns)
        {
            if (column.isPk())
            {
                table.setPkColumn(column);
                break;
            }
        }
        if (StringUtils.isNull(table.getPkColumn()))
        {
            table.setPkColumn(columns.get(0));
        }
    }

    /**
     * 设置代码生成其他选项值
     *
     * @param genTable 设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable)
    {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj))
        {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
        }
    }
}