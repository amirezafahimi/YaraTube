package com.yaratech.yaratube.ui.login.loginconfirmphone;

import android.util.Log;

import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.source.GetResultInterface;
import com.yaratech.yaratube.data.source.Repository;

public class ConfirmPresenter implements ConfirmContract.Presenter {

    ConfirmContract.View confirmViewListener;
    Repository repository;

    public ConfirmPresenter(ConfirmContract.View confirmViewListener, Repository repository) {
        this.confirmViewListener = confirmViewListener;
        this.repository = repository;

    }

    @Override
    public void sendActivaionCode(String num,
                                  String deviceId,
                                  String activationCode,
                                  String nickname) {
        repository.confirmationCode(num, deviceId, activationCode, nickname, new GetResultInterface<MobileLoginStep2>() {
                    @Override
                    public void onSuccess(MobileLoginStep2 step2) {
                        confirmViewListener.loginMessege(step2);
                    }

                    @Override
                    public void onFail(String err) {
                        confirmViewListener.showErrorMessage(err);
                    }
                }
        );

    }
}
