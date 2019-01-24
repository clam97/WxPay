package org.WXpay;

import org.ToolUtil.DealXml;
import org.ToolUtil.JDBC_feedback;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class PaySuccess {//支付成功后的回调类

private  static Logger logger = Logger.getLogger(PaySuccess.class);
    public void paySuccess(String strXML){
        Map<String, String> requestMap = new HashMap<String, String>();
        try {
           requestMap=DealXml.xmlToMap(strXML);
        } catch (Exception e) {
            logger.info("支付成功解析微信请求异常");
            e.printStackTrace();
        }

        String openId = null;
        if (requestMap.get("result_code").equalsIgnoreCase("SUCCESS")){
           //微信支付订单号
            String transaction_id = requestMap.get("transaction_id");

            //商户订单号
            String  out_trade_no = requestMap.get("out_trade_no");
            logger.info("微信支付订单号：" + transaction_id);
            logger.info("商户订单号：" + out_trade_no);

            openId = requestMap.get("openid");	// 微信返回openId
            String fullValueMoneyString = requestMap.get("total_fee"); // 分
            int fullValueMoney = Integer.parseInt(fullValueMoneyString) / 100; // 充值金额
            logger.info("微信返回openId：" + openId);
            logger.info("缴费金额：" + fullValueMoney);

            //用openid 查数据库里的 userid
            JDBC_feedback jdbc_feedback = new JDBC_feedback();
            int userid = jdbc_feedback.Selectuserid(openId,fullValueMoney);
            //将user表的钱更新
            jdbc_feedback.UpdateAccount(openId);
            //把customerjilu表更新
            jdbc_feedback.UpdateCustomer(userid);





        }


    }
}
