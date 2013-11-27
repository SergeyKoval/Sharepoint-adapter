package com.exadel.operations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;

import java.io.IOException;

public class Cleaner {

    private HttpClient httpClient;
    private Log log;



    public Cleaner(HttpClient httpClient) {
        this.httpClient = httpClient;
        log = LogFactory.getLog(Cleaner.class);
    }


    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public int delete(String fileUrl) throws IOException {
        log.info("Send DELETE...");

        HttpDelete httpDelete = new HttpDelete(fileUrl);
        HttpResponse response = httpClient.execute(httpDelete);

        StatusLine statusLine = response.getStatusLine();

        log.info("DELETE response status code: " + statusLine.getStatusCode());

        return statusLine.getStatusCode();
    }
}
