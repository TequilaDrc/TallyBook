package com.tequila.tallybook.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tequila on 2017/5/17.
 */

public class ResultModel {

    @SerializedName("errorInfo")
    @Expose
    private String errorInfo;

    @SerializedName("returnInfo")
    @Expose
    private String returnInfo;

    @SerializedName("succeedFlag")
    @Expose
    private String succeedFlag;

    /**
     *
     * @return
     * The errorInfo
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     *
     * @param errorInfo
     * The errorInfo
     */
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     *
     * @return
     * The returnInfo
     */
    public String getReturnInfo() {
        return returnInfo;
    }

    /**
     *
     * @param returnInfo
     * The returnInfo
     */
    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    /**
     *
     * @return
     * The succeedFlag
     */
    public String getSucceedFlag() {
        return succeedFlag;
    }

    /**
     *
     * @param succeedFlag
     * The succeedFlag
     */
    public void setSucceedFlag(String succeedFlag) {
        this.succeedFlag = succeedFlag;
    }
}