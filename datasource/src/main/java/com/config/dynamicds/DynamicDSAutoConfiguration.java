package com.config.dynamicds;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DynamicDatasourceConfigProperties.class)
public class DynamicDSAutoConfiguration {
	
	@Autowired
	private DynamicDatasourceConfigProperties properties;
	
	public DynamicDatasourceConfigProperties getProperties() {
		return properties;
	}

	public void setProperties(DynamicDatasourceConfigProperties properties) {
		this.properties = properties;
	}

	@Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(SaasDynamicDatasource.class)
    DataSource dataSource (){
		SaasDynamicDatasource ds = new SaasDynamicDatasource();
		ds.setDsProperties(properties);
		return ds;
    }
	

	@Bean // 将数据源纳入spring事物管理
	@Primary
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
