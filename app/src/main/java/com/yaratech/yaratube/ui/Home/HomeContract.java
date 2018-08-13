package com.yaratech.yaratube.ui.Home;

import com.yaratech.yaratube.data.model.Home;

public interface HomeContract {

    interface View{

        void showListHome(Home home);

        void showMessage();

        void showLoading();

        void hideLoading();
    }
    interface Presenter{

        void fetchHomeFromRemote();
    }

}
