package com.qz;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallRecordManager {
    private TelephonyManager telephonyManager = null;
    private PhoneReceiver mReceiver = null;
    private CallStateListener mListener = null;
    private RecordAudioUtil recordAudioUtil = null;
    private String phoneNumber = null;

    private static CallRecordManager sHelper = null;
    public static CallRecordManager getInstance(){
        if(null==sHelper){
            sHelper = new CallRecordManager();
        }
        return sHelper;
    }

    private void registerSafeFlier(Context context){
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        intentFilter.setPriority(Integer.MAX_VALUE);
        mReceiver = new PhoneReceiver();
        context.registerReceiver(mReceiver,intentFilter);
    }

    public void prepare(Context context, String phoneNumber){
        this.telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        this.mListener = new CallStateListener();
        this.telephonyManager.listen(this.mListener, PhoneStateListener.LISTEN_CALL_STATE);
        this.phoneNumber = phoneNumber;
        registerSafeFlier(context);
        registerSafeFlier(context);
    }

    public void safeFliter(String phoneNumber){
        Logger.log("safeFliter==>phoneNumber="+phoneNumber);
        if(null==phoneNumber||null==this.mListener||phoneNumber.equals(this.phoneNumber)){
            Logger.log("info:sikped....");
            return;
        }
        if(null!=this.mListener) {
            this.telephonyManager.listen(this.mListener, PhoneStateListener.LISTEN_NONE);
            this.mListener = null;
        }
        this.phoneNumber = null;
    }

    public void onCallEstablised(){
        Logger.log("onCallEstablised==>phoneNumber="+this.phoneNumber);
        if(null!=this.recordAudioUtil){
            this.recordAudioUtil.stop();
            this.recordAudioUtil = null;
        }
        if(null==this.phoneNumber||this.phoneNumber.length()==0){
            this.phoneNumber = String.valueOf(System.currentTimeMillis());
        }
        this.recordAudioUtil = new RecordAudioUtil();
        this.recordAudioUtil.record(this.phoneNumber);
    }

    public void onCallHangup(){
        Logger.log("onCallHangup");
        if(null==this.recordAudioUtil||null==this.phoneNumber){
            Logger.log("info:sikped....");
            return;
        }
        this.recordAudioUtil.stop();
        this.recordAudioUtil = null;
        this.phoneNumber = null;
        if(null!=this.mListener) {
            this.telephonyManager.listen(this.mListener, PhoneStateListener.LISTEN_NONE);
            this.mListener = null;
        }
    }

    public void clear(Context context){
        Logger.log("clear");
        if(null!=mReceiver) {
            context.unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        if(null!=this.mListener) {
            this.telephonyManager.listen(this.mListener, PhoneStateListener.LISTEN_NONE);
            this.mListener = null;
        }
        this.phoneNumber = null;
    }
}
