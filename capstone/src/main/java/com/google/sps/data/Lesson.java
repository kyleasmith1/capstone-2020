package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;
import java.util.Date;

public abstract class Lesson {

    public static final String LESSON_ENTITY_NAME = "Lesson";
    public static final String ISDRAFT_PROPERTY_KEY = "isDraft";
    public static final String TITLE_PROPERTY_KEY = "title";
    public static final String DESCRIPTION_PROPERTY_KEY = "description";
    // public static final String TAGS_PROPERTY_KEY = "tags";
    public static final String DATE_PROPERTY_KEY = "date";

    private Entity entity;

    public Lesson(Entity entity) {
        this.entity = entity;
    }

    public Lesson(String title, String description) {
        this.entity = new Entity(Lesson.LESSON_ENTITY_NAME);
        this.entity.setProperty(Lesson.ISDRAFT_PROPERTY_KEY, false);
        this.entity.setProperty(Lesson.TITLE_PROPERTY_KEY, title);
        this.entity.setProperty(Lesson.DESCRIPTION_PROPERTY_KEY, description);
        this.entity.setProperty(Lesson.DATE_PROPERTY_KEY, new Date());
    }

    public Entity getLessonEntity() {
        return this.entity;
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

}