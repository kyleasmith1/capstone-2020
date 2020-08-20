package com.google.sps.data;
 
import com.google.sps.data.Room;
import com.google.sps.data.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.lang.Iterable;
 
public class RecommenderAlgorithm {
 
    public static ArrayList<Room> recommendRooms(User user) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        HashMap<String, Integer> userTagMap = RecommenderAlgorithm.populateUserTagMap(user);
        
        Query otherUserQuery = new Query(User.USER_ENTITY_NAME);
        PreparedQuery otherUserResults = datastore.prepare(otherUserQuery);
 
        User otherUser = null;
        HashMap<String, Integer> otherUserTagMap = null;
        HashMap<User, HashMap<String,Integer>> otherUserVectorMap = new HashMap<User, HashMap<String,Integer>>();
        for(Entity otherUserEntity : otherUserResults.asIterable()) {
            otherUser = new User(otherUserEntity);
            otherUserTagMap = RecommenderAlgorithm.populateUserTagMap(otherUser);
            otherUserVectorMap.put(otherUser, otherUserTagMap);
        }
 
        return null;
    }
 
    public static HashMap<String, Integer> populateUserTagMap(User user) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Filter userRoomFilter = new FilterPredicate(Room.FOLLOWERS_PROPERTY_KEY, FilterOperator.EQUAL, user.getUserKey());
        Query userRoomQuery = new Query(Room.ROOM_ENTITY_NAME).setFilter(userRoomFilter);
        PreparedQuery userRoomResults = datastore.prepare(userRoomQuery);
 
        HashMap<String, Integer> userTagMap = Tag.constructUserVectorMap();
        List<String> userTags = null;
        for(Entity roomEntity : userRoomResults.asIterable()){
            userTags = new Room(roomEntity).getAllTags();
            for(String tag : userTags) {
                userTagMap.put(tag, userTagMap.get(tag) + 1);
            }
        }

        return userTagMap;
    }
 
}
