package com.yaratech.yaratube.ui.login.logintype;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.ui.login.OnLoginDialogActionListener;


public class SelectLoginTypeFragment extends DialogFragment {

    LinearLayout loginWithPhone, loginBWithGoogle;

    public SelectLoginTypeFragment() {
        // Required empty public constructor
    }

    public static SelectLoginTypeFragment newInstance() {
        SelectLoginTypeFragment fragment = new SelectLoginTypeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginWithPhone = view.findViewById(R.id.login_with_phone_number);
        loginBWithGoogle = view.findViewById(R.id.login_with_google_account);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnLoginDialogActionListener)getParentFragment()).goTologinWithPhoneFragment();
            }
        });
    }

}
