package com.google.sps.servlets;

import java.io.IOException;
import com.google.sps.data.Lesson;
import com.google.sps.data.Room;
import com.google.sps.data.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.EntityNotFoundException;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.sps.data.Lesson;
import com.google.sps.data.Room;
import com.google.sps.data.RequestParser;
import com.google.sps.service.DatabaseService;
import com.google.sps.data.RequestParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/join")
public class JoinServlet extends HttpServlet {

    private static final String JOIN_PROPERTY_KEY = "join";
    private static final String UNJOIN_PROPERTY_KEY = "unjoin";
    private static final String ROOM_ID_PROPERTY_KEY = "room_id";
    private static final String ACTION_PROPERTY_KEY = "action";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        User user = (User) request.getAttribute(User.USER_ENTITY_NAME);
        PreparedQuery roomResults = datastore.prepare(new Query(Room.ROOM_ENTITY_NAME));
        Room room = null;

        for(Entity entity : roomResults.asIterable()) {
            if (new Room(entity).getRoomKey().getId() == Long.parseLong(request.getParameter(JoinServlet.ROOM_ID_PROPERTY_KEY))) {
                room = new Room(entity);
            }
        }

        if (request.getParameter(JoinServlet.ACTION_PROPERTY_KEY).equals(JoinServlet.JOIN_PROPERTY_KEY) && !(room.getAllFollowers().contains(user.getUserKey()))) {
            room.addFollower(user);
        } 
        
        if (request.getParameter(JoinServlet.ACTION_PROPERTY_KEY).equals(JoinServlet.UNJOIN_PROPERTY_KEY)) {
            room.removeFollower(user);
        }

        DatabaseService.save(room.getRoomEntity());

        System.out.println(room.getRoomEntity());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}