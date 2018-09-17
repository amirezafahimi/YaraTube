package com.yaratech.yaratube.ui.profile;

public interface ProfileContract {
    interface View {

    }

    interface Presenter {
        String getUserToken();
        void sendProfileData(String authorization,
                             String name,
                             String gender,
                             String birthday);
    }
}
