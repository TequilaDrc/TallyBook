package com.tequila.tallybook.mode;

/**
 * Created by Tequila on 2017/5/18.
 */

public class UsrUserModel {

    private String sUserName = "";
    private String sUserPhone = "";
    private String sPasswd = "";

    public String getsUserName() {
        return sUserName;
    }

    public void setsUserName(String sUserName) {
        this.sUserName = sUserName;
    }

    public String getsUserPhone() {
        return sUserPhone;
    }

    public void setsUserPhone(String sUserPhone) {
        this.sUserPhone = sUserPhone;
    }

    public String getsPasswd() {
        return sPasswd;
    }

    public void setsPasswd(String sPasswd) {
        this.sPasswd = sPasswd;
    }
}
