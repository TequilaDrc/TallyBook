package com.tequila.tallybook.net.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tequila on 2017/6/6.
 */

public class saveLifeInfoQuery {

    private String sMakerName = "";
    private int PeopleNumber = 0;
    private List<String> PeopleName = new ArrayList<>();
    private float Money = 0;
    private String PluInfo = "";
    private String Remark = "";

    public String getsMakerName() {
        return sMakerName;
    }

    public void setsMakerName(String sMakerName) {
        this.sMakerName = sMakerName;
    }

    public int getPeopleNumber() {
        return PeopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        PeopleNumber = peopleNumber;
    }

    public List<String> getPeopleName() {
        return PeopleName;
    }

    public void setPeopleName(List<String> peopleName) {
        PeopleName.clear();
        PeopleName.addAll(peopleName);
    }

    public float getMoney() {
        return Money;
    }

    public void setMoney(float money) {
        Money = money;
    }

    public String getPluInfo() {
        return PluInfo;
    }

    public void setPluInfo(String pluInfo) {
        PluInfo = pluInfo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
