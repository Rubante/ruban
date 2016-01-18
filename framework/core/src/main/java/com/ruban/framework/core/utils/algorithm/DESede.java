package com.ruban.framework.core.utils.algorithm;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * java默认填充方式是：DESede/ECB/PKCS5Padding c++默认填充方式是：PKCS7Padding
 */
public class DESede {

    /* 3des */
    public static final String tripleDES = "DESede";

    /**
     * NoPadding
     */
    public static final String NoPadding = "NoPadding";
    /**
     * PKCS5Padding
     */
    public static final String PKCS5Padding = "PKCS5Padding";
    /**
     * PKCS7Padding
     */
    public static final String PKCS7Padding = "PKCS7Padding";

    // /**
    // * set padding mode
    // *
    // * @param paddingMode
    // */
    // public static void setPaddingMode(String paddingMode) {
    // DESede.paddingMode = paddingMode;
    // Security.addProvider(new BouncyCastleProvider());
    // }

    /**
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Key generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(tripleDES);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(System.currentTimeMillis());
        keyGenerator.init(secureRandom);
        Key key = keyGenerator.generateKey();
        return key;
    }

    /**
     * @param keyBytes
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key getKey(byte[] keyBytes) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        DESedeKeySpec spec = new DESedeKeySpec(keyBytes);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(tripleDES);
        return keyfactory.generateSecret(spec);
    }

    /**
     * @param key
     * @return
     */
    public static byte[] getKey(Key key) {
        return key.getEncoded();
    }

    /**
     * @param key
     * @param data
     * @return
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static byte[] encode(Key key, byte[] data) throws IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(tripleDES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encodeBytes = cipher.doFinal(data);
        return encodeBytes;
    }

    /**
     * @param key
     * @param data
     * @return
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static byte[] decode(Key key, byte[] data) throws IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        Cipher cipher = Cipher.getInstance(tripleDES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodeBytes = cipher.doFinal(data);

        return decodeBytes;
    }

    /**
     * @param key
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encodeWithECB(Key key, byte[] data, String paddingMode) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(tripleDES + "/ECB/" + paddingMode);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encodeBytes = cipher.doFinal(data);

        return encodeBytes;
    }

    /**
     * @param key
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decodeWithECB(Key key, byte[] data, String paddingMode) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(tripleDES + "/ECB/" + paddingMode);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodeBytes = cipher.doFinal(data);

        return decodeBytes;

    }

    /**
     * @param key
     * @param keyIv
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * 
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encodeWithCBC(Key key, byte[] keyIv, byte[] data, String paddingMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(tripleDES + "/CBC/" + paddingMode);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyIv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encodeBytes = cipher.doFinal(data);

        return encodeBytes;
    }

    /**
     * @param key
     * @param keyIv
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * 
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decodeWithCBC(Key key, byte[] keyIv, byte[] data, String paddingMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(tripleDES + "/CBC/" + paddingMode);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyIv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] decodeBytes = cipher.doFinal(data);

        return decodeBytes;
    }

    /**
     * @param key
     * @param keyIv
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * 
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encodeWithCFB(Key key, byte[] keyIv, byte[] data, String paddingMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(tripleDES + "/CFB/" + paddingMode);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyIv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encodeBytes = cipher.doFinal(data);

        return encodeBytes;
    }

    /**
     * @param key
     * @param keyIv
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * 
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decodeWithCFB(Key key, byte[] keyIv, byte[] data, String paddingMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(tripleDES + "/CFB/" + paddingMode);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyIv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] encodeBytes = cipher.doFinal(data);

        return encodeBytes;
    }

    /**
     * @param key
     * @param keyIv
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * 
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encodeWithOFB(Key key, byte[] keyIv, byte[] data, String paddingMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(tripleDES + "/OFB/" + paddingMode);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyIv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encodeBytes = cipher.doFinal(data);

        return encodeBytes;
    }

    /**
     * @param key
     * @param keyIv
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * 
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decodeWithOFB(Key key, byte[] keyIv, byte[] data, String paddingMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(tripleDES + "/OFB/" + paddingMode);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyIv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] encodeBytes = cipher.doFinal(data);

        return encodeBytes;
    }
}
