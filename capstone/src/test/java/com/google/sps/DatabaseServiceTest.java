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
import com.google.sps.data.Form;
import com.google.sps.data.Lesson;
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
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.sps.service.DatabaseService;

@RunWith(JUnit4.class)
public final class DatabaseServiceTest {

    //Helper to set local datastore service
    private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private static final String TEST_TITLE = "Piano Lessons";
    private static final String TEST_DESCRIPTION = "Easy lessons for beginners";
    private static final String TEST_URL = "www.piano.com";
    private static final String TEST_EDIT_URL = "www.piano.com/edit";

    private static DatastoreService datastore;

    @Before
    public void setUp() {
        helper.setUp();
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
   
    @Test
    public void serializeJsonGeneralTest() throws EntityNotFoundException {
        Entity entity = new Entity(Lesson.LESSON_ENTITY_NAME);
        entity.setProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, DatabaseServiceTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, DatabaseServiceTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, DatabaseServiceTest.TEST_EDIT_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, DatabaseServiceTest.TEST_URL);
        DatabaseService.save(entity);

        Query query = new Query(Lesson.LESSON_ENTITY_NAME);
        PreparedQuery results = datastore.prepare(query);
        Lesson lesson = DatabaseService.getLesson(results.asSingleEntity().getKey());

        Assert.assertEquals(lesson.getType(), Lesson.TYPE_FORM);
        Assert.assertEquals(lesson.getTitle(), DatabaseServiceTest.TEST_TITLE);
        Assert.assertEquals(lesson.getDescription(), DatabaseServiceTest.TEST_DESCRIPTION);
        Assert.assertTrue(lesson instanceof Form);
        Form form = (Form) lesson; 
        Assert.assertEquals(form.getEditUrl(), DatabaseServiceTest.TEST_EDIT_URL);
        Assert.assertEquals(form.getUrl(), DatabaseServiceTest.TEST_URL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void serializeJsonMissingTypeTest() throws EntityNotFoundException {
        Entity entity = new Entity(Lesson.LESSON_ENTITY_NAME);
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, DatabaseServiceTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, DatabaseServiceTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, DatabaseServiceTest.TEST_EDIT_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, DatabaseServiceTest.TEST_URL);
        DatabaseService.save(entity);
        
        Query query = new Query(Lesson.LESSON_ENTITY_NAME);
        PreparedQuery results = datastore.prepare(query);
        Lesson lesson = DatabaseService.getLesson(results.asSingleEntity().getKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void serializeJsonWrongTypeTest() throws EntityNotFoundException {
        Entity entity = new Entity(Lesson.LESSON_ENTITY_NAME);
        entity.setProperty(Lesson.TYPE_PROPERTY_KEY, "MalformedForm");
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, DatabaseServiceTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, DatabaseServiceTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, DatabaseServiceTest.TEST_EDIT_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, DatabaseServiceTest.TEST_URL);
        DatabaseService.save(entity);

        Query query = new Query(Lesson.LESSON_ENTITY_NAME);
        PreparedQuery results = datastore.prepare(query);
        Lesson lesson = DatabaseService.getLesson(results.asSingleEntity().getKey());
    }
}
