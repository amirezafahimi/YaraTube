package com.yaratech.yaratube.ui.login;

import android.content.Context;
import android.net.Uri;

import com.yaratech.yaratube.data.model.GoogleLoginResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.source.ApiResultCallback;
import com.yaratech.yaratube.data.source.InsertIntoDatabaseCallback;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.local.entity.User;
import com.yaratech.yaratube.data.source.local.utility.DataGenerator;

public class LoginDialogContainerPresenter implements LoginDialogContainerContract.Presenter {

    LoginDialogContainerContract.View loginDialogContainerViewListener;
    Repository repository;

    public LoginDialogContainerPresenter
            (LoginDialogContainerContract.View loginDialogContainerViewListener,
             Repository repository) {
        this.loginDialogContainerViewListener = loginDialogContainerViewListener;
        this.repository = repository;
    }

    @Override
    public void sendGoogleToken(String tokenId,
                                String deviceId,
                                String deviceOs,
                                String deviceModel,
                                String name,
                                String email,
                                String photoUri) {
        repository.googleLogin(tokenId, deviceId, deviceOs, deviceModel,
                new ApiResultCallback<GoogleLoginResponse>() {
                    @Override
                    public void onSuccess(GoogleLoginResponse result) {
                        User user = new User();
                        user.setId(1);
                        user.setToken(result.getToken());
                        user.setNickname(name);
                        user.setEmail(email);
                        user.setPhotoUri(photoUri);
                        repository.sendUserDataToDatabase(user,
                                new InsertIntoDatabaseCallback() {
                                    @Override
                                    public void onUserDataInserted() {
                                        loginDialogContainerViewListener.showMessege(result.getMessage());
                                    }
                                });
                        repository.setUserIsLogedIn(true);
                    }

                    @Override
                    public void onFail(String err) {
                        loginDialogContainerViewListener.showMessege(err);
                    }
                });

    }

    @Override
    public void saveUserData(MobileLoginStepTwoResponse step2, String phoneNumber) {
        User user = new User();
        user.setId(1);
        user.setFinoToken(step2.getFinoToken());
        user.setNickname(step2.getNickname());
        user.setToken(step2.getToken());
        user.setMessage(step2.getMessage());
        user.setPhoneNember(phoneNumber);
        repository.sendUserDataToDatabase(
                user,
                new InsertIntoDatabaseCallback() {
                    @Override
                    public void onUserDataInserted() {
                        loginDialogContainerViewListener.showMessege(user.getMessage());
                    }
                });
        repository.setUserIsLogedIn(true);
    }
}
