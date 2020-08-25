package com.google.sps.data;

import com.google.appengine.api.datastore.EmbeddedEntity;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

public final class Tag {

    private Tag() {
        
    }
    
    public static final String TAG_EDUCATION = "Education";
    public static final String TAG_COOKING = "Cooking";
    public static final String TAG_FITNESS = "Fitness"; 
    public static final String TAG_LITERATURE = "Literature"; 
    public static final String TAG_MUSIC = "Music";
    public static final String TAG_TECHNOLOGY = "Technology";
    public static final String TAG_GAMING = "Gaming";
    public static final String TAG_MISCELLANEOUS = "Miscellaneous";
    public static final String TAG_RELIGIOUS = "Religious";
    public static final String TAG_HISTORIC = "Historic";
    public static final String TAG_SPACE = "Space";
    public static final String TAG_CAREER = "Career";
    public static final String TAG_ARCHITECTURE = "Architecture";
    public static final String TAG_ART = "Art";
    public static final String TAG_DANCING = "Dancing";
    public static final String TAG_CHALLENGE = "Challenge";
    public static final String TAG_DIETING = "Dieting";
    public static final String TAG_ADVENTURE = "Adventure";
    public static final String TAG_MEDITATION = "Meditation";
    public static final String TAG_NEWS = "News";

    public static final String TAG_EASY_DIFFICULTY = "Easy"; 
    public static final String TAG_MEDIUM_DIFFICULTY = "Medium";
    public static final String TAG_HARD_DIFFICULTY = "Hard"; 

    public static final String TAG_ONE_MINUTE = "1:00"; 
    public static final String TAG_TWO_MINUTES = "2:00"; 
    public static final String TAG_THREE_MINUTES = "3:00"; 
    public static final String TAG_FOUR_MINUTES = "4:00";
    public static final String TAG_FIVE_MINUTES = "5:00";
    public static final String TAG_TEN_MINUTES = "10:00";
    public static final String TAG_TWENTY_MINUTES = "20:00+";

    public static final List<String> CATEGORY_TAGS = Arrays.asList(
        Tag.TAG_EDUCATION,
        Tag.TAG_COOKING,
        Tag.TAG_FITNESS,
        Tag.TAG_LITERATURE,
        Tag.TAG_MUSIC,
        Tag.TAG_TECHNOLOGY,
        Tag.TAG_GAMING,
        Tag.TAG_MISCELLANEOUS,
        Tag.TAG_RELIGIOUS,
        Tag.TAG_HISTORIC,
        Tag.TAG_SPACE,
        Tag.TAG_CAREER,
        Tag.TAG_ARCHITECTURE,
        Tag.TAG_ART,
        Tag.TAG_DANCING,
        Tag.TAG_CHALLENGE,
        Tag.TAG_DIETING,
        Tag.TAG_ADVENTURE,
        Tag.TAG_MEDITATION,
        Tag.TAG_NEWS
    );

    public static final List<String> DIFFICULTY_TAGS = Arrays.asList(
        Tag.TAG_EASY_DIFFICULTY,
        Tag.TAG_MEDIUM_DIFFICULTY,
        Tag.TAG_HARD_DIFFICULTY
    );

    public static final List<String> TIME_TAGS = Arrays.asList(
        Tag.TAG_ONE_MINUTE,
        Tag.TAG_TWO_MINUTES,
        Tag.TAG_THREE_MINUTES,
        Tag.TAG_FOUR_MINUTES,
        Tag.TAG_FIVE_MINUTES,
        Tag.TAG_TEN_MINUTES,
        Tag.TAG_TWENTY_MINUTES
    );

    public static List<String> all() {
        List<String> tags = new ArrayList<>(Tag.CATEGORY_TAGS);
        tags.addAll(Tag.DIFFICULTY_TAGS);
        tags.addAll(Tag.TIME_TAGS);
        return tags;
    }

    public static String get(String tag) {
        return tag;
    }

    public static final EmbeddedEntity constructUserEmbeddedEntity() {
        EmbeddedEntity tags = new EmbeddedEntity();
        tags.setProperty(Tag.TAG_EDUCATION, 0.0);
        tags.setProperty(Tag.TAG_COOKING, 0.0);
        tags.setProperty(Tag.TAG_FITNESS, 0.0);
        tags.setProperty(Tag.TAG_LITERATURE, 0.0);
        tags.setProperty(Tag.TAG_MUSIC, 0.0);
        tags.setProperty(Tag.TAG_TECHNOLOGY, 0.0);
        tags.setProperty(Tag.TAG_GAMING, 0.0);
        tags.setProperty(Tag.TAG_MISCELLANEOUS, 0.0);
        tags.setProperty(Tag.TAG_RELIGIOUS, 0.0);
        tags.setProperty(Tag.TAG_HISTORIC, 0.0);
        tags.setProperty(Tag.TAG_SPACE, 0.0);
        tags.setProperty(Tag.TAG_CAREER, 0.0);
        tags.setProperty(Tag.TAG_ARCHITECTURE, 0.0);
        tags.setProperty(Tag.TAG_ART, 0.0);
        tags.setProperty(Tag.TAG_DANCING, 0.0);
        tags.setProperty(Tag.TAG_CHALLENGE, 0.0);
        tags.setProperty(Tag.TAG_DIETING, 0.0);
        tags.setProperty(Tag.TAG_ADVENTURE, 0.0);
        tags.setProperty(Tag.TAG_MEDITATION, 0.0);
        tags.setProperty(Tag.TAG_NEWS, 0.0);
        return tags;
    }
}