package com.example.acer.wuli.model;



import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class User extends LitePalSupport implements Serializable {
    private int ID;//用户ID
    @Column(nullable = false)
    private String password;//密码
    @Column(nullable = false,unique =true)
    private String phoneNumber;//手机
    private String nickName;//昵称

    public User() {
    }

    public User(int ID, String password, String phoneNumber, String nickName) {
        this.ID = ID;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
