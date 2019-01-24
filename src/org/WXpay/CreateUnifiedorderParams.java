package org.WXpay;

import org.ToolUtil.*;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreateUnifiedorderParams {//创建统一支付接口参数
    private static Logger logger = Logger.getLogger(CreateUnifiedorderParams.class);

    public String createUnifiedorderParams(String openid, int total_fee, String nonceStr) {
        // 微信版本域名
        String domain = PropertiesUtil.getStringByKey("domain", "weixin.properties");
        logger.info("domain*****************************"+domain);
        // 微信支付二级目录
        // 公众号id
        String appid = PropertiesUtil.getStringByKey("appID", "weixin.properties");
        // 商户号
        logger.info("appid*****************************"+appid);
        String mch_id = PropertiesUtil.getStringByKey("mch_id", "weixin.properties");
        logger.info("mch_id*****************************"+mch_id);
        // API密钥
        String key = PropertiesUtil.getStringByKey("key", "weixin.properties");
        logger.info("key*****************************"+key);
        // spbill_create_ip
        String spbill_create_ip = PropertiesUtil.getStringByKey("spbill_create_ip", "weixin.properties");
        logger.info("spbill_create_ip*****************************"+spbill_create_ip);
        String body = "法眼察-充值";

        // 获取uuid作为随机字符串
//		String nonceStr = uuidGenerator.generate();
//		logger.info("nonceStr*****************************"+nonceStr);
        String notify_url = "http://fayancha1.daodaolvfa.com/fayancha/paySuccess.action";
        logger.info("进入createUnifiedorderParams 这个方法----------");
        logger.info("notify_url：" + notify_url);

        String format = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String code = RandomUtil.createCode(6);
        // 商户缴费订单号
        String out_trade_no = format + code;

        String s_total_fee = String.valueOf(total_fee);

        Map<String, String> params = new HashMap<String, String>();


        params.put("appid", appid);
        params.put("body", body);
        params.put("mch_id", mch_id);
        params.put("nonce_str", nonceStr);
        params.put("notify_url", notify_url);
        params.put("out_trade_no", out_trade_no);
        params.put("openid", openid);
        params.put("spbill_create_ip", spbill_create_ip);
        params.put("total_fee", s_total_fee);
        params.put("trade_type", "JSAPI");
        //params.put("sign",unifiedOrderPo.getSign());


        // 除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(params);
        String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        logger.info("请求的字符串--------->"+prestr);
        String sign = MD5.sign(prestr, "&key=" + key,
                AlipayConfig.input_charset).toUpperCase();

        logger.info("sign------------------"+sign);

        // 构造微信统一支付消息对象
        UnifiedOrderPo unifiedOrderPo = new UnifiedOrderPo();
        unifiedOrderPo.setAppid(appid);
        unifiedOrderPo.setMch_id(mch_id);
        unifiedOrderPo.setBody(body);
        unifiedOrderPo.setNonce_str(nonceStr);
        unifiedOrderPo.setNotify_url(notify_url);
        unifiedOrderPo.setOut_trade_no(out_trade_no);
        unifiedOrderPo.setTotal_fee(s_total_fee);
        unifiedOrderPo.setSpbill_create_ip(spbill_create_ip);
        unifiedOrderPo.setTrade_type("JSAPI");
        unifiedOrderPo.setOpenid(openid);
        unifiedOrderPo.setSign(sign);

        Map<String,String> data = new HashMap<>();
        data.put(appid,unifiedOrderPo.getAppid());
        data.put(mch_id,unifiedOrderPo.getMch_id());
        data.put(body,unifiedOrderPo.getBody());
        data.put(nonceStr,unifiedOrderPo.getNonce_str());
        data.put(notify_url,unifiedOrderPo.getNotify_url());
        data.put(out_trade_no,unifiedOrderPo.getOut_trade_no());
        data.put(s_total_fee,unifiedOrderPo.getTotal_fee());
        data.put(spbill_create_ip,unifiedOrderPo.getSpbill_create_ip());
        data.put("JSAPI","JSAPI");
        data.put(openid,unifiedOrderPo.getOpenid());
        data.put(sign,unifiedOrderPo.getSign());


        // 转换成xml形式
        String reqXml = null;
        try {
            reqXml = DealXml.mapToXml(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqXml = reqXml.replace("__", "_");

        return reqXml;
    }

}
