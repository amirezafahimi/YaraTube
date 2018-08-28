package com.yaratech.yaratube.ui.login;

import android.content.Context;

import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.source.InsertIntoDatabaseCallback;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.local.AppDatabase;
import com.yaratech.yaratube.data.source.local.entity.User;
import com.yaratech.yaratube.data.source.local.utility.DataGenerator;

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
    public void insertUserData(Context context, MobileLoginStep2 step2, String phoneNumber) {
        repository.sendUserDataToDatabase(
                context,
                step2,
                phoneNumber,
                new InsertIntoDatabaseCallback() {
            @Override
            public void onUserDataInserted(String message) {
                loginDialogContainerViewListener.showMessege(message);
            }
        });
    }
}
