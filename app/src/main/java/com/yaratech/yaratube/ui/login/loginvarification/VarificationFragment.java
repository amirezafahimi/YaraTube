package com.yaratech.yaratube.ui.login.loginvarification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.OnLoginDialogActionListener;
import com.yaratech.yaratube.util.AppConstants;


public class VarificationFragment extends DialogFragment implements VarificationContract.View {

    String phoneNumber;
    EditText activtionCode;
    Button send, numberCorrection;

    VarificationPresenter varificationPresenter;

    public VarificationFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VarificationFragment newInstance(String phoneNum) {
        VarificationFragment fragment = new VarificationFragment();
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
        return inflater.inflate(R.layout.fragment_varification_dialog, container, false);
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
        varificationPresenter = new VarificationPresenter(this, new Repository());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                varificationPresenter.sendActivaionCode(phoneNumber,
                        AppConstants.getDeviceId(getContext()),
                        activtionCode.getText().toString(),
                        "");
            }
        });
        numberCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnLoginDialogActionListener)getParentFragment()).goTologinWithPhoneFragment();
            }
        });
    }

    //-----------------------------------------------------------------------------

    @Override
    public void loginMessege(MobileLoginStepTwoResponse step2) {
        ((OnLoginDialogActionListener)getParentFragment()).dissmissVarificationFragment(step2);
    }

    @Override
    public void showErrorMessage(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_LONG).show();
    }
}