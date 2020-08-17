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

@RunWith(JUnit4.class)
public final class LessonTest {

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
    public void serializeJsonGeneralTest() {
        Entity entity = new Entity("Lesson");
        entity.setProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, LessonTest.TEST_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, LessonTest.TEST_EDIT_URL);

        try{ 
            Lesson lesson = Lesson.serializeJson(entity);

            Assert.assertEquals(lesson.getType(), Lesson.TYPE_FORM);
            Assert.assertEquals(lesson.getTitle(), LessonTest.TEST_TITLE);
            Assert.assertEquals(lesson.getDescription(), LessonTest.TEST_DESCRIPTION);
            Assert.assertTrue(lesson instanceof Form);
            Form form = (Form) lesson; 
            Assert.assertEquals(form.getEditUrl(), LessonTest.TEST_URL);
            Assert.assertEquals(form.getUrl(), LessonTest.TEST_EDIT_URL);
            
        } catch (IOException e) {
            Assert.fail("IOException Thrown");
        }

    }

    @Test
    public void serializeJsonMissingTypeTest() {
        Entity entity = new Entity("Lesson");
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, LessonTest.TEST_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, LessonTest.TEST_EDIT_URL);

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
        entity.setProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, LessonTest.TEST_URL);
        entity.setProperty(Form.URL_PROPERTY_KEY, LessonTest.TEST_EDIT_URL);

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
