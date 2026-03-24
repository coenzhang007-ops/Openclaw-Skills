package com.vadas.crm.provider.uac.controller.mcp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrmTokenClient {

    public String loginAndGetToken(SkillConfig config) throws Exception {
        HttpURLConnection connection = null;
        try {
            String basicAuthPlain = config.getBasicAuthUsername() + ":" + config.getBasicAuthPassword();
            String basicAuth = Base64.getEncoder().encodeToString(basicAuthPlain.getBytes("UTF-8"));

            String formBody = buildFormBody(
                    "scope", config.getScope(),
                    "grant_type", config.getGrantType(),
                    "username", config.getCrmUsername(),
                    "password", config.getCrmPassword()
            );

            URL url = new URL(config.getTokenUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + basicAuth);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(formBody.getBytes("UTF-8").length));

            OutputStream os = connection.getOutputStream();
            os.write(formBody.getBytes("UTF-8"));
            os.flush();
            os.close();

            int statusCode = connection.getResponseCode();
            String responseBody = readResponse(connection, statusCode);

            if (statusCode < 200 || statusCode >= 300) {
                throw new RuntimeException("登录失败，HTTP状态码=" + statusCode + "，响应=" + responseBody);
            }

            String accessToken = extractAccessToken(responseBody);
            if (accessToken == null || accessToken.trim().isEmpty()) {
                throw new RuntimeException("登录成功但未提取到 access_token，响应=" + responseBody);
            }

            return accessToken;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String loginAndGetRawResponse(SkillConfig config) throws Exception {
        HttpURLConnection connection = null;
        try {
            String basicAuthPlain = config.getBasicAuthUsername() + ":" + config.getBasicAuthPassword();
            String basicAuth = Base64.getEncoder().encodeToString(basicAuthPlain.getBytes("UTF-8"));

            String formBody = buildFormBody(
                    "scope", config.getScope(),
                    "grant_type", config.getGrantType(),
                    "username", config.getCrmUsername(),
                    "password", config.getCrmPassword()
            );

            URL url = new URL(config.getTokenUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + basicAuth);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(formBody.getBytes("UTF-8").length));

            OutputStream os = connection.getOutputStream();
            os.write(formBody.getBytes("UTF-8"));
            os.flush();
            os.close();

            int statusCode = connection.getResponseCode();
            return readResponse(connection, statusCode);

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String buildFormBody(String... params) throws Exception {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("参数必须成对出现");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(params[i], "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(params[i + 1], "UTF-8"));
        }
        return sb.toString();
    }

    private String readResponse(HttpURLConnection connection, int statusCode) throws Exception {
        InputStream is;
        if (statusCode >= 200 && statusCode < 400) {
            is = connection.getInputStream();
        } else {
            is = connection.getErrorStream();
        }

        if (is == null) {
            return "";
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    private String extractAccessToken(String json) {
        if (json == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("\\\"access_token\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
