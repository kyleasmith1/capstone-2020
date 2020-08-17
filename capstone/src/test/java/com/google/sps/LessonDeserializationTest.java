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
import java.io.IOException;
import com.google.appengine.api.datastore.Entity;

// Update tests in next PR

@RunWith(JUnit4.class)
public final class LessonDeserializationTest {

    //Helper to set local datastore service
    private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
          .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

    private static final String TEST_TITLE = "Piano Lessons";
    private static final String TEST_DESCRIPTION = "Easy lessons for beginners";
    private static final String TEST_URL = "www.piano.com";
    private static final String TEST_EDIT_URL = "www.piano.com/edit";

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void deserializeJsonGeneralTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonDeserializationTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonDeserializationTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonDeserializationTest.TEST_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonDeserializationTest.TEST_EDIT_URL);

        try{ 
            Lesson lesson = Lesson.deserializeJson(jsonObject.toString());

            Assert.assertEquals(lesson.getType(), Lesson.TYPE_FORM);
            Assert.assertEquals(lesson.getTitle(), LessonDeserializationTest.TEST_TITLE);
            Assert.assertEquals(lesson.getDescription(), LessonDeserializationTest.TEST_DESCRIPTION);
            Assert.assertEquals(lesson.getIsDraft(), false);
            Assert.assertTrue(lesson instanceof Form);
            Form form = (Form) lesson; 
            Assert.assertEquals(form.getEditUrl(), LessonDeserializationTest.TEST_URL);
            Assert.assertEquals(form.getUrl(), LessonDeserializationTest.TEST_EDIT_URL);
            
        } catch (IOException e) {
            Assert.fail("IOException Thrown");
        }
    }

    @Test
    public void deserializeJsonMissingFieldTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonDeserializationTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonDeserializationTest.TEST_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonDeserializationTest.TEST_EDIT_URL);

        try{ 
            Lesson lesson = Lesson.deserializeJson(jsonObject.toString());
            Assert.fail();
        } catch(NullPointerException e) {
            return;
        } catch(Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void deserializeJsonMissingTypeTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonDeserializationTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonDeserializationTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonDeserializationTest.TEST_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonDeserializationTest.TEST_EDIT_URL);

        try{ 
            Lesson lesson = Lesson.deserializeJson(jsonObject.toString());
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            Assert.fail();
        } 
    }

    @Test
    public void deserializeJsonWrongTypeTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, "MalformedForm");
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonDeserializationTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonDeserializationTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonDeserializationTest.TEST_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonDeserializationTest.TEST_EDIT_URL);

        try{ 
            Lesson lesson = Lesson.deserializeJson(jsonObject.toString());
            Assert.fail();
        } catch (IOException e) {
            return;
        } catch (Exception e) {
            Assert.fail();
        } 
    }

    @Test
    public void deserializeJsonExtraFieldTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonDeserializationTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonDeserializationTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonDeserializationTest.TEST_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonDeserializationTest.TEST_EDIT_URL);
        jsonObject.addProperty("Random Field", "Random Value");

        try{ 
            Lesson lesson = Lesson.deserializeJson(jsonObject.toString());

            Assert.assertEquals(lesson.getType(), Lesson.TYPE_FORM);
            Assert.assertEquals(lesson.getTitle(), LessonDeserializationTest.TEST_TITLE);
            Assert.assertEquals(lesson.getDescription(), LessonDeserializationTest.TEST_DESCRIPTION);
            Assert.assertEquals(lesson.getIsDraft(), false);
            Assert.assertTrue(lesson instanceof Form);
            Form form = (Form) lesson; 
            Assert.assertEquals(form.getEditUrl(), LessonDeserializationTest.TEST_URL);
            Assert.assertEquals(form.getUrl(), LessonDeserializationTest.TEST_EDIT_URL);
            
        } catch (IOException e) {
            Assert.fail("IOException Thrown");
        }
    }

    @Test
    public void serializeJsonGeneralTest() {
        Entity entity = new Entity("Lesson");
        entity.setProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, LessonDeserializationTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonDeserializationTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, LessonDeserializationTest.TEST_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, LessonDeserializationTest.TEST_EDIT_URL);

        try{ 
            Lesson lesson = Lesson.serializeJson(entity);

            Assert.assertEquals(lesson.getType(), Lesson.TYPE_FORM);
            Assert.assertEquals(lesson.getTitle(), LessonDeserializationTest.TEST_TITLE);
            Assert.assertEquals(lesson.getDescription(), LessonDeserializationTest.TEST_DESCRIPTION);
            Assert.assertTrue(lesson instanceof Form);
            Form form = (Form) lesson; 
            Assert.assertEquals(form.getEditUrl(), LessonDeserializationTest.TEST_URL);
            Assert.assertEquals(form.getUrl(), LessonDeserializationTest.TEST_EDIT_URL);
            
        } catch (IOException e) {
            Assert.fail("IOException Thrown");
        }

    }

    @Test
    public void serializeJsonMissingTypeTest() {
        Entity entity = new Entity("Lesson");
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, LessonDeserializationTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonDeserializationTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, LessonDeserializationTest.TEST_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, LessonDeserializationTest.TEST_EDIT_URL);

        try{ 
            Lesson lesson = Lesson.serializeJson(entity);
            Assert.fail();
        } catch (IOException e) {
            Assert.fail();
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            Assert.fail();
        }

    }

    @Test
    public void serializeJsonWrongTypeTest() {
        Entity entity = new Entity("Lesson");
        entity.setProperty(Lesson.TYPE_PROPERTY_KEY, "MalformedForm");
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, LessonDeserializationTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonDeserializationTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, LessonDeserializationTest.TEST_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, LessonDeserializationTest.TEST_EDIT_URL);

        try{ 
            Lesson lesson = Lesson.serializeJson(entity);
            Assert.fail();
        } catch (IOException e) {
            return;
        } catch (Exception e) {
            Assert.fail();
        }

    }

}
