package com.yaratech.yaratube.ui.login.loginwithphone;

import com.yaratech.yaratube.data.model.MobileLoginStep1;

public interface LoginWithPhoneContract {
    interface View{
        void goToNextDialog(MobileLoginStep1 step1);
        void showErrorMessage(String msg);
    }

    interface Presenter{
        void sendPhoneNumber(String num,String deviceId,
                             String deviceModel,
                             String deviceOs,
                             String gcm);
    }
}
