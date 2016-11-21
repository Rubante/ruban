package com.ruban.learning.http;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

public class HttpsRequest {

    @Test
    public void testHttps() {
        HttpGet httpGet = new HttpGet("https://www.baidu.com");
        HttpClientBuilder builder = HttpClientBuilder.create();

        try {

            CustomX509TrustManager x509 = new CustomX509TrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { x509 }, new java.security.SecureRandom());

            builder.setSSLContext(sslContext);

            HttpClient httpClient = builder.build();

            HttpResponse response = httpClient.execute(httpGet);

            InputStream is = response.getEntity().getContent();

            byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                System.out.println(new String(buffer));
            }

        } catch (ClientProtocolException ex) {

        } catch (IOException ex) {

        } catch (NoSuchAlgorithmException ex) {

        } catch (KeyManagementException ex) {

        }

    }
}
