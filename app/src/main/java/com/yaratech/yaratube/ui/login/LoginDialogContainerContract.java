package com.yaratech.yaratube.ui.login;

import android.content.Context;

import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;

public interface LoginDialogContainerContract {

    interface View {
        void setUserIsLogedIn(boolean userIsLogedIn);
        void showMessege(String message);
    }

    interface Presenter {
        void insertUserData(Context context, MobileLoginStepTwoResponse step2, String phoneNumber);
    }
}
