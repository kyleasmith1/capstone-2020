package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;
import java.util.Date;
import com.google.sps.data.Survey;
import java.io.IOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.ArrayList;

public abstract class Lesson {

    protected static final String LESSON_ENTITY_NAME = "Lesson";
    protected static final String TYPE_PROPERTY_KEY = "type";
    protected static final String ISDRAFT_PROPERTY_KEY = "isDraft";
    protected static final String TITLE_PROPERTY_KEY = "title";
    protected static final String DESCRIPTION_PROPERTY_KEY = "description";
    protected static final String DATE_PROPERTY_KEY = "date";
    protected static final String TAG_LIST_PROPERTY_KEY = "tagList";

    protected static final String TYPE_SURVEY = "survey";
    protected static final String TYPE_VIDEO = "video";
    protected static final String TYPE_IMAGE = "image";
    protected static final String TYPE_CONTENT = "content";
    protected static final String TYPE_TAG = "tag";  
 
    protected Entity entity;

    public Lesson(Entity entity) {
        this.entity = entity;
    }

    public Lesson(String type, String title, String description) {
        this.entity = new Entity(Lesson.LESSON_ENTITY_NAME);
        this.entity.setProperty(Lesson.TYPE_PROPERTY_KEY, type);
        this.entity.setProperty(Lesson.ISDRAFT_PROPERTY_KEY, false);
        this.entity.setProperty(Lesson.TITLE_PROPERTY_KEY, title);
        this.entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, description);
        this.entity.setProperty(Lesson.DATE_PROPERTY_KEY, new Date());
    }

    public void setTagList() {
        this.entity.setProperty(Lesson.TAG_LIST_PROPERTY_KEY, Tag.all());
    }

    public String getType() {
        return (String) this.entity.getProperty(Lesson.TYPE_PROPERTY_KEY);
    }

    public boolean getIsDraft() {
        return (boolean) this.entity.getProperty(Lesson.ISDRAFT_PROPERTY_KEY);
    }

    public void publish() {
        this.entity.setProperty(Lesson.ISDRAFT_PROPERTY_KEY, true);
    }

    public void unpublish() {
        this.entity.setProperty(Lesson.ISDRAFT_PROPERTY_KEY, false);
    }

    public String getTitle() {
        return (String) this.entity.getProperty(Lesson.TITLE_PROPERTY_KEY);
    }

    public void setTitle(String title) {
        this.entity.setProperty(Lesson.TITLE_PROPERTY_KEY, title);
    }

    public String getDescription() {
        return (String) this.entity.getProperty(Lesson.DESCRIPTION_PROPERTY_KEY);
    }

    public void setDescription(String description) {
        this.entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, description);
    }

    public Date getDate() {
        return (Date) this.entity.getProperty(Lesson.DATE_PROPERTY_KEY);
    }

    public Entity getLessonEntity() {
        return this.entity;
    }
}