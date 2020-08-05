package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.List;
import com.google.sps.service.DatabaseService;

public class Classroom {
    protected static final String CLASSROOM = "Classroom";
    protected static final String TEACHER = "teacher";
    protected static final String SUBJECT = "subject";
    protected static final String STUDENTS = "students";
    protected static final String FORMS = "forms";

    protected Entity entity;

    public Classroom(Entity entity) {
        this.entity = entity;
    }

    public Classroom(User teacher, String subject) { 
        this.entity = new Entity(Classroom.CLASSROOM);
        this.entity.setProperty(Classroom.TEACHER, teacher.getUserKey());
        this.entity.setProperty(Classroom.SUBJECT, subject);
        this.entity.setProperty(Classroom.STUDENTS, new ArrayList<>());
        this.entity.setProperty(Classroom.FORMS, new ArrayList<>());
    }

    public void setTeacher(User teacher) { 
        this.entity.setProperty(Classroom.TEACHER, teacher.getUserKey());
    }
    
    public Key getTeacher() {
        return (Key) this.entity.getProperty(Classroom.TEACHER);
    }

    public String getSubject() {
        return (String) this.entity.getProperty(Classroom.SUBJECT);
    }

    public Entity getClassroomEntity() {
        return this.entity;
    }

    @SuppressWarnings("unchecked")
    public List<Key> getAllStudents() {
        return (ArrayList<Key>) this.entity.getProperty(Classroom.STUDENTS);
    }

    @SuppressWarnings("unchecked")
    public List<Key> getAllForms() { 
        return (ArrayList<Key>) this.entity.getProperty(Classroom.FORMS); 
    }
    
    @SuppressWarnings("unchecked")
    public void addStudent(User student) {
        List<Key> students = (ArrayList<Key>) this.entity.getProperty(Classroom.STUDENTS);
        students.add(student.getUserKey());

        this.entity.setProperty(STUDENTS, students);
    }

    @SuppressWarnings("unchecked")
    public void removeStudent(User student) {
        List<Key> students = (ArrayList<Key>) this.entity.getProperty(Classroom.STUDENTS);
        students.remove(student.getUserKey());

        this.entity.setProperty(Classroom.STUDENTS, students);
    }

    @SuppressWarnings("unchecked")
    public void addForm(Form form) {
        List<Key> forms = (ArrayList<Key>) this.entity.getProperty(Classroom.FORMS);
        forms.add(form.getFormKey());

        this.entity.setProperty(FORMS, forms);
    }

    @SuppressWarnings("unchecked")
    public void removeForm(Form form) {
        List<Key> forms = (ArrayList<Key>) this.entity.getProperty(Classroom.FORMS);
        forms.remove(form.getFormKey());

        this.entity.setProperty(FORMS, forms);
    }

    @SuppressWarnings("unchecked")
    public boolean isStudentInClass(User student) {
        List<Key> students = (ArrayList<Key>) this.entity.getProperty(Classroom.STUDENTS);

        assert students.size() > 0: "There are no students in this class!";

        if (students.lastIndexOf(student.getUserKey()) == -1) {
            return false;
        } else {
            return true;
        }
    }
}