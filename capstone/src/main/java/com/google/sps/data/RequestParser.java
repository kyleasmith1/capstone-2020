package com.google.sps.data;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class RequestParser {
  
    public static <T> T parseObjectFromRequest(HttpServletRequest request, Class<T> type) throws IOException, JsonSyntaxException {
        return (new Gson()).fromJson(parseStringFromRequest(request), type);
    }

    public static String parseStringFromRequest(HttpServletRequest request) throws IOException, JsonSyntaxException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            reader.close();
        }
        return sb.toString();
    }

    public static GoogleIdToken verifyToken(String tokenToVerify) throws IOException, GeneralSecurityException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
            .setAudience(Collections.singletonList(Config.CLIENT_ID))
            .build();
        GoogleIdToken idToken = verifier.verify(tokenToVerify);
        if (idToken != null) {
            return idToken;
        }
        throw new GeneralSecurityException();
    }
}