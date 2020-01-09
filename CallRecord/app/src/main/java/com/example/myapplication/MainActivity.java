package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qz.CallRecordManager;
import com.qz.Logger;
import com.qz.RecordAudioUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private String szTestNumber = "10086";
    private final int CODE_REQUEST_AUDIORECORD = 320;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.call_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(szTestNumber);
            }
        });
    }

    public void callPhone_Realdo(String phoneNum) {
        CallRecordManager.getInstance().prepare(this,phoneNum);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    public void callPhone(String phoneNum){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkPermission =
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    +ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_REQUEST_AUDIORECORD);
                return;
            }
        }
        callPhone_Realdo(phoneNum);
    }

    @Override
    protected void onDestroy() {
        CallRecordManager.getInstance().clear(this);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CODE_REQUEST_AUDIORECORD){
            callPhone(szTestNumber);
        }
    }
}
