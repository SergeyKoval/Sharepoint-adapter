package com.exadel.operations;

import com.exadel.authentication.JCIFSEngine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.auth.NTLMScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.*;

public class Uploader {

    private HttpClient httpClient;
    private Log log;



    public Uploader(HttpClient httpClient) {
        this.httpClient = httpClient;
        log = LogFactory.getLog(Uploader.class);
    }


    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public int upload(String fileUrl, String filePath) throws IOException {
        log.info("Send PUT (Upload)...");

        HttpPut httpPut = new HttpPut(fileUrl);
        httpPut.setEntity(new FileEntity(new File(filePath)));

        HttpResponse response = httpClient.execute(httpPut);
        StatusLine statusLine = response.getStatusLine();

        // This is important!!! (Server will be "think", that request is processed)
        response.getEntity().getContent().close();

        log.info("PUT (Upload) response status code: " + statusLine.getStatusCode());

        return statusLine.getStatusCode();
    }
}
