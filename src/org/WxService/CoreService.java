package org.WxService;


import org.ToolUtil.DealXml;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;

public class CoreService {//订阅和自动回复处理
    private  static Logger logger = Logger.getLogger(CoreService.class);


    private static String requestMessage(String xmltest) {
         Map<String, String> requestMap;
         String respXml=null;
        try {
            requestMap = DealXml.xmlToMap(xmltest);
            // 发送方帐号
            String fromUserName = requestMap.get("fromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("toUserName");
            // 消息类型
            String msgType = requestMap.get("msgType");
            // 消息内容
            //String content = requestMap.get("Content");

            BaseMessage baseMessage = new BaseMessage();

            baseMessage.setFromUserName(fromUserName);
            baseMessage.setToUserName(toUserName);
            baseMessage.setMsgType(BaseMessage.RESP_MESSAGE_TYPE_TEXT);
            baseMessage.setCreateTime(new Date().getTime());
            System.out.println("------------------1------------------fromUserName------------"+fromUserName);
            System.out.println("------------------2------------------toUserName--------------"+toUserName);
            System.out.println("------------------3------------------msgType-----------------"+msgType);
            // 事件推送
            if (msgType.equals(BaseMessage.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                if (eventType.equals(BaseMessage.EVENT_TYPE_SUBSCRIBE)) {
                    // 事件KEY值，qrscene_为前缀，后面为二维码的参数值

                    baseMessage.setContent("嗨~ 我们是讯飞开放平台孵化企业\n专注合同AI审查\n支持合同文本以及合同图片在线审查。上传合同，选择合同角色、即可获得合同相应风险和专业建议。");
                    // 将消息对象转换成xml
                    logger.info("实现订阅关注就统一回复");
                    System.out.println("实现订阅关注就统一回复");
                    respXml = DealXml.messageToXml(baseMessage);
                }


            } else {
                baseMessage.setContent("嗨~ 我们是讯飞开放平台孵化企业\n专注合同AI审查\n支持合同文本以及合同图片在线审查。上传合同，选择合同角色、即可获得合同相应风险和专业建议。");
                logger.info("自动回复");
                System.out.println("自动回复");
                respXml = DealXml.messageToXml(baseMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

    public static void main(String[] args) {
        String xmltest="<xml>\n" +
                "  <fromUserName><![CDATA[wx2421b1c4370ec43b]]></fromUserName>\n" +
                "  <toUserName><![CDATA[支付测试]]></toUserName>\n" +
                "  <msgType><![CDATA[CFT]]></msgType>\n" +
                "  <event><![CDATA[subscribe]]></event>\n" +
                "</xml>";
        CoreService.requestMessage(xmltest);
    }
}
