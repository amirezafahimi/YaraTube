package com.yaratech.yaratube.ui.profile;

import android.net.Uri;
import android.util.Log;

import com.yaratech.yaratube.data.model.Data;
import com.yaratech.yaratube.data.model.ProfileGetResponse;
import com.yaratech.yaratube.data.model.ProfilePostResponse;
import com.yaratech.yaratube.data.source.ApiResultCallback;
import com.yaratech.yaratube.data.source.InsertIntoDatabaseCallback;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.local.entity.User;

import java.io.File;

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View profileViewListener;
    Repository repository;
    User user;

    public ProfilePresenter(Repository repository, ProfileContract.View profileViewListener) {
        this.repository = repository;
        this.profileViewListener = profileViewListener;
        user = repository.getUserFromDB();
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void readUserDataFromDB() {
        profileViewListener.fillFroile(user.getNickname(), user.getSex(), user.getBirhDate());
        Log.d("1235456889", "readUserDataFromDB: " + user.getPhotoUri());
        profileViewListener.setProfileImage(Uri.parse(user.getPhotoUri()));
    }

    @Override
    public void getProfileDataFromServer() {
        repository.getProfile(repository.getUserToken(), new ApiResultCallback<ProfileGetResponse>() {
            @Override
            public void onSuccess(ProfileGetResponse result) {
                profileViewListener.fillFroile(result.getNickname(),
                        result.getGender(),
                        result.getDateOfBirth());
                if (result.getAvatar().equals("")) {
                    profileViewListener.setProfileImage(null);
                } else {
                    profileViewListener.setProfileImage(Uri.parse(result.getImageLink()));
                }
            }

            @Override
            public void onFail(String err) {
                profileViewListener.showMessege(err);
            }
        });
    }


    @Override
    public void sendProfileDataToServer(String name,
                                        String gender,
                                        String birthdate,
                                        String deviceId,
                                        String deviceModel,
                                        String deviceOs) {
        repository.sendProfile(name,
                gender,
                birthdate,
                deviceId,
                deviceModel,
                deviceOs,
                repository.getUserToken(),
                new ApiResultCallback<ProfilePostResponse>() {
                    @Override
                    public void onSuccess(ProfilePostResponse result) {
                        sendProfileDetailToDB(result.getData());
                    }

                    @Override
                    public void onFail(String err) {
                        profileViewListener.showMessege(err);
                    }
                });
    }

    @Override
    public void sendProfieImageToServer(File imageFile) {
        repository.sendProfileImage(imageFile, repository.getUserToken(),
                new ApiResultCallback<ProfilePostResponse>() {
                    @Override
                    public void onSuccess(ProfilePostResponse result) {
                        Log.d("1", "onSuccess: "+result.getData().getAvatar());
                        sendProfileImageToDB(result.getData().getImageLink());
                    }

                    @Override
                    public void onFail(String err) {
                        profileViewListener.showMessege("123");
                    }
                });
    }

    @Override
    public void sendProfileDetailToDB(Data data) {
        user.setToken(repository.getUserToken());
        user.setNickname(data.getNickname());
        user.setSex(data.getGender());
        user.setBirhDate(data.getDateOfBirth());
        user.setPhotoUri(data.getImageLink());
        repository.sendUserDataToDatabase(user, new InsertIntoDatabaseCallback() {
            @Override
            public void onUserDataInserted() {
                profileViewListener.showMessege("تغییرات با موفقیت اعمال شد");
            }
        });
    }

    @Override
    public void sendProfileImageToDB(String imageFileAvatar) {
        user.setPhotoUri(imageFileAvatar);
        repository.sendUserDataToDatabase(user, new InsertIntoDatabaseCallback() {
            @Override
            public void onUserDataInserted() {
                profileViewListener.setProfileImage(Uri.parse(imageFileAvatar));
                profileViewListener.showMessege("عکس پروفایل تنظیم شد");
            }
        });
    }

    @Override
    public void signOutUser() {
        repository.deleteUserToken(repository.getUserFromDB());
        repository.setUserIsLogedIn(false);
        profileViewListener.showMessege("خروج موفقیت آمیز بود");
    }
}
