package com.module.test.mapper;

import java.util.List;
import com.module.test.po.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.cache.annotation.CachePut;

/**
 * 测试表Mapper接口
 * 
 * @author cjt
 * @date 2021-04-25
 */
public interface TestMapper extends BaseMapper<Test>
{
    /**
     * 查询测试表
     * 
     * @param id 测试表ID
     * @return 测试表
     */

    public Test selectTestById(Integer id);

    /**
     * 查询测试表列表
     * 
     * @param test 测试表
     * @return 测试表集合
     */
    public List<Test> selectTestList(Test test);

    /**
     * 新增测试表
     * 
     * @param test 测试表
     * @return 结果
     */
    public int insertTest(Test test);

    /**
     * 修改测试表
     * 
     * @param test 测试表
     * @return 结果
     */
    public int updateTest(Test test);

    /**
     * 删除测试表
     * 
     * @param id 测试表ID
     * @return 结果
     */
    public int deleteTestById(Integer id);

    /**
     * 批量删除测试表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestByIds(String[] ids);
}
