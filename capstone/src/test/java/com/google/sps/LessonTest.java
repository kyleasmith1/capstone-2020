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

@RunWith(JUnit4.class)
public final class LessonTest {

    // Maximum eventual consistency.
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
}