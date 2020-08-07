package com.google.sps.servlets;
import java.io.IOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.sps.data.User;
import com.google.sps.data.Classroom;
import com.google.sps.service.DatabaseService;
import com.google.sps.data.RequestParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard-handler")
public class DashboardHandlerServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JsonObject jobject = JsonParser.parseString(RequestParser.parseStringFromRequest(request)).getAsJsonObject();

        User teacher = new User(jobject.get(User.USER_ID_PROPERTY_KEY).getAsString(),
            jobject.get(User.NICKNAME_PROPERTY_KEY).getAsString());
        DatabaseService.save(teacher.getUserEntity());

        Classroom classroom = new Classroom(teacher, jobject.get(Classroom.SUBJECT_PROPERTY_KEY).getAsString());
        DatabaseService.save(classroom.getClassroomEntity());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}