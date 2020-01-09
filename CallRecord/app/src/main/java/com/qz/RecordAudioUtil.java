package com.qz;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordAudioUtil {
    public  String lastRecrodFileName = null;
    private MediaRecorder mediaRecorder=null;
    private File recAudioSaveFileDir =null;
    private boolean isRec = false;

    public RecordAudioUtil() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            this.recAudioSaveFileDir = new File(Environment.getExternalStorageDirectory().toString()+File.separator);    //保存录音目录
        }
    }

    public void record(String phoneNumber){
        lastRecrodFileName = this.recAudioSaveFileDir.toString()+File.separator+"CallRec_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+"_"+phoneNumber+".amr";
        this.mediaRecorder = new MediaRecorder();
        try {
            this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            this.mediaRecorder.setOutputFile(lastRecrodFileName);
            this.mediaRecorder.prepare();
            this.mediaRecorder.start();
            this.isRec = true;
        } catch (Exception e){
            Logger.log("[Error==>]"+e.getMessage());
        }
    }

    public void stop(){
        if (this.isRec){
            try {
                this.mediaRecorder.stop();
            } catch (Exception e){
                try {
                    this.mediaRecorder.reset();
                }catch (Exception c){}
                Logger.log("[Error2==>]"+e.getMessage());
            }
        }
        this.mediaRecorder.release();
        this.mediaRecorder = null;
        this.isRec = false;
    }
}
