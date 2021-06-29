package com.utils;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;

import java.util.*;

/**
 * @Author: cjt
 * @Date: 2021/05/17/14:28
 * @Description:
 */
public class MysqlParser {

    public static final String QUERY = "查询";
    public static final String CREATR = "创建";
    public static final String UPDATE = "修改";
    public static final String DELETE = "删除";

    private static String globalName = "project_id";

    public static String parser(String sql) {
        try {
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            SQLStatement stmt = parser.parseStatement();
            if (stmt instanceof SQLSelectStatement) {
                return select((SQLSelectStatement) stmt);
            }
            if (stmt instanceof SQLUpdateStatement) {
                return update((SQLUpdateStatement) stmt);
            }
            if (stmt instanceof SQLDeleteStatement) {
                return delete((SQLDeleteStatement) stmt);
            }
        }catch (Exception e) {

        }

        return sql;
    }

    private static String update(SQLUpdateStatement stmt) throws Exception {
        parseUpdateBlock(stmt);
        return stmt.toString();
    }

    private static String delete(SQLDeleteStatement stmt) throws Exception {
        parseDeleteBlock(stmt);
        return stmt.toString();
    }

    private static String select(SQLSelectStatement stmt) throws Exception {
        SQLSelectQueryBlock select = (SQLSelectQueryBlock) stmt.getSelect().getQuery();
        parseQueryBlock(select);
        return stmt.toString();
    }

    private static void parseUpdateBlock(SQLUpdateStatement updateBlcok) throws Exception {
        Map<String, String> tables = new HashMap<String, String>();
        SQLTableSource tableSource = updateBlcok.getTableSource();
        parseTables(tableSource, tables);
        addCondition(updateBlcok, tables);
    }

    private static void addCondition(SQLUpdateStatement updateBlcok, Map<String, String> tables)
            throws Exception {
        Set<Map.Entry<String, String>> sets = tables.entrySet();
        for (Map.Entry<String, String> entry : sets) {
            SQLExpr expr = addCondition(entry.getValue(), entry.getKey(), UPDATE);
            if (expr != null) {
                updateBlcok.addCondition(expr);
            }
        }
    }

    private static void parseDeleteBlock(SQLDeleteStatement deleteBlcok) throws Exception {
        Map<String, String> tables = new HashMap<String, String>();
        SQLTableSource tableSource = deleteBlcok.getTableSource();
        parseTables(tableSource, tables);
        addCondition(deleteBlcok, tables);
    }

    private static void addCondition(SQLDeleteStatement deleteBlcok, Map<String, String> tables)
            throws Exception {
        Set<Map.Entry<String, String>> sets = tables.entrySet();
        for (Map.Entry<String, String> entry : sets) {
            SQLExpr expr = addCondition(entry.getValue(), entry.getKey(), DELETE);
            if (expr != null) {
                deleteBlcok.addCondition(expr);
            }
        }
    }

    private static void parseQueryBlock(SQLSelectQueryBlock queryBlock) throws Exception {
        Map<String, String> tables = new HashMap<String, String>();
        SQLTableSource tableSource = queryBlock.getFrom();
        parseTables(tableSource, tables);
        addCondition(queryBlock, tables);
    }

    private static void addCondition(SQLSelectQueryBlock queryBlock, Map<String, String> tables)
            throws Exception {
        Set<Map.Entry<String, String>> sets = tables.entrySet();
        for (Map.Entry<String, String> entry : sets) {
            SQLExpr expr = addCondition(entry.getValue(), entry.getKey(), QUERY);
            if (expr != null) {
                queryBlock.addCondition(expr);
            }
        }
    }

    private static SQLExpr addCondition(String table, String alias, String operation) throws Exception {

        List<String> availCodes = new ArrayList<>(); //用户可访问项目列表
        availCodes.add("11");
        availCodes.add("22");

        if (availCodes == null) {
            throw new RuntimeException("没有对表[" + table + "]的" + operation + "权限");
        }
        List<SQLExpr> orExprs = new ArrayList<SQLExpr>();
        SQLExpr sQLExpr = null;

        for (String code : availCodes) {
            orExprs.add(new SQLBinaryOpExpr(new SQLPropertyExpr(new SQLIdentifierExpr(alias), globalName),
                    SQLBinaryOperator.Equality, new SQLCharExpr(code)));

        }
        sQLExpr = SQLBinaryOpExpr.combine(orExprs,SQLBinaryOperator.BooleanOr);
        return sQLExpr;
    }

    private static void parseTables(SQLTableSource tableSource, Map<String, String> tables) {
//		if (tableSource instanceof SQLExprTableSource) {
//			SQLIdentifierExpr expr = (SQLIdentifierExpr) ((SQLExprTableSource) tableSource).getExpr();
//			String alias = tableSource.getAlias() == null ? expr.getName() : tableSource.getAlias();
//			tables.put(alias, expr.getName());
//			return;
//		}
        if (tableSource instanceof SQLExprTableSource) {
            SQLExpr sqlExpr = ((SQLExprTableSource) tableSource).getExpr();
            SQLIdentifierExpr expr = null;
            if (sqlExpr instanceof SQLPropertyExpr){
                expr = (SQLIdentifierExpr) ((SQLPropertyExpr) sqlExpr).getOwner();
            }else {
                expr = (SQLIdentifierExpr)sqlExpr;
            }
            String alias = tableSource.getAlias() == null ? expr.getName() : tableSource.getAlias();
            tables.put(alias, expr.getName());
            return;
        }
        if (tableSource instanceof SQLJoinTableSource) {
            tableSource = (SQLJoinTableSource) tableSource;
            parseTables(((SQLJoinTableSource) tableSource).getLeft(), tables);
            parseTables(((SQLJoinTableSource) tableSource).getRight(), tables);
            return;
        }
    }

//    public static void main(String[] args) throws Exception {
//        String sql = "delete from org_code_defined a set a.`status` = 1 where a.id = 1";
//        System.out.println(MysqlParser.parser(sql));
//    }
}
