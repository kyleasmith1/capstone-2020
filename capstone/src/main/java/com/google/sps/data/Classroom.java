package com.google.sps.data;
import java.io.*;
import com.google.sps.data.Teacher;
import com.google.sps.data.Student;
import com.google.sps.data.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; 
import com.google.appengine.api.datastore.Entity;


public class Classroom {

    private Teacher teacher;
    private List<Student> students = new ArrayList<>();
    private List<Form> forms = new ArrayList<>();
    private String subject;
    private Key key;

    public Classroom(Entity classroomEntity, List<Student> students, List<Form> forms) {
        this.teacher = (Teacher) classroomEntity.getProperty("teacher");
        this.students = students;
        this.forms = forms;
        this.subject = (String) classroomEntity.getProperty("subject");
        this.key = (Key) classroomEntity.getProperty("key");
    }

    // Setters
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        // update
    }

    public void setSubject(String subject) {
        this.subject = subject;
        // update
    }

    public void setKey(Key key) {
        this.key = key;
        // update
    }

    // Getters
    public Teacher getTeacher() {
        return this.teacher;
    }

    public String getSubject() {
        return this.subject;
    }

    public List<Student> getAllStudents() {
        return this.students;
    }

    public List<Form> getForms() {
        return this.forms;
    }

    public Key getKey() {
        assert this.key.getId() != -1: "The key doesn't exist.";
        return this.key;
    }

    // Database
    public void addStudent(Student student) { 
        students.add(student);
        // update datstore
    }

    public void addForm(Form form) {
        forms.add(form);
        // update datastore
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        // update datastore
    }

    public boolean isStudentInClass(Student student) {
        assert students.size() > 0: "There are no students in this class!";

        if (students.lastIndexOf(student) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Entity toDatastoreEntity(){
        Entity classroomEntity = new Entity("Classroom");
        classroomEntity.setProperty("teacher", this.teacher);
        classroomEntity.setProperty("students", this.students);
        classroomEntity.setProperty("forms", this.forms);
        classroomEntity.setProperty("subject", this.subject);
        classroomEntity.setProperty("key", this.key);
        return classroomEntity;
    }
}