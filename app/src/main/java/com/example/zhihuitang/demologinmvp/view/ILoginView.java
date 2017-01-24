package com.example.zhihuitang.demologinmvp.view;

import android.location.LocationManager;

/**
 * Created by zhihuitang on 26/08/16.
 */
public interface ILoginView {
    void onClearText();

    void onLoginResult(Boolean result, int code);

    void onSetProgressBarVisibility(int visibility);

    void onLocation(double latitude, double longitude);

    LocationManager getLocationManager();

    void showLocationService();
}
