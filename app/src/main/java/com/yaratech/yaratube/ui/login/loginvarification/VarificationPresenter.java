package com.yaratech.yaratube.ui.login.loginvarification;

import android.widget.Toast;

import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.source.ApiResultCallback;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.local.AppDatabase;
import com.yaratech.yaratube.data.source.local.entity.User;
import com.yaratech.yaratube.data.source.local.utility.DataGenerator;

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
                new ApiResultCallback<MobileLoginStep2>() {
                    @Override
                    public void onSuccess(MobileLoginStep2 step2) {
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
