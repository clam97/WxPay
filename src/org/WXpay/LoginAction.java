package org.WXpay;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import org.ToolUtil.JdbcUtils_DBCP;
import org.ToolUtil.PropertiesUtil;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class LoginAction extends ChannelInboundHandlerAdapter {//微信支付接口

    private Logger logger = Logger.getLogger(String.valueOf(LoginAction.this));


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHR = (FullHttpRequest) msg;
        Charset charset = Charset.forName("UTF-8");
        String url = fullHR.uri();

    }



    public String paywx(){//接收前端的 userid,money

    logger.info("欢迎进入LoginAction处理类");
    //从配置文件读取需要的量 商户ID appid
    String appId = PropertiesUtil.getStringByKey("appID","weixin.properties") ;
    // 微信版本域名
    String domain = PropertiesUtil.getStringByKey("domain","weixin.properties");
    //前端接收到的userid
    String userid="";
    //前端接收到的前数
    String total_fees="";

    //启动连接池
    new JdbcUtils_DBCP();

    WxPayLogin wxPayLogin = new WxPayLogin();
    wxPayLogin.wxpayLogin(userid,total_fees,appId);
    System.out.println("完成第一步下订单--------");




        return "";
    }

}
