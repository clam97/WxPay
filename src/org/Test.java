package org;

import org.ToolUtil.JDBC_Operate;
import org.ToolUtil.JDBC_feedback;
import org.ToolUtil.JdbcUtils_DBCP;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
        try {
            JdbcUtils_DBCP.getConnection();
            JDBC_Operate jdbc_operate = new JDBC_Operate();
            jdbc_operate.SelectOpenid("793837838");

            JDBC_feedback jdbc_feedback = new JDBC_feedback();
            jdbc_feedback.Selectuserid("oOPCV0-uM5HkpetprZwXpuFK4DVo",100);
            jdbc_feedback.UpdateAccount("oOPCV0-uM5HkpetprZwXpuFK4DVo");
            jdbc_feedback.UpdateCustomer(793837838);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
