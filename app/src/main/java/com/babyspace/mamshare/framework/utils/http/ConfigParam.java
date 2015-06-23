package com.babyspace.mamshare.framework.utils.http;

public class ConfigParam {

    public static final int REFRESH_MODE_PULL_DOWN = 0;
    public static final int REFRESH_MODE_PULL_UP = 1;
    public static final int REFRESH_MODE_NONE = -1;


    public int refreshMode = REFRESH_MODE_NONE;
    public boolean showTip = true;

    public ConfigParam() {

    }

    public ConfigParam(Boolean showTip) {
        this.showTip = showTip;
    }
}
