package com.socket.util;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 *
 * @Description:
 * @Author: november
 * @CreateTime: 2021/9/15 2:29 下午
 * @UpdateTIme:
 */
public class BytesUtil {

    /**
     * Created by IntelliJ IDEA.
     *
     * @param src String Byte字符串，每个Byte之间没有分隔符(字符范围:0-9 A-F)
     * @return
     * @Description: 十六进制转byte
     * bytes字符串转换为Byte值
     * @author november
     * @CreateTime: 2021/9/15 2:45 下午
     * @UpdateTime:
     */
    public static byte[] hexToBytes(String src) {
        if (src.length() % 2 == 1) {
            src = "0" + src;
        }
        /*对输入值进行规范化整理*/
        src = src.trim().replace(" ", "").toUpperCase(Locale.US);
        //处理值初始化
        int m = 0, n = 0;
        int iLen = src.length() / 2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间

        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
        }
        return ret;
    }

    /**
     * Created by IntelliJ IDEA.
     *
     * @param
     * @return
     * @Description: byte转十六进制
     * @author november
     * @CreateTime: 2021/9/15 2:56 下午
     * @UpdateTime:
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (byte aByte : bytes) {
            int v = aByte & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
//            stringBuilder.append(" ");
        }
        return stringBuilder.toString().toUpperCase(Locale.ROOT);
    }
}
