package com.example.acer.wuli.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Collect extends LitePalSupport implements Serializable {

    private int ID;//ID
    private int userID;//用户ID
    private int movieID;//电影ID

    public Collect() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }
}
