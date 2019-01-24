package org.ToolUtil;


import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_Operate {
    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String openid = null;
    private static Logger logger = Logger.getLogger(JDBC_Operate.class);
    //从数据库查询openid

    public String SelectOpenid(String userid) {
        try {
            conn = JdbcUtils_DBCP.getConnection();
            if (conn==null){
               return "==============================exit====================================";
            }
            String sql = "SELECT openid FROM user WHERE  id= "+userid;
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println("查询openid的sql语句为---------------"+sql);
            logger.info("查询openid的sql语句为---------------"+sql);
            while (resultSet.next()){
                 openid = resultSet.getString("openid");
            }
            conn.close();
            statement.close();
            System.out.println("openid----------->"+openid);
            logger.info("openid----------->"+openid);
        } catch (SQLException e) {
            logger.error("sql ------------>"+e.getMessage());
            e.printStackTrace();
        }
         return openid;
    }
}