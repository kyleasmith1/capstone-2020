package com.google.sps.servlets;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.sps.data.Lesson;
import com.google.sps.data.Room;
import com.google.sps.data.RequestParser;
import com.google.sps.service.DatabaseService;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.EntityNotFoundException;


@WebServlet("/room")
public class RoomHandlerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery roomResults = datastore.prepare(new Query(Room.ROOM_ENTITY_NAME));
        Room room = null;

        for(Entity entity : roomResults.asIterable()) {
            if (new Room(entity).getRoomKey().getId() == Long.parseLong(request.getParameter("room_id"))) {
                room = new Room(entity);
            }
        }
        
        response.setContentType("application/json");
        response.getWriter().println(new Gson().toJson(getLessons(room)));
    }

    public List<Lesson> getLessons(Room room) {
        List<Lesson> lessons = new ArrayList<>();
    
        try {
            for(Key key : room.getAllLessons()) {
                lessons.add(DatabaseService.getLesson(key));
            }
        }
        catch (EntityNotFoundException e) {
            System.out.println("Lesson entities don't exist.");
        }
        return lessons;
    }
}