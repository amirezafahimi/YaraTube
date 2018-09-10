package com.yaratech.yaratube;

import android.content.Context;

public interface MainContract {
    interface View{

    }
    interface Presenter{
        void setDatabaseWithContext(Context context);
        boolean checkIfUserIsLogedIn();
        void setUserIsLogedIn(boolean isLogedIn);
        void signOutUser(int userId);
    }
}
