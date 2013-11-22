package com.exadel.operations;

import com.exadel.authentication.JCIFSEngine;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.auth.NTLMScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.File;
import java.io.IOException;

public class Uploader {

    private DefaultHttpClient httpClient;
    private String docLibURI;


    public Uploader(String login, String password, String domain,
                    String docLibURI) {

        this.docLibURI = docLibURI;
        httpClientInit(login, password, domain);
    }


    public int upload(String fileName) {
        HttpPut request = new HttpPut(docLibURI + fileName);
        request.setEntity(new FileEntity(new File(fileName)));

        try {
            StatusLine statusLine = httpClient.execute(request).getStatusLine();
            return statusLine.getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HttpStatus.SC_BAD_REQUEST;
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
