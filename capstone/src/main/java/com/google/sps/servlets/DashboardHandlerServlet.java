package com.google.sps.servlets;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Entity;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.sps.data.User;
import com.google.sps.data.Classroom;
import com.google.sps.service.DatabaseService;
import com.google.sps.data.RequestParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardHandlerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        // TODO: In a later CL we will make it so that it only gets User-specific classrooms
        PreparedQuery results = datastore.prepare(new Query(Classroom.CLASSROOM_ENTITY_NAME));
    
        List<Classroom> classrooms = new ArrayList<>();
        for(Entity entity : results.asIterable()){
            classrooms.add(new Classroom(entity));
        }

        response.setContentType("application/json");
        response.getWriter().println(new Gson().toJson(classrooms));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JsonObject jobject = JsonParser.parseString(RequestParser.parseStringFromRequest(request)).getAsJsonObject();

        User teacher = new User(jobject.get(User.USER_ID_PROPERTY_KEY).getAsString(),
            jobject.get(User.NICKNAME_PROPERTY_KEY).getAsString());
        DatabaseService.save(teacher.getUserEntity());

        Classroom classroom = new Classroom(teacher, jobject.get(Classroom.SUBJECT_PROPERTY_KEY).getAsString());
        DatabaseService.save(classroom.getClassroomEntity());

        response.setStatus(HttpServletResponse.SC_OK);
    }
}