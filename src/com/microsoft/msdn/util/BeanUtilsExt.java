package com.microsoft.msdn.util;


import org.apache.commons.beanutils.BeanUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

public class BeanUtilsExt extends BeanUtils {
    private BeanUtilsExt() {

    }

    public static List getBeanPropertyValues(List beanList, String Property) throws Exception {
        List result = new ArrayList();
        if (beanList == null) return result;
        for (Object bean : beanList) {
            if (bean == null) continue;
            result.add(getProperty(bean, Property));
        }
        return result;
    }

    /**
     * ��ȡ��KEY
     *
     * @param p
     * @return
     * @throws Exception
     */
    public static String getTrueKey(String p) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        String key = "theresprojectisthebestfm";
        p = ThreeDes.encrypt(key, p);

        char[] first = p.toCharArray();
        char[] second = new char[first.length];
        for (int i = 0; i < first.length; i += 2) {
            if (i != first.length - 1) {
                second[i] = first[i + 1];
                second[i + 1] = first[i];
            } else
                second[i] = first[i];
        }
        p = new String(second);

        return new BASE64Encoder().encodeBuffer(p.getBytes());
    }

    /**
     * ��ȡ��ʵKEY
     *
     * @param p
     * @return
     * @throws Exception
     */
    public static String getFalseKey(String p) throws Exception {
        p = new String(new BASE64Decoder().decodeBuffer(p));

        char[] first = p.toCharArray();
        char[] second = new char[first.length];
        for (int i = 0; i < first.length; i += 2) {
            if (i != first.length - 1) {
                second[i] = first[i + 1];
                second[i + 1] = first[i];
            } else
                second[i] = first[i];
        }
        p = new String(second);

        String key = "theresprojectisthebestfm";
        p = ThreeDes.decrypt(key, p);
        return p;
    }

    public static <T> T getEnumValue(int value, Class<T> eClass) throws Exception {
        Integer v = null;
        if ((eClass == null) || (eClass.isEnum() == false))
            throw new Exception();
        for (Object o : eClass.getEnumConstants()) {
            v = (Integer) o.getClass().getMethod("getValue").invoke(o);
            if (v.intValue() != value)
                continue;
            return (T) o;
        }

        return null;
    }

    public static Object getEnumValue(String value, Class eClass) throws Exception {
        String v = null;
        if ((eClass == null) || (eClass.isEnum() == false))
            throw new Exception();
        for (Object o : eClass.getEnumConstants()) {
            v = (String) o.getClass().getMethod("getValue").invoke(o);
            if (value.equals(v) == false)
                continue;
            return o;
        }

        return null;
    }

    private static class ThreeDes {
        private static final String Algorithm = "DESede"; //���� �����㷨,���� DES,DESede,Blowfish

        //keybyteΪ������Կ������Ϊ24�ֽ�
        //srcΪ�����ܵ���ݻ�����Դ��
        public static byte[] encryptMode(byte[] keybyte, byte[] src) {
            try {
                //�����Կ
                SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

                //����
                Cipher c1 = Cipher.getInstance(Algorithm);
                c1.init(Cipher.ENCRYPT_MODE, deskey);
                return c1.doFinal(src);
            } catch (java.security.NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            } catch (javax.crypto.NoSuchPaddingException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            return null;
        }

        //keybyteΪ������Կ������Ϊ24�ֽ�
        //srcΪ���ܺ�Ļ�����
        public static byte[] decryptMode(byte[] keybyte, byte[] src) {
            try {
                //�����Կ
                SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

                //����
                Cipher c1 = Cipher.getInstance(Algorithm);
                c1.init(Cipher.DECRYPT_MODE, deskey);
                return c1.doFinal(src);
            } catch (java.security.NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            } catch (javax.crypto.NoSuchPaddingException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            return null;
        }

        //ת����ʮ������ַ�
        public static String byte2hex(byte[] b) {
            String hs = "";
            String stmp = "";

            for (int n = 0; n < b.length; n++) {
                stmp = (Integer.toHexString(b[n] & 0XFF));
                if (stmp.length() == 1) hs = hs + "0" + stmp;
                else hs = hs + stmp;
                if (n < b.length - 1) hs = hs + ":";
            }
            return hs.toUpperCase();
        }

        //ת����ʮ������ַ�
        public static byte[] hex2Byte(byte[] b) {
            if ((b.length % 2) != 0) {
                throw new IllegalArgumentException("des conver error!");
            }
            byte[] b2 = new byte[b.length / 2];
            for (int n = 0; n < b.length; n += 2) {
                String item = new String(b, n, 2);
                b2[n / 2] = (byte) Integer.parseInt(item, 16);
            }
            return b2;
        }


        public static String byte2Hex(byte[] b) {
            String hs = "";
            String stmp = "";
            for (int i = 0; i < b.length; i++) {
                stmp = (Integer.toHexString(b[i] & 0XFF));
                if (stmp.length() == 1) {
                    hs = hs + "0" + stmp;
                } else {
                    hs = hs + stmp;
                }
            }
            return hs.toUpperCase();
        }


        public static String encrypt(String key, String src) {
            try {
                byte[] encoded = encryptMode(key.getBytes(), src.getBytes());
                BASE64Encoder base64 = new BASE64Encoder();
                return base64.encode(encoded);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return src;
        }

        public static String decrypt(String key, String src) {
            try {
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] decodeBytes = decoder.decodeBuffer(src);
                byte[] srcBytes = decryptMode(key.getBytes(), decodeBytes);
                return new String(srcBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return src;
        }
    }
}
