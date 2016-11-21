package com.ruban.learning.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DHAlgorithm {

    private static final String PUBLIC_KEY = "DHPublicKey";

    private static final String PRIVATE_KEY = "DHPrivateKey";

    private static final String ALGORITHM = "DH";

    private static final String SECRET_ALGORITHM = "AES";

    /**
     * 构建甲方密钥对
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public Map<String, Object> initKey() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

        Map<String, Object> result = new HashMap<String, Object>();

        result.put(PUBLIC_KEY, publicKey);
        result.put(PRIVATE_KEY, privateKey);

        return result;

    }

    /**
     * 使用对方公钥生成自己的密钥对
     * 
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidAlgorithmParameterException
     */
    public Map<String, Object> initKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException,
            InvalidAlgorithmParameterException {

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        DHParameterSpec dhParameterSpec = ((DHPublicKey) publicKey).getParams();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(dhParameterSpec);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();

        Map<String, Object> result = new HashMap<String, Object>();

        result.put(PUBLIC_KEY, dhPublicKey);
        result.put(PRIVATE_KEY, dhPrivateKey);

        return result;
    }

    /**
     * 加密
     * 
     * @param key
     * @param data
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] encrypt(byte[] key, byte[] data) throws InvalidKeySpecException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
    
    /**
     * 解密
     * 
     * @param key
     * @param data
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] decrypt(byte[] key,byte[] data) throws InvalidKeySpecException,NoSuchPaddingException,NoSuchAlgorithmException,InvalidKeyException,BadPaddingException,IllegalBlockSizeException{
        
        SecretKey secretKey = new SecretKeySpec(key,SECRET_ALGORITHM);
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        
        return cipher.doFinal(data);
    }
    
    public byte[] getSecretKey(byte[] publicKey,byte[] privateKey) throws Exception {
        
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
        
        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
        
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        
        
        KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        
        keyAgreement.init(priKey);
        
        keyAgreement.doPhase(pubKey, true);
        
        SecretKey secretKey = keyAgreement.generateSecret(SECRET_ALGORITHM);
        
        return secretKey.getEncoded();
    }
}
