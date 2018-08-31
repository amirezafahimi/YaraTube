package com.yaratech.yaratube.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private double error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}