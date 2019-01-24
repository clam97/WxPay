package org.WXpay;


import net.sf.json.JSONObject;
import org.ToolUtil.*;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class WxPayLogin {
    private UUIDHexGenerator uuidGenerator = UUIDHexGenerator.getInstance(); // 获取随机生成主键Id
    private static Logger logger = Logger.getLogger(WxPayLogin.class);
    private String result;// ajax回调的数据

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void wxpayLogin(String userid, String total_fees, String appId) {
        JDBC_Operate jdbc_operate = new JDBC_Operate();
        String openid = jdbc_operate.SelectOpenid(userid);


        String user_agent = "user-agent";
        if (openid != null) {
            String total_fee = total_fees;
            CreateUnifiedorderParams cParams = new CreateUnifiedorderParams();
            logger.info("进入支付接口---------------------------------------");
            try {
                int i_total_fee = (int) (Float.parseFloat(total_fee) * 100);
                if (i_total_fee > 0) {
                    String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                    String method = "POST";

                    String nonceStr = uuidGenerator.generate();
                    logger.info("nonceStr*****************************" + nonceStr);

//						String outputStr = createUnifiedorderParams(openid, orderID, userid, i_total_fee);
                    String outputStr = cParams.createUnifiedorderParams(openid, i_total_fee, nonceStr);

                    logger.info("xml----------" + outputStr);

                    Map<String, String> resultMap = null;
                    try {
                        resultMap = WeixinPayUtil //微信证书路径
                                .httpsRequest(url, method, outputStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    logger.info("请求已发出//////////////////----------");
                    // 预支付ID
                    String prepay_id = resultMap.get("prepay_id");

                    logger.info("prepay_id:--------------- " + prepay_id);

                    // API密钥
                    String key = PropertiesUtil.getStringByKey("key", "weixin.properties");

                    // 时间戳
                    String timeStamp = Long.toString(new Date().getTime());
                    // 获取uuid作为随机字符串
//						String nonceStr = uuidGenerator.generate();

                    // 构造请求参数

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appId", appId); // 公众号id
                    params.put("timeStamp", timeStamp); // 时间戳
                    params.put("nonceStr", nonceStr); // 随机字符串
                    params.put("package", "prepay_id=" + prepay_id); // 订单详情扩展字段
                    params.put("signType", "MD5"); // 签名方式

                    // 除去数组中的空值和签名参数
                    Map<String, String> sPara = AlipayCore
                            .paraFilter(params);
                    String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
                    String paySign = MD5.sign(prestr,
                            "&key=" + key,
                            AlipayConfig.input_charset).toUpperCase();
                    logger.info("二次签名paySign-----------------+++++++++" + paySign);
                    params.put("paySign", paySign); // 签名
                   // String userAgent = request.getHeader("user-agent");//从微信服务器获取道德参数
                    String userAgent = user_agent;//从微信服务器获取道德参数
                    char agent = userAgent.charAt(userAgent
                            .indexOf("MicroMessenger") + 15);
                    params.put("agent", new String(new char[]{agent}));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。

                    // ajax回调的数据 result
                    JSONObject jsonObj = JSONObject.fromObject(params);
                    logger.info("支付接口调用结束----------------");
                    result = jsonObj.toString();
                } else {
                    logger.info("缴费金额 不大于0");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("moneyError", "true");
                    // ajax回调的数据 result
                    JSONObject jsonObj = JSONObject.fromObject(params);
                    result = jsonObj.toString();
                }
            } catch (NumberFormatException e) {
                logger.info("String类型缴费金额解析Double类型出现异常------------String类型缴费金额："
                        + total_fee);
                logger.error("异常-----------" + e.getMessage());
                Map<String, String> params = new HashMap<String, String>();
                params.put("numFormatException", "true");
                // ajax回调的数据 result
                JSONObject jsonObj = JSONObject.fromObject(params);
                result = jsonObj.toString();
                logger.info("result-------------->" + result);
            }
 //          response.setHeader("Access-Control-Allow-Origin","*");
//             response.addHeader("Access-Control-Allow-Method","POST,GET");
//             response.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
//
//             response.setCharacterEncoding("utf-8");
//             response.setContentType("text/json;charset=utf-8");
////				response.addHeader("Access-Control-Allow-Origin","*");
//
//             response.getWriter().print(result);
//             logger.info("参数已返回到前端===="+result);
//             return null;
        } else {
//             response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=http%3A%2F%2F" + domain + "%2Fwxpay%2Fconfig%2FuserPaymentAgain.action%3Fshowwxpaytitle%3D1%26openID%3D"
//                     + openid + "%26orderID%3D"
////						+ orderID + "%26customerID%3D"
////						+ customerID
//                     + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
//             return null;
        }
    }
}




