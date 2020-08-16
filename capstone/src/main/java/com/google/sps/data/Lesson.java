package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public abstract class Lesson {
    public static final String LESSON_ENTITY_NAME = "Lesson";
    public static final String TYPE_PROPERTY_KEY = "type";
    public static final String ISDRAFT_PROPERTY_KEY = "isDraft";
    public static final String TITLE_PROPERTY_KEY = "title";
    public static final String DESCRIPTION_PROPERTY_KEY = "description";
    public static final String DATE_PROPERTY_KEY = "date";
    public static final String TAGS_PROPERTY_KEY = "tags";

    public static final String TYPE_SURVEY = "survey";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_CONTENT = "content";
    public static final String TYPE_TAG = "tag";  
 
    public Entity entity;

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

    @SuppressWarnings("unchecked")
    public void addTag(String tag) {
        if (this.entity.getProperty(Lesson.TAGS_PROPERTY_KEY) == null) {
            this.entity.setProperty(Lesson.TAGS_PROPERTY_KEY, new ArrayList<>());
        }
        List<String> tags = (ArrayList<String>) this.entity.getProperty(Lesson.TAGS_PROPERTY_KEY);
        tags.add(tag);
    }
    
    @SuppressWarnings("unchecked")
    public void removeTag(String tag) {
        ArrayList<String> tags = (ArrayList<String>) this.entity.getProperty(Lesson.TAGS_PROPERTY_KEY);
        tags.remove(tag);
    }

    @SuppressWarnings("unchecked")
    public List<String> getAllTags() { 
        if (this.entity.getProperty(Lesson.TAGS_PROPERTY_KEY) == null) {
            return new ArrayList<String>();
        }
        return (ArrayList<String>) this.entity.getProperty(Lesson.TAGS_PROPERTY_KEY);
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

    public Key getLessonKey() {
        return this.entity.getKey();
    }
}