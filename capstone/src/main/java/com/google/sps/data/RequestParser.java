package com.google.sps.data;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class RequestParser {
  
    public static <T> T parseObjectFromRequest(HttpServletRequest request, Class<T> type) throws IOException, JsonSyntaxException {
        return (new Gson()).fromJson(parseStringFromRequest(request), type);
    }

    public static String parseStringFromRequest(HttpServletRequest request) throws IOException, JsonSyntaxException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            reader.close();
        }
        return sb.toString();
    }
}