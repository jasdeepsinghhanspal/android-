package com.example.tourch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isTorchOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the device has a flash
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "Your device does not have a flash.", Toast.LENGTH_SHORT).show();
            finish();
        }

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void toggleTorch(View view) {
        try {
            if (isTorchOn) {
                turnOffTorch();
            } else {
                turnOnTorch();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOnTorch() throws CameraAccessException {
        if (cameraId != null) {
            cameraManager.setTorchMode(cameraId, true);
            isTorchOn = true;
        }
    }

    private void turnOffTorch() throws CameraAccessException {
        if (cameraId != null) {
            cameraManager.setTorchMode(cameraId, false);
            isTorchOn = false;
        }
    }
}
--------------------------------------------------------------------------------------------------------------------------------------


  <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:id="@+id/toggleButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Toggle Torch"
        android:layout_centerInParent="true"
        android:onClick="toggleTorch" />

</RelativeLayout>

  --------------------------------------------------------------------------------------------------------------------------------------------

  <uses-permission android:name="android.permission.CAMERA" />
