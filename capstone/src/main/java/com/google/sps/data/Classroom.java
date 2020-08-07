package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.List;
import com.google.sps.service.DatabaseService;

public class Classroom {
    public static final String CLASSROOM_ENTITY_NAME = "Classroom";
    public static final String TEACHER_PROPERTY_KEY = "teacher";
    public static final String SUBJECT_PROPERTY_KEY = "subject";
    public static final String STUDENTS_PROPERTY_KEY = "students";
    public static final String FORMS_PROPERTY_KEY = "forms";

    private Entity entity;

    public Classroom(Entity entity) {
        this.entity = entity;
    }

    public Classroom(User teacher, String subject) { 
        this.entity = new Entity(Classroom.CLASSROOM_ENTITY_NAME);
        this.entity.setProperty(Classroom.TEACHER_PROPERTY_KEY, teacher.getUserKey());
        this.entity.setProperty(Classroom.SUBJECT_PROPERTY_KEY, subject);
        this.entity.setProperty(Classroom.STUDENTS_PROPERTY_KEY, new ArrayList<>());
        this.entity.setProperty(Classroom.FORMS_PROPERTY_KEY, new ArrayList<>());
    }

    public void setTeacher(User teacher) { 
        this.entity.setProperty(Classroom.TEACHER_PROPERTY_KEY, teacher.getUserKey());
    }
    
    public Key getTeacher() {
        return (Key) this.entity.getProperty(Classroom.TEACHER_PROPERTY_KEY);
    }

    public String getSubject() {
        return (String) this.entity.getProperty(Classroom.SUBJECT_PROPERTY_KEY);
    }

    public Entity getClassroomEntity() {
        return this.entity;
    }

    @SuppressWarnings("unchecked")
    public List<Key> getAllStudents() {
        return (ArrayList<Key>) this.entity.getProperty(Classroom.STUDENTS_PROPERTY_KEY);
    }

    @SuppressWarnings("unchecked")
    public List<Key> getAllForms() { 
        return (ArrayList<Key>) this.entity.getProperty(Classroom.FORMS_PROPERTY_KEY); 
    }
    
    @SuppressWarnings("unchecked")
    public void addStudent(User student) {
        List<Key> students = (ArrayList<Key>) this.entity.getProperty(Classroom.STUDENTS_PROPERTY_KEY);
        students.add(student.getUserKey());
    }

    @SuppressWarnings("unchecked")
    public void removeStudent(User student) {
        List<Key> students = (ArrayList<Key>) this.entity.getProperty(Classroom.STUDENTS_PROPERTY_KEY);
        students.remove(student.getUserKey());
    }

    @SuppressWarnings("unchecked")
    public void addForm(Form form) {
        List<Key> forms = (ArrayList<Key>) this.entity.getProperty(Classroom.FORMS_PROPERTY_KEY);
        forms.add(form.getFormKey());
    }

    @SuppressWarnings("unchecked")
    public void removeForm(Form form) {
        List<Key> forms = (ArrayList<Key>) this.entity.getProperty(Classroom.FORMS_PROPERTY_KEY);
        forms.remove(form.getFormKey());
    }

    @SuppressWarnings("unchecked")
    public boolean isStudentInClass(User student) {
        List<Key> students = (ArrayList<Key>) this.entity.getProperty(Classroom.STUDENTS_PROPERTY_KEY);
        return (!(students.lastIndexOf(student.getUserKey()) == -1));
    }
}