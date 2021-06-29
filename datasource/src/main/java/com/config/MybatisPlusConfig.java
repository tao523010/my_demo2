package com.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.github.pagehelper.PageInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
public class MybatisPlusConfig {

	/**
	 * 分页插件，自动识别数据库类型
	 */
	@Bean
	public PageInterceptor paginationInterceptor() {
		return new PageInterceptor();
	}

	/**
	 * 乐观锁插件
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}

	/**
	 * 如果是对全表的删除或更新操作，就会终止该操作
	 */
	@Bean
	public SqlExplainInterceptor sqlExplainInterceptor() {
		return new SqlExplainInterceptor();
	}


//    @Autowired
//    private SqlSessionFactory sqlSessionFactory;
//
//    @PostConstruct
//    public void addMySqlInterceptor() {
//        MySqlInterceptor interceptor = new MySqlInterceptor();
////        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
//
//            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
//
////        }
//    }

}
