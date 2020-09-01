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
import com.google.sps.data.Room;
import com.google.sps.data.User;
import com.google.sps.data.Tag;
import com.google.sps.data.RecommenderAlgorithm;
import com.google.sps.service.DatabaseService;
import com.google.appengine.api.datastore.Entity;
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

        TEST_ROOM_POETRY = new Room(TEST_USER_SMITH, "How to Teach Poetry", "Learn how to teach Poetry");
        TEST_ROOM_RUN = new Room(TEST_USER_JOHN, "Run Efficiently", "Improve your running experience");
        TEST_ROOM_COOK = new Room(TEST_USER_BILL, "Iron Chef Master Course", "Cook Better");
        TEST_ROOM_COOK_TWO = new Room(TEST_USER_BILL, "Iron Chef Master Course II", "Cook Better Part 2");
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void alwaysPass(){
        
    }
}
