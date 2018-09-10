package com.yaratech.yaratube.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.orhanobut.hawk.Hawk;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.logintype.SelectLoginTypeFragment;
import com.yaratech.yaratube.ui.login.logintype.loginwithphone.loginvarification.VarificationFragment;
import com.yaratech.yaratube.ui.login.logintype.loginwithphone.loginsendphonenumber.LoginSendPhoneNumberFragment;
import com.yaratech.yaratube.util.Util;

public class LoginDialogContainer
        extends DialogFragment
        implements OnLoginDialogActionListener,
        LoginDialogContainerContract.View {

    ImageView close;
    String phoneNumber;
    private int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;


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
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(),
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build());
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PassLoginProcess();
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
            Util.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                    VarificationFragment.newInstance(phoneNumber), "varificationFragment", true);
        } else {
            Util.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                    SelectLoginTypeFragment.newInstance(), "selectLoginTypeFragment", true);
        }
    }

    @Override
    public void goTologinWithPhoneFragment() {
        if (Hawk.contains("phone_number"))
            Hawk.delete("phone_number");
        Util.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
                LoginSendPhoneNumberFragment.newInstance(), "loginWithPhoneFragment", true);
    }

    @Override
    public void goToVarificationFragment(MobileLoginStepOneResponse step1, String phoneNum) {
        this.phoneNumber = phoneNum;
        Util.setDialogFragment(R.id.dialog_container, getChildFragmentManager(),
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
        loginDialogContainerPresenter.saveUserData(step2, phoneNumber);
        dismiss();
    }

    @Override
    public void goTologinWithGoogleActivity() {
        signIn();
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void showMessege(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    //----------------------------------------------------------------------------------------------
    private void signIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("chi", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            String statusMessage = result.getStatus().getStatusMessage();
            Log.e("result status code", "onActivityResult: "+statusCode + " "+ statusMessage);
            // developer error; status: 10
            // SHA-1 doesn't set up correctly

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("to try", "handleSignInResult: "+account.getEmail()
                    +" "+account.getIdToken());
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            e.printStackTrace();
            Log.w("amme", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {

    }
    //----------------------------------------------------------------------------------------------
}