package com.tequila.tallybook.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tequila on 2017/6/14.
 */

public class AccountDetailsModel {

    @SerializedName("sUserName")
    @Expose
    private String sUserName = "";

    @SerializedName("fPrice")
    @Expose
    private String fPrice = "";

    public String getsUserName() {
        return sUserName;
    }

    public void setsUserName(String sUserName) {
        this.sUserName = sUserName;
    }

    public String getfPrice() {
        return fPrice;
    }

    public void setfPrice(String fPrice) {
        this.fPrice = fPrice;
    }
}
