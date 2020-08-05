package com.google.sps.data;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.gson.Gson;

public class RequestJsonParser {

    public static <T> T parseObjectFromRequest(HttpServletRequest request, Class<T> type) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        Gson gson = new Gson();
        T object = gson.fromJson(sb.toString(), type); 
        return object;
    }
}