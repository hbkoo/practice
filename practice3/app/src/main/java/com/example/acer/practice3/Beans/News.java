package com.example.acer.practice3.Beans;

/**
 * Created by 19216 on 2019/1/10.
 */

public class News {

    private int image;        // 图片资源
    private String title;     // 标题
    private String address;   // 地点
    private String time;      // 时间
    private String category;  // 类别


    public News(int image, String title, String address, String time, String category) {
        this.image = image;
        this.title = title;
        this.address = address;
        this.time = time;
        this.category = category;
    }

    @Override
    public String toString() {
        return "News{" +
                "image=" + image +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
