package com.exadel;

import com.exadel.operations.Cleaner;
import com.exadel.operations.Downloader;
import com.exadel.operations.Uploader;
import com.exadel.util.FBAHttpClientBuilder;
import org.apache.http.client.HttpClient;

import java.io.IOException;

public class Main {

    // users credentials from db (not active dir.!!!) for form base authentication
    public static final String login = "Jack Sparrow";
    public static final String password = "HG*as5W";

    public static final String serverURL = "http://localhost:2004";


    public static void main(String[] args) throws IOException {
        FBAHttpClientBuilder httpClientBuilder = FBAHttpClientBuilder.getInstance();
        HttpClient httpClient = httpClientBuilder.build(serverURL, login, password);

        Uploader uloader = new Uploader(httpClient);
        Downloader dloader = new Downloader(httpClient);
        Cleaner cleaner = new Cleaner(httpClient);

        uloader.upload(serverURL + "/library/FBA.mp4", "D:/FBA.mp4");
        dloader.download(serverURL + "/library/FBA.mp4", "D:/Del/FBA.mp4");
        cleaner.delete(serverURL + "/library/FBA.mp4");
    }
}
