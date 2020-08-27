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
import com.google.appengine.api.datastore.KeyFactory;
import java.util.List;
import java.util.ArrayList;
import java.lang.NoSuchMethodException;
import java.lang.InstantiationException;
import java.lang.IllegalArgumentException;
import java.lang.reflect.Modifier;
import java.lang.RuntimeException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

public class FilterService {

    private static final String KEY_PROPERTY = "__key__";

    public static Entity getEntity(String entity_name, Long id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = new KeyFactory.Builder(entity_name, id).getKey();
        Filter filter = new FilterPredicate(KEY_PROPERTY, FilterOperator.EQUAL, key);
        Query query = new Query(entity_name).setFilter(filter);
        PreparedQuery result = datastore.prepare(query);
        return result.asSingleEntity();
    }

    public static <T> List<T> getEntityListByKeyedProperty(Class<T> clazz, String entity_name, String property, Key key) {
        Constructor<T> entityConstructor = null;
        try {
            entityConstructor = clazz.getDeclaredConstructor(Entity.class);
            if (!Modifier.isPublic(entityConstructor.getModifiers())) {
                throw new IllegalArgumentException("Constuctor for " + clazz.toString() + " which accepts single database entity is not public");
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(clazz.toString() + " does not have a constructor that takes a single entity");
        }

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Filter filter = new FilterPredicate(property, FilterOperator.EQUAL, key);
        Query query = new Query(entity_name).setFilter(filter);
        PreparedQuery results = datastore.prepare(query);

        List<T> list = new ArrayList<T>();
        for(Entity entity : results.asIterable()){
            try {
                list.add(entityConstructor.newInstance(entity));
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate new instance of " + clazz.toString());
            }
        }

        return list;
    }
}