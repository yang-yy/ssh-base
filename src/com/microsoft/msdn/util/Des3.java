package com.microsoft.msdn.util;

import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.Security;

public final class Des3 {
    private static final String Algorithm = "DESede";

    private static final byte[] Key = "cnfolbtcbeijingyuexingli".getBytes();

    private static final Des3 instance = new Des3();

    static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }

    private Des3() {

    }

    public static Des3 getInstance() {
        return instance;
    }

    private byte[] encryptMode(byte[] keybyte, byte[] src) throws Exception {
        SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    private byte[] decryptMode(byte[] keybyte, byte[] src) throws Exception {
        SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    /**
     * 加密方法
     *
     * @param src
     * @return
     * @throws Exception
     */
    public String encryptString(String src) throws Exception {
        src = StringUtils.trimToEmpty(src);
        byte[] encoded = src.getBytes();
        encoded = this.encryptMode(Key, encoded);
        return new String(new BASE64Encoder().encode(encoded));
    }

    /**
     * 解密方法
     *
     * @param src
     * @return
     * @throws Exception
     */
    public String decryptString(String src) throws Exception {
        byte[] srcBytes = new BASE64Decoder().decodeBuffer(src);
        srcBytes = this.decryptMode(Key, srcBytes);
        return new String(srcBytes);
    }

    public String getMD5Str(String str) throws Exception {
        MessageDigest messageDigest = null;

        messageDigest = MessageDigest.getInstance("MD5");

        messageDigest.reset();

        messageDigest.update(str.getBytes("UTF-8"));

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    public String encryptBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            str = str == null ? "" : str.trim();
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    public String decryptBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
