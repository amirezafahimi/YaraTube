package com.yaratech.yaratube.ui.profile;

import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yaratech.yaratube.data.source.local.entity.User;

public interface ProfileContract {
    interface View {
        void fillFroile(String name, String gender, String birthDate,
                   String profileImageUri);
    }

    interface Presenter {
        void getProfileData(String authorization);
        void readUserDataFromDB();
        String getUserToken();
        void sendProfieImage();
        void sendProfileData(String name,
                             String gender,
                             String birthday);

        void signOutUser();
    }
}
