package com.yaratech.yaratube.ui.home;

import com.yaratech.yaratube.data.source.GetResultInterface;
import com.yaratech.yaratube.data.source.Repository;
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

        repository.getHome(new GetResultInterface<Home>() {

            @Override
            public void onSuccess(Home home) {

                homeViewListener.hideProgrssBar();

                homeViewListener.showListHome(home);
            }

            @Override
            public void onFail(String err) {
                homeViewListener.hideProgrssBar();
                homeViewListener.showErrorMessage(err);
            }
        });
    }
}

