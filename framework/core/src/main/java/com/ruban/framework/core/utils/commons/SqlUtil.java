package com.ruban.framework.core.utils.commons;

/**
 * 该类用于清理查询条件中包含的特殊的SQL字符
 * 
 * @author 王一进
 * 
 */
public class SqlUtil {

    /**
     * 
     * 描 述：转义SQL字符
     * 
     * @author 王一进
     * @param conditions
     * @return
     */
    public static String escapeSql(String conditions) {

        if (conditions != null) {
            // 将单引号变为双引号''
            conditions = conditions.replaceAll("'", "''");
            return conditions;
        } else {
            return "";
        }
    }

    /**
     * 
     * 描 述：转义SQL字符
     * 
     * @author 王一进
     * @param conditions
     * @return
     */
    public static String escapeLikeSql(String conditions) {

        if (conditions != null) {
            // 将百分号转义
            conditions = conditions.replaceAll("/", "//");
            conditions = conditions.replaceAll("%", "/%");
            conditions = escapeSql(conditions);

            return conditions;
        } else {
            return "";
        }
    }

    /**
     * 用于生成LIKE语句
     * 
     * @param value
     * @return
     */
    public static String generateLikeSql(String columnName, String value) {
        return " " + columnName + " like'%" + escapeLikeSql(value) + "%' escape '/' ";
    }

    /**
     * 用于生成LIKE语句
     * 
     * @param value
     * @return
     */
    public static String generateRightLikeSql(String columnName, String value) {
        return " " + columnName + " like '" + escapeLikeSql(value) + "%' escape '/' ";
    }

    /**
     * 用于生成=''语句
     * 
     * @param columnName
     * @return
     */
    public static String generateEqualSql(String columnName, String value) {
        return " " + columnName + " ='" + escapeSql(value) + "'";
    }

    /**
     * 用于生成!=语句
     * 
     * @param columnName
     * @param value
     * @return
     */
    public static String generateNotEqualSql(String columnName, String value) {
        return " " + columnName + " !='" + escapeSql(value) + "'";
    }

    public static String generateContainSql(String value) {
        return "'" + escapeSql(value) + "'";
    }

    /**
     * 用于生成>=语句
     * 
     * @param columnName
     * @param value
     * @return
     */
    public static String generateGreaterSql(String columnName, String value) {
        return " " + columnName + ">='" + escapeSql(value) + "' ";
    }

    /**
     * 用于生成>语句
     * 
     * @param columnName
     * @param value
     * @return
     */
    public static String generateGreaterNotEqualSql(String columnName, String value) {
        return " " + columnName + ">'" + escapeSql(value) + "' ";
    }

    /**
     * 用于生成<=语句
     * 
     * @param columnName
     * @param value
     * @return
     */
    public static String generateLittlerSql(String columnName, String value) {
        return " " + columnName + "<='" + escapeSql(value) + "' ";
    }

    /**
     * 用于生成<语句
     * 
     * @param columnName
     * @param value
     * @return
     */
    public static String generateLittlerNotEqualSql(String columnName, String value) {
        return " " + columnName + "<'" + escapeSql(value) + "' ";
    }

    /**
     * 生成TO_CHAR类型的SQL
     * 
     * @param columnName
     * @return
     */
    public static String generateDateToCharSql(String columnName) {
        return " TO_CHAR(" + columnName + ",'yyyy-MM-dd HH24:mi:ss')";
    }
    
    public  static String inSql(int... inValues){
    	StringBuilder inSql = new StringBuilder();
    	
    	inSql.append("(");
    	if(inValues!=null){
    		for(int i=0;i<inValues.length;i++){
    			inSql.append(inValues[i]);
    			if(i<inValues.length-1){
    				inSql.append(",");
    			}
    		}
    	}
    	inSql.append(")");
    	
    	return inSql.toString();
    }

}
