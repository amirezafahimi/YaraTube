package com.yaratech.yaratube.data.source;

public interface ApiResultCallback<T> {
    void onSuccess(T result);
    void onFail(String err);
}
