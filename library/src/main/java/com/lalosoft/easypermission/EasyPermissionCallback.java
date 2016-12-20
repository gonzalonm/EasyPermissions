package com.lalosoft.easypermission;

/**
 * Created by Gonzalo.Martin on 12/20/2016
 */
public interface EasyPermissionCallback {

    void onRequestPermissionGranted(String[] permission, int[] grantResults);

    void onRequestPermissionDenied(String[] permission, int[] grantResults);

}
