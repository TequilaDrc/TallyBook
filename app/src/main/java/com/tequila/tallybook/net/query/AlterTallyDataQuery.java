package com.tequila.tallybook.net.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tequila on 2017/6/27.
 */

public class AlterTallyDataQuery {

    @SerializedName("sBillNo")
    @Expose
    private String sBillNo = "";

    @SerializedName("sMakerName")
    @Expose
    private String sMakerName = "";

    @SerializedName("AlterType")
    @Expose
    private String AlterType = "0";

    public String getsBillNo() {
        return sBillNo;
    }

    public void setsBillNo(String sBillNo) {
        this.sBillNo = sBillNo;
    }

    public String getsMakerName() {
        return sMakerName;
    }

    public void setsMakerName(String sMakerName) {
        this.sMakerName = sMakerName;
    }

    public String getAlterType() {
        return AlterType;
    }

    public void setAlterType(String alterType) {
        AlterType = alterType;
    }
}
