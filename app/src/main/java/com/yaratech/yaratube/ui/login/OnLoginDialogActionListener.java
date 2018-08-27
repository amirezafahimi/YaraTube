package com.yaratech.yaratube.ui.login;

import com.yaratech.yaratube.data.model.MobileLoginStep1;
import com.yaratech.yaratube.data.model.MobileLoginStep2;

public interface OnLoginDialogActionListener {
    void goTologinWithPhoneFragment();
    void goTovarificationFragment(MobileLoginStep1 step1, String phoneNumber);
    void goToSelectLoginType();
    void dissmissVarificationFragment(MobileLoginStep2 step2);
}
