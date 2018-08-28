package com.yaratech.yaratube.data.source.local.utility;

import com.yaratech.yaratube.data.source.local.AppDatabase;
import com.yaratech.yaratube.data.source.local.entity.User;

public class DataGenerator {


    private static DataGenerator instance;
    private static AppDatabase dataBase;
    private static User user;

    public static DataGenerator with(AppDatabase appDataBase) {

        if (dataBase == null)
            dataBase = appDataBase;

        if (instance == null)
            instance = new DataGenerator();

        return instance;
    }

    public static User userInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public static void deleteUser(int id) {
        dataBase.DBDao().userDelete(id);
    }

    public static void addUser(User user) {
        dataBase.DBDao().insertUsers(user);
    }
}
