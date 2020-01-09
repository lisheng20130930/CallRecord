package com.qz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            Logger.log("去电======================================");
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            CallRecordManager.getInstance().safeFliter(phoneNumber);
        }
    }
}
