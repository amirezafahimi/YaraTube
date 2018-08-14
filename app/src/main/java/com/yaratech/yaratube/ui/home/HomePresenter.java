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
        //view 1:
        homeViewListener.showProgrssBar();

        repository.getHome(new GetResultInterface<Home>() {

            @Override
            public void onSuccess(Home home) {
                //view 2:
                homeViewListener.hideProgrssBar();

                //view 3:
                homeViewListener.showListHome(home);
            }

            @Override
            public void onFail() {
                //view 4:
                homeViewListener.showErrorMessage();
            }
        });
    }
}

