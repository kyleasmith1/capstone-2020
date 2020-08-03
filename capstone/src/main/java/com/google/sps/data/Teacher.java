package com.google.sps.data;

import java.io.IOException;
import java.io.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.google.sps.data.Classroom;
import com.google.sps.data.User;
import com.google.sps.data.Form;

import com.google.sps.service.DatabaseService;

public class Teacher extends User {

    public Teacher(Entity entity) {
        super(entity);
    }

    public Teacher(String email, String nickname) {
        super(email, nickname);
    }

    public void addStudent(Key classroomKey, Key studentKey) { 
        try {
            Classroom classroom = DatabaseService.getClassroom(classroomKey);
            classroom.addStudent(studentKey);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Classroom does not exist.");
        }
    }

    public void removeStudent(Key classroomKey, Key studentKey) { 
        try {
            Classroom classroom = DatabaseService.getClassroom(classroomKey);
            classroom.removeStudent(studentKey);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Classroom does not exist.");
        }
    }

    public void addForm(Key classroomKey, Key formKey) { 
        try {
            Classroom classroom = DatabaseService.getClassroom(classroomKey);
            classroom.addForm(formKey);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Classroom does not exist.");
        }
    }

    public void removeForm(Key classroomKey, Key formKey) { 
        try {
            Classroom classroom = DatabaseService.getClassroom(classroomKey);
            classroom.removeForm(formKey);
        }
        catch (EntityNotFoundException e) {
            System.out.println("Classroom does not exist.");
        }
    }
}