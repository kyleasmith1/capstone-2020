package com.google.sps.data;

import com.google.sps.data.Room;
import com.google.sps.data.User;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import java.util.HashMap;

public class RecommenderAlgorithm {

    public static List<Room> recommendRooms(User user) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key userKey = user.getUserKey();
        Filter roomFilter = new FilterPredicate(Room.HOST_PROPERTY_KEY, FilterOperator.EQUAL, userKey);
        // Filter tagFilter = new FilterPredicate(Room.TAG_LIST_PROPERTY_KEY, )
        Query query = new Query(Room.ROOM_ENTITY_NAME).setFilter(roomFilter);
        PreparedQuery results = datastore.prepare(query);
        ArrayList<Room> rooms = new ArrayList<>();
        for(Entity entity : results.asIterable()) {
            rooms.add(new Room(entity));
        }

        HashMap<String, Integer> UserRoomMap = new HashMap<>();
        for(Room room : rooms){
            rooms.put 
        }



    }
}