package com.yaratech.yaratube.ui.loginwithphone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yaratech.yaratube.R;

public class LoginWithPhoneDialog extends DialogFragment {

    private DismissDialog mListener;
    Button send;



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

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.dismissLoginWithPhoneDialog();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DismissDialog) {
            mListener = (DismissDialog) context;
        }
    }


    public interface DismissDialog {
        void dismissLoginWithPhoneDialog();
    }
}
