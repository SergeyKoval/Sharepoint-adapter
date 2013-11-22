package com.exadel.operations;

import com.exadel.authentication.JCIFSEngine;
import org.apache.http.*;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.NTLMScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.*;

public class Downloader {

    private DefaultHttpClient httpClient;
    private String docLibURI;


    public Downloader(String login, String password, String domain,
                      String docLibURI) {

        this.docLibURI = docLibURI;
        httpClientInit(login, password, domain);
    }


    public int download(String fileName) {
        HttpGet request = new HttpGet(docLibURI + fileName);

        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    BufferedInputStream bis = new BufferedInputStream(instream);
                    String filePath = "D:/" + fileName;
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    int inByte;
                    while ((inByte = bis.read()) != -1 ) {
                        bos.write(inByte);
                    }
                    bis.close();
                    bos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HttpStatus.SC_NOT_FOUND;
    }


    private void httpClientInit(String login, String password, String domain) {

        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        httpClient = new DefaultHttpClient(params);

        httpClient.getAuthSchemes().register("ntlm", new AuthSchemeFactory() {
            @Override
            public AuthScheme newInstance(HttpParams params) {
                return new NTLMScheme(new JCIFSEngine());
            }
        });

        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,
                new NTCredentials(login, password, "", domain));
    }
}
