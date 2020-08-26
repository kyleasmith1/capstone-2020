package com.google.sps.servlets;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Key;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.sps.data.User;
import com.google.sps.data.Room;
import com.google.sps.data.RecommenderAlgorithm;
import com.google.sps.data.RequestParser;
import com.google.sps.service.DatabaseService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardHandlerServlet extends HttpServlet {

    private static final String TYPE_REQUEST_PROPERTY = "type";
    private static final String PERSONAL_PROPERTY_TYPE = "personal";
    private static final String FOLLOWED_PROPERTY_TYPE = "followed";
    private static final String RECOMMENDED_PROPERTY_TYPE = "recommended";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Key userKey = ((User) request.getAttribute(User.USER_ENTITY_NAME)).getUserKey();
        Filter roomFilter = new FilterPredicate(Room.HOST_PROPERTY_KEY, FilterOperator.EQUAL, userKey);
        PreparedQuery personalResults = datastore.prepare(new Query(Room.ROOM_ENTITY_NAME).setFilter(roomFilter));
        List<Room> personal = new ArrayList<>();    
        for(Entity entity : personalResults.asIterable()) {
            personal.add(new Room(entity));
        }

        PreparedQuery followedResults = datastore.prepare(new Query(Room.ROOM_ENTITY_NAME));
        List<Room> followed = new ArrayList<>();
        for(Entity entity : followedResults.asIterable()) {
            Room room = new Room(entity);
            if (room.getAllFollowers().contains(userKey)) {
                followed.add(room);
            }
        }

        List<Room> recommended = new ArrayList<>();
        try {
            List<Key> recommendedRooms = RecommenderAlgorithm.recommendRooms(DatabaseService.getUser(userKey), 25);
            for (Key room : recommendedRooms) {
                recommended.add(DatabaseService.getRoom(room));
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Entity not found!");
        }

        List<List<Room>> rooms = new ArrayList<>();
        rooms.add(personal);
        rooms.add(followed);
        rooms.add(recommended);

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