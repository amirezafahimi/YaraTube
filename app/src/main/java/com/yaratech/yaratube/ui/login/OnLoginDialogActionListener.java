package com.yaratech.yaratube.ui.login;

import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;

public interface OnLoginDialogActionListener {

    void PassLoginProcess();
    void goTologinWithPhoneFragment();
    void goToVarificationFragment(MobileLoginStepOneResponse step1, String phoneNumber);
    void dissmissVarificationFragment(MobileLoginStepTwoResponse step2);
}
