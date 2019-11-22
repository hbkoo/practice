package com.example.acer.wuli.model;

import org.litepal.crud.LitePalSupport;

public class Praise extends LitePalSupport {
    private int id;
    private int commentID;//评论号
    private int UserID;//用户ID

    public Praise() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}
