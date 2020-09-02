package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import com.google.sps.servlets.ImageHandlerServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/blobstore")
public class BlobstoreUploadUrlServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        String uploadUrl = blobstoreService.createUploadUrl("/image");

        System.out.println("URL: " + uploadUrl);

        response.setContentType("text/html");
        response.getWriter().println(uploadUrl);
    }
}
