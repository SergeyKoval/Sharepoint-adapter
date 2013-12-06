package com.exadel.authenticator;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class NTLMAuthenticator extends Authenticator {

    private final String username;
    private final char[] password;


    public NTLMAuthenticator(final String username, final String password) {
        super();

        this.username = username;
        this.password = password.toCharArray();
    }


    public PasswordAuthentication getPasswordAuthentication() {
        return (new PasswordAuthentication(username, password));
    }
}