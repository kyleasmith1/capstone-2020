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
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.sps.data.User;
import com.google.sps.data.Room;
import com.google.sps.service.DatabaseService;
import com.google.sps.service.FilterService;
import com.google.sps.data.RequestParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardHandlerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Key userKey = ((User) request.getAttribute(User.USER_ENTITY_NAME)).getUserKey();
        PreparedQuery results = FilterService.getQueryOfKey(Room.ROOM_ENTITY_NAME, Room.HOST_PROPERTY_KEY, userKey);
    
        ArrayList<Room> rooms = new ArrayList<>();
        for(Entity entity : results.asIterable()){
            rooms.add(new Room(entity));
        }

        response.setContentType("application/json");
        response.getWriter().println(new Gson().toJson(rooms));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jobject = JsonParser.parseString(RequestParser.parseStringFromRequest(request)).getAsJsonObject();
        Room room = new Room((User) request.getAttribute(User.USER_ENTITY_NAME), 
            jobject.get(Room.TITLE_PROPERTY_KEY).getAsString(), jobject.get(Room.DESCRIPTION_PROPERTY_KEY).getAsString());

        DatabaseService.save(room.getRoomEntity());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}