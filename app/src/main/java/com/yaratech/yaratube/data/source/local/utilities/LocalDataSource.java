package com.yaratech.yaratube.data.source.local.utilities;

import android.util.Log;

import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.source.local.AppDatabase;
import com.yaratech.yaratube.data.source.local.entity.User;

import java.util.List;


public class LocalDataSource {

    private static AppDatabase database;
    private static LocalDataSource localDataSource;
    public static LocalDataSource with(AppDatabase appDatabase) {
        if (database == null)
            database = appDatabase;
        if (localDataSource==null)
            localDataSource = new LocalDataSource();
        return localDataSource;
    }

    public boolean userIsLogin() {
        if (database.DBDao().isLogin() == 1) {
            return true;
        } else return false;
    }
}

