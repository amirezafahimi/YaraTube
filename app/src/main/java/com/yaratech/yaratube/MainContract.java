package com.yaratech.yaratube;

import android.content.Context;

public interface MainContract {
    interface View{
        void setUserIsLogedIn(boolean userIsLogedIn);
    }
    interface Presenter{
        void checkIfUserIsLogedIn(Context context);
    }
}
