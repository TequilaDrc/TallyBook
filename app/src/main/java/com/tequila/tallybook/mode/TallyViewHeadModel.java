package com.tequila.tallybook.mode;

/**
 * Created by Tequila on 2017/6/9.
 */

public class TallyViewHeadModel {

    private float SumPrice = 0.0f;
    private String markerName = "";

    public float getSumPrice() {
        return SumPrice;
    }

    public void setSumPrice(float sumPrice) {
        SumPrice = sumPrice;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }
}
