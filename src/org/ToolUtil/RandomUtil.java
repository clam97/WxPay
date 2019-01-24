package org.ToolUtil;

public class RandomUtil {
    public static String createCode(int codeLength) {
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            code += (int) (Math.random() * 10);
        }
        return code;
    }

    /**
     * 获得订单编号尾号(6�?
     *
     * @param orderCode
     * @return
     */
    public static String getOrderCodeTailNumber(String orderCode) {
        String result = orderCode;
        if (orderCode != null && orderCode.length() >= 6) {
            result = orderCode.substring(orderCode.length() - 6);
        }
        return result;
    }
}
