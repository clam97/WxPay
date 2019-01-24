package org.ToolUtil;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信支付公共类
 * @author xjw
 *
 */
public class WeixinPayUtil {
    //商户支付密钥
    public static String API_KEY = "8a8a02be5635a907015635a909380000";
    private static Logger logger = Logger.getLogger(WeixinPayUtil.class);

    /**
     * 发送https请求
     * @param url 请求地址
     * @param method 请求方法
     * @param outputStr 提交数据
     * @return hashmap 微信服务器响应的信息 封装成Map
     * @throws Exception
     */
    public static Map<String, String> httpsRequest(String url, String method, String outputStr) throws Exception {
        logger.info("进入开始发请求---------------------------");
        // 商户号
        String mch_id = PropertiesUtil.getStringByKey("mch_id", "weixin.properties");
        logger.info("mch_id------------------------------------"+mch_id);
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        logger.info("keyStore------------------------------"+keyStore);
        FileInputStream instream = new FileInputStream(new File("/home/fyc/apiclient_cert.p12"));// 微信商户平台 路径

        logger.info("这文件我们真的读了**********"+instream);
        try {
            keyStore.load(instream, mch_id.toCharArray());
            logger.info("已读keystore============");
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mch_id.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        try {

            HttpPost httpPost = new HttpPost(url);

            StringEntity reqEntity = new StringEntity(outputStr, "utf-8");

            // 设置类型
            reqEntity.setContentType("application/x-www-form-urlencoded");

            httpPost.setEntity(reqEntity);

            logger.info("executing request" + httpPost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpPost);

            logger.info("请求已发到wx服务器------------");
            try {
                HttpEntity entity = response.getEntity();
                logger.info("获取entity-------------------");
                if (entity != null) {
                    // 从request中取得输入流
                    InputStream inputStream = entity.getContent();
                    // 读取输入流
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(inputStream);
                    // 得到xml根元素
                    Element root = document.getRootElement();
                    // 得到根元素的所有子节点
                    List<Element> elementList = root.elements();

                    logger.info("微信支付结果：");
                    // 遍历所有子节点
                    for (Element e : elementList){
                        map.put(e.getName(), e.getText());
                        logger.info(e.getName() + ":" + e.getText());
                    }

                    // 释放资源
                    inputStream.close();
                    inputStream = null;

                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

        return map;
    }

    /**
     * 微信支付通用通知接口，商户处理后同步返回给微信参数
     * @param return_code
     * @param return_msg
     * @return
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }
}
