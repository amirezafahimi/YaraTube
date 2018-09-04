package com.yaratech.yaratube.ui.login;

import android.content.Context;

import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.source.InsertIntoDatabaseCallback;
import com.yaratech.yaratube.data.source.Repository;

public class LoginDialogContainerPresenter implements LoginDialogContainerContract.Presenter {

    LoginDialogContainerContract.View loginDialogContainerViewListener;
    Repository repository;

    public LoginDialogContainerPresenter
            (LoginDialogContainerContract.View loginDialogContainerViewListener,
             Repository repository) {
        this.loginDialogContainerViewListener = loginDialogContainerViewListener;
        this.repository = repository;
    }


    @Override
    public void saveUserData(MobileLoginStepTwoResponse step2, String phoneNumber) {
        repository.sendUserDataToDatabase(
                step2,
                phoneNumber,
                new InsertIntoDatabaseCallback() {
            @Override
            public void onUserDataInserted(String message) {
                loginDialogContainerViewListener.showMessege(message);
            }
        });
        repository.setUserIsLogedIn(true);
    }
}
