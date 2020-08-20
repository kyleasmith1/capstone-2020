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

    private static final List<String> TEST_CATEGORY_TAGS = Tag.CATEGORY_TAGS;

    private static User TEST_USER_SMITH;
    private static User TEST_USER_JOHN;
    private static User TEST_USER_BILL;
    private static User TEST_USER_SAM;
    private static Room TEST_ROOM_POETRY;
    private static Room TEST_ROOM_RUN;
    private static Room TEST_ROOM_COOK; 
    private static Room TEST_ROOM_COOK_TWO;

    @Before
    public void setUp() {
        helper.setUp();
        TEST_USER_SMITH = new User("1234", "Smith");
        TEST_USER_JOHN = new User("2341", "John");
        TEST_USER_BILL = new User("3412", "Bill");
        TEST_USER_SAM = new User("4123", "Sam");
        DatabaseService.save(TEST_USER_SMITH.getUserEntity());
        DatabaseService.save(TEST_USER_JOHN.getUserEntity());
        DatabaseService.save(TEST_USER_BILL.getUserEntity());
        DatabaseService.save(TEST_USER_SAM.getUserEntity());
 
        TEST_ROOM_POETRY = new Room(TEST_USER_SMITH, "How to Teach Poetry", "Learn how to teach Poetry");
        TEST_ROOM_RUN = new Room(TEST_USER_JOHN, "Run Efficiently", "Improve your running experience");
        TEST_ROOM_COOK = new Room(TEST_USER_BILL, "Iron Chef Master Course", "Cook Better");
        TEST_ROOM_COOK_TWO = new Room(TEST_USER_BILL, "Iron Chef Master Course II", "Cook Better Part 2");
        DatabaseService.save(TEST_ROOM_POETRY.getRoomEntity());
        DatabaseService.save(TEST_ROOM_RUN.getRoomEntity());
        DatabaseService.save(TEST_ROOM_COOK.getRoomEntity());
        DatabaseService.save(TEST_ROOM_COOK_TWO.getRoomEntity());
    }
 
    @After
    public void tearDown() {
        helper.tearDown();
    }
 
    @Test
    public void populateUserTagMapOneTagOneClassroomTest() { 
        TEST_ROOM_RUN.addTag(Tag.TAG_FITNESS);
        TEST_ROOM_RUN.addFollower(TEST_USER_SMITH);
        DatabaseService.save(TEST_ROOM_RUN.getRoomEntity());

        HashMap<String, Integer> userTagMap = RecommenderAlgorithm.populateUserTagMap(TEST_USER_SMITH);
        for(String tag : TEST_CATEGORY_TAGS){
            if(tag.equals(Tag.TAG_FITNESS)){
                Assert.assertEquals((int) userTagMap.get(tag), 1);
                continue;
            }
            Assert.assertEquals((int) userTagMap.get(tag), 0);
        }

    }

    @Test
    public void populateUserTagMapMultipleTagOneClassroomTest() { 
        TEST_ROOM_POETRY.addTag(Tag.TAG_EDUCATION);
        TEST_ROOM_POETRY.addTag(Tag.TAG_LITERATURE);
        TEST_ROOM_POETRY.addFollower(TEST_USER_SMITH);
        DatabaseService.save(TEST_ROOM_POETRY.getRoomEntity());

        HashMap<String, Integer> userTagMap = RecommenderAlgorithm.populateUserTagMap(TEST_USER_SMITH);
        for(String tag : TEST_CATEGORY_TAGS) {
            if(tag.equals(Tag.TAG_EDUCATION) || tag.equals(Tag.TAG_LITERATURE)) {
                Assert.assertEquals((int) userTagMap.get(tag), 1);
                continue;
            }
            Assert.assertEquals((int) userTagMap.get(tag), 0);
        }
    }

    @Test
    public void populateUserTagMapMultipleTagMultipleClassroomTest() { 
        TEST_ROOM_POETRY.addTag(Tag.TAG_EDUCATION);
        TEST_ROOM_POETRY.addTag(Tag.TAG_LITERATURE);
        TEST_ROOM_POETRY.addFollower(TEST_USER_SMITH);
        DatabaseService.save(TEST_ROOM_POETRY.getRoomEntity());

        TEST_ROOM_RUN.addTag(Tag.TAG_FITNESS);
        TEST_ROOM_RUN.addFollower(TEST_USER_SMITH);
        DatabaseService.save(TEST_ROOM_RUN.getRoomEntity());

        HashMap<String, Integer> userTagMap = RecommenderAlgorithm.populateUserTagMap(TEST_USER_SMITH);
        for(String tag : TEST_CATEGORY_TAGS) {
            if(tag.equals(Tag.TAG_FITNESS) || tag.equals(Tag.TAG_EDUCATION) 
                || tag.equals(Tag.TAG_LITERATURE)) {
                Assert.assertEquals((int) userTagMap.get(tag), 1);
                continue;
            }
            Assert.assertEquals((int) userTagMap.get(tag), 0);
        }
    }

    @Test
    public void populateUserTagMapSameTagMultipleClassroomTest() { 
        TEST_ROOM_COOK.addTag(Tag.TAG_COOKING);
        TEST_ROOM_COOK.addFollower(TEST_USER_SMITH);
        DatabaseService.save(TEST_ROOM_COOK.getRoomEntity());

        TEST_ROOM_COOK_TWO.addTag(Tag.TAG_COOKING);
        TEST_ROOM_COOK_TWO.addFollower(TEST_USER_SMITH);
        DatabaseService.save(TEST_ROOM_COOK_TWO.getRoomEntity());

        HashMap<String, Integer> userTagMap = RecommenderAlgorithm.populateUserTagMap(TEST_USER_SMITH);
        for(String tag : TEST_CATEGORY_TAGS) {
            if(tag.equals(Tag.TAG_COOKING)) {
                Assert.assertEquals((int) userTagMap.get(tag), 2);
                continue;
            }
            Assert.assertEquals((int) userTagMap.get(tag), 0);
        }
    }

}
