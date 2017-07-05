package com.minipro.util;

import com.minipro.conf.ErrorConfig;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


public class HttpsClient {

    private CloseableHttpClient httpsClient;

    public HttpsClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, (chain, authType) -> true).build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
       this.httpsClient = HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();
    }



    public String get(String url, Header[] headers)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeaders(headers);
        HttpResponse httpResponse = httpsClient.execute(httpGet);
        String result = entity2String(httpResponse.getEntity());

        // 关闭资源
        httpsClient.close();
        httpGet.releaseConnection();
        return result;
    }


    public String get(String url)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return get(url, null);
    }


    public String post(String url, Header[] headers, HttpEntity entity)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        httpPost.setEntity(entity);
        HttpResponse httpResponse = httpsClient.execute(httpPost);
        String result = entity2String(httpResponse.getEntity());

        // 关闭资源
        httpsClient.close();
        httpPost.releaseConnection();
        return result;
    }


    public String post(String url)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return post(url, null, null);
    }


    public String post(String url, Header[] headers)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return post(url, headers, null);
    }


    public String post(String url, HttpEntity entity)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return post(url, null, entity);
    }


    private String entity2String(HttpEntity entity) {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = entity.getContent();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            // 读取数据
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void main(String[] args) {

        String token_acess = "6626EA4D059440FAF595C205226726BE";
        String url = String.format("https://graph.qq.com/oauth2.0/me?access_token=%s",token_acess);
        try {
            HttpsClient client = new HttpsClient();
            System.out.println(client.get(url).contains("openid"));
        } catch (Exception ex) {
            System.out.println("exception happen");
        }
    }
}
