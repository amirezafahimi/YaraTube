package com.yaratech.yaratube.data.source;

public interface ReadFromDatabaseCallback<T> {
    void onUserDataLoded(T result);
}
