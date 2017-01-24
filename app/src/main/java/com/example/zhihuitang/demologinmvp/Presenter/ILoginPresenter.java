package com.example.zhihuitang.demologinmvp.Presenter;

import android.content.Context;

/**
 * Created by zhihuitang on 26/08/16.
 */
public interface ILoginPresenter {
    void clear();

    void doLogin(String name, String password);

    void setProgressBarVisibility(int visibility);

    void setLocation(double latitude, double longitude);

    void checkLocationService(Context context);
}
