package com.yaratech.yaratube.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yaratech.yaratube.util.Util;

import java.util.List;


public class ProfileGetResponse {

    @SerializedName("friends")
    @Expose
    private List<Object> friends = null;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("magic_credit")
    @Expose
    private MagicCredit magicCredit;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("credit")
    @Expose
    private Credit credit;

    public List<Object> getFriends() {
        return friends;
    }

    public void setFriends(List<Object> friends) {
        this.friends = friends;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public MagicCredit getMagicCredit() {
        return magicCredit;
    }

    public void setMagicCredit(MagicCredit magicCredit) {
        this.magicCredit = magicCredit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar+"";
    }

    public String getImageLink() {
        return Util.BASE_URL + avatar+"";
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender+"";
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

}