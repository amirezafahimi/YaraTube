package com.yaratech.yaratube.ui.login.loginwithphone;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.DialogContainer;
import com.yaratech.yaratube.ui.login.DialogContainerContract;
import com.yaratech.yaratube.ui.login.DialogContainerPresenter;
import com.yaratech.yaratube.ui.login.logintype.LoginDialog;
import com.yaratech.yaratube.util.AppConstants;

public class LoginWithPhoneDialog extends Fragment implements LoginWithPhoneContract.View{

    Button send;
    EditText phoneNumber;

    LoginWithPhonePresenter loginWithPhonePresenter;


    public LoginWithPhoneDialog() {
        // Required empty public constructor
    }

    public static LoginWithPhoneDialog newInstance() {
        LoginWithPhoneDialog fragment = new LoginWithPhoneDialog();
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
        return inflater.inflate(R.layout.fragment_login_with_phone_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        send = view.findViewById(R.id.send_phone_button);
        phoneNumber = view.findViewById(R.id.phone_edit_text);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginWithPhonePresenter = new LoginWithPhonePresenter(new Repository(), this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithPhonePresenter.sendPhoneNumber(phoneNumber.getText().toString(),
                        AppConstants.getDeviceId(getContext()),
                        AppConstants.getDeviceModel(),
                        AppConstants.getDeviceOS(),
                        "");
            }
        });
    }

    @Override
    public void goToNextDialog(MobileLoginStep1 step1) {
        ((DismissDialog)getParentFragment()).goToConfirmDialog(step1, phoneNumber.getText().toString());
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    ((DismissDialog)getParentFragment()).goToSelectLoginType();
                    return true;
                }
                return false;
            }
        });
    }


    public interface DismissDialog {
        void goToConfirmDialog(MobileLoginStep1 step1, String phoneNum);
        void goToSelectLoginType();
    }
}
