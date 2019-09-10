package com.example.demo.base;

import com.example.demo.config.MySQLDataSourceConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.MessageFormat;
import java.util.List;

@Component
public class SQLHelper {

    private final HikariDataSource mysqlDataSource;

    public SQLHelper(MySQLDataSourceConfig mysqlDataSourceConfig) {
        mysqlDataSource = new HikariDataSource(mysqlDataSourceConfig);
    }

    public int add(String sql, List<Object> params) {

        int result = -1;

        HikariDataSource dataSource = mysqlDataSource;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
            }
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet != null) {
                if (resultSet.next())
                    result = resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            colseConn(connection);
        }
        return result;
    }

    public int update(String sql, List<Object> params) {
        int result = -1;

        HikariDataSource dataSource = mysqlDataSource;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
            }

            result = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            colseConn(connection);
        }

        return result;
    }

    public <T> List<T> query(String sql, List<Object> params, Class<T> type) {

        HikariDataSource dataSource = mysqlDataSource;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
            }

            ResultSet resultSet = statement.executeQuery();
            List<T> list = ResultSetHelper.toList(resultSet, type);

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            colseConn(connection);
        }

        return null;
    }

    /*
     * 将上面的查询语句 和 参数 抽取出来
     */
    public String executeScalar(String sql, List<Object> params) {
        String result = null;
        HikariDataSource dataSource = mysqlDataSource;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }
            }

            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next())
                result = resultSet.getString(1);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            colseConn(connection);
        }

        return result;
    }

    public <T> PageResult<T> queryPage(String sql, List<Object> params, int currentPage, int pageSize,
                                       Class<T> type) {

        PageResult<T> result = new PageResult<T>();

        int startIndex = (currentPage - 1) * pageSize;

        String querySql = String.format("%s limit %s,%s", sql, startIndex, pageSize);

        List<T> models = query(querySql, params, type);
        result.setData(models);

        // 正则，将select 和 from 中间的字符串替换成 count(0);
        String countSql = MessageFormat.format("select count(0) from ({0})t ", sql);
        String countStr = executeScalar(countSql, params);
        int countVal = 0;
        if (!isBlank(countStr))
            countVal = Integer.parseInt(countStr);

        int totalPage = (int) ((countVal + pageSize - 1) / pageSize);

        result.setTotalCount(countVal);
        result.setTotalPage(totalPage);

        return result;
    }

    private boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    private void colseConn(Connection connection){
        if (connection != null) {
            try {
                if(!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                System.out.println("连接关闭失败");
                e.printStackTrace();
            }
        }
    }
}
