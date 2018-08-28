package com.yaratech.yaratube.ui.login.loginvarification;

import com.yaratech.yaratube.data.model.MobileLoginStep2;

public interface VarificationContract {
    interface View {
        void loginMessege(MobileLoginStep2 step2);
        void showErrorMessage(String err);
    }

    interface Presenter {
        void sendActivaionCode(String num,
                                String deviceId,
                                String activationCode,
                                String nickname);
    }
}
