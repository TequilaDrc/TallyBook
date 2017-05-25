package com.tequila.tallybook.net.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tequila on 2017/5/25.
 */

public class tallyHeadQuery {

    @SerializedName("sBillNo")
    @Expose
    private String sBillNo;
    @SerializedName("sMakerName")
    @Expose
    private String sMakerName;
    @SerializedName("sMakerGoods")
    @Expose
    private String sMakerGoods;
    @SerializedName("sMakerPrice")
    @Expose
    private String sMakerPrice;
    @SerializedName("JZDate")
    @Expose
    private String jZDate;
    @SerializedName("nEatNum")
    @Expose
    private String nEatNum;
    @SerializedName("sRemark")
    @Expose
    private String sRemark;
    @SerializedName("sDetail")
    @Expose
    private tTallyBodyQuery sDetail;

    public String getSBillNo() {
        return sBillNo;
    }

    public void setSBillNo(String sBillNo) {
        this.sBillNo = sBillNo;
    }

    public String getSMakerName() {
        return sMakerName;
    }

    public void setSMakerName(String sMakerName) {
        this.sMakerName = sMakerName;
    }

    public String getSMakerGoods() {
        return sMakerGoods;
    }

    public void setSMakerGoods(String sMakerGoods) {
        this.sMakerGoods = sMakerGoods;
    }

    public String getSMakerPrice() {
        return sMakerPrice;
    }

    public void setSMakerPrice(String sMakerPrice) {
        this.sMakerPrice = sMakerPrice;
    }

    public String getJZDate() {
        return jZDate;
    }

    public void setJZDate(String jZDate) {
        this.jZDate = jZDate;
    }

    public String getNEatNum() {
        return nEatNum;
    }

    public void setNEatNum(String nEatNum) {
        this.nEatNum = nEatNum;
    }

    public String getSRemark() {
        return sRemark;
    }

    public void setSRemark(String sRemark) {
        this.sRemark = sRemark;
    }

    public tTallyBodyQuery getSDetail() {
        return sDetail;
    }

    public void setSDetail(tTallyBodyQuery sDetail) {
        this.sDetail = sDetail;
    }

}
