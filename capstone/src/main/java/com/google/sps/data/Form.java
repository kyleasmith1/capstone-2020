package com.google.sps.data;
import java.io.*;
import com.google.appengine.api.datastore.Entity;

/**
 * The Form class holds the user's forms for their classes
 */
public class Form {

    private String editUrl;
    private String Url;
    private boolean isPublished;

    public Form(String editUrl, String Url) {
        this.editUrl = editUrl;
        this.Url = Url;
        this.isPublished = false;
    }

    public Form(Entity entity){
        this.editUrl = (String) entity.getProperty("editUrl");
        this.Url = (String) entity.getProperty("Url");
        this.isPublished = false;
    }

    // Getters
    public String geteditUrl() {
        return this.editUrl;
    }

    public String getUrl() {
        return this.Url;
    }

    public boolean isPublished() {
        return this.isPublished;
    }

    // Setters
    public void publish(){
        this.isPublished = true;
    }

    public void unpublish(){
        this.isPublished = false;
    }
    
    public Entity toDatastoreEntity(){
        Entity formEntity = new Entity("Form");
        formEntity.setProperty("editUrl", this.editUrl);
        formEntity.setProperty("Url", this.Url);
        formEntity.setProperty("isPublished", this.isPublished);
        return formEntity;
    }
}