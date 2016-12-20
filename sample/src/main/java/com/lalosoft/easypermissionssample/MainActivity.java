package com.lalosoft.easypermissionssample;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.lalosoft.easypermission.EasyPermissionActivity;
import com.lalosoft.easypermission.RegisterPermission;

@RegisterPermission(permissions = Manifest.permission.WRITE_EXTERNAL_STORAGE)
public class MainActivity extends EasyPermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestPermissionGranted(String[] permission) {
        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionDenied(String[] permission) {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
    }
}
