package com.yaratech.yaratube.ui.login;

import android.content.Context;

import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;

public interface LoginDialogContainerContract {

    interface View {
        void showMessege(String message);
    }

    interface Presenter {
        void sendGoogleToken(String tokenId,
                             String deviceId,
                             String deviceOs,
                             String deviceModel,
                             String name,
                             String email,
                             String photoUrl);

        void saveUserData(MobileLoginStepTwoResponse step2, String phoneNumber);
    }
}
