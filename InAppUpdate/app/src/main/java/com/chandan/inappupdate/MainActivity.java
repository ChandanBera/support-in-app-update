package com.chandan.inappupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.sanojpunchihewa.updatemanager.BuildConfig;
import com.sanojpunchihewa.updatemanager.UpdateManager;
import com.sanojpunchihewa.updatemanager.UpdateManagerConstant;

public class MainActivity extends AppCompatActivity {

    UpdateManager mUpdateManager;
    TextView txtFlexibleUpdateProgress;
    Button fexibleUpdate,immediateUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtCurrentVersion = findViewById(R.id.txt_current_version);
        TextView txtAvailableVersion = findViewById(R.id.txt_available_version);
        TextView txtStalenessDays = findViewById(R.id.txt_staleness_days);
        txtFlexibleUpdateProgress = findViewById(R.id.txt_flexible_progress);
        fexibleUpdate = findViewById(R.id.fexibleUpdate);
        immediateUpdate = findViewById(R.id.immediateUpdate);

        txtCurrentVersion.setText(String.valueOf(BuildConfig.VERSION_CODE));

        mUpdateManager = UpdateManager.Builder(this);

        mUpdateManager.addUpdateInfoListener(new UpdateManager.UpdateInfoListener() {
            @Override
            public void onReceiveVersionCode(int code) {
                Log.d("XXXXXXXXX",code+"--");
                txtAvailableVersion.setText(String.valueOf(code));
            }

            @Override
            public void onReceiveStalenessDays(int days) {
                txtStalenessDays.setText(String.valueOf(days));
            }
        });

        mUpdateManager.addFlexibleUpdateDownloadListener(new UpdateManager.FlexibleUpdateDownloadListener() {
            @Override
            public void onDownloadProgress(long bytesDownloaded, long totalBytes) {
                txtFlexibleUpdateProgress.setText("Downloading: " + bytesDownloaded + " / " + totalBytes);

            }
        });

        fexibleUpdate.setOnClickListener(fexibleUpdateListener);
        immediateUpdate.setOnClickListener(immediateUpdateListener);

    }


    View.OnClickListener fexibleUpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            callFlexibleUpdate();
        }
    };

    View.OnClickListener immediateUpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                callImmediateUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    public void callFlexibleUpdate() {
        // Start a Flexible Update
        mUpdateManager.mode(UpdateManagerConstant.FLEXIBLE).start();
        txtFlexibleUpdateProgress.setVisibility(View.VISIBLE);
    }
    public void callImmediateUpdate() {
        // Start a Immediate Update
        mUpdateManager.mode(UpdateManagerConstant.IMMEDIATE).start();
    }
}