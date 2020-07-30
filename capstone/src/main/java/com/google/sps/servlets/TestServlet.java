// set up a doGet and create Class objects and store inside of datastore to see what it outputs

package com.google.sps.servlets;

import java.io.IOException;
import java.io.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.google.sps.data.Form;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.sps.data.Teacher;
import com.google.sps.data.Classroom;
import com.google.sps.data.Student;
import com.google.sps.data.Form;
import com.google.sps.data.ClassKey;



/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/test")
public class TestServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Entity studentEntity = new Entity("Student");
    studentEntity.setProperty("email", "kyle@google.com");
    studentEntity.setProperty("nickname", "kyle");
    studentEntity.setProperty("id", 1020);

    Student student = new Student(studentEntity);

    Entity teacherEntity = new Entity("Teacher");
    teacherEntity.setProperty("email", "teacher@google.com");
    teacherEntity.setProperty("nickname", "teacher");
    teacherEntity.setProperty("id", 1050);

    Teacher teacher = new Teacher(teacherEntity);

    List<Student> students = new ArrayList<>();

    students.add(student);

    System.out.println(students); // Outputs an array with a Student object

    ClassKey key1 = null;

    Entity classroomEntity = new Entity("Classroom");
    classroomEntity.setProperty("teacher", teacher); // just store teacher specific ids

    //"getDatabaseEntity() code
    classroomEntity.setProperty("students", students); // store student references (ids)
    // Iterate through

    classroomEntity.setProperty("subject", "English");
    classroomEntity.setProperty("key", key1);

    Classroom classroom = new Classroom(classroomEntity, students);

    classroom.userInfo(classroom.getTeacher(), classroom.getAllStudents(), "English", classroom.getKey());

    // 

    /*Entity keyEntity = new Entity("Key");
    keyEntity.setProperty("classroom", classroom);
    keyEntity.setProperty("teacher", teacher);
    keyEntity.setProperty("id", 12345678);

    ClassKey key = new ClassKey(keyEntity, 12345678);

    classroomEntity.setProperty("teacher", teacher);
    classroomEntity.setProperty("students", students);
    classroomEntity.setProperty("subject", "English");
    classroomEntity.setProperty("key", key);*/

    //student.userInfo(student.getEmail(), student.getNickname(), student.getId());
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Gson gson = new Gson();
    String json = gson.toJson(teacher);
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {    
    Entity studentEntity = new Entity("Student");
    studentEntity.setProperty("email", "kyle@google.com");
    studentEntity.setProperty("nickname", "kyle");
    studentEntity.setProperty("id", 1020);

    Student student = new Student(studentEntity);

    Entity teacherEntity = new Entity("Teacher");
    teacherEntity.setProperty("email", "teacher@google.com");
    teacherEntity.setProperty("nickname", "teacher");
    teacherEntity.setProperty("id", 1050);

    Teacher teacher = new Teacher(teacherEntity);

    List<Student> students = new ArrayList<>();

    students.add(student);

    ClassKey key1 = null;

    Entity classroomEntity = new Entity("Classroom");
    classroomEntity.setProperty("teacher", teacher);
    classroomEntity.setProperty("students", students);
    classroomEntity.setProperty("subject", "English");
    classroomEntity.setProperty("key", key1);

    Classroom classroom = new Classroom(classroomEntity, students);

    Entity keyEntity = new Entity("Key");
    keyEntity.setProperty("classroom", classroom);
    keyEntity.setProperty("teacher", teacher);
    keyEntity.setProperty("id", 12345678);

    ClassKey key = new ClassKey(keyEntity, 12345678);

    classroomEntity.setProperty("teacher", teacher);
    classroomEntity.setProperty("students", students);
    classroomEntity.setProperty("subject", "English");
    classroomEntity.setProperty("key", key);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(classroomEntity);

    response.sendRedirect("/index.html");
  }
}
