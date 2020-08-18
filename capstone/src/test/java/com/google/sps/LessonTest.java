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
import com.google.gson.JsonArray;
import com.google.sps.data.Form;
import com.google.sps.data.Video;
import com.google.sps.data.Image;
import com.google.sps.data.Content;
import com.google.sps.data.Lesson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import com.google.appengine.api.datastore.Entity;
import java.util.Iterator;

@RunWith(JUnit4.class)
public final class LessonTest {

    //Helper to set local datastore service
    private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
          .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

    private static final String TEST_TITLE = "Piano Lessons";
    private static final String TEST_DESCRIPTION = "Easy lessons for beginners";
    private static final String TEST_URL = "www.piano.com";
    private static final String TEST_EDIT_URL = "www.piano.com/edit";
    private static final String TEST_CONTENT = "The average piano has 7 octaves";

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void deserializeJsonFormTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonTest.TEST_EDIT_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonTest.TEST_URL);

        Lesson lesson = Lesson.deserializeJson(jsonObject.toString());

        Assert.assertEquals(lesson.getType(), Lesson.TYPE_FORM);
        Assert.assertEquals(lesson.getTitle(), LessonTest.TEST_TITLE);
        Assert.assertEquals(lesson.getDescription(), LessonTest.TEST_DESCRIPTION);
        Assert.assertEquals(lesson.getIsDraft(), false);
        Assert.assertTrue(lesson instanceof Form);
        Form form = (Form) lesson; 
        Assert.assertEquals(form.getEditUrl(), LessonTest.TEST_EDIT_URL);
        Assert.assertEquals(form.getUrl(), LessonTest.TEST_URL);
    }

    @Test
    public void deserializeJsonVideoTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_VIDEO);
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Video.URL_PROPERTY_KEY, LessonTest.TEST_URL);

        Lesson lesson = Lesson.deserializeJson(jsonObject.toString());

        Assert.assertEquals(lesson.getType(), Lesson.TYPE_VIDEO);
        Assert.assertEquals(lesson.getTitle(), LessonTest.TEST_TITLE);
        Assert.assertEquals(lesson.getDescription(), LessonTest.TEST_DESCRIPTION);
        Assert.assertEquals(lesson.getIsDraft(), false);
        Assert.assertTrue(lesson instanceof Video);
        Video video = (Video) lesson; 
        Assert.assertEquals(video.getUrl(), LessonTest.TEST_URL);
    }

    @Test
    public void deserializeJsonImageTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_IMAGE);
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Image.URL_PROPERTY_KEY, LessonTest.TEST_URL);

        Lesson lesson = Lesson.deserializeJson(jsonObject.toString());

        Assert.assertEquals(lesson.getType(), Lesson.TYPE_IMAGE);
        Assert.assertEquals(lesson.getTitle(), LessonTest.TEST_TITLE);
        Assert.assertEquals(lesson.getDescription(), LessonTest.TEST_DESCRIPTION);
        Assert.assertEquals(lesson.getIsDraft(), false);
        Assert.assertTrue(lesson instanceof Image);
        Image image = (Image) lesson; 
        Assert.assertEquals(image.getUrl(), LessonTest.TEST_URL);
    }

    @Test
    public void deserializeJsonContentTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_CONTENT);
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Content.CONTENT_PROPERTY_KEY, LessonTest.TEST_CONTENT);

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(LessonTest.TEST_URL);
        jsonArray.add(LessonTest.TEST_EDIT_URL);
        jsonObject.add(Content.URLS_PROPERTY_KEY, (JsonElement) jsonArray);

        ArrayList<String> testList = new ArrayList<>();
        Iterator<JsonElement> listIterator = jsonArray.iterator();
        while(listIterator.hasNext()){
            testList.add(listIterator.next().getAsString());
        }
        
        Lesson lesson = Lesson.deserializeJson(jsonObject.toString());

        Assert.assertEquals(lesson.getType(), Lesson.TYPE_CONTENT);
        Assert.assertEquals(lesson.getTitle(), LessonTest.TEST_TITLE);
        Assert.assertEquals(lesson.getDescription(), LessonTest.TEST_DESCRIPTION);
        Assert.assertEquals(lesson.getIsDraft(), false);
        Assert.assertTrue(lesson instanceof Content);
        Content content = (Content) lesson; 
        Assert.assertEquals(content.getContent(), LessonTest.TEST_CONTENT);
        Assert.assertArrayEquals(content.getAllUrls().toArray(), testList.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void deserializeJsonMissingFieldTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonTest.TEST_EDIT_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonTest.TEST_URL);
        
        Lesson lesson = Lesson.deserializeJson(jsonObject.toString());
    }

    @Test
    public void deserializeJsonMissingTypeTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonTest.TEST_EDIT_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonTest.TEST_URL);

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
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonTest.TEST_EDIT_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonTest.TEST_URL);

        try{ 
            Lesson lesson = Lesson.deserializeJson(jsonObject.toString());
            Assert.fail();
        } catch (IllegalArgumentException e) {
            return;
        } catch (Exception e) {
            Assert.fail();
        } 
    }

    @Test
    public void deserializeJsonExtraFieldTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Lesson.TYPE_PROPERTY_KEY, Lesson.TYPE_FORM);
        jsonObject.addProperty(Lesson.TITLE_PROPERTY_KEY, LessonTest.TEST_TITLE);
        jsonObject.addProperty(Lesson.DESCRIPTION_PROPERTY_KEY, LessonTest.TEST_DESCRIPTION);
        jsonObject.addProperty(Form.EDIT_URL_PROPERTY_KEY, LessonTest.TEST_EDIT_URL);
        jsonObject.addProperty(Form.URL_PROPERTY_KEY, LessonTest.TEST_URL);
        jsonObject.addProperty("Random Field", "Random Value");

        try{ 
            Lesson lesson = Lesson.deserializeJson(jsonObject.toString());

            Assert.assertEquals(lesson.getType(), Lesson.TYPE_FORM);
            Assert.assertEquals(lesson.getTitle(), LessonTest.TEST_TITLE);
            Assert.assertEquals(lesson.getDescription(), LessonTest.TEST_DESCRIPTION);
            Assert.assertEquals(lesson.getIsDraft(), false);
            Assert.assertTrue(lesson instanceof Form);
            Form form = (Form) lesson; 
            Assert.assertEquals(form.getEditUrl(), LessonTest.TEST_EDIT_URL);
            Assert.assertEquals(form.getUrl(), LessonTest.TEST_URL);
            
        } catch (IllegalArgumentException e) {
            Assert.fail("IllegalArgumentException Thrown");
        }
    }

}
