package com.yaratech.yaratube.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileLoginStepOneResponse implements Parcelable {

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("message")
    @Expose
    private String message;

    protected MobileLoginStepOneResponse(Parcel in) {
        error = in.readString();
        message = in.readString();
        nickname = in.readString();
    }

    public static final Creator<MobileLoginStepOneResponse> CREATOR = new Creator<MobileLoginStepOneResponse>() {
        @Override
        public MobileLoginStepOneResponse createFromParcel(Parcel in) {
            return new MobileLoginStepOneResponse(in);
        }

        @Override
        public MobileLoginStepOneResponse[] newArray(int size) {
            return new MobileLoginStepOneResponse[size];
        }
    };

    public String getError() {
        return error;
    }

    @SerializedName("nickname")
    @Expose
    private String nickname;


    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(error);
        parcel.writeString(message);
        parcel.writeString(nickname);
    }
}