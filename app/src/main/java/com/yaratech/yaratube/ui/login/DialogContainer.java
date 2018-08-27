package com.yaratech.yaratube.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.orhanobut.hawk.Hawk;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.source.local.AppDatabase;
import com.yaratech.yaratube.data.source.local.utility.DataGenerator;
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

    public LoginDialog loginDialog;
    LoginWithPhoneDialog loginWithPhoneDialog;
    ConfirmDialog confirmDialog;
    String phoneNumber;

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
        if (Hawk.contains("phone_number")) {
            phoneNumber = Hawk.get("phone_number");
            confirmDialog = ConfirmDialog.newInstance(phoneNumber);
            AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                    confirmDialog, "confirmDialog", true);

        } else {
            loginDialog = LoginDialog.newInstance();
            AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                    loginDialog, "loginDialog", true);
        }
    }

    @Override
    public void goToLoginWithPhoneDialog() {
        if (Hawk.contains("phone_number"))
            Hawk.delete("phone_number");
        loginWithPhoneDialog = LoginWithPhoneDialog.newInstance();
        AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                loginWithPhoneDialog, "loginWithPhoneDialog", true);
    }

    @Override
    public void goToConfirmDialog(MobileLoginStep1 step1, String phoneNum) {
        confirmDialog = ConfirmDialog.newInstance(phoneNum);
        this.phoneNumber = phoneNum;
        AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                confirmDialog, "confirmDialog", true);
        Toast.makeText(getContext(), step1.getMessage(), Toast.LENGTH_LONG).show();
        Hawk.put("phone_number", phoneNumber);
    }

    @Override
    public void goToSelectLoginType() {
        AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                loginDialog, "loginDialog", true);
    }

    @Override
    public void dissmissConfirmDialog(MobileLoginStep2 step2) {
        if (Hawk.contains("phone_number"))
            Hawk.delete("phone_number");
        dismiss();
        DataGenerator
                .with(AppDatabase.getAppDatabase(getContext()))
                .addUser(1,
                        step2.getFinoToken(),
                        step2.getNickname(),
                        step2.getToken(),
                        step2.getMessage(),
                        phoneNumber,
                        1);
        Toast.makeText(getContext(), step2.getMessage(), Toast.LENGTH_LONG).show();
    }
}
