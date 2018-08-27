package com.yaratech.yaratube.ui.mainpage.home;

import com.yaratech.yaratube.data.model.Home;

public interface HomeContract {

    interface View{

        void showListHome(Home home);

        void showErrorMessage(String err);

        void showProgrssBar();

        void hideProgrssBar();
    }
    interface Presenter{

        void fetchHome();
    }

}
