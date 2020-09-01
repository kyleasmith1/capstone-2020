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
import com.google.sps.data.CachedInterestVector;
import com.google.sps.service.DatabaseService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EmbeddedEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.lang.Math;

@RunWith(JUnit4.class)
public final class CachedInterestVectorTest {

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
    public void embeddedEntityToHashMapTest(){
        EmbeddedEntity embeddedVectorMap = new EmbeddedEntity();
        embeddedVectorMap.setProperty(Tag.EDUCATION.getTag(), (2.0/3.0));
        embeddedVectorMap.setProperty(Tag.COOKING.getTag(), (2.0/3.0));
        embeddedVectorMap.setProperty(Tag.FITNESS.getTag(), (1.0/3.0));

        HashMap<String, Double> vectorHashMap = CachedInterestVector.embeddedEntityToHashMap(embeddedVectorMap);
        Assert.assertEquals(vectorHashMap.get(Tag.EDUCATION.getTag()), (2.0/3.0), .1);
        Assert.assertEquals(vectorHashMap.get(Tag.COOKING.getTag()), (2.0/3.0), .1);
        Assert.assertEquals(vectorHashMap.get(Tag.FITNESS.getTag()), (1.0/3.0), .1);

    }

    @Test
    public void hashMapToEmbeddedEntityTest(){
        HashMap<String, Double> vectorHashMap = new HashMap<>();
        vectorHashMap.put(Tag.EDUCATION.getTag(), (2.0/3.0));
        vectorHashMap.put(Tag.COOKING.getTag(), (2.0/3.0));
        vectorHashMap.put(Tag.FITNESS.getTag(), (1.0/3.0));

        EmbeddedEntity embeddedVectorMap = CachedInterestVector.hashMapToEmbeddedEntity(vectorHashMap);

        Assert.assertEquals((double) embeddedVectorMap.getProperty(Tag.EDUCATION.getTag()), (2.0/3.0), .1);
        Assert.assertEquals((double) embeddedVectorMap.getProperty(Tag.COOKING.getTag()), (2.0/3.0), .1);
        Assert.assertEquals((double) embeddedVectorMap.getProperty(Tag.FITNESS.getTag()), (1.0/3.0), .1);

    }
}
