package com.babyspace.mamshare.framework.model;


import com.babyspace.mamshare.framework.utils.http.ConfigParam;

public class RequestStatusBean extends ConfigParam {

    public static final int STATUS_REQUEST_START = 0;

    public static final int STATUS_REQUEST_NONE = -1;
    public static final int STATUS_REQUEST_END_SUCCESS = 1;
    public static final int STATUS_REQUEST_END_FAIL = 2;


    public Class<?> className;
    public int Status = STATUS_REQUEST_NONE;
    public String url;
}
