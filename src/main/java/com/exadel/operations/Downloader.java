package com.exadel.operations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.*;

public class Downloader {

    private HttpClient httpClient;
    private Log log;



    public Downloader(HttpClient httpClient) {
        this.httpClient = httpClient;
        log = LogFactory.getLog(Downloader.class);
    }


    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public int download(String fileUrl, String newFilePath) throws IOException {
        log.info("Send GET (Download)...");

        HttpGet httpGet = new HttpGet(fileUrl);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            saveStreamAsFile(entity.getContent(), newFilePath);
        }

        StatusLine statusLine = response.getStatusLine();

        log.info("GET (Download) response status code: " + statusLine.getStatusCode());

        return statusLine.getStatusCode();
    }


    private void saveStreamAsFile(InputStream istream, String newFilePath) throws IOException {
        BufferedInputStream bistream = new BufferedInputStream(istream);
        BufferedOutputStream bostream = new BufferedOutputStream(new FileOutputStream(new File(newFilePath)));

        int inByte = bistream.read();

        while (inByte != -1) {
            bostream.write(inByte);
            inByte = bistream.read();
        }

        bistream.close();
        bostream.close();
    }
}
