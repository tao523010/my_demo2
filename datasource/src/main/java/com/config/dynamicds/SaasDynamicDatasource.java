package com.config.dynamicds;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.datasource.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class SaasDynamicDatasource extends AbstractDataSource{

public Map<String, DruidDataSource> dataSourceMap = new WeakHashMap<String, DruidDataSource>();
	
	private GeneralAttributes generalAttributes;
	private Map<String, TenantDatasourceAttributes> tenantDatasourceAttributesMap;
	
	
	public void setDsProperties(DynamicDatasourceConfigProperties dsProperties) {
		parse(dsProperties);
	}
	
	private void parse(DynamicDatasourceConfigProperties dsProperties2) {
		Map<String, String> generalMap = dsProperties2.getGeneral();
		generalAttributes = JSONObject.parseObject(JSON.toJSONString(generalMap),GeneralAttributes.class);



		Map<String, Map<String, String>> tenants = dsProperties2.getTenants();
		tenantDatasourceAttributesMap = new HashMap<String, TenantDatasourceAttributes>();
		
		for (String orgCode : tenants.keySet()) {
			Map<String, String> tenantDSAttr = tenants.get(orgCode);
			TenantDatasourceAttributes tenantDatasourceAttributes = JSONObject.parseObject(JSON.toJSONString(generalAttributes),TenantDatasourceAttributes.class);
			tenantDatasourceAttributes.setUrl(tenantDSAttr.get("url"));
			tenantDatasourceAttributes.setDriver(tenantDSAttr.get("driver"));
			tenantDatasourceAttributes.setUserName(tenantDSAttr.get("userName"));
			tenantDatasourceAttributes.setPassword(tenantDSAttr.get("password"));
			tenantDatasourceAttributes.setFilters(generalAttributes.getFilters());
			if(tenantDSAttr.containsKey("maxPoolSize")) {
				tenantDatasourceAttributes.setMaxPoolSize(Integer.parseInt(tenantDSAttr.get("maxPoolSize")));
			}
			else {
				tenantDatasourceAttributes.setMaxPoolSize(generalAttributes.getMaxPoolSize());
			}
			if(tenantDSAttr.containsKey("minIdle")) {
				tenantDatasourceAttributes.setMinIdle(Integer.parseInt(tenantDSAttr.get("minIdle")));
			}
			else {
				tenantDatasourceAttributes.setMinIdle(generalAttributes.getMinIdle());
			}
			tenantDatasourceAttributesMap.put(orgCode, tenantDatasourceAttributes);
		}
		
	}

	private static class GeneralAttributes {
		private int maxPoolSize;
		private String defaultTenant;
		private String filters;

		private int initialSize;
		private int minIdle;
		private int maxActive;
		private int maxWait;
		private int timeBetweenEvictionRunsMillis;
		private int minEvictableIdleTimeMillis;
		private int maxEvictableIdleTimeMillis;
		private String validationQuery;
		private boolean testWhileIdle;
		private boolean testOnBorrow;
		private boolean testOnReturn;
		
		public void setFilters(String filters) {
			this.filters = filters;
		}
		public String getFilters() {
			return filters;
		}
		public int getMaxPoolSize() {
			return maxPoolSize;
		}
		public void setMaxPoolSize(int maxPoolSize) {
			this.maxPoolSize = maxPoolSize;
		}
		public int getMinIdle() {
			return minIdle;
		}
		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}
		public String getDefaultTenant() {
			return defaultTenant;
		}
		public void setDefaultTenant(String defaultTenant) {
			this.defaultTenant = defaultTenant;
		}
		public int getInitialSize() {
			return initialSize;
		}
		public void setInitialSize(int initialSize) {
			this.initialSize = initialSize;
		}
		public int getMaxActive() {
			return maxActive;
		}
		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}
		public int getMaxWait() {
			return maxWait;
		}
		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}
		public int getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}
		public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}
		public int getMinEvictableIdleTimeMillis() {
			return minEvictableIdleTimeMillis;
		}
		public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}
		public int getMaxEvictableIdleTimeMillis() {
			return maxEvictableIdleTimeMillis;
		}
		public void setMaxEvictableIdleTimeMillis(int maxEvictableIdleTimeMillis) {
			this.maxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis;
		}
		public String getValidationQuery() {
			return validationQuery;
		}
		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}
		public boolean isTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public boolean isTestOnBorrow() {
			return testOnBorrow;
		}

		public void setTestOnBorrow(boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}

		public boolean isTestOnReturn() {
			return testOnReturn;
		}

		public void setTestOnReturn(boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
		}
	}
	
	private static class TenantDatasourceAttributes {
		private String url;
		private String driver;
		private String userName;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getDriver() {
			return driver;
		}
		public void setDriver(String driver) {
			this.driver = driver;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int getMaxPoolSize() {
			return maxPoolSize;
		}
		public void setMaxPoolSize(int maxPoolSize) {
			this.maxPoolSize = maxPoolSize;
		}
		public int getMinIdle() {
			return minIdle;
		}
		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}
		public void setFilters(String filters) {
			this.filters = filters;
		}
		public String getFilters() {
			return filters;
		}
		private String password;
		private int maxPoolSize;
		private int minIdle;
		private String filters;

		private int initialSize;
		private int maxActive;
		private int maxWait;
		private int timeBetweenEvictionRunsMillis;
		private int minEvictableIdleTimeMillis;
		private int maxEvictableIdleTimeMillis;
		private String validationQuery;
		private boolean testWhileIdle;
		private boolean testOnBorrow;
		private boolean testOnReturn;
		public int getInitialSize() {
			return initialSize;
		}
		public void setInitialSize(int initialSize) {
			this.initialSize = initialSize;
		}
		public int getMaxActive() {
			return maxActive;
		}
		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}
		public int getMaxWait() {
			return maxWait;
		}
		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}
		public int getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}
		public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}
		public int getMinEvictableIdleTimeMillis() {
			return minEvictableIdleTimeMillis;
		}
		public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}
		public int getMaxEvictableIdleTimeMillis() {
			return maxEvictableIdleTimeMillis;
		}
		public void setMaxEvictableIdleTimeMillis(int maxEvictableIdleTimeMillis) {
			this.maxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis;
		}
		public String getValidationQuery() {
			return validationQuery;
		}
		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}
		public boolean isTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public boolean isTestOnBorrow() {
			return testOnBorrow;
		}

		public void setTestOnBorrow(boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}

		public boolean isTestOnReturn() {
			return testOnReturn;
		}

		public void setTestOnReturn(boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		String currentOrgCode = OrgCodeHolder.getOrgCode();
		if(currentOrgCode == null) {
			currentOrgCode = generalAttributes.getDefaultTenant();
		}
		if(!tenantDatasourceAttributesMap.containsKey(currentOrgCode)) {
			throw new SQLException("there is no datasource configuration for the organization with code " + currentOrgCode);
		}
		TenantDatasourceAttributes tenantDatasourceAttributes = tenantDatasourceAttributesMap.get(currentOrgCode);
		DruidDataSource ds = dataSourceMap.get(currentOrgCode);
		//double check
		if(ds == null) {
			synchronized(this) {
				ds = dataSourceMap.get(currentOrgCode);
				if(ds == null) {
//					HikariConfig config = new HikariConfig();
//					config.setDriverClassName(tenantDatasourceAttributes.getDriver());
//					config.setJdbcUrl(tenantDatasourceAttributes.getUrl());
//					config.setUsername(tenantDatasourceAttributes.getUserName());
//					config.setPassword(tenantDatasourceAttributes.getPassword());
//					config.setMaximumPoolSize(tenantDatasourceAttributes.getMaxPoolSize());
//					config.setMinimumIdle(tenantDatasourceAttributes.getMinIdle());
					ds = new DruidDataSource();
					ds.setDriverClassName(tenantDatasourceAttributes.getDriver());
					ds.setUrl(tenantDatasourceAttributes.getUrl());
					ds.setUsername(tenantDatasourceAttributes.getUserName());
					ds.setPassword(tenantDatasourceAttributes.getPassword());
					ds.setMinIdle(tenantDatasourceAttributes.getMinIdle());
					ds.setFilters(tenantDatasourceAttributes.getFilters());
					dataSourceMap.put(currentOrgCode, ds);
				}
			}
		}
		return ds.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}
}
