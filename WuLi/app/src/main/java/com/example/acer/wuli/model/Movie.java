package com.example.acer.wuli.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Movie extends LitePalSupport implements Serializable {
    private int id;                   //电影ID
    private String movieName;         //电影名
    private String type;              //类型
    private String directorName;      //导演
    private String mainActor;         //主演
    private String summary;           //剧情简介
    private boolean isShow;           //正在上映
    private String postID;            //图片ID
    private double point;             //评分
    private int commentNumber;        //评论数
    private int collectNumber;        //收藏数
    private String photo1 = "";            //剧照
    private String photo2 = "";            //剧照
    private String photo3 = "";            //剧照

    public Movie() {
    }

    public Movie(int id, String movieName, String type, String directorName,
                 String mainActor, String summary, boolean isShow, String postID,
                 double point, int commentNumber, int collectNumber) {
        this.id = id;
        this.movieName = movieName;
        this.type = type;
        this.directorName = directorName;
        this.mainActor = mainActor;
        this.summary = summary;
        this.isShow = isShow;
        this.postID = postID;
        this.point = point;
        this.commentNumber = commentNumber;
        this.collectNumber = collectNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }


    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(int collectNumber) {
        this.collectNumber = collectNumber;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", type='" + type + '\'' +
                ", directorName='" + directorName + '\'' +
                ", mainActor='" + mainActor + '\'' +
                ", summary='" + summary + '\'' +
                ", isShow=" + isShow +
                ", postID='" + postID + '\'' +
                ", point=" + point +
                ", commentNumber=" + commentNumber +
                ", collectNumber=" + collectNumber +
                ", photo1='" + photo1 + '\'' +
                ", photo2='" + photo2 + '\'' +
                ", photo3='" + photo3 + '\'' +
                '}';
    }
}
