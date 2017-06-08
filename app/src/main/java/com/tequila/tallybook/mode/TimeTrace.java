package com.tequila.tallybook.mode;

/**
 * Created by Tequila on 2017/5/19.
 */

public class TimeTrace {
    /** 单据号*/
    private String sBillNo = "";
    /** 时间 */
    private String JZDate = "";
    /** 购买物品 */
    private String sMakerGoods = "";
    /** 购买人*/
    private String sMakerName = "";

    public String getsBillNo() {
        return sBillNo;
    }

    public void setsBillNo(String sBillNo) {
        this.sBillNo = sBillNo;
    }

    public String getJZDate() {
        return JZDate;
    }

    public void setJZDate(String JZDate) {
        this.JZDate = JZDate;
    }

    public String getsMakerGoods() {
        return sMakerGoods;
    }

    public void setsMakerGoods(String sMakerGoods) {
        this.sMakerGoods = sMakerGoods;
    }

    public String getsMakerName() {
        return sMakerName;
    }

    public void setsMakerName(String sMakerName) {
        this.sMakerName = sMakerName;
    }
}