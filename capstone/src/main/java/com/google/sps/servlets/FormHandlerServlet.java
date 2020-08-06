package com.google.sps.servlets;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.sps.data.Form;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.service.DatabaseService;
import com.google.sps.data.RequestParser;

import com.google.sps.service.DatabaseService;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {
    protected static final String FORM = "Form";
    protected static final String EDIT = "editUrl";
    protected static final String URL = "Url";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query (FormHandlerServlet.FORM);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        
        List<Form> forms = new ArrayList<>();
        for(Entity entity : results.asIterable()){
            Form form = new Form(entity);
            forms.add(form);
        }
    
        Gson gson = new Gson();
        String json = gson.toJson(forms);
        response.setContentType("application/json");
        response.getWriter().println(json);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sb = RequestParser.parseStringFromRequest(request);

        JsonElement jelement = JsonParser.parseString(sb);
        JsonObject jobject = jelement.getAsJsonObject();

        String editUrl = jobject.get(FormHandlerServlet.EDIT).getAsString();
        String url = jobject.get(FormHandlerServlet.URL).getAsString();

        Form form = new Form(editUrl, url);

        DatabaseService.save(form.getFormEntity());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}