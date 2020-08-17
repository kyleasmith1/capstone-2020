package com.google.sps.data;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public final class Tag {

    private Tag() {
        
    }
    
    private static final String TAG_EDUCATION = "Education";
    private static final String TAG_COOKING = "Cooking";
    private static final String TAG_FITNESS = "Fitness"; 
    private static final String TAG_LITERATURE = "Literature"; 
    private static final String TAG_MUSIC = "Music";
    private static final String TAG_TECHNOLOGY = "Technology";
    private static final String TAG_RANDOM = "Random";
    private static final String TAG_MISCELLANEOUS = "Miscellaneous";
    private static final String TAG_RELIGIOUS = "Religious";
    private static final String TAG_ANCIENT = "Ancient";
    private static final String TAG_SPACE = "Space";
    private static final String TAG_ROMANTIC = "Romantic";
    private static final String TAG_COMEDY = "Comedy";
    private static final String TAG_ART = "Art";
    private static final String TAG_BAKING = "Baking";
    private static final String TAG_DANCING = "Dancing";
    private static final String TAG_CHALLENGE = "Challenge";
    private static final String TAG_BODY_BUILDING = "Body Building";
    private static final String TAG_DIETING = "Dieting";
    private static final String TAG_DESSERTS = "Desserts";
    private static final String TAG_ADVENTURE = "Adventure";
    private static final String TAG_MEDITATION = "Meditation";
    private static final String TAG_NEWS = "News";

    private static final String TAG_EASY_DIFFICULTY = "Easy"; 
    private static final String TAG_MEDIUM_DIFFICULTY = "Medium";
    private static final String TAG_HARD_DIFFICULTY = "Hard"; 

    private static final String TAG_ONE_MINUTE = "1:00"; 
    private static final String TAG_TWO_MINUTES = "2:00"; 
    private static final String TAG_THREE_MINUTES = "3:00"; 
    private static final String TAG_FOUR_MINUTES = "4:00";
    private static final String TAG_FIVE_MINUTES = "5:00";
    private static final String TAG_TEN_MINUTES = "10:00";
    private static final String TAG_TWENTY_MINUTES = "20:00+";

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
        List<String> tags = new ArrayList(Tag.category);
        tags.addAll(Tag.difficulty);
        tags.addAll(Tag.time);
        return tags;
    }

    public static String get(String tag) {
        return tag;
    }
}