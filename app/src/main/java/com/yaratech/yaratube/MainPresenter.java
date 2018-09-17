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
    public void setDatabaseWithContext(Context context) {
        repository.setDatabase(repository.getDatabaseWithContext(context));
    }

    @Override
    public boolean checkIfUserIsLogedIn() {
        return  repository.checkIfUserIsLogedIn();
    }

    @Override
    public void signOutUser(int userId) {
        repository.deleteUserToken(userId);
        repository.setUserIsLogedIn(false);
    }
}
