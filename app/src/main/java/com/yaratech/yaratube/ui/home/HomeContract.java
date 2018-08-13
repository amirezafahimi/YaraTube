package com.yaratech.yaratube.ui.home;

import com.yaratech.yaratube.data.model.Home;

public interface HomeContract {

    interface View{

        void showListHome(Home home);

        void showErrorMessage();

        void showProgrssBar();

        void hideProgrssBar();
    }
    interface Presenter{

        void fetchHome();
    }

}
