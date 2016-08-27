package com.example.zhihuitang.demologinmvp.Presenter;

/**
 * Created by zhihuitang on 26/08/16.
 */
public interface ILoginPresenter {
    void clear();
    void doLogin(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);
    void setLocation(double latitude, double longtitude);
}
