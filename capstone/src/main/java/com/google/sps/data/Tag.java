package com.google.sps.data;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

public enum Tag {

    EDUCATION("Education"),
    COOKING("Cooking"),
    FITNESS("Fitness"),
    LITERATURE("Literature"),
    MUSIC("Music"),
    TECHNOLOGY("Technology"),
    RANDOM("Random"),
    MISCELLANEOUS("Miscellaneous"),
    RELIGIOUS("Religious"),
    ANCIENT("Ancient"),
    SPACE("Space"),
    ROMANTIC("Romantic"),
    COMEDY("Comedy"),
    ART("Art"),
    BAKING("Baking"),
    DANCING("Dancing"),
    CHALLENGE("Challenge"),
    BODY_BUILDING("Body Building"),
    DIETING("Dieting"),
    DESSERTS("Desserts"),
    ADVENTURE("Adventure"),
    MEDITATION("Meditation"),
    NEWS("News"),

    EASY_DIFFICULTY("Easy"),
    MEDIUM_DIFFICULTY("Medium"),
    HARD_DIFFICULTY("Hard"),

    ONE_MINUTE("1:00"),
    TWO_MINUTES("2:00"),
    THREE_MINUTES("3:00"),
    FOUR_MINUTES("4:00"),
    FIVE_MINUTES("5:00"),
    TEN_MINUTES("10:00"),
    TWENTY_MINUTES("20:00");


    private final String tag;

    private Tag(String tag) {
        this.tag = tag;
    }
    
    public static final List<String> CATEGORY_TAGS = Arrays.asList(
        Tag.EDUCATION.getTag(),
        Tag.COOKING.getTag(),
        Tag.FITNESS.getTag(),
        Tag.LITERATURE.getTag(),
        Tag.MUSIC.getTag(),
        Tag.TECHNOLOGY.getTag(),
        Tag.RANDOM.getTag(),
        Tag.MISCELLANEOUS.getTag(),
        Tag.RELIGIOUS.getTag(),
        Tag.ANCIENT.getTag(),
        Tag.SPACE.getTag(),
        Tag.ROMANTIC.getTag(),
        Tag.COMEDY.getTag(),
        Tag.ART.getTag(),
        Tag.BAKING.getTag(),
        Tag.DANCING.getTag(),
        Tag.CHALLENGE.getTag(),
        Tag.BODY_BUILDING.getTag(),
        Tag.DIETING.getTag(),
        Tag.DESSERTS.getTag(),
        Tag.ADVENTURE.getTag(),
        Tag.MEDITATION.getTag(),
        Tag.NEWS.getTag()
    );

    public static final List<String> DIFFICULTY_TAGS = Arrays.asList(
        Tag.EASY_DIFFICULTY.getTag(),
        Tag.MEDIUM_DIFFICULTY.getTag(),
        Tag.HARD_DIFFICULTY.getTag()
    );

    public static final List<String> TIME_TAGS = Arrays.asList(
        Tag.ONE_MINUTE.getTag(),
        Tag.TWO_MINUTES.getTag(),
        Tag.THREE_MINUTES.getTag(),
        Tag.FOUR_MINUTES.getTag(),
        Tag.FIVE_MINUTES.getTag(),
        Tag.TEN_MINUTES.getTag(),
        Tag.TWENTY_MINUTES.getTag()
    );

    public static List<String> all() {
        List<String> tags = new ArrayList<>(Tag.CATEGORY_TAGS);
        tags.addAll(Tag.DIFFICULTY_TAGS);
        tags.addAll(Tag.TIME_TAGS);
        return tags;
    }

    public String getTag() {
        return this.tag;
    }

    public static final HashMap<String, Integer> constructUserVectorMap() {
        HashMap<String, Integer> vector = new HashMap<>();

        for(String tag : CATEGORY_TAGS) {
            vector.put(tag, 0);
        }
        
        return vector;
    }
}