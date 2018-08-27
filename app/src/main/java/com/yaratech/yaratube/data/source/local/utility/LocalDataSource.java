package com.yaratech.yaratube.data.source.local.utility;

import com.yaratech.yaratube.data.source.local.AppDatabase;


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

