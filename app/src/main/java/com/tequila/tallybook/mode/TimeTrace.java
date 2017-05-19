package com.tequila.tallybook.mode;

/**
 * Created by Tequila on 2017/5/19.
 */

public class TimeTrace {
    /** 时间 */
    private String acceptTime;
    /** 描述 */
    private String acceptStation;
    private String name;

    public TimeTrace(String acceptTime, String acceptStation) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}