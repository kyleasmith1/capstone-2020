package com.google.sps.servlets;

import java.io.IOException;
import com.google.sps.data.Lesson;
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
        response.setStatus(HttpServletResponse.SC_OK);
    }
}