package com.ruban.learning.security.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.springframework.util.Base64Utils;

/**
 * 摘要算法（散列算法，单向算法，杂凑算法）
 * 
 * @author ruban
 *
 */
public class MessageDigestTest {

    @Test
    public void testMD2() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            md.update("123456".getBytes());
            String result = Base64Utils.encodeToString(md.digest());
            System.out.println("MD2:" + result);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testMD5() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("123456".getBytes());
            String result = Base64Utils.encodeToString(md.digest());
            System.out.println("MD5:" + result);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSHA1() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update("123456".getBytes());
            String result = Base64Utils.encodeToString(md.digest());
            System.out.println("SHA-1:" + result);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSHA256() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update("123456".getBytes());
            String result = Base64Utils.encodeToString(md.digest());
            System.out.println("SHA-256:" + result);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSHA384() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update("123456".getBytes());
            String result = Base64Utils.encodeToString(md.digest());
            System.out.println("SHA-384:" + result);

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSHA512() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update("123456".getBytes());
            String result = Base64Utils.encodeToString(md.digest());
            System.out.println("SHA-512:" + result);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
}