package com.google.sps.servlets;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Entity;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.sps.data.Form;
import com.google.sps.data.RequestJsonParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Form> forms = new ArrayList<>();
        Form form = null;
        Query query = new Query ("Form");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
    
        for(Entity entity : results.asIterable()){
            form = new Form(entity);
            forms.add(form);
        }
    
        Gson gson = new Gson();
        String json = gson.toJson(forms);
        response.setContentType("application/json");
        response.getWriter().println(json);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Form form = RequestJsonParser.parseObjectFromRequest(request, Form.class);

        Entity formEntity = form.toDatastoreEntity();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(formEntity);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}