package com.exadel.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FBAHttpClientBuilder {

    private static FBAHttpClientBuilder builder;
    private Log log;


    private FBAHttpClientBuilder() {
        log = LogFactory.getLog(FBAHttpClientBuilder.class);
    }


    public static FBAHttpClientBuilder getInstance() {
        if (builder == null) {
            builder = new FBAHttpClientBuilder();
        }

        return builder;
    }


    public HttpClient build(String serverUrl, String login, String password) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        fbaLogon(httpClient, serverUrl, login, password);

        return httpClient;
    }


    private void fbaLogon(HttpClient httpClient, String serverUrl, String login, String password) throws IOException {
        log.info("Send GET (Login page)...");

        String loginPageUrl = serverUrl + "/_forms/default.aspx";

        HttpGet httpGet = new HttpGet(loginPageUrl);
        HttpResponse response = httpClient.execute(httpGet);

        log.info("GET (Login page) response status code: " + response.getStatusLine().getStatusCode());

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException(response.getStatusLine().toString());
        }

        HttpEntity httpEntity = response.getEntity();
        String responseBody = EntityUtils.toString(httpEntity);
        String viewState = getViewState(responseBody);
        String eventvalidation = getEventvalidation(responseBody);

        log.info("Send POST (Logon)...");

        HttpPost httpPost = new HttpPost("http://localhost:2004/_forms/default.aspx");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ctl00$PlaceHolderMain$signInControl$UserName", login));
        nvps.add(new BasicNameValuePair("ctl00$PlaceHolderMain$signInControl$password", password));
        nvps.add(new BasicNameValuePair("ctl00$PlaceHolderMain$signInControl$login", "Sign In"));
        nvps.add(new BasicNameValuePair("ctl00$PlaceHolderMain$signInControl$RememberMe", "on"));
        nvps.add(new BasicNameValuePair("__VIEWSTATE", viewState));
        nvps.add(new BasicNameValuePair("__EVENTVALIDATION", eventvalidation));
        nvps.add(new BasicNameValuePair("__LASTFOCUS", ""));
        nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
        nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse loginResponse = httpClient.execute(httpPost);
        StatusLine statusLine = loginResponse.getStatusLine();

        log.info("POST (Logon) response status code: " + statusLine.getStatusCode());
    }


    private String getViewState(String content) {
        Pattern pattern = Pattern.compile("id=\"__VIEWSTATE\" value=\"([/=\\w]+)\"", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return matcher.group(1);
        return "";
    }


    private String getEventvalidation(String content) {
        Pattern pattern = Pattern.compile("id=\"__EVENTVALIDATION\" value=\"([/\\+=\\w]+)\"", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return matcher.group(1);
        return "";
    }
}
