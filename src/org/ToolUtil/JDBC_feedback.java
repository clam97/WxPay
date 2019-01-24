package org.ToolUtil;

import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JDBC_feedback {

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String openid = null;
    double  insertaccount;
    int account;
    public static Logger logger = Logger.getLogger(JDBC_feedback.class);

    public int Selectuserid(String openId,int fullValueMoney){//用openid 查出userid（用户ID） 和 account（账户余额）
        int userid=0;
        Double yuanaccount=0.0;
        String sql = "SELECT id,account FROM user WHERE  openid= '"+openId+"'";
        try {

            conn = JdbcUtils_DBCP.getConnection();

            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println("查询userid和account的sql语句为---------------"+sql);
            logger.info("查询userid和account的sql语句为---------------"+sql);

            while (resultSet.next()){
                userid = resultSet.getInt("id");
                yuanaccount = resultSet.getDouble("account");

            }
            System.out.println("userid----------->"+userid);
            logger.info("userid----------->"+userid);
            System.out.println("查询出用户原来的金额为："+yuanaccount);
            logger.info("查询出用户原来的金额为："+yuanaccount);
            account = fullValueMoney;
            logger.info("充值金额 -------------------------"+account);
            insertaccount=account+yuanaccount;
            System.out.println("插入的金额-----------"+insertaccount);
            logger.info("插入的金额-----------"+insertaccount);


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        logger.info("查询openid的sql语句为---------------"+sql);
         return userid;

    }

    public void UpdateAccount(String openId){


        try {
            String sql = "update user set account = "+insertaccount+" where openid = '"+openId+"'";
            conn = JdbcUtils_DBCP.getConnection();
            statement = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println("重置金额语句为"+sql);
            logger.info("重置金额语句为"+sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void UpdateCustomer(int userid){
        try {
            conn = JdbcUtils_DBCP.getConnection();
            PreparedStatement pstmt = null;
            String sql = "insert into customerjilu (id,useaccount,time,type,userid) values (?,?,?,?,?)";
            long timeStamp=System.currentTimeMillis();
            SimpleDateFormat sdf=new SimpleDateFormat("MMDDhhMMSS");
            String sd=sdf.format(new Date(timeStamp));
            String id= userid+sd;


            //生成时间
            long timestamp=System.currentTimeMillis();
            SimpleDateFormat sdftime=new SimpleDateFormat("MM-DD hh:MM:SS");
            String time = sdf.format(new Date(timestamp));
            pstmt=(PreparedStatement)conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setDouble(2, account);
            pstmt.setString(3, time);
            pstmt.setString(4, "充值");
            pstmt.setInt(5, userid);
            pstmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
