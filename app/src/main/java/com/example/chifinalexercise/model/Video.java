package com.example.chifinalexercise.model;

import androidx.annotation.NonNull;

public class Video {

    private String mTitle;
    private String mDescription;
    private int mLikes;

    public Video(String title, String description, int likes) {
        this.mTitle = title;
        this.mDescription = description;
        this.mLikes = likes;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getLikes() {
        return mLikes;
    }
}
