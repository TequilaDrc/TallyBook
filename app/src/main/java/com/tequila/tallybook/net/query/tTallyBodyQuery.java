package com.tequila.tallybook.net.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tequila on 2017/5/25.
 */

public class tTallyBodyQuery {

    @SerializedName("sBillNo")
    @Expose
    private String sBillNo;
    @SerializedName("sUserName")
    @Expose
    private String sUserName;
    @SerializedName("fPrice")
    @Expose
    private String fPrice;
    @SerializedName("JZDate")
    @Expose
    private String jZDate;

    public String getSBillNo() {
        return sBillNo;
    }

    public void setSBillNo(String sBillNo) {
        this.sBillNo = sBillNo;
    }

    public String getSUserName() {
        return sUserName;
    }

    public void setSUserName(String sUserName) {
        this.sUserName = sUserName;
    }

    public String getFPrice() {
        return fPrice;
    }

    public void setFPrice(String fPrice) {
        this.fPrice = fPrice;
    }

    public String getJZDate() {
        return jZDate;
    }

    public void setJZDate(String jZDate) {
        this.jZDate = jZDate;
    }
}
