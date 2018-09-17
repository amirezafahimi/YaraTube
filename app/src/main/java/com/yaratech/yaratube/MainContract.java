package com.yaratech.yaratube;

import android.content.Context;

public interface MainContract {
    interface View{

    }
    interface Presenter{
        void setDatabaseWithContext(Context context);
        boolean checkIfUserIsLogedIn();
        void signOutUser(int userId);
    }
}
