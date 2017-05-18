package com.tequila.tallybook.bean;

/**
 * Created by tequila on 2017/5/18.
 */

public class UserBean {

    private boolean dai = false;
    private boolean yang = false;
    private boolean xu = false;
    private boolean zhou = false;
    private boolean zhang = false;

    public boolean isDai() {
        return dai;
    }

    public void setDai(boolean dai) {
        this.dai = dai;
    }

    public boolean isYang() {
        return yang;
    }

    public void setYang(boolean yang) {
        this.yang = yang;
    }

    public boolean isXu() {
        return xu;
    }

    public void setXu(boolean xu) {
        this.xu = xu;
    }

    public boolean isZhou() {
        return zhou;
    }

    public void setZhou(boolean zhou) {
        this.zhou = zhou;
    }

    public boolean isZhang() {
        return zhang;
    }

    public void setZhang(boolean zhang) {
        this.zhang = zhang;
    }

    public int getPeopleNumber() {
        int count = 0;

        if (dai) count++;
        if (yang) count++;
        if (xu) count++;
        if (zhou) count++;
        if (zhang) count++;

        return count;
    }
}
