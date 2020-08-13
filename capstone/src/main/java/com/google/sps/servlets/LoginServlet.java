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
    private static final String NAME_PROPERTY_KEY = "name";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        GoogleIdToken idToken = null;

        try {
           idToken = RequestParser.verifyToken(RequestParser.parseStringFromRequest(request));
        } catch (GeneralSecurityException e){
            System.out.println("Cannot verify token: " + e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        String userId = idToken.getPayload().getSubject();
        String name = (String) idToken.getPayload().get(LoginServlet.NAME_PROPERTY_KEY);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Filter userFilter = new FilterPredicate(User.USER_ID_PROPERTY_KEY, FilterOperator.EQUAL, userId);
        Query query = new Query(User.USER_ENTITY_NAME).setFilter(userFilter);
        PreparedQuery results = datastore.prepare(query);

        if (results.countEntities() == 0) {
            DatabaseService.save(new User(userId, name).getUserEntity());
        }
        response.setStatus(HttpServletResponse.SC_OK); 
    }
}