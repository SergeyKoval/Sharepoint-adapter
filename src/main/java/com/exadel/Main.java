package com.exadel;

import com.exadel.operations.Downloader;
import com.exadel.operations.Uploader;

public class Main {

    // TODO Remove it
    public static final String login = "pmitrafanau";
    public static final String password = "******";
    public static final String domain = "eltegra";

    public static final String docLibURI = "http://localhost/test/";


    public static void main(String[] args) {
        Uploader uloader = new Uploader(login, password, domain, docLibURI);
        Downloader dloader = new Downloader(login, password, domain, docLibURI);
        uloader.upload("ttt.txt");
        dloader.download("ttt.txt");
    }
}
