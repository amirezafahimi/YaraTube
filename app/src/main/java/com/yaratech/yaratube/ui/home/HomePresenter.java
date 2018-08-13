package com.yaratech.yaratube.ui.home;

import com.yaratech.yaratube.data.Repository;
import com.yaratech.yaratube.data.model.Home;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View homeViewListener;
    private Repository repository;


    HomePresenter(HomeContract.View homeViewListener, Repository repository) {
        this.homeViewListener = homeViewListener;
        this.repository = repository;
    }

    @Override
    public void fetchHome() {

        homeViewListener.showProgrssBar();
        repository.getHome(new getHome());

    }

    private class getHome implements GetHomeInterface{

        @Override
        public void loadHomeData(Home home) {
            homeViewListener.hideProgrssBar();
            homeViewListener.showListHome(home);
        }

        @Override
        public void onFail() {
            homeViewListener.showErrorMessage();
        }
    }
    public interface GetHomeInterface {
        void loadHomeData(Home home);
        void onFail();
    }
}
