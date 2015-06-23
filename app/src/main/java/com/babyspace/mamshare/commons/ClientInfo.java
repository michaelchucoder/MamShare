package com.babyspace.mamshare.commons;

import java.io.Serializable;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.framework.app
 * Author: MichaelChuCoder
 * Date: 2015/5/16
 * Time: 17:55
 * To change this template use File | Settings | File and Code Templates.
 */
public class ClientInfo implements Serializable {


    public static final String INTERFACE_TOKEN = "interfacetoken";

    public  String deviceId;
    public  String USER_AGENT;
    public  String equipmentModel;
    public  String equipmentOSVersion;
    public  String setupChannel;
    public  String VERSION;
    public  String platform;
    public  String screensize;
    public  String appname;

    public Location local = new Location();


    public class Location {
        /**
         * 当前纬度
         */
        public String latitude;
        /**
         * 当前经度
         */
        public String longitude;

        /**
         * xx省／xx市
         */
        public String province;
        /**
         * xx市
         */
        public String city;
        /**
         * xx区／xx县
         */
        public String district;

        public String nearByHasAirportCity;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "appname='" + appname + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", USER_AGENT='" + USER_AGENT + '\'' +
                ", equipmentModel='" + equipmentModel + '\'' +
                ", equipmentOSVersion='" + equipmentOSVersion + '\'' +
                ", setupChannel='" + setupChannel + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", platform='" + platform + '\'' +
                ", screensize='" + screensize + '\'' +
                ", local=" + local +
                '}';
    }
}
