
package com.yaratech.yaratube.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yaratech.yaratube.util.AppConstants;

public class FeatureAvatar {

    @SerializedName("xxxdpi")
    @Expose
    private String xxxdpi;

    public String getXxxdpi() {
        return AppConstants.BASE_URL + xxxdpi;
    }

    public void setXxxdpi(String xxxdpi) {
        this.xxxdpi = xxxdpi;
    }

}
