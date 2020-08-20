package com.google.sps.data;

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
    public static final String TAG_RANDOM = "Random";
    public static final String TAG_MISCELLANEOUS = "Miscellaneous";
    public static final String TAG_RELIGIOUS = "Religious";
    public static final String TAG_ANCIENT = "Ancient";
    public static final String TAG_SPACE = "Space";
    public static final String TAG_ROMANTIC = "Romantic";
    public static final String TAG_COMEDY = "Comedy";
    public static final String TAG_ART = "Art";
    public static final String TAG_BAKING = "Baking";
    public static final String TAG_DANCING = "Dancing";
    public static final String TAG_CHALLENGE = "Challenge";
    public static final String TAG_BODY_BUILDING = "Body Building";
    public static final String TAG_DIETING = "Dieting";
    public static final String TAG_DESSERTS = "Desserts";
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

    public static final List<String> category = Arrays.asList(
        Tag.TAG_EDUCATION,
        Tag.TAG_COOKING,
        Tag.TAG_FITNESS,
        Tag.TAG_LITERATURE,
        Tag.TAG_MUSIC,
        Tag.TAG_TECHNOLOGY,
        Tag.TAG_RANDOM,
        Tag.TAG_MISCELLANEOUS,
        Tag.TAG_RELIGIOUS,
        Tag.TAG_ANCIENT,
        Tag.TAG_SPACE,
        Tag.TAG_ROMANTIC,
        Tag.TAG_COMEDY,
        Tag.TAG_ART,
        Tag.TAG_BAKING,
        Tag.TAG_DANCING,
        Tag.TAG_CHALLENGE,
        Tag.TAG_BODY_BUILDING,
        Tag.TAG_DIETING,
        Tag.TAG_DESSERTS,
        Tag.TAG_ADVENTURE,
        Tag.TAG_MEDITATION,
        Tag.TAG_NEWS
    );

    public static final List<String> difficulty = Arrays.asList(
        Tag.TAG_EASY_DIFFICULTY,
        Tag.TAG_MEDIUM_DIFFICULTY,
        Tag.TAG_HARD_DIFFICULTY
    );

    public static final List<String> time = Arrays.asList(
        Tag.TAG_ONE_MINUTE,
        Tag.TAG_TWO_MINUTES,
        Tag.TAG_THREE_MINUTES,
        Tag.TAG_FOUR_MINUTES,
        Tag.TAG_FIVE_MINUTES,
        Tag.TAG_TEN_MINUTES,
        Tag.TAG_TWENTY_MINUTES
    );

    public static List<String> all() {
        List<String> tags = new ArrayList<>(Tag.category);
        tags.addAll(Tag.difficulty);
        tags.addAll(Tag.time);
        return tags;
    }

    public static String get(String tag) {
        return tag;
    }

    public static final HashMap<String, Integer> constructUserVectorMap() {
        HashMap<String, Integer> vector = new HashMap<>();
        vector.put(Tag.TAG_EDUCATION, 0);
        vector.put(Tag.TAG_COOKING, 0);
        vector.put(Tag.TAG_FITNESS, 0);
        vector.put(Tag.TAG_LITERATURE, 0);
        vector.put(Tag.TAG_MUSIC, 0);
        vector.put(Tag.TAG_TECHNOLOGY, 0);
        vector.put(Tag.TAG_RANDOM, 0);
        vector.put(Tag.TAG_MISCELLANEOUS, 0);
        vector.put(Tag.TAG_RELIGIOUS, 0);
        vector.put(Tag.TAG_ANCIENT, 0);
        vector.put(Tag.TAG_SPACE, 0);
        vector.put(Tag.TAG_ROMANTIC, 0);
        vector.put(Tag.TAG_COMEDY, 0);
        vector.put(Tag.TAG_ART, 0);
        vector.put(Tag.TAG_BAKING, 0);
        vector.put(Tag.TAG_DANCING, 0);
        vector.put(Tag.TAG_CHALLENGE, 0);
        vector.put(Tag.TAG_BODY_BUILDING, 0);
        vector.put(Tag.TAG_DIETING, 0);
        vector.put(Tag.TAG_DESSERTS, 0);
        vector.put(Tag.TAG_ADVENTURE, 0);
        vector.put(Tag.TAG_MEDITATION, 0);
        vector.put(Tag.TAG_NEWS, 0);
        
        return vector;
    }
}