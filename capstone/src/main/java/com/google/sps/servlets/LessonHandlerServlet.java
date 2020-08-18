package com.google.sps.servlets;

import java.io.IOException;
import com.google.sps.data.Lesson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Key;
import com.google.sps.service.DatabaseService;
import com.google.sps.data.RequestParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lesson")
public class LessonHandlerServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Lesson lesson = Lesson.deserializeJson(RequestParser.parseStringFromRequest(request));
        DatabaseService.save(lesson.getLessonEntity());

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery roomResults = datastore.prepare(new Query(Room.ROOM_ENTITY_NAME));
        Room room = null;

        for(Entity entity : roomResults.asIterable()) {
            if (new Room(entity).getRoomKey().getId() == Long.parseLong(request.getParameter("c"))) {
                room = new Room(entity);
                room.addLesson(lesson);
            }
        }
        
        DatabaseService.save(room.getRoomEntity()); 
        response.setStatus(HttpServletResponse.SC_OK);
    }
}