package com.qz;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Author Listen.Li
 */
public class CallStateListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch(state){
            case TelephonyManager.CALL_STATE_IDLE:
                Logger.log("挂断");
                CallRecordManager.getInstance().onCallHangup();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Logger.log("接听");
                CallRecordManager.getInstance().onCallEstablised();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Logger.log("响铃");
                CallRecordManager.getInstance().safeFliter("");
                break;
            default:
                break;
        }
    }
}
