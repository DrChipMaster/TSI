package com.drchip.ihelp;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.util.List;

import static android.content.Context.WIFI_SERVICE;

class NetworkInfo {
    Network net =   new Network();

    NetworkInfo(Context context)
    {
        net.wifiManager =   (WifiManager)   context.getSystemService(WIFI_SERVICE);
        net.wifiInfo    =   net.wifiManager.getConnectionInfo();
        net.bssidList   =   net.wifiManager.getScanResults();
        net.ip          =   net.wifiInfo.getIpAddress();
        net.macAdress   =   net.wifiInfo.getBSSID();
        net.linkspeed   =   net.wifiInfo.getLinkSpeed();
        net.networkId   =   net.wifiInfo.getNetworkId();
        net.ssid        =   net.wifiInfo.getSSID();
        net.ipAddress   =   Formatter.formatIpAddress(net.ip);
    }

    void stScan(Context context)
    {
        ((WifiManager) context.getSystemService(WIFI_SERVICE)).startScan();
    }

    String sNetwork() {
        int nBssid;
        String bssidList_toSting = "\n";

        for (nBssid = 0; nBssid < net.bssidList.size(); nBssid++) {
            bssidList_toSting += net.bssidList.get(nBssid).BSSID + "\n";
        }

        String info =   "Network Name: "        +   net.ssid
                        +      ", Id: "         + net.networkId     +
                        "\n" + "Ipaddress: "    +   net.ipAddress   +
                        "\n" + "MacAddress: "   +   net.macAdress   +
                        "\n" + "Linkspeed: "    +   net.linkspeed   +
                        "\n" + "List BSSID: "   +   bssidList_toSting;
        return info;
    }

    String[] getBssidList_toString()
    {
        int nBssid;
        String bssidList[] = new String[0];

        for (nBssid = 0; nBssid < net.bssidList.size(); nBssid++) {
            bssidList[nBssid] = net.bssidList.get(nBssid).BSSID;
        }
        return bssidList;
    }

    List<ScanResult> getBssidList()
    {
        return net.bssidList;
    }

}
