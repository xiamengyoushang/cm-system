package com.excelsecu.cmsystem.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Utils {

    // MD5加密
    public static byte[] md5(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            return md5.digest(str.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 生成随机数
    public static byte[] genRandom(int len){
        byte[] key = new byte[len];
        Random random = new Random();
        for (int i=0; i<len; i++){
            key[i] = (byte)(random.nextInt(254)+1);
        }
        return key;
    }

}
