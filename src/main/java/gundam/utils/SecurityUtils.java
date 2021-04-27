package gundam.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class SecurityUtils {
    public static String byte2Hex(byte[] byteArray) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] >= 0 && byteArray[i] < 16) {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
        }
        return strBuf.toString();
    }

    public static byte[] hex2Byte(String hexString) throws Exception {
        byte[] arrB = hexString.getBytes();
        int iLen = arrB.length;
        //两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            return new String(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
