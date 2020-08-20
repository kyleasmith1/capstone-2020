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

public class RecommenderAlgorithm {

    public static ArrayList<Room> recommendRooms(User user) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key userKey = user.getUserKey();
        Filter userRoomFilter = new FilterPredicate(Room.FOLLOWERS_PROPERTY_KEY, FilterOperator.EQUAL, userKey);
        Query userRoomQuery = new Query(Room.ROOM_ENTITY_NAME).setFilter(userRoomFilter);
        PreparedQuery userRoomResults = datastore.prepare(userRoomQuery);

        ArrayList<String> userTags = null;
        HashMap<String, Integer> userTagMap = Tag.constructUserVectorMap();
        for(Entity entity : userRoomResults.asIterable()) {
            userTags = new Room(entity).getAllTags();
            for(String tag : userTags) {
                userTagMap.put(tag, userTagMap.get(tag) + 1);
            }
        }
        
        Key otherUserKey = null;
        ArrayList<String> otherUserTags = null;
        HashMap<User, HashMap<String,Integer>> otherUserVectorMap = new HashMap<User, HashMap<String,Integer>>();
        HashMap<String, Integer> otherUserTagMap = null;
        Query otherUserQuery = new Query(User.USER_ENTITY_NAME);
        PreparedQuery otherUserResults = datastore.prepare(otherUserQuery);
        for(Entity userEntity : otherUserResults.asIterable()) {
            otherUserKey = userEntity.getKey();
            otherUserTagMap = Tag.constructUserVectorMap();
            Filter otherUserRoomFilter = new FilterPredicate(Room.FOLLOWERS_PROPERTY_KEY, FilterOperator.EQUAL, otherUserKey);
            Query otherUserRoomQuery = new Query(Room.ROOM_ENTITY_NAME).setFilter(otherUserRoomFilter);
            PreparedQuery otherUserRoomResults = datastore.prepare(otherUserRoomQuery);
            for(Entity roomEntity : otherUserRoomResults.asIterable()){
                otherUserTags = new Room(roomEntity).getAllTags();
                for(String tag : otherUserTags) {
                    otherUserTagMap.put(tag, userTagMap.get(tag) + 1);
                }
            }
            otherUserVectorMap.put(new User(userEntity), otherUserTagMap);
        }



        return null;

    }
}