package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.sps.data.Tag;

public class User {
    public static final String USER_ENTITY_NAME = "User";
    public static final String NICKNAME_PROPERTY_KEY = "nickname";
    public static final String USER_ID_PROPERTY_KEY = "userId";
    public static final String TAGS_PROPERTY_KEY = "tags";

    private Entity delegate;

    public User(Entity delegate) {
        this.delegate = delegate;
    }

    public User(String id, String nickname) {
        this.delegate = new Entity(User.USER_ENTITY_NAME);
        this.delegate.setProperty(User.NICKNAME_PROPERTY_KEY, nickname);
        this.delegate.setProperty(User.USER_ID_PROPERTY_KEY, id);
        this.delegate.setProperty(User.TAGS_PROPERTY_KEY, Tag.constructUserEmbeddedEntity());
    }

    public void setEmbeddedTags(EmbeddedEntity entity) {
        this.delegate.setProperty(User.TAGS_PROPERTY_KEY, entity);
    }

    public void setNickname(String nickname) {
        this.delegate.setProperty(User.NICKNAME_PROPERTY_KEY, nickname);
    }

    public String getId() {
        return (String) this.delegate.getProperty(User.USER_ID_PROPERTY_KEY);
    }

    public EmbeddedEntity getEmbeddedTags() {
        return (EmbeddedEntity) this.delegate.getProperty(User.TAGS_PROPERTY_KEY);
    }

    public String getNickname() {
        return (String) this.delegate.getProperty(User.NICKNAME_PROPERTY_KEY);
    }

    public Entity getUserEntity() {
        return this.delegate;
    }

    public Key getUserKey() {
        return this.delegate.getKey();
    }
}