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

import com.ruban.framework.core.utils.commons.ByteUtil;

public class RSA {

    private static final BigInteger PublicExponent = BigInteger.valueOf(0x10001);
    /**
     * RSA
     */
    public static final String CON_RSA = "rsa";
    
    /**
     * Crypt By PrivateKey
     *
     * @param privateKey
     * @param cipherMode
     * @param plainTextBytes
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public static byte[] cryptByPrivateKey(PrivateKey privateKey,
                                           int cipherMode, byte[] plainTextBytes)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(CON_RSA);
        cipher.init(cipherMode, privateKey);
        return cipher.doFinal(plainTextBytes);
    }

    /**
     * Crypt By PublicKey
     *
     * @param publicKey
     * @param cipherMode
     * @param bytes
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public static byte[] cryptByPublicKey(PublicKey publicKey, int cipherMode,
                                          byte[] bytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(CON_RSA);
        cipher.init(cipherMode, publicKey);
        return cipher.doFinal(bytes);
    }

    /**
     * generate PairKey
     *
     * @return KeyPair
     * @throws java.security.NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair(int digit) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CON_RSA);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(System.currentTimeMillis());
        keyPairGenerator.initialize(digit, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return RSA.generateKeyPair(1024);
    }

    /**
     * Acquire keyString
     *
     * @param key
     * @return
     */
    public static String getKeyStringByHex(Key key) {
        byte[] keyBytes = key.getEncoded();
        return ByteUtil.bytes2HexString(keyBytes);
    }

    /**
     * Acquire PrivateKey
     *
     * @param modulus         , naked publickey
     * @param privateExponent , naked privatekey
     * @return PrivateKey
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static RSAPrivateKey getPrivateKey(BigInteger modulus, BigInteger privateExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(CON_RSA);

        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(rsaPrivateKeySpec);
        return rsaPrivateKey;
    }

    /**
     * RSAPrivateCrtKey
     *
     * @param modulus
     * @param privateExponent
     * @param primeP
     * @param primeQ
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static RSAPrivateCrtKey getPrivateKey(BigInteger modulus,
                                                 BigInteger privateExponent,
                                                 BigInteger primeP, BigInteger primeQ)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        BigInteger oneBigInteger = BigInteger.ONE;

        BigInteger primeExponentP = privateExponent.mod(primeP.subtract(oneBigInteger));
        BigInteger primeExponentQ = privateExponent.mod(primeQ.subtract(oneBigInteger));
        BigInteger crtCoefficient = primeQ.modInverse(primeP);

        return RSA.getPrivateKey(modulus, PublicExponent, privateExponent,
                primeP, primeQ, primeExponentP, primeExponentQ, crtCoefficient);
    }

    /**
     * Acquire PrivateKey
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
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static RSAPrivateCrtKey getPrivateKey(BigInteger modulus,
                                                 BigInteger publicExponent, BigInteger privateExponent,
                                                 BigInteger primeP, BigInteger primeQ, BigInteger primeExponentP,
                                                 BigInteger primeExponentQ, BigInteger crtCoefficient)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = new RSAPrivateCrtKeySpec(
                modulus, publicExponent, privateExponent, primeP, primeQ,
                primeExponentP, primeExponentQ, crtCoefficient);

        KeyFactory keyFactory = KeyFactory.getInstance(CON_RSA);

        RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(rsaPrivateCrtKeySpec);

        return rsaPrivateCrtKey;
    }

    /**
     * Acquire PrivateKey
     *
     * @param privateCrtKeyBytes
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static RSAPrivateCrtKey getPrivateKey(byte[] privateCrtKeyBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateCrtKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CON_RSA);
        RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(keySpec);

        return rsaPrivateCrtKey;
    }

    /**
     * Acquire PublicKey
     *
     * @param modulus
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static RSAPublicKey getPublicKey(BigInteger modulus)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        return RSA.getPublicKey(modulus, PublicExponent);
    }

    /**
     * Acquire PublicKey
     *
     * @param modulus
     * @param publicExponent
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static RSAPublicKey getPublicKey(BigInteger modulus, BigInteger publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(CON_RSA);

        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(rsaPublicKeySpec);
        return rsaPublicKey;
    }

    /**
     * Acquire PublicKey
     *
     * @param publicKeyBytes
     * @return
     * @throws java.io.IOException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] publicKeyBytes)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(CON_RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * digit signature
     *
     * @param privateKey
     * @param signatureAlgorithm
     * @param sourceDataBytes
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws java.security.InvalidKeyException
     * @throws java.security.SignatureException
     */
    public static byte[] signature(PrivateKey privateKey,
                                   String signatureAlgorithm, byte[] sourceDataBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            InvalidKeyException, SignatureException {

        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initSign(privateKey);
        signature.update(sourceDataBytes);
        byte[] signedDataBytes = signature.sign();

        return signedDataBytes;
    }

    /**
     * verify
     *
     * @param publicKey
     * @param signatureAlgorithm
     * @param plainTextBytes
     * @param signedDataBytes
     * @return
     * @throws java.security.InvalidKeyException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws java.security.SignatureException
     * @throws java.io.IOException
     */
    public static boolean verify(PublicKey publicKey, String signatureAlgorithm,
                                 byte[] plainTextBytes, byte[] signedDataBytes)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, SignatureException, IOException {

        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initVerify(publicKey);
        signature.update(plainTextBytes);

        return signature.verify(signedDataBytes);
    }
    /**
     * Acquire NakedPublicKeyBytes
     *
     * @param rsaPublicKey
     * @return
     */
    public static byte[] getNakedPublicKeyBytes(RSAPublicKey rsaPublicKey) {
        BigInteger modulus = rsaPublicKey.getModulus();
        byte[] nakedPublicKeyBytes = ByteUtil.hexString2Bytes(modulus.toString(16));
        return nakedPublicKeyBytes;
    }
}