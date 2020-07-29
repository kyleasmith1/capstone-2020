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
import com.google.sps.data.Student;
import com.google.sps.data.Form;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/test")
public class TestServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // ArrayList<Student> students = new ArrayList<>();

    //
    Entity studentEntity = new Entity("Student");
    studentEntity.setProperty("email", "kyle@google.com");
    studentEntity.setProperty("nickname", "kyle");
    studentEntity.setProperty("id", 1020);

    Student student = new Student(studentEntity);
    //
    
    //Query query = new Query("Students");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    //PreparedQuery results = datastore.prepare(query);

    //System.out.println(results);

    /*for (Entity entity : results.asIterable()) {
        student = new Student(entity);
        students.add(student);
    }*/

    Gson gson = new Gson();
    String json = gson.toJson(student);
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {    
    Entity studentEntity = new Entity("Student");
    studentEntity.setProperty("email", "kyle@google.com");
    studentEntity.setProperty("nickname", "kyle");
    studentEntity.setProperty("id", 1020);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(studentEntity);

    response.sendRedirect("/index.html");
  }
}
