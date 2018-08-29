package com.yaratech.yaratube.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.orhanobut.hawk.Hawk;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.logintype.SelectLoginTypeFragment;
import com.yaratech.yaratube.ui.login.loginvarification.VarificationFragment;
import com.yaratech.yaratube.ui.login.loginwithphone.LoginWithPhoneFragment;
import com.yaratech.yaratube.util.AppConstants;

import static com.yaratech.yaratube.MainActivity.USER_IS_LOGED_IN;

public class LoginDialogContainer
        extends DialogFragment
        implements OnLoginDialogActionListener,
        LoginDialogContainerContract.View {

    ImageView close;
    String phoneNumber;

    public LoginDialogContainer() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginDialogContainer newInstance() {
        LoginDialogContainer fragment = new LoginDialogContainer();
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
        close = view.findViewById(R.id.close_login_dialog);
        PassLoginProcess();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void PassLoginProcess() {
        if (Hawk.contains("phone_number")) {
            phoneNumber = Hawk.get("phone_number");
            AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                    VarificationFragment.newInstance(phoneNumber), "varificationFragment", true);
        } else {
            AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                    SelectLoginTypeFragment.newInstance(), "selectLoginTypeFragment", true);
        }
    }

    @Override
    public void goTologinWithPhoneFragment() {
        if (Hawk.contains("phone_number"))
            Hawk.delete("phone_number");
        AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                LoginWithPhoneFragment.newInstance(), "loginWithPhoneFragment", true);
    }

    @Override
    public void goToVarificationFragment(MobileLoginStepOneResponse step1, String phoneNum) {
        this.phoneNumber = phoneNum;
        AppConstants.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                VarificationFragment.newInstance(phoneNum), "varificationFragment", true);
        Toast.makeText(getContext(), step1.getMessage(), Toast.LENGTH_LONG).show();
        Hawk.put("phone_number", phoneNumber);
    }

    @Override
    public void dissmissVarificationFragment(MobileLoginStepTwoResponse step2) {
        if (Hawk.contains("phone_number"))
            Hawk.delete("phone_number");
        LoginDialogContainerPresenter loginDialogContainerPresenter = new
                LoginDialogContainerPresenter(this, new Repository());
        loginDialogContainerPresenter.insertUserData(getContext(), step2, phoneNumber);
        dismiss();
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void setUserIsLogedIn(boolean userIsLogedIn) {
        USER_IS_LOGED_IN = userIsLogedIn;
    }

    @Override
    public void showMessege(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}