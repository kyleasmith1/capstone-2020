package com.google.sps.servlets;

import java.io.IOException;
import java.io.BufferedReader;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.security.GeneralSecurityException;
import com.google.sps.data.User;
import com.google.sps.data.RequestParser;
import com.google.sps.service.DatabaseService;
import java.util.Collections;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {    
        try {
            GoogleIdToken idToken = tokenVerifier().verify(RequestParser.parseStringFromRequest(request));
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                String name = (String) payload.get("name");

                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

                Filter userFilter =
                    new FilterPredicate(User.USER_ID_PROPERTY_KEY, FilterOperator.EQUAL, userId);
                Query query = new Query(User.USER_ENTITY_NAME).setFilter(userFilter);

                PreparedQuery results = datastore.prepare(query);

                if (results.countEntities() == 0) {
                    User user = new User(userId, name);
                    DatabaseService.save(user.getUserEntity());
                }

            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (GeneralSecurityException e){
            System.out.println("Cannot verify token: (GeneralSecurityException)");
        }
    }

    public GoogleIdTokenVerifier tokenVerifier() {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
        // Specify the CLIENT_ID of the app that accesses the backend:
        .setAudience(Collections.singletonList("914921573408-h6di03psfac1qc76n53p2qb6kjkge8pn.apps.googleusercontent.com"))
        // Or, if multiple clients access the backend:
        .build();
        return verifier;
    }
}