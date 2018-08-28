package com.yaratech.yaratube;

import android.content.Context;

import com.yaratech.yaratube.data.source.ReadFromDatabaseCallback;
import com.yaratech.yaratube.data.source.Repository;

public class MainPresenter implements MainContract.Presenter {

    MainContract.View mainActivityViewListener;
    Repository repository;

    public MainPresenter(MainContract.View mainActivityViewListener, Repository repository) {
        this.mainActivityViewListener = mainActivityViewListener;
        this.repository = repository;
    }

    @Override
    public void checkIfUserIsLogedIn(Context context) {
        repository.checkIfUserIsLogedIn(context, new ReadFromDatabaseCallback<Boolean>(){

            @Override
            public void onUserDataLoded(Boolean result) {
                mainActivityViewListener.setUserIsLogedIn(result);
            }
        });
    }
}
