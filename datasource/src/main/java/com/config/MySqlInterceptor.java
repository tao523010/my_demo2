//package com.config;
//
//import com.utils.MysqlParser;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ParameterMapping;
//import org.apache.ibatis.mapping.SqlSource;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Properties;
//
///**
// * @Author: cjt
// * @Date: 2021/05/17/14:12
// * @Description:
// * 自定义 MyBatis 拦截器
// */
//@Intercepts({@Signature(type = Executor.class, method = "query",
//        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
//public class MySqlInterceptor implements Interceptor {
//    private static final Logger logger= LoggerFactory.getLogger(MySqlInterceptor.class);
//
//    /**
//     * intercept 方法用来对拦截的sql进行具体的操作
//     * @param invocation
//     * @return
//     * @throws Throwable
//     */
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        logger.info("执行intercept方法：{}", invocation.toString());
//
//        Object[] args = invocation.getArgs();
//        MappedStatement ms = (MappedStatement) args[0];
//        Object parameterObject = args[1];
//
//        // id为执行的mapper方法的全路径名，如com.mapper.UserMapper
//        String id = ms.getId();
//
//        // sql语句类型 select、delete、insert、update
//        String sqlCommandType = ms.getSqlCommandType().toString();
//
//        // 仅拦截 select 查询
//        //if (!sqlCommandType.equals(SqlCommandType.SELECT.toString())) {
//        //    return invocation.proceed();
//        //}
//
//        BoundSql boundSql = ms.getBoundSql(parameterObject);
//        String origSql = boundSql.getSql();
//        logger.info("原始SQL: {}", origSql);
//
//        // 组装新的 sql
//        String newSql = MysqlParser.parser(origSql);
//
//        // 重新new一个查询语句对象
//        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), newSql,
//                boundSql.getParameterMappings(), boundSql.getParameterObject());
//
//        // 把新的查询放到statement里
//        MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
//        invocation.getArgs()[0] = newMs;
//
////        MappedStatement newMs = newMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
////        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
////            String prop = mapping.getProperty();
////            if (boundSql.hasAdditionalParameter(prop)) {
////                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
////            }
////        }
////
////        Object[] queryArgs = invocation.getArgs();
////        queryArgs[0] = newMs;
//
//        logger.info("改写的SQL: {}", newSql);
//
//        return invocation.proceed();
//    }
//
//    // 复制原始MappedStatement
//    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
//        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
//                ms.getSqlCommandType());
//        builder.resource(ms.getResource());
//        builder.fetchSize(ms.getFetchSize());
//        builder.statementType(ms.getStatementType());
//        builder.keyGenerator(ms.getKeyGenerator());
//        if (ms.getKeyProperties() != null) {
//            for (String keyProperty : ms.getKeyProperties()) {
//                builder.keyProperty(keyProperty);
//            }
//        }
//        builder.timeout(ms.getTimeout());
//        builder.parameterMap(ms.getParameterMap());
//        builder.resultMaps(ms.getResultMaps());
//        builder.cache(ms.getCache());
//        builder.useCache(ms.isUseCache());
//        return builder.build();
//    }
//
//    /**
//     * 定义一个内部辅助类，作用是包装 SQL
//     */
//    class BoundSqlSqlSource implements SqlSource {
//        private BoundSql boundSql;
//        public BoundSqlSqlSource(BoundSql boundSql) {
//            this.boundSql = boundSql;
//        }
//        @Override
//        public BoundSql getBoundSql(Object parameterObject) {
//            return boundSql;
//        }
//
//    }
//
//    private MappedStatement newMappedStatement (MappedStatement ms, SqlSource newSqlSource) {
//        MappedStatement.Builder builder = new
//                MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
//        builder.resource(ms.getResource());
//        builder.fetchSize(ms.getFetchSize());
//        builder.statementType(ms.getStatementType());
//        builder.keyGenerator(ms.getKeyGenerator());
//        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
//            builder.keyProperty(ms.getKeyProperties()[0]);
//        }
//        builder.timeout(ms.getTimeout());
//        builder.parameterMap(ms.getParameterMap());
//        builder.resultMaps(ms.getResultMaps());
//        builder.resultSetType(ms.getResultSetType());
//        builder.cache(ms.getCache());
//        builder.flushCacheRequired(ms.isFlushCacheRequired());
//        builder.useCache(ms.isUseCache());
//        return builder.build();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        logger.info("plugin方法：{}", target);
//
//        if (target instanceof Executor) {
//            return Plugin.wrap(target, this);
//        }
//        return target;
//
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        // 获取属性
//        // String value1 = properties.getProperty("prop1");
//        logger.info("properties方法：{}", properties.toString());
//    }
//}
