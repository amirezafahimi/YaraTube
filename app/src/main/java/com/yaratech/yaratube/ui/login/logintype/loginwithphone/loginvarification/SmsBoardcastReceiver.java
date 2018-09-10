package com.yaratech.yaratube.ui.login.logintype.loginwithphone.loginvarification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.yaratech.yaratube.util.Util;

public class SmsBoardcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    private static Listener listener;
    private String verificationCode;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            Bundle smsBundle = intent.getExtras();
            if (smsBundle != null) {
                Object[] pdus = (Object[]) smsBundle.get("pdus");
                if (pdus == null) {
                    // Display some error to the user
                    Log.e(TAG, "SmsBundle had no pdus key");
                    return;
                }
                SmsMessage messages;
                for (int i = 0; i < pdus.length; i++) {
                    messages = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    smsBody += messages.getMessageBody();
                    smsSender = messages.getOriginatingAddress();
                    verificationCode = smsBody.replaceAll("[^0-9]", "");

                    if (smsSender.equals(Util.OTP_SENDER_NUMBER)) {
                        if (listener != null) {
                            listener.onTextReceived(verificationCode);
                        }
                    }
                }

            }

        }
    }

    static void setListener(Listener opotListener) {
        listener = opotListener;
    }

    interface Listener {
        void onTextReceived(String text);
    }
}