package com.yaratech.yaratube.ui.profile;

import android.net.Uri;

import com.yaratech.yaratube.data.model.Data;
import com.yaratech.yaratube.data.source.local.entity.User;

import java.io.File;

public interface ProfileContract {
    interface View {
        void showMessege(String msg);

        void fillFroile(String name, String gender, String birthDate);

        void setProfileImage(Uri imageUri);
    }

    interface Presenter {

        void readUserDataFromDB();

        void getProfileDataFromServer();

        void sendProfileDataToServer(String name,
                                     String gender,
                                     String birthdate,
                                     String deviceId,
                                     String deviceModel,
                                     String deviceOs);

        void sendProfieImageToServer(File imageFile);

        void sendProfileDetailToDB(Data data);

        void sendProfileImageToDB(String imageFileAvatar);

        void signOutUser();
    }
}
