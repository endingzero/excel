package com.zjw.excel.jdbc;

import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @ClassName: QueryCompare
 * @Description: 查询比较
 * @author: jiewei
 * @date: 2019/11/5
 */
public class QueryCompare {

    private static Logger log = LoggerFactory.getLogger(QueryCompare.class);

    /**
     * 普通查询
     * @throws SQLException
     */
    public static void selectNormal() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.245:3306/test", "erp", "123456");
        PreparedStatement statement = connection.prepareStatement("select * from app_account where activated = 1",ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            log.info(resultSet.getString("id"));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void selectStream() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.245:3306/test", "erp", "123456");
        PreparedStatement statement = connection.prepareStatement("select * from app_account where activated = 1",ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        statement.setFetchSize(Integer.MIN_VALUE);

        long begin = System.currentTimeMillis();
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            log.info(resultSet.getString("id"));
        }
        long end = System.currentTimeMillis();
        log.info("selectStream span time="+(end-begin) + "ms");

        resultSet.close();
        statement.close();
        connection.close();
    }

    /**
     * 游标查询
     * @throws SQLException
     */
    public static void selectStreamWithUseCursorFetch() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.245:3306/test?useCursorFetch=true", "erp", "123456");
        PreparedStatement statement = connection.prepareStatement("select * from app_account where activated = 1");
        statement.setFetchSize(10);

        long begin = System.currentTimeMillis();
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            log.info(resultSet.getString("id"));
        }

        long end = System.currentTimeMillis();
        log.info("selectStreamWithUseCursorFetch span time="+(end-begin) + "ms");
        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
//        selectNormal();
//        selectStream();
        selectStreamWithUseCursorFetch();
    }
}
