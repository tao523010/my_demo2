package com.module.test.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import com.web.domain.AjaxResult;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.module.test.mapper.TestMapper;
import com.module.test.po.Test;
import com.module.test.service.ITestService;
import com.utils.Convert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试表Service业务层处理
 * 
 * @author cjt
 * @date 2021-04-25
 */
@Service
public class TestServiceImpl implements ITestService 
{
    @Autowired
    private TestMapper testMapper;

    /**
     * 查询测试表
     * 
     * @param id 测试表ID
     * @return 测试表
     */
    @Override
    public Test selectTestById(Integer id)
    {
        return (Test)fromMysql(testMapper.selectTestById(id),id).get("data");
    }

    @CachePut(cacheNames = {"Test"}, key = "#id")
    public AjaxResult fromMysql(Test t, Integer id){
        System.out.println("没进缓存");
        t.setName(t.getName()+"我是缓存中的数据");
        return AjaxResult.success(t);
    }

    /**
     * 查询测试表列表
     * 
     * @param test 测试表
     * @return 测试表
     */
    @Override
    public List<Test> selectTestList(Test test)
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        reflectSetparam(request,"orgCode","value");
        System.out.println(request.getHeader("orgCode"));
        return testMapper.selectTestList(test);
    }

    /**
     * 修改header信息，key-value键值对儿加入到header中
     * @param request
     * @param key
     * @param value
     */
    private void reflectSetparam(HttpServletRequest request, String key, String value){
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        System.out.println("request实现类="+requestClass.getName());
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            System.out.println("coyoteRequest实现类="+o1.getClass().getName());
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            o2.addValue(key).setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 新增测试表
     * 
     * @param test 测试表
     * @return 结果
     */
    @Override
    public int insertTest(Test test)
    {
        return testMapper.insertTest(test);
    }

    /**
     * 修改测试表
     * 
     * @param test 测试表
     * @return 结果
     */
    @Override
    public int updateTest(Test test)
    {
        return testMapper.updateTest(test);
    }

    /**
     * 删除测试表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestByIds(String ids)
    {
        return testMapper.deleteTestByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除测试表信息
     * 
     * @param id 测试表ID
     * @return 结果
     */
    @Override
    public int deleteTestById(Integer id)
    {
        return testMapper.deleteTestById(id);
    }


    @CachePut(cacheNames = {"Test"}, key = "#id")
    @Override
    public AjaxResult test(Integer id){
        System.out.println("重新设置缓存");
        Test t = testMapper.selectTestById(id);
        t.setName(t.getName()+"我是重新缓存中的数据"+System.currentTimeMillis());
        return AjaxResult.success(t);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add1(){
        Test t = new Test();
        t.setName("c");
        testMapper.insertTest(t);
        int i = 1/0;

    }
}
