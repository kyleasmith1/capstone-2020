package com.google.sps.data;

import java.io.IOException;
import java.io.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.google.sps.data.Classroom;
import com.google.sps.data.Form;
import com.google.sps.data.User;

import com.google.sps.service.DatabaseService;

public class Student extends User {

    public Student(Entity entity) {
        super(entity);
    }

    public Student(String email, String nickname) {
        super(email, nickname);
    }

    public void joinClassroom(Key classroomKey) {
        try {
            Classroom classroom = DatabaseService.getClassroom(classroomKey);
            classroom.addStudent(this.delegate.getKey());
        }
        catch (EntityNotFoundException e) {
            System.out.println("Classroom does not exist.");
        }
    }

    public void leaveClassroom(Key classroomKey) {
        try {
            Classroom classroom = DatabaseService.getClassroom(classroomKey);
            classroom.removeStudent(this.delegate.getKey());
        }
        catch (EntityNotFoundException e) {
            System.out.println("Classroom does not exist.");
        }
    }
}