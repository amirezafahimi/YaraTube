package com.yaratech.yaratube.ui.login;

import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.model.MobileLoginStep2;

public interface OnLoginDialogActionListener {

    void PassLoginProcess();
    void goTologinWithPhoneFragment();
    void goToVarificationFragment(MobileLoginStep1 step1, String phoneNumber);
    void dissmissVarificationFragment(MobileLoginStep2 step2);
}
