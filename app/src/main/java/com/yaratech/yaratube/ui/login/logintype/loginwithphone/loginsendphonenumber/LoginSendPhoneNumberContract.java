package com.yaratech.yaratube.ui.login.logintype.loginwithphone.loginsendphonenumber;

import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;

public interface LoginSendPhoneNumberContract {
    interface View{
        void goToNextDialog(MobileLoginStepOneResponse step1);
        void showErrorMessage(String msg);
    }

    interface Presenter{
        void sendPhoneNumber(String num,String deviceId,
                             String deviceModel,
                             String deviceOs,
                             String gcm);
    }
}
