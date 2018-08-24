package com.yaratech.yaratube.ui.loginconfirmphone;

import com.yaratech.yaratube.data.model.MobileLoginStep2;

public interface ConfirmContract {
    interface View {
        void loginMessege(MobileLoginStep2 step1);
        void showErrorMessage(String err);
    }

    interface Presenter {
        void sendActivaionCode(String num,
                                String deviceId,
                                String activationCode,
                                String nickname);
    }
}
