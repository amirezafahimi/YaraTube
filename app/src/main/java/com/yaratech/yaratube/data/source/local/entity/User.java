package com.yaratech.yaratube.data.source.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "fino_token")
    private String finoToken="";
    @ColumnInfo(name = "nickname")
    private String nickname="";
    @ColumnInfo(name = "token")
    private String token;
    @ColumnInfo(name = "message")
    private String message="";
    @ColumnInfo(name = "‌phone_number")
    private String phoneNember="";
    @ColumnInfo(name = "name")
    private String name="";
    @ColumnInfo(name = "sex")
    private String sex="";
    @ColumnInfo(name = "birth_date")
    private String birhDate="";
    @ColumnInfo(name = "email")
    private String email="";
    @ColumnInfo(name = "photo_url")
    private String photoUri="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFinoToken() {
        return finoToken;
    }

    public void setFinoToken(String finoToken) {
        this.finoToken = finoToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNember() {
        return phoneNember;
    }

    public void setPhoneNember(String phoneNember) {
        this.phoneNember = phoneNember;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirhDate() {
        return birhDate;
    }

    public void setBirhDate(String birhDate) {
        this.birhDate = birhDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
