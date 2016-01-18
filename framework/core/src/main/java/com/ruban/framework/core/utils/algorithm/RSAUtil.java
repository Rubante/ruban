package com.ruban.framework.core.utils.algorithm;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.util.Base64Utils;

import com.ruban.framework.core.utils.commons.ByteUtil;
import com.ruban.framework.core.utils.commons.Constants;

/**
 * Description: RSA工具类 User: I8800
 */
public class RSAUtil {

    private static final BigInteger PublicExponent = BigInteger.valueOf(0x10001);

    /**
     * 用私钥加密或者解密
     * 
     * @param privateKey
     * @param cipherMode
     * @param plainTextBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] cryptByPrivateKey(PrivateKey privateKey, int cipherMode, byte[] plainTextBytes)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {

        Cipher cipher = Cipher.getInstance(Constants.RSA);
        cipher.init(cipherMode, privateKey);
        return cipher.doFinal(plainTextBytes);
    }

    /**
     * 用公钥加密或者解密
     * 
     * @param publicKey
     * @param cipherMode
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] cryptByPublicKey(PublicKey publicKey, int cipherMode, byte[] bytes)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {

        Cipher cipher = Cipher.getInstance(Constants.RSA);
        cipher.init(cipherMode, publicKey);
        return cipher.doFinal(bytes);
    }

    /**
     * 产生一个RSA密钥对
     * 
     * @return KeyPair
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair(int digit) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Constants.RSA);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(System.nanoTime());
        keyPairGenerator.initialize(digit, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * 默认产生1024位的密钥对
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return RSAUtil.generateKeyPair(1024);
    }

    /**
     * 把密钥转成十六进制字符串
     * 
     * @param key
     * @return
     */
    public static String getKeyStringByHex(Key key) {
        byte[] keyBytes = key.getEncoded();
        return ByteUtil.bytes2HexString(keyBytes);
    }

    /**
     * 把密钥转成Base64编码的字符串
     * 
     * @param key
     * @return
     */
    public static String getKeyStringByBase64(Key key) {
        byte[] keyBytes = key.getEncoded();
        String keyStringByBase64 = Base64Utils.encodeToString(keyBytes);
        return keyStringByBase64;
    }

    /**
     * 根据私钥获取d，私钥指数
     * 
     * @param rsaPrivateKey
     * @return
     */
    public static byte[] getNakedPrivateKeyBytes(RSAPrivateKey rsaPrivateKey) {

        BigInteger privateExponent = rsaPrivateKey.getPrivateExponent();

        byte[] nakedPrivateKeyBytes = ByteUtil.hexString2Bytes(privateExponent.toString(Constants.HEX));

        return nakedPrivateKeyBytes;
    }

    /**
     * 根据公钥获取n，模
     * 
     * @param rsaPublicKey
     * @return
     */
    public static byte[] getNakedPublicKeyBytes(RSAPublicKey rsaPublicKey) {

        BigInteger modulus = rsaPublicKey.getModulus();

        byte[] nakedPublicKeyBytes = ByteUtil.hexString2Bytes(modulus.toString(Constants.HEX));

        return nakedPublicKeyBytes;
    }

    /**
     * 根据模n和私钥指数d获取私钥
     * 
     * @param modulus
     *            naked publickey
     * @param privateExponent
     *            naked privatekey
     * @return PrivateKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateKey getPrivateKey(BigInteger modulus, BigInteger privateExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(Constants.RSA);

        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(rsaPrivateKeySpec);
        return rsaPrivateKey;
    }

    /**
     * 根据模n、私钥指数d、p、q获取Crt私钥
     * 
     * @param modulus
     * @param privateExponent
     * @param primeP
     * @param primeQ
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateCrtKey getPrivateKey(BigInteger modulus, BigInteger privateExponent, BigInteger primeP,
            BigInteger primeQ) throws NoSuchAlgorithmException, InvalidKeySpecException {

        BigInteger oneBigInteger = BigInteger.ONE;

        BigInteger primeExponentP = privateExponent.mod(primeP.subtract(oneBigInteger));
        BigInteger primeExponentQ = privateExponent.mod(primeQ.subtract(oneBigInteger));
        BigInteger crtCoefficient = primeQ.modInverse(primeP);

        return RSAUtil.getPrivateKey(modulus, PublicExponent, privateExponent, primeP, primeQ, primeExponentP,
                primeExponentQ, crtCoefficient);
    }

    /**
     * 根据模n，公钥指数e、...获取Crt私钥
     * 
     * @param modulus
     * @param publicExponent
     * @param privateExponent
     * @param primeP
     * @param primeQ
     * @param primeExponentP
     * @param primeExponentQ
     * @param crtCoefficient
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateCrtKey getPrivateKey(BigInteger modulus, BigInteger publicExponent,
            BigInteger privateExponent, BigInteger primeP, BigInteger primeQ, BigInteger primeExponentP,
            BigInteger primeExponentQ, BigInteger crtCoefficient) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent,
                primeP, primeQ, primeExponentP, primeExponentQ, crtCoefficient);

        KeyFactory keyFactory = KeyFactory.getInstance(Constants.RSA);

        RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(rsaPrivateCrtKeySpec);

        return rsaPrivateCrtKey;
    }

    /**
     * 根据Crt私钥字节数组获得私钥
     * 
     * @param privateCrtKeyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateCrtKey getPrivateKey(byte[] privateCrtKeyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateCrtKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Constants.RSA);
        RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(keySpec);

        return rsaPrivateCrtKey;
    }

    /**
     * 根据模n获得公钥
     * 
     * @param modulus
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPublicKey getPublicKey(BigInteger modulus) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        return RSAUtil.getPublicKey(modulus, PublicExponent);
    }

    /**
     * 根据模n和公钥指数e获取公钥
     * 
     * @param modulus
     * @param publicExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPublicKey getPublicKey(BigInteger modulus, BigInteger publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(Constants.RSA);

        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(rsaPublicKeySpec);
        return rsaPublicKey;
    }

    /**
     * 根据公钥数组获得公钥
     * 
     * @param publicKeyBytes
     * @return
     * @throws java.io.IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] publicKeyBytes) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(Constants.RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 数据签名
     * 
     * @param privateKey
     * @param signatureAlgorithm
     * @param sourceDataBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] signature(PrivateKey privateKey, String signatureAlgorithm, byte[] sourceDataBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initSign(privateKey);
        signature.update(sourceDataBytes);
        byte[] signedDataBytes = signature.sign();

        return signedDataBytes;
    }

    /**
     * 验签
     * 
     * @param publicKey
     * @param signatureAlgorithm
     * @param plainTextBytes
     * @param signedDataBytes
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws SignatureException
     * @throws IOException
     */
    public static boolean verify(PublicKey publicKey, String signatureAlgorithm, byte[] plainTextBytes,
            byte[] signedDataBytes) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
            SignatureException, IOException {

        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initVerify(publicKey);
        signature.update(plainTextBytes);

        return signature.verify(signedDataBytes);
    }
}
