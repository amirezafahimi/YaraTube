package com.yaratech.yaratube.data.source.local.utility;

import com.yaratech.yaratube.data.source.local.AppDatabase;


public class LocalDataSource {

    private static AppDatabase database;
    private static LocalDataSource localDataSource;
    private static Boolean userIsLogedIn;

    public static LocalDataSource with(AppDatabase appDatabase) {
        if (database == null)
            database = appDatabase;
        if (localDataSource == null)
            localDataSource = new LocalDataSource();
        return localDataSource;
    }

    static public boolean getUserIsLogedIn() {
        if (userIsLogedIn == null)
            userIsLogedIn = database.DBDao().getToken() != null;
        return userIsLogedIn;
    }

    public static void setUserIsLogedIn(boolean userIsLogined) {
        LocalDataSource.userIsLogedIn = userIsLogined;
    }
    static public String getToken() {
        return database.DBDao().getToken();
    }
}

