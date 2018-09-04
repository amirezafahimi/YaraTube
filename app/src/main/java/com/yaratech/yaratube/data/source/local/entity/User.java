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
    private String finoToken;
    @ColumnInfo(name = "nickname")
    private String nickname;
    @ColumnInfo(name = "token")
    private String token;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "‌phone_number")
    private String phoneNember;

    private static User user;
    public static User userInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

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
}
