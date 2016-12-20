package com.lalosoft.easypermission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzalo.Martin on 12/20/2016
 */

public abstract class EasyPermissionActivity extends AppCompatActivity implements EasyPermissionCallback {

    private static final int MY_PERMISSIONS_REQUEST = 1;
    private EasyPermissionCallback callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check permissions in runtime from Android M (API Level 23)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            callback = this;
            checkPermissions();
        }
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                callback.onRequestPermissionGranted(permissions, grantResults);
            } else {
                // permission denied
                callback.onRequestPermissionDenied(permissions, grantResults);
            }
        }
    }

    @Override
    public void onRequestPermissionGranted(String[] permission, int[] grantResults) {
        // This method can be implemented by subclasses
    }

    @Override
    public void onRequestPermissionDenied(String[] permission, int[] grantResults) {
        // This method can be implemented by subclasses
    }

    private void checkPermissions() {
        RegisterPermission registerPermission = this.getClass().getAnnotation(RegisterPermission.class);
        if (registerPermission != null) {
            // get current permissions
            String[] permissions = registerPermission.permissions();

            // request permissions
            List<String> deniedPermissions = getDeniedPermissions(permissions);
            if (!deniedPermissions.isEmpty()) {
                ActivityCompat.requestPermissions(this, deniedPermissions.toArray(new String[deniedPermissions.size()]), MY_PERMISSIONS_REQUEST);
            }
        }
    }

    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!isGrantedPermission(permissions[i])) {
                result.add(permissions[i]);
            }
        }
        return result;
    }

    private boolean isGrantedPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
