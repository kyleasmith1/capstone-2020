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

import com.google.sps.data.User;
import com.google.sps.data.Student;
import com.google.sps.data.Teacher;
import com.google.sps.data.Form;

import com.google.sps.service.DatabaseService;

public class Classroom {

    private Entity entity;

    public Classroom(Entity entity) {
        this.entity = entity;
    }

    public Classroom(Key teacher, List<Key> students, List<Key> forms, String subject) {
        this.entity = new Entity("Classroom");
        this.entity.setProperty("teacher", teacher);
        this.entity.setProperty("students", students);
        this.entity.setProperty("forms", forms);
        this.entity.setProperty("subject", subject);
    }

    public void changeTeacher(Key teacherKey) {
        this.entity.setProperty("teacher", teacherKey);
        DatabaseService.save(this.entity);
    }

    public Key getTeacher() {
        return (Key) this.entity.getProperty("teacher");
    }

    public String getSubject() {
        return (String) this.entity.getProperty("subject");
    }

    public Entity getClassroomEntity() {
        return (Entity) this.entity;
    }

    @SuppressWarnings("unchecked")
    public List<Key> getAllStudents() {
        return (ArrayList<Key>) this.entity.getProperty("students");
    }

    @SuppressWarnings("unchecked")
    public List<Key> getAllForms() { 
        return (ArrayList<Key>) this.entity.getProperty("forms"); 
    }
    
    @SuppressWarnings("unchecked")
    public void addStudent(Key studentKey) { 
        List<Key> students = (ArrayList<Key>) this.entity.getProperty("students");
        students.add(studentKey);

        this.entity.setProperty("students", students);

        DatabaseService.save(this.entity);
    }

    @SuppressWarnings("unchecked")
    public void addForm(Key formKey) {
        List<Key> forms = (ArrayList<Key>) this.entity.getProperty("forms");
        forms.add(formKey);

        this.entity.setProperty("forms", forms);

        DatabaseService.save(this.entity);
    }

    @SuppressWarnings("unchecked")
    public void removeStudent(Key studentKey) {
        List<Key> students = (ArrayList<Key>) this.entity.getProperty("students");
        students.remove(studentKey);

        this.entity.setProperty("students", students);

        DatabaseService.save(this.entity);
    }

    @SuppressWarnings("unchecked")
    public void removeForm(Key formKey) {
        List<Key> forms = (ArrayList<Key>) this.entity.getProperty("forms");
        forms.remove(formKey);

        this.entity.setProperty("forms", forms);

        DatabaseService.save(this.entity);
    }

    @SuppressWarnings("unchecked")
    public boolean isStudentInClass(Key studentKey) {
        List<Key> students = (ArrayList<Key>) this.entity.getProperty("students");

        assert students.size() > 0: "There are no students in this class!";

        if (students.lastIndexOf(studentKey) == -1) {
            return false;
        } else {
            return true;
        }
    }
}