package com.drchip.ihelp;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

class Network{
    WifiManager wifiManager;
    WifiInfo wifiInfo;

    List<ScanResult> bssidList ;

    String macAdress;
    String ssid;
    String ipAddress;

    int linkspeed;
    int networkId;
    int ip;

    Network()
    {
        wifiManager =   null;
        wifiInfo    =   null;
        bssidList   =   null;
        macAdress   =   "";
        ssid        =   "";
        ipAddress   =   "";
        linkspeed   =   0;
        networkId   =   0;
        ip          =   0;
    }
}
