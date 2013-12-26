package com.exadel.wsdl;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class WsdlDownloader {

    private String host;
    private String port;

    private DefaultHttpClient httpClient;

    public WsdlDownloader(String host, String port, String login, String password) {
        this.host = host;
        this.port = port;

        httpClient = new DefaultHttpClient();
        httpClient.getCredentialsProvider().setCredentials(
                new AuthScope("sp2013", 5108),
                new UsernamePasswordCredentials(login, password));
    }

    public void getWsdl(String wsdlName, String destinationPath) throws IOException {
        HttpGet httpGet = new HttpGet("http://" + host + ":" + port + "/_vti_bin/" + wsdlName + ".asmx?wsdl");
        HttpResponse response = httpClient.execute(httpGet);
        System.out.println(response.getEntity().getContent().available());
    }

    /*
    public static void main(String[] args) {


        try {
            // URL url = new URL ("http://ip:port/download_url");
            URL url = new URL(serverFilePath);
            String authStr = user + ":" + pass;
            String authEncoded = Base64.encodeBase64String(authStr.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            File file = new File("D:/copysssss.wsdl");
            InputStream in = (InputStream) connection.getInputStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
            out.close();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
*/
}