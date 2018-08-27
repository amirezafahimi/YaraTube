package com.yaratech.yaratube.ui.login.loginconfirmphone;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.yaratech.yaratube.data.model.MobileLoginStep2;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.logintype.LoginDialog;
import com.yaratech.yaratube.util.AppConstants;


public class ConfirmDialog extends DialogFragment implements ConfirmContract.View {

    String phoneNumber;
    EditText activtionCode;
    Button send, numberCorrection;

    ConfirmPresenter confirmPresenter;

    public ConfirmDialog() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ConfirmDialog newInstance(String phoneNum) {
        ConfirmDialog fragment = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("phone_number", phoneNum);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getString("phone_number");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activtionCode = view.findViewById(R.id.code_edit_text);
        send = view.findViewById(R.id.send_button);
        numberCorrection = view.findViewById(R.id.phone_correction_botton);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        confirmPresenter = new ConfirmPresenter(this, new Repository());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPresenter.sendActivaionCode(phoneNumber,
                        AppConstants.getDeviceId(getContext()),
                        activtionCode.getText().toString(),
                        "");
            }
        });
        numberCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginDialog.DismissDialog)getParentFragment()).goToLoginWithPhoneDialog();
            }
        });
    }

    //-----------------------------------------------------------------------------

    @Override
    public void loginMessege(MobileLoginStep2 step2) {
        ((DismissDialog)getParentFragment()).dissmissConfirmDialog(step2);
    }

    @Override
    public void showErrorMessage(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_LONG).show();
    }

    //-----------------------------------------------------------------------------

    public interface DismissDialog {
        // TODO: Update argument type and name
        void dissmissConfirmDialog(MobileLoginStep2 step2);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    ((LoginDialog.DismissDialog)getParentFragment()).goToLoginWithPhoneDialog();
                    return true;
                }
                return false;
            }
        });
    }
}