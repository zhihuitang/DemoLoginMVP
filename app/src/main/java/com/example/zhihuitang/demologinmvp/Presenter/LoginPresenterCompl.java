package com.example.zhihuitang.demologinmvp.Presenter;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.example.zhihuitang.demologinmvp.model.IUser;
import com.example.zhihuitang.demologinmvp.model.UserModel;
import com.example.zhihuitang.demologinmvp.view.ILoginView;
import java.util.Locale;

/**
 * Created by zhihuitang on 26/08/16.
 */
public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    Handler handler;
    final String TAG = "tang-demo";

    public LoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());

        // location
        LocationManager locationManager = this.iLoginView.getLocationManager();
        LocationListener locationListener = new MyLocationListener();
        //        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);

    }

    private void initUser() {
        user = new UserModel("mvp", "mvp");
    }

    @Override public void clear() {
        iLoginView.onClearText();
    }

    @Override public void doLogin(String name, String passwd) {
        Boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name, passwd);
        if (code != 0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 2000);

        double a = 18.122322, b = 18.122322;
        if (a == b) {
            Log.d("tang", String.format(Locale.getDefault(), " %f equals %f", a, b));
        } else {
            Log.d("tang", String.format(Locale.getDefault(), " %f not equals %f", a, b));
        }
    }

    @Override public void setProgressBarVisibility(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }

    @Override public void setLocation(double latitude, double longtitude) {
        Log.d(TAG, String.format("get location: %f, %f", latitude, longtitude));
        iLoginView.onLocation(latitude, longtitude);
    }

    @Override public void checkLocationService(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!gps_enabled && !network_enabled) {
            iLoginView.showLocationService();
        } else {
            Log.d("tang", "location service enabled");
        }
    }

    private class MyLocationListener implements LocationListener {
        @Override public void onLocationChanged(Location location) {
            String text =
                String.format(Locale.getDefault(), "(%f, %f), accuracy: %f", location.getLatitude(),
                    location.getLongitude(), location.getAccuracy());
            Log.d(TAG, text);
            iLoginView.onLocation(location.getLatitude(), location.getLongitude());
        }

        @Override public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override public void onProviderEnabled(String s) {

        }

        @Override public void onProviderDisabled(String s) {

        }
    }
}
