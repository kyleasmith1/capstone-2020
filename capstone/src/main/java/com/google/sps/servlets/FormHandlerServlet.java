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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(new Query(Form.FORM_ENTITY_NAME));
        
        List<Form> forms = new ArrayList<>();    
        for(Entity entity : results.asIterable()){
            forms.add(new Form(entity));
        }
    
        response.setContentType("application/json");
        response.getWriter().println(new Gson().toJson(forms));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sb = RequestParser.parseStringFromRequest(request);

        JsonElement jelement = JsonParser.parseString(sb);
        JsonObject jobject = jelement.getAsJsonObject();

        String editUrl = jobject.get(Form.EDIT_URL_PROPERTY_KEY).getAsString();
        String url = jobject.get(Form.URL_PROPERTY_KEY).getAsString();
        
        DatabaseService.save((new Form(editUrl, url)).getFormEntity());
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}