package com.example.acer.wuli.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Comment extends LitePalSupport implements Serializable {
    private int ID;                 //评论ID
    private int userID;             //用户ID
    private int movieID = 0;            //电影ID
    private String commentTitle;    //标题
    private String commentContent;  //内容
    private int commentPoint = 10;       //评分
    private String commentTime;     //评论时间
    private int praiseNumber = 0;       //点赞数

    public Comment() {
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

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentPoint() {
        return commentPoint;
    }

    public void setCommentPoint(int commentPoint) {
        this.commentPoint = commentPoint;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "ID=" + ID +
                ", userID=" + userID +
                ", movieID=" + movieID +
                ", commentTitle='" + commentTitle + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentPoint=" + commentPoint +
                ", commentTime='" + commentTime + '\'' +
                ", praiseNumber=" + praiseNumber +
                '}';
    }
}
