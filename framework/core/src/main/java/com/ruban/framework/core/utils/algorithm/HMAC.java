package com.ruban.framework.core.utils.algorithm;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.ruban.framework.core.utils.commons.ByteUtil;

public class HMAC {

    /**
     * hmacsha1长度 单位：字节
     */
    public static final int HMAC_SHA1_LENGTH = 64;
    /**
     * SHA1摘要算法
     */
    public static final String SHA1 = "SHA1";
    /**
     * 默认摘要算法
     */
    public static final String DEFAULT_ALGORITHM = "HmacSHA1";
    /**
     * 安全随机数算法
     */
    public static final String DEFAULT_RAND_ALGORITHM = "SHA1PRNG";

    /**
     * 产生一个随机密钥
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Key getKey() throws NoSuchAlgorithmException {
        return getKey(DEFAULT_ALGORITHM, DEFAULT_RAND_ALGORITHM, System.nanoTime());
    }

    /**
     * 给定hmac摘要算法、随机数算法和随机数种子获得一个密钥
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Key getKey(String algorithm, String randAlg, long rand) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecureRandom secureRandom = SecureRandom.getInstance(randAlg);
        secureRandom.setSeed(rand);
        keyGenerator.init(secureRandom);
        Key key = keyGenerator.generateKey();
        return key;
    }

    /**
     * 把byte数组转为密钥
     * 
     * @param keyBytes
     * @return
     */
    public static Key getKey(byte[] keyBytes) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, DEFAULT_ALGORITHM);
        return secretKeySpec;
    }

    /**
     * 把key转为byte数组
     * 
     * @param key
     * @return
     */
    public static byte[] getEncoded(Key key) {
        return key.getEncoded();
    }

    /**
     * 根据密钥，求源字节数组摘要值
     * 
     * @param key
     * @param srcBytes
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] doFinal(Key key, byte[] srcBytes) throws InvalidKeyException, NoSuchAlgorithmException {
        return doFinal(key, srcBytes, DEFAULT_ALGORITHM);
    }

    /**
     * 根据密钥、hmac摘要算法，求源字节数组摘要值
     * 
     * @param key
     * @param srcBytes
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] doFinal(Key key, byte[] srcBytes, String algorithm) throws NoSuchAlgorithmException,
            InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        mac.update(srcBytes);
        byte[] digestBytes = mac.doFinal();
        return digestBytes;
    }

    /**
     * 根据给定键值和原文，按照取非获取hmacsha摘要值
     * 
     * @param keyBytes
     * @param srcBytes
     * @return
     */
    public static byte[] getHmacSha1withNOT(byte[] keyBytes, byte[] srcBytes) throws NoSuchAlgorithmException {
        byte[] ipadArray = new byte[HMAC_SHA1_LENGTH];
        byte[] opadArray = new byte[HMAC_SHA1_LENGTH];
        byte[] keyArray = new byte[HMAC_SHA1_LENGTH];

        int length = keyBytes.length;
        if (length >= HMAC_SHA1_LENGTH) {
            System.arraycopy(keyBytes, 0, keyArray, 0, HMAC_SHA1_LENGTH);
        } else {
            System.arraycopy(keyBytes, 0, keyArray, 0, length);
            // 长度不够时，在后面补0
            Arrays.fill(keyArray, length, HMAC_SHA1_LENGTH, (byte) 0x00);
        }
        // 标准的运算
        // for (int i = 0; i < keyArray.length; i++) {
        // ipadArray[i] = (byte) (keyArray[i] ^ 0x36);
        // opadArray[i] = (byte) (keyArray[i] ^ 0x5c);
        // }

        // 自己定制
        for (int i = 0; i < HMAC_SHA1_LENGTH; i++) {
            ipadArray[i] = (byte) (~keyArray[i]);
            opadArray[i] = (byte) (~keyArray[i]);
        }
        byte[] ipadBytes = ByteUtil.merge(ipadArray, srcBytes);
        byte[] mdBytes = MessageDigestUtil.digest(ipadBytes, SHA1);
        byte[] opadBytes = ByteUtil.merge(opadArray, mdBytes);
        return MessageDigestUtil.digest(opadBytes, SHA1);
    }

    /**
     * 根据给定键值和原文，获取hmacsha标准的摘要值
     * 
     * @param keyBytes
     * @param srcBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] getHmacSha1withXOR(byte[] keyBytes, byte[] srcBytes) throws NoSuchAlgorithmException,
            InvalidKeyException {
        Key key = getKey(keyBytes);
        return doFinal(key, srcBytes);
    }

    /**
     * 使用标准的HMAC-SHA1球摘要值
     * 
     * @param key
     * @param srcBytes
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getHmacSha1(Key key, byte[] srcBytes) throws InvalidKeyException, NoSuchAlgorithmException {
        return getHmacSha(key, srcBytes, DEFAULT_ALGORITHM);
    }

    /**
     * 根据给定键值、原文和摘要算法，获取标准摘要值
     * 
     * @param key
     * @param srcBytes
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] getHmacSha(Key key, byte[] srcBytes, String algorithm) throws NoSuchAlgorithmException,
            InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        mac.update(srcBytes);
        return mac.doFinal();
    }
}
