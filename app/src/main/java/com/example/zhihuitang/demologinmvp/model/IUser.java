package com.example.zhihuitang.demologinmvp.model;

/**
 * Created by zhihuitang on 26/08/16.
 */
public interface IUser {

    String getName();

    String getPasswd();

    int checkUserValidity(String name, String passwd);
}
