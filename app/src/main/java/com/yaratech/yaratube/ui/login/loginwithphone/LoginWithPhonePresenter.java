package com.yaratech.yaratube.ui.login.loginwithphone;

import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
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
                new ApiResultCallback<MobileLoginStepOneResponse>() {
                    @Override
                    public void onSuccess(MobileLoginStepOneResponse step1) {
                        loginWithPhoneViewListener.goToNextDialog(step1);
                    }

                    @Override
                    public void onFail(String err) {
                        loginWithPhoneViewListener.showErrorMessage(err);
                    }
                });
    }
}
