package com.note.common;

import java.security.MessageDigest;

public class MD5Util {
    public static String Encryption(String psw) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = psw.getBytes();
            byte[] outputData = md.digest(inputData);
            result = bytesToHex(outputData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
