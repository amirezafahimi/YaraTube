package com.yaratech.yaratube.data.source;

public interface GetResultInterface<T> {
    void onSuccess(T result);
    void onFail();
}
