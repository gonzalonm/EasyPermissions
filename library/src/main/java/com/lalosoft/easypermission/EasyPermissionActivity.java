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

        callback = this;
        // check permissions in runtime from Android M (API Level 23)
        if (isCorrectApiLevel()) {
            checkClassPermissions();
        } else {
            // grant permission
            callback.onRequestPermissionGranted(getClassPermissions());
        }
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                callback.onRequestPermissionGranted(permissions);
            } else {
                // permission denied
                callback.onRequestPermissionDenied(permissions);
            }
        }
    }

    @Override
    public void onRequestPermissionGranted(String[] permission) {
        // This method can be implemented by subclasses
    }

    @Override
    public void onRequestPermissionDenied(String[] permission) {
        // This method can be implemented by subclasses
    }

    public void askPermission(String[] permissions, EasyPermissionCallback callback) {
        this.callback = callback;
        if (isCorrectApiLevel()) {
            askForPermissions(permissions);
        } else {
            // grant the permission
            callback.onRequestPermissionGranted(permissions);
        }
    }

    private void checkClassPermissions() {
        // get current permissions
        String[] classPermissions = getClassPermissions();
        if (classPermissions != null) {
            askForPermissions(classPermissions);
        }
    }

    private void askForPermissions(String[] permissions) {
        // request permissions
        List<String> deniedPermissions = getDeniedPermissions(permissions);
        if (!deniedPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, deniedPermissions.toArray(new String[deniedPermissions.size()]), MY_PERMISSIONS_REQUEST);
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

    private boolean isCorrectApiLevel() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private String[] getClassPermissions() {
        RegisterPermission registerPermission = this.getClass().getAnnotation(RegisterPermission.class);
        if (registerPermission != null) {
            return registerPermission.permissions();
        }
        return null;
    }
}
