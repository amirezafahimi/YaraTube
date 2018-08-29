package com.yaratech.yaratube.ui.login.loginwithphone;

import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;

public interface LoginWithPhoneContract {
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
