/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.gps;

import com.baidu.mapapi.MKGeneralListener;

/**
 * 
 * 2014-7-22下午4:38:21 类MyGeneralListener
 * 
 * @author Mars zhang
 * 
 */
public class MyGeneralListener implements MKGeneralListener {

    @Override
    public void onGetNetworkState(int iError) {
        // if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
        // Toast.makeText(DemoApplication.getInstance().getApplicationContext(),
        // "您的网络出错啦！",
        // Toast.LENGTH_LONG).show();
        // }
        // else if (iError == MKEvent.ERROR_NETWORK_DATA) {
        // Toast.makeText(DemoApplication.getInstance().getApplicationContext(),
        // "输入正确的检索条件！",
        // Toast.LENGTH_LONG).show();
        // }
        // ...
    }

    @Override
    public void onGetPermissionState(int iError) {
        // 非零值表示key验证未通过
        // if (iError != 0) {
        // //授权Key错误：
        // Toast.makeText(DemoApplication.getInstance().getApplicationContext(),
        // "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "+iError,
        // Toast.LENGTH_LONG).show();
        // DemoApplication.getInstance().m_bKeyRight = false;
        // }
        // else{
        // DemoApplication.getInstance().m_bKeyRight = true;
        // Toast.makeText(DemoApplication.getInstance().getApplicationContext(),
        // "key认证成功", Toast.LENGTH_LONG).show();
        // }
    }
}