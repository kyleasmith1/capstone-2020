package com.google.sps.data;
import java.io.*;
import com.google.sps.data.Teacher;
import com.google.sps.data.Classroom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; 
import com.google.appengine.api.datastore.Entity;


public class Key {

    private int id;
    private Classroom classroom;
    private Teacher teacher;

    public Key (Entity keyEntity, int id) {
        this.classroom = (Classroom) keyEntity.getProperty("classroom");
        this.teacher = (Teacher) keyEntity.getProperty("teacher");
        this.id = (Integer) keyEntity.getProperty("id");
    }

    // Setters
    public void setId(List<Integer> idList) {
        Random random = new Random();

        this.id = random.nextInt(100);

        while (idList.contains(this.id)) {
            this.id = random.nextInt(100);
        }

        idList.add(this.id);
        // update datastore
    }

    // Getters
    public int getId() {
        assert this.id != -1: "ID needs to be created";
        return this.id;
    }

    public Classroom getClassroom() {
        return this.classroom;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public Entity toDatastoreEntity(){
        Entity keyEntity = new Entity("Key");
        keyEntity.setProperty("classroom", this.classroom);
        keyEntity.setProperty("teacher", this.teacher);
        keyEntity.setProperty("id", this.id);
        return keyEntity;
    }
}