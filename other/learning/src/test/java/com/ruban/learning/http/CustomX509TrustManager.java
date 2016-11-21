package com.ruban.learning.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Hex;

public class CustomX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        for (int i = 0; i < chain.length; i++) {
            System.out.println(Hex.encodeHexString(chain[i].getPublicKey().getEncoded()));
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}
