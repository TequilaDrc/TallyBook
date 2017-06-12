package com.tequila.tallybook.net.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tequila on 2017/6/12.
 */

public class UpdataQuery {

    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("url")
    @Expose
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
