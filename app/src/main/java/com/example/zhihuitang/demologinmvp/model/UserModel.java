package com.example.zhihuitang.demologinmvp.model;

/**
 * Created by zhihuitang on 26/08/16.
 */
public class UserModel implements IUser {
    String name;
    String password;
    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPasswd() {
        return password;
    }

    @Override
    public int checkUserValidity(String name, String passwd) {
        if (name==null||passwd==null||!name.equals(getName())||!passwd.equals(getPasswd())){
            return -1;
        }
        return 0;
    }
}
