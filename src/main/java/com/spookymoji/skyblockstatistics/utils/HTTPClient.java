package com.spookymoji.skyblockstatistics.utils;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Callable;

public class HTTPClient {

    String url;

    public HTTPClient(String url) {
        this.url = url;
    }

    public String getRawResponse() throws IOException {

        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(this.url).openConnection();
        try {
            httpClient.setRequestMethod("GET");
            int status = httpClient.getResponseCode();
        } catch(IOException e) {
            httpClient.disconnect();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
        } catch (IOException e) {
            br.close();
        }

        br.close();
        httpClient.disconnect();

        return sb.toString();
    }
}
