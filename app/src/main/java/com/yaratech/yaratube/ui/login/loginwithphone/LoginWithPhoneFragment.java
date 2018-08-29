package com.yaratech.yaratube.ui.login.loginwithphone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.OnLoginDialogActionListener;
import com.yaratech.yaratube.util.AppConstants;

public class LoginWithPhoneFragment extends Fragment implements LoginWithPhoneContract.View{

    Button send;
    EditText phoneNumber;

    LoginWithPhonePresenter loginWithPhonePresenter;


    public LoginWithPhoneFragment() {
        // Required empty public constructor
    }

    public static LoginWithPhoneFragment newInstance() {
        LoginWithPhoneFragment fragment = new LoginWithPhoneFragment();
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
    public void goToNextDialog(MobileLoginStepOneResponse step1) {
        ((OnLoginDialogActionListener)getParentFragment())
                .goToVarificationFragment(step1, phoneNumber.getText().toString());
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
