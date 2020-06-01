package com.genecrusher.utils;

public class SqlUtil {

    public static final String TABLE_NAME = "USERS";
    public static final String CREATE_USERS_TABLE_STATEMENT = "create table " + TABLE_NAME + "(login_name varchar2(100), password varchar2(200)," +
            "email varchar2(100), roles varchar2(240), created_by varchar2(100))";

    public static String constructInsertUserStatement(String loginName, String password, String email, String roles, String createdBy) {
        return "INSERT INTO " + TABLE_NAME + "(login_name,password,email,roles,created_by) " +
                "VALUES (" +
                "'" + loginName + "'," +
                "'" + password + "'," +
                "'" + email + "'," +
                "'" + roles + "'," +
                "'" + createdBy + "'"
                + ")";
    }

    public static String constructFetchUserStatement(String loginName) {
        return "SELECT * FROM " + TABLE_NAME + " where login_name= " +
                "'" + loginName + "'";
    }
}
