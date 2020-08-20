package com.google.sps.service;

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
import com.google.appengine.api.datastore.KeyFactory.Builder;

public class FilterService {

    private static final String KEY_PROPERTY = "__key__";

    public static Entity getEntity(String entity, Long id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = new Builder(entity, id).getKey();
        Filter filter = new FilterPredicate(KEY_PROPERTY, FilterOperator.EQUAL, key);
        Query query = new Query(entity).setFilter(filter);
        PreparedQuery result = datastore.prepare(query);
        return result.asSingleEntity();
    }

    public static PreparedQuery getAll(String entity, String property, Key key) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Filter filter = new FilterPredicate(property, FilterOperator.EQUAL, key);
        Query query = new Query(entity).setFilter(filter);
        PreparedQuery results = datastore.prepare(query);
        return results;
    }
}