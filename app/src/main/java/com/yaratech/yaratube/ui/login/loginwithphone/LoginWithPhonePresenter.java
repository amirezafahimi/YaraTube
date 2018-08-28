package com.yaratech.yaratube.ui.login.loginwithphone;

import android.util.Log;

import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.source.ApiResultCallback;
import com.yaratech.yaratube.data.source.Repository;

public class LoginWithPhonePresenter implements LoginWithPhoneContract.Presenter {
    Repository repository;
    LoginWithPhoneContract.View loginWithPhoneViewListener;

    public LoginWithPhonePresenter(Repository repository, LoginWithPhoneContract.View loginWithPhoneViewListener) {
        this.repository = repository;
        this.loginWithPhoneViewListener = loginWithPhoneViewListener;
    }

    @Override
    public void sendPhoneNumber(String num,
                                String deviceId,
                                String deviceModel,
                                String deviceOs,
                                String gcm) {

        repository.requestActivationCode(num,
                deviceId,
                deviceModel,
                deviceOs,
                gcm,
                new ApiResultCallback<MobileLoginStep1>() {
                    @Override
                    public void onSuccess(MobileLoginStep1 step1) {
                        loginWithPhoneViewListener.goToNextDialog(step1);
                    }

                    @Override
                    public void onFail(String err) {
                        loginWithPhoneViewListener.showErrorMessage(err);
                    }
                });
    }
}
