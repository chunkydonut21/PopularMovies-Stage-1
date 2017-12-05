package com.example.hp.popularmovies;


public class Movies {

    private String mTitle;
    private String mOverview;
    private String mVote;
    private String mLanguage;
    private String mPath;
    private String mRelease;


    public Movies(String title, String overview, String vote, String language, String path, String release) {
        mTitle = title;
        mOverview = overview;
        mVote = vote;
        mLanguage = language;
        mPath = path;
        mRelease = release;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getVote() {
        return mVote;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getPath() {
        return mPath;
    }

    public String getRelease() {
        return mRelease;
    }
}
