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
import com.google.sps.data.Tag;
import com.google.sps.data.RecommenderAlgorithm;
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
import java.util.HashMap;
 
@RunWith(JUnit4.class)
public final class RecommenderAlgorithmTest {
    
    private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
 
    @Before
    public void setUp() {
        helper.setUp();
    }
 
    @After
    public void tearDown() {
        helper.tearDown();
    }
 
    @Test
    public void populateUserTagMapGeneralTest() { 
        User smith = new User("1234", "Smith");
        User john = new User("2341", "John");
        User bill = new User("3412", "Bill");
        User sam = new User("4123", "Sam");
        DatabaseService.save(smith.getUserEntity());
        DatabaseService.save(john.getUserEntity());
        DatabaseService.save(bill.getUserEntity());
        DatabaseService.save(sam.getUserEntity());
 
        Room poetryRoom = new Room(smith, "How to Teach Poetry", "Learn how to teach Poetry");
        poetryRoom.addTag(Tag.TAG_EDUCATION);
        poetryRoom.addTag(Tag.TAG_LITERATURE);
        poetryRoom.addFollower(john);
        poetryRoom.addFollower(sam);
        DatabaseService.save(poetryRoom.getRoomEntity());
 
        Room runRoom = new Room(john, "Run Efficiently", "Improve your running experience");
        runRoom.addTag(Tag.TAG_FITNESS);
        runRoom.addFollower(smith);
        runRoom.addFollower(bill);
        DatabaseService.save(runRoom.getRoomEntity());
 
        Room cookRoom = new Room(bill, "Iron Chef Master Course", "Cook Better");
        cookRoom.addTag(Tag.TAG_COOKING);
        cookRoom.addFollower(john);
        cookRoom.addFollower(bill);
        DatabaseService.save(cookRoom.getRoomEntity());
 
        Room cookRoomTwo = new Room(bill, "Iron Chef Master Course II", "Cook Better Part 2");
        cookRoomTwo.addTag(Tag.TAG_COOKING);
        cookRoomTwo.addFollower(john);
        cookRoomTwo.addFollower(bill);
        DatabaseService.save(cookRoomTwo.getRoomEntity());
 
        // Smith
        //     1 Fitness
        // John
        //     1 Literature
        //     1 Education
        //     2 Cooking
        // Bill
        //     1 Fitness
        //     2 Cooking
        // Sam
        //     1 Literature
        //     1 Education
 
        HashMap<String, Integer> userTagMap = RecommenderAlgorithm.populateUserTagMap(smith);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_EDUCATION), 0);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_LITERATURE), 0);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_FITNESS), 1);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_COOKING), 0);
 
        userTagMap = RecommenderAlgorithm.populateUserTagMap(john);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_EDUCATION), 1);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_LITERATURE), 1);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_FITNESS), 0);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_COOKING), 2);
 
        userTagMap = RecommenderAlgorithm.populateUserTagMap(bill);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_EDUCATION), 0);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_LITERATURE), 0);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_FITNESS), 1);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_COOKING), 2);
 
        userTagMap = RecommenderAlgorithm.populateUserTagMap(sam);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_EDUCATION), 1);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_LITERATURE), 1);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_FITNESS), 0);
        Assert.assertEquals((int) userTagMap.get(Tag.TAG_COOKING), 0);
 
    }

    @Test
    public void kyleTest() { 

        User smith = new User("1234", "Smith");
        DatabaseService.save(smith.getUserEntity());
 
        Room poetryRoom = new Room(smith, "How to Teach Poetry", "Learn how to teach Poetry");
        DatabaseService.save(poetryRoom.getRoomEntity());

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Filter roomFilter = new FilterPredicate("__key__", FilterOperator.EQUAL, poetryRoom.getRoomKey());
        Query query = new Query(Room.ROOM_ENTITY_NAME).setFilter(roomFilter);
        PreparedQuery results = datastore.prepare(query);
        System.out.println(results.asSingleEntity());

        // Query queryTwo = new Query(Room.ROOM_ENTITY_NAME);
        // PreparedQuery resultsTwo = datastore.prepare(queryTwo);
        // System.out.println(resultsTwo.asSingleEntity());
    }
}
