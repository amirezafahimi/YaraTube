package com.yaratech.yaratube.ui.login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.loginconfirmphone.ConfirmDialog;
import com.yaratech.yaratube.ui.login.logintype.LoginDialog;
import com.yaratech.yaratube.ui.login.loginwithphone.LoginWithPhoneDialog;
import com.yaratech.yaratube.util.AppConstants;


public class DialogContainer
        extends DialogFragment
        implements LoginDialog.DismissDialog,
        LoginWithPhoneDialog.DismissDialog,
        ConfirmDialog.DismissDialog,
        DialogContainerContract.View {

    LoginDialog loginDialog;
    LoginWithPhoneDialog loginWithPhoneDialog;
    ConfirmDialog confirmDialog;

    String phoneNumber;
    MobileLoginStep1 step1;
    MobileLoginStep2 step2;

    public DialogContainer() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DialogContainer newInstance() {
        DialogContainer fragment = new DialogContainer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginDialog = LoginDialog.newInstance();
        AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(), loginDialog, "loginDialog", true);
    }


    @Override
    public void goToLoginWithPhoneDialog() {
        loginWithPhoneDialog = LoginWithPhoneDialog.newInstance();
        AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(), loginWithPhoneDialog, "loginWithPhoneDialog", true);
    }

    @Override
    public void goToConfirmDialog(MobileLoginStep1 step1, String phoneNum) {
        confirmDialog = ConfirmDialog.newInstance(step1, phoneNum);
        this.step1 = step1;
        this.phoneNumber = phoneNum;
        AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(), confirmDialog, "confirmDialog", true);
        Toast.makeText(getContext(), step1.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void dissmissConfirmDialog(String msg) {
        dismiss();
        Hawk.put("USER_LOGIN", msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

    }
}
