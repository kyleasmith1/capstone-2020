package com.google.sps;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.sps.data.Room;
import com.google.sps.data.Lesson;
import com.google.sps.data.User;
import java.lang.IllegalArgumentException;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.sps.service.DatabaseService;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public final class RecommenderAlgorithmTest {
    
    private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
          .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void arrayListTest() { 
        User user = new User("123", "Andrew");
        User follower = new User("321", "Kyle");
        User Matt = new User("456", "Matt");
        DatabaseService.save(user.getUserEntity());
        DatabaseService.save(follower.getUserEntity());
        DatabaseService.save(Matt.getUserEntity());

        Room room = new Room(user, "Ukulele", "Free Lessons");
        DatabaseService.save(room.getRoomEntity());
        room.addFollower(follower);
        room.addFollower(Matt);
        
        List<Key> followers = room.getAllFollowers();
        // System.out.println(followers);
    }

    @Test
    public void filterTest(){
        User user = new User("123", "Andrew");
        User follower = new User("321", "Kyle");
        User Matt = new User("456", "Matt");
        DatabaseService.save(user.getUserEntity());
        DatabaseService.save(follower.getUserEntity());
        DatabaseService.save(Matt.getUserEntity());

        Room room = new Room(user, "Ukulele", "Free Lessons");
        DatabaseService.save(room.getRoomEntity());
        room.addFollower(follower);
        room.addFollower(Matt);
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Key> followers = room.getAllFollowers();
        System.out.println(followers);
        Key userKey = Matt.getUserKey();
        System.out.println(userKey);
        // Filter userRoomFilter = new FilterPredicate(Room.FOLLOWERS_PROPERTY_KEY, FilterOperator.EQUAL, userKey);
        Query userRoomQuery = new Query(Room.ROOM_ENTITY_NAME);
        PreparedQuery userRoomResults = datastore.prepare(userRoomQuery);

        for(Entity entity : userRoomResults.asIterable()) {
            System.out.println("HERE " + entity);
        }
    }
}