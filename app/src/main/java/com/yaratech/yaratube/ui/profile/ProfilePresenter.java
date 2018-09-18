package com.yaratech.yaratube.ui.profile;

import com.yaratech.yaratube.data.source.Repository;

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View profileViewListener;
    Repository repository;

    public ProfilePresenter(Repository repository, ProfileContract.View profileViewListener) {
        this.repository = repository;
        this.profileViewListener = profileViewListener;

    }

    @Override
    public String getUserToken() {
        return repository.getUserToken();
    }

    @Override
    public void sendProfileData(String authorization, String name, String gender, String birthday) {

    }

    @Override
    public void signOutUser(int userId) {
        repository.deleteUserToken(userId);
        repository.setUserIsLogedIn(false);
    }
}
