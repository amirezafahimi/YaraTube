package com.yaratech.yaratube.ui.login.logintype.loginwithphone.loginvarification;

import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;

public interface VarificationContract {
    interface View {
        void loginMessege(MobileLoginStepTwoResponse step2);
        void showErrorMessage(String err);
    }

    interface Presenter {
        void sendActivaionCode(String num,
                                String deviceId,
                                String activationCode,
                                String nickname);
    }
}
