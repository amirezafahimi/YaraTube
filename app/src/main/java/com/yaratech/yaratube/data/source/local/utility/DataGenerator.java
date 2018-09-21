package com.yaratech.yaratube.data.source.local.utility;

import com.yaratech.yaratube.data.source.local.AppDatabase;
import com.yaratech.yaratube.data.source.local.entity.User;

public class DataGenerator {


    private static DataGenerator instance;
    private static AppDatabase dataBase;

    public static DataGenerator with(AppDatabase appDataBase) {

        if (dataBase == null)
            dataBase = appDataBase;

        if (instance == null)
            instance = new DataGenerator();

        return instance;
    }

    private static User user;

    public static User userInstance() {
        if (user == null) {
            if (dataBase.DBDao().loadAllUsers().length == 0) {
                user = new User();
            } else {
                user = dataBase.DBDao().loadAllUsers()[0];
            }
        }
        return user;
    }

    public static void deleteUser(int id) {
        dataBase.DBDao().userDelete(id);
    }

    public static void putUserData(User user) {
        dataBase.DBDao().insertUsers(user);
    }
}
