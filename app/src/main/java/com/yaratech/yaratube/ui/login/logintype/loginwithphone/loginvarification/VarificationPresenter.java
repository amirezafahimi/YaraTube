package com.yaratech.yaratube.ui.login.logintype.loginwithphone.loginvarification;

import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.source.ApiResultCallback;
import com.yaratech.yaratube.data.source.Repository;

public class VarificationPresenter implements VarificationContract.Presenter {

    VarificationContract.View varificationViewListener;
    Repository repository;

    public VarificationPresenter(VarificationContract.View varificationViewListener, Repository repository) {
        this.varificationViewListener = varificationViewListener;
        this.repository = repository;

    }

    @Override
    public void sendActivaionCode(String phoneNumber,
                                  String deviceId,
                                  String activationCode,
                                  String nickname) {
        repository.varificationCode(phoneNumber, deviceId, activationCode, nickname,
                new ApiResultCallback<MobileLoginStepTwoResponse>() {
                    @Override
                    public void onSuccess(MobileLoginStepTwoResponse step2) {
                        varificationViewListener.loginMessege(step2);
                    }

                    @Override
                    public void onFail(String err) {
                        varificationViewListener.showErrorMessage(err);
                    }
                }
        );

    }
}
