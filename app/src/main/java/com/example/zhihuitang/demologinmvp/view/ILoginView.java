package com.example.zhihuitang.demologinmvp.view;

/**
 * Created by zhihuitang on 26/08/16.
 */
public interface ILoginView {
    public void onClearText();
    public void onLoginResult(Boolean result, int code);
    public void onSetProgressBarVisibility(int visibility);
}
