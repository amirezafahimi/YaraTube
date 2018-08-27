package com.yaratech.yaratube.ui.login.loginvarifification;

import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.source.GetResultInterface;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.loginvarifification.VarificationContract;

public class VarificationPresenter implements VarificationContract.Presenter {

    VarificationContract.View varificationViewListener;
    Repository repository;

    public VarificationPresenter(VarificationContract.View varificationViewListener, Repository repository) {
        this.varificationViewListener = varificationViewListener;
        this.repository = repository;

    }

    @Override
    public void sendActivaionCode(String num,
                                  String deviceId,
                                  String activationCode,
                                  String nickname) {
        repository.varificationationCode(num, deviceId, activationCode, nickname, new GetResultInterface<MobileLoginStep2>() {
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
