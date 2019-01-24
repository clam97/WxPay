package org.ToolUtil;


/**
 * 微信统一支付消息实体�?
 * @date 2015-02-27
 * @author xjw
 *
 */
public class UnifiedOrderPo {

    private String appid;

    private String mch_id;

    private String body;

    private String nonce_str;

    private String notify_url;

    private String out_trade_no;

    private String total_fee;

    private String spbill_create_ip;

    private String trade_type;

    private String openid;

    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mchId) {
        mch_id = mchId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonceStr) {
        nonce_str = nonceStr;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notifyUrl) {
        notify_url = notifyUrl;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String outTradeNo) {
        out_trade_no = outTradeNo;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String totalFee) {
        total_fee = totalFee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbillCreateIp) {
        spbill_create_ip = spbillCreateIp;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String tradeType) {
        trade_type = tradeType;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }



}
