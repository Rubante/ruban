package com.ruban.framework.core.utils.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util implements Serializable {

    private static final long serialVersionUID = -7873249154468102130L;

    private final static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    /**
     * 根据原文获取MD5信息摘要
     * 
     * @param source
     * @return
     */
    public static String getMD5(String source) {
        if (logger.isDebugEnabled()) {
            logger.debug("MD5Util.getMD5(String)");
        }
        try {
            byte[] bytes = MessageDigestUtil.digest(source.getBytes(), Constants.MD5);
            return ByteUtil.bytes2HexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            if (logger.isErrorEnabled()) {
                logger.error("MD5Util.getMD5(String) ERROR", e);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据文件数据获取文件的MD5值
     * 
     * @param file
     * @return
     */
    public static String getFileMD5(File file) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("MD5Util.getFileMD5(File)");
        }
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream fileInputStream;
        String md5;
        try {
            digest = MessageDigest.getInstance(Constants.MD5);
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            fileInputStream.close();
            digest.update(buffer);
            md5 = ByteUtil.bytes2HexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            if (logger.isErrorEnabled()) {
                logger.error("MD5Util.getFileMD5(File) error", e);
            }
            throw e;
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("MD5Util.getFileMD5(File) error", e);
            }
            throw e;
        }
        return md5;
    }

    /**
     * 获取Hmac Md5值
     * 
     * @param keyValue
     * @param value
     * @return
     */
    public static String getHmacMD5(String keyValue, String value) {

        try {
            SecretKey key = new SecretKeySpec(keyValue.getBytes(), "HmacMD5");
            Mac mac = Mac.getInstance(key.getAlgorithm());
            mac.init(key);
            byte[] code = mac.doFinal(value.getBytes());
            return ByteUtil.bytes2HexString(code);
        } catch (NoSuchAlgorithmException ex) {
            logger.error("getHamcMD5", ex);
        } catch (InvalidKeyException ex) {
            logger.error("getHmacMD5", ex);
        }

        return "";
    }
}
