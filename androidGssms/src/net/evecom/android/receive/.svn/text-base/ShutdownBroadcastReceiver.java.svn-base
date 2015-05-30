/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.receive;

import java.util.Calendar;

import net.evecom.android.util.ShareUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.TrafficStats;
/**
 * 
 * 描述ShutdownBroadcastReceiver
 * @author Mars zhang
 * @created 2014-11-5 上午10:53:32
 */
public class ShutdownBroadcastReceiver extends BroadcastReceiver {
    /** MemberVariables */
    private Calendar calendar = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TelephonyManager tm = (TelephonyManager)
        // context.getSystemService(Context.TELEPHONY_SERVICE);
        // String deviceid = tm.getDeviceId();
        // String tel = tm.getLine1Number();
        // String imei = tm.getSimSerialNumber();
        // String imsi = tm.getSubscriberId();
        // //流量统计
        // System.out.println("UID="+android.os.Process.myUid()
        // +"\n"+"PID="+android.os.Process.myPid()
        // +"\n"+"总接收流量(含WIFI):"+
        // (TrafficStats.getTotalRxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalRxBytes()/1024))+"K"
        // +"\n"+"总发送流量(含WIFI):"+
        // (TrafficStats.getTotalTxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalTxBytes()/1024))+"K"
        // +"\n"+"总接收流量(GPRS):"+
        // (TrafficStats.getMobileRxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getMobileRxBytes()/1024))+"K"
        // +"\n"+"总发送流量(GPRS):"+
        // (TrafficStats.getMobileTxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getMobileTxBytes()/1024))+"K"
        // +"\n"+"总接收流量(网格e通):"+
        // (TrafficStats.getUidRxBytes(android.os.Process.myUid())==
//        TrafficStats.UNSUPPORTED?0:(TrafficStats.getUidRxBytes(android.os.Process.myUid())/1024))+"K"
        // +"\n"+"总发送流量(网格e通):"+
        // (TrafficStats.getUidTxBytes(android.os.Process.myUid())==
//        TrafficStats.UNSUPPORTED?0:(TrafficStats.getUidTxBytes(android.os.Process.myUid())/1024))+"K"
        // +"\n"+"deviceid:"+deviceid
        // +"\n"+"tel:"+tel
        // +"\n"+"imei(设备唯一):"+imei
        // +"\n"+"imsi(SIM唯一):"+imsi
        // );
        calendar = Calendar.getInstance();
        SharedPreferences sp = context.getSharedPreferences("FLOW", context.MODE_PRIVATE);
        Editor editor = sp.edit();
        long LJWG_Rx = (TrafficStats.getUidRxBytes(android.os.Process.myUid()) == TrafficStats.UNSUPPORTED ? 0
                : (TrafficStats.getUidRxBytes(android.os.Process.myUid()) / 1024));
        long LJWG_Tx = (TrafficStats.getUidTxBytes(android.os.Process.myUid()) == TrafficStats.UNSUPPORTED ? 0
                : (TrafficStats.getUidTxBytes(android.os.Process.myUid()) / 1024));
        long all = LJWG_Rx + LJWG_Tx + ShareUtil.getLong(context, "FLOW", "LJWG", 0L);

        long ALL_Rx = (TrafficStats.getTotalRxBytes() == TrafficStats.UNSUPPORTED ? 0
                : (TrafficStats.getTotalRxBytes() / 1024));
        long ALL_Tx = (TrafficStats.getTotalTxBytes() == TrafficStats.UNSUPPORTED ? 0
                : (TrafficStats.getTotalTxBytes() / 1024));
        long all_android = ALL_Rx + ALL_Tx + ShareUtil.getLong(context, "FLOW", "ALL_WIFI_GPRS", 0L);
        editor.putLong("LJWG", all);
        editor.putLong("ALL_WIFI_GPRS", all_android);
        editor.putString("Date", calendar.get(Calendar.DAY_OF_MONTH) + "");
        editor.commit();
    }
}
