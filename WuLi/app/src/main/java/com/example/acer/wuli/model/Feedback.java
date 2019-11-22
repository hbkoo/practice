package com.example.acer.wuli.model;

import org.litepal.crud.LitePalSupport;

public class Feedback extends LitePalSupport {
    private int id;
    private int userID;//用户ID
    private String feedbackContent;//反馈内容

    public Feedback() {
    }

    public Feedback(int userID, String feedbackContent) {
        this.userID = userID;
        this.feedbackContent = feedbackContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", userID=" + userID +
                ", feedbackContent='" + feedbackContent + '\'' +
                '}';
    }
}
