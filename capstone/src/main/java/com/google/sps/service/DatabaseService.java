package com.google.sps.service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.google.sps.data.User;
import com.google.sps.data.Form;
import com.google.sps.data.Room;
import com.google.sps.data.Lesson;

public class DatabaseService {
    public static User getUser(Key key) throws EntityNotFoundException { 
        return new User(DatastoreServiceFactory.getDatastoreService().get(key));
    }

    public static Room getRoom(Key key) throws EntityNotFoundException {
        return new Room(DatastoreServiceFactory.getDatastoreService().get(key));  
    }

    public static Form getForm(Key key) throws EntityNotFoundException {
        return new Form(DatastoreServiceFactory.getDatastoreService().get(key));
    }

    public static Lesson getLesson(Key key) throws EntityNotFoundException {
        return new Form(DatastoreServiceFactory.getDatastoreService().get(key));
    }

    public static void save(Entity entity) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(entity);
    }  
}