package com.example.zhihuitang.demologinmvp.view;

import android.location.LocationManager;

/**
 * Created by zhihuitang on 26/08/16.
 */
public interface ILoginView {
    public void onClearText();
    public void onLoginResult(Boolean result, int code);
    public void onSetProgressBarVisibility(int visibility);
    public void onLocation(double latitude, double longtitude);
    public LocationManager getLocationManager();
}
