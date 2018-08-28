package com.yaratech.yaratube.ui.login;

import android.content.Context;

import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.model.MobileLoginStep2;

public interface LoginDialogContainerContract {

    interface View {
        void showMessege(String message);
    }

    interface Presenter {
        void insertUserData(Context context, MobileLoginStep2 step2, String phoneNumber);
    }
}
