package me.yoyojyv.springmybatis.codegen.gen

/**
 * Created by yoyojyv on 6/19/15.
 */
class MariadbColumnConverter {

    def static columnTypeToJavaType(type) {
        def result
        if (type.indexOf("bigint") > -1 || type.indexOf("bigint") > -1) {
            result = "Long"
        } else if (type.indexOf("int") > -1 || type.indexOf("decimal") > -1) {
            result = "Integer"
        } else {
            result = "String"
        }
        return result
    }

//    def static javaTypeToColumnType(type) {
//        if (type.indexOf("bigint") > -1 || type.indexOf("bigint") > -1) {
//        } else if (type.indexOf("int") > -1 || type.indexOf("decimal") > -1) {
//        } else {
//        }
//    }



    // old
//    public static String getFirstUpper(String columnName) {
//        String result = "";
//        String upperStr = columnName.substring(0, 1).toUpperCase();
//        result = upperStr + columnName.substring(1);
//        return result;
//    }
//
//    public static String getFirstLower(String columnName) {
//        String result = "";
//        String lowerStr = columnName.substring(0, 1).toLowerCase();
//        result = lowerStr + columnName.substring(1);
//        return result;
//    }
//
//    public static String getPackagePostString(String dbName) {
//        String result = Property.getProperty(DB_PROPERTY_PREFIX + dbName);
//        if (StringUtil.isNotEmpty(result)) {
//            return result;
//        }
//        return dbName.toLowerCase();
//    }
//
//    public static String getExtendsDaoImplClassMiddleString(String dbName) {
//        String result = Property.getProperty(DAO_IMPL_PROPERTY_PREFIX + dbName);
//        if (StringUtil.isNotEmpty(result)) {
//            return result;
//        }
//        return dbName.toLowerCase();
//    }

}
