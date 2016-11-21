package com.ruban.learning.security.mac;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;
import org.springframework.util.Base64Utils;

public class MacTest {

    @Test
    public void testHmacMD5() {

        try {
            String algorithm = "HmacMD5";
            Mac mac = Mac.getInstance(algorithm);

            SecretKey key = new SecretKeySpec("123456".getBytes(), algorithm);
            mac.init(key);

            mac.update("123456".getBytes());
            String result = Base64Utils.encodeToString(mac.doFinal());

            System.out.println(result);

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testHmacSHA1() {
        try {
            String algorithm = "HmacSHA1";

            Mac mac = Mac.getInstance(algorithm);

            SecretKey key = new SecretKeySpec("123456".getBytes(), algorithm);

            mac.init(key);

            mac.update("123456".getBytes());

            String result = Base64Utils.encodeToString(mac.doFinal());

            System.out.println(result);

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testHmacSHA256() {
        String algorithm = "HmacSHA256";

        try {
            SecretKey key = new SecretKeySpec("123456".getBytes(), algorithm);

            Mac mac = Mac.getInstance(algorithm);
            mac.init(key);
            mac.update("123456".getBytes());

            String result = Base64Utils.encodeToString(mac.doFinal());

            System.out.println(result);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testHmacSHA384() {
        String algorithm = "HmacSHA384";

        try {
            SecretKey key = new SecretKeySpec("123456".getBytes(), algorithm);
            Mac mac = Mac.getInstance(algorithm);

            mac.init(key);
            mac.update("123456".getBytes());
            String result = Base64Utils.encodeToString(mac.doFinal());
            System.out.println(result);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testHmacSHA512() {
        String algorithm = "HmacSHA512";
        try {
            SecretKey key = new SecretKeySpec("123456".getBytes(), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(key);
            mac.update("123456".getBytes());
            String result = Base64Utils.encodeToString(mac.doFinal());
            System.out.println(result);

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
    }
}
