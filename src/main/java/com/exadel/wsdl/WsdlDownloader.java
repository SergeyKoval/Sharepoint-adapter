package com.exadel.wsdl;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URL;


public class WsdlDownloader {

    private boolean isConnectionOpened;

    private CloseableHttpClient httpClient;
    private HttpClientContext httpClientContext;
    private HttpHost targetHost;


    public WsdlDownloader() {
    }


    public void openConnection(String strUrl, String login, String password) throws Exception {
        if (isConnectionOpened) {
            throw new Exception("Connection already opened");
        }

        URL url = new URL(strUrl);

        targetHost = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
        initHttpClient();
        initHttpClientContext(login, password);

        isConnectionOpened = true;
    }


    public void closeConnection() throws Exception {
        if (!isConnectionOpened) {
            throw new Exception("Connection isn't opened");
        } else {
            httpClient.close();
            isConnectionOpened = false;
        }
    }


    public void getWsdl(String wsdlName, String destinationPath) throws Exception {
        if (!isConnectionOpened) {
            throw new Exception("First you must open connection");
        }

        HttpGet httpGet = new HttpGet("/_vti_bin/" + wsdlName + ".asmx?wsdl");
        HttpResponse response = httpClient.execute(targetHost, httpGet, httpClientContext);

        switch (response.getStatusLine().getStatusCode()) {
            case HttpStatus.SC_OK:
                writeStreamIntoFile(response.getEntity().getContent(), destinationPath);
                break;

            case HttpStatus.SC_UNAUTHORIZED:
                throw new Exception("401 UNAUTHORIZED");
        }
    }

    private void writeStreamIntoFile(InputStream input, String destinationPath) {
        OutputStream output = null;

        try {
            output = new FileOutputStream(new File(destinationPath));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void initHttpClientContext(String login, String password) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials(login, password));

        httpClientContext = new HttpClientContext();
        httpClientContext.setCredentialsProvider(credentialsProvider);
    }


    private void initHttpClient() {
        HttpClientBuilder httpCBuilder = HttpClientBuilder.create();
        httpClient = httpCBuilder.build();
    }
}