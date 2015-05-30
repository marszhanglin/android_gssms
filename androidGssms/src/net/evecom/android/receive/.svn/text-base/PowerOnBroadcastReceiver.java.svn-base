/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.receive;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 
 * 描述PowerOnBroadcastReceiver
 * @author Mars zhang
 * @created 2014-11-5 上午10:27:20
 */
public class PowerOnBroadcastReceiver extends BroadcastReceiver {
    /** MemberVariables */
    private Calendar calendar = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        calendar = Calendar.getInstance();

        SharedPreferences sp = context.getSharedPreferences("FLOW", context.MODE_PRIVATE);
        if (null != calendar && Integer.parseInt(sp.getString("Date", "31")) > calendar.get(Calendar.DAY_OF_MONTH)) {
            Editor editor = sp.edit();
            editor.putLong("ALL_WIFI_GPRS", 0L);
            editor.commit();
        }
    }

    // if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
    // Intent newIntent = new Intent(context, XXX);
    // newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //注意，必须添加这个标记，否则启动会失败
    // context.startActivity(newIntent);
}
