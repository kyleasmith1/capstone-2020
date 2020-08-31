package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EmbeddedEntity;
import java.util.Set;
import com.google.sps.data.Tag;
import com.google.sps.data.User;

public class CachedInterestVector {
 
    private EmbeddedEntity cachedInterestVector;
 
    public CachedInterestVector(EmbeddedEntity cachedInterestVector) {
        this.cachedInterestVector = cachedInterestVector; 
    }
 
    public void denormalizeInterestVector(Double magnitude) {
        for(String tag : this.cachedInterestVector.getProperties().keySet()) {
            this.cachedInterestVector.setProperty(tag, (((Double) this.cachedInterestVector.getProperty(tag))*magnitude));
        }
    }

    public void addTagToDenormalizedInterestVector(Set<Tag> tags) {
        for(Tag tag : tags) {
            if (this.cachedInterestVector.getProperty(tag.getTag()) == null) {
                this.cachedInterestVector.setProperty(tag.getTag(), 1);
            } else {
                this.cachedInterestVector.setProperty(tag.getTag(), (Double) this.cachedInterestVector.getProperty(tag.getTag()) + 1);
            }
        }
    }

    public Double magnitude() {
        double sum = 0;
        for (String tag : this.cachedInterestVector.getProperties().keySet()) {
            double value = (Double) cachedInterestVector.getProperty(tag);
            sum += Math.pow(value, 2.0);
        }
        return Math.sqrt(sum);
    }

    public void renormalizeInterestVector(Double magnitude) {
        for(String tag : this.cachedInterestVector.getProperties().keySet()) {
            this.cachedInterestVector.setProperty(tag, (((Double) this.cachedInterestVector.getProperty(tag))/magnitude));
        }
    }
 
    public EmbeddedEntity getCachedInterestVectorEntity() {
        return this.cachedInterestVector;
    }
}