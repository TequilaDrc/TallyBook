package com.tequila.tallybook.mode;

/**
 * Created by Tequila on 2017/5/19.
 */

public class TimeTrace {
    /** 时间 */
    private String JZDate;
    /** 购买物品 */
    private String sMakerGoods;
    /** 购买人*/
    private String sMakerName;

    public TimeTrace(String acceptTime, String acceptStation, String name) {
        this.JZDate = acceptTime;
        this.sMakerGoods = acceptStation;
        this.sMakerName = name;
    }

    public String getAcceptTime() {
        return JZDate;
    }

    public void setAcceptTime(String acceptTime) {
        this.JZDate = acceptTime;
    }

    public String getAcceptStation() {
        return sMakerGoods;
    }

    public void setAcceptStation(String acceptStation) {
        this.sMakerGoods = acceptStation;
    }

    public String getName() {
        return sMakerName;
    }

    public void setName(String name) {
        this.sMakerName = name;
    }
}