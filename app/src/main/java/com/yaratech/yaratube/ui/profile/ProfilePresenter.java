package com.yaratech.yaratube.ui.profile;

import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.local.entity.User;
import com.yaratech.yaratube.data.source.local.utility.DataGenerator;

import java.io.File;

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View profileViewListener;
    Repository repository;

    public ProfilePresenter(Repository repository, ProfileContract.View profileViewListener) {
        this.repository = repository;
        this.profileViewListener = profileViewListener;
    }

    @Override
    public void getProfileData(String authorization) {

    }

    @Override
    public void readUserDataFromDB() {
        User user = repository.getUserFromDB();
        profileViewListener.fillFroile(user.getNickname(),
                user.getSex(),
                user.getBirhDate(),
                user.getPhotoUri());
    }


    @Override
    public String getUserToken() {
        return repository.getUserToken();
    }

    @Override
    public void sendProfieImage() {

    }

    @Override
    public void sendProfileData(String name, String gender, String birthday) {

    }

    @Override
    public void signOutUser() {
        repository.deleteUserToken(repository.getUserFromDB());
        repository.setUserIsLogedIn(false);
    }
}
