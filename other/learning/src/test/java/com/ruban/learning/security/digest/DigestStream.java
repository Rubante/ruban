package com.ruban.learning.security.digest;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.springframework.util.Base64Utils;

public class DigestStream {

    @Test
    public void digestInputStream() {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(DigestStream.class.getResource("a.txt").getFile());
            DigestInputStream dis = new DigestInputStream(fis, digest);
            
            String result = Base64Utils.encodeToString(dis.getMessageDigest().digest());
            
            System.out.println(result);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
