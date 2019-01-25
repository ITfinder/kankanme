package com.ziyi.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author ziyi
 * @create 2019/1/23
 */
public class HttpRequestUtil {
    private static CloseableHttpClient httpClient;

    public static void getClient(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String get(String url) {
        String result = null;
        HttpEntity respEntity = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;

        try {
            httpclient = HttpClients.createDefault();
            HttpGet httpGet = createHttpGet(url);
            response = httpclient.execute(httpGet);
            if (200 != response.getStatusLine().getStatusCode()) {
                Object var6 = null;
                return (String)var6;
            }

            respEntity = response.getEntity();
            if (respEntity != null) {
                result = EntityUtils.toString(respEntity, "UTF-8");
            }
        } catch (IOException var17) {
        } finally {
            try {
                EntityUtils.consume(respEntity);
            } catch (IOException var16) {

            }

            close(response, httpclient);
        }

        return result;
    }


    private static void close(CloseableHttpResponse response, CloseableHttpClient httpclient) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException var4) {
                ;
            }
        }

        if (httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException var3) {
                ;
            }
        }

    }

    private static HttpGet createHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.addHeader("Connection", "Keep-Alive");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        httpGet.addHeader("Cookie", "");
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(300000).setConnectTimeout(300000).setConnectionRequestTimeout(300000).setStaleConnectionCheckEnabled(true).build();
        httpGet.setConfig(defaultRequestConfig);
        return httpGet;
    }

    public static String post(String url, String jsonString) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
