/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.gps;

import net.evecom.android.R;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 
 * 2014-5-28 上午9:31:10 GPSActivity
 * 
 * @author Mars zhang
 * 
 */
public class GPSActivity extends Activity implements OnClickListener {
    /** MemberVariables */
    private Button backButton;
    // private Button upGPS=null;
    /** 定位相关s */
    LocationClient mLocClient;
    /** MemberVariables */
    LocationData locData = null;
    /** MemberVariables */
    public MyLocationListenner myListener = new MyLocationListenner();

    // 定位图层
    /** MemberVariables */
    locationOverlay myLocationOverlay = null;
    // 弹出泡泡图层
    /** MemberVariables */
    private PopupOverlay pop = null;// 弹出泡泡图层，浏览节点时使用
    /** MemberVariables */
    private TextView popupText = null;// 泡泡view
    /** MemberVariables */
    private View viewCache = null;

    // 地图相关，使用继承MapView的MyLocationMapView目的是重写touch事件实现泡泡处理
    // 如果不处理touch事件，则无需继承，直接使用MapView即可
    /** MemberVariables */
    MyLocationMapView mMapView = null; // 地图View
    /** MemberVariables */
    private MapController mMapController = null;
    /** MemberVariables */
    BMapManager mBMapMan;
    /** MemberVariables */
    boolean isRequest = false;// 是否手动触发请求定位
    /** MemberVariables */
    boolean isFirstLoc = true;// 是否首次定位

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application app = getApplication();
        if (mBMapMan == null) {
            mBMapMan = new BMapManager(getApplicationContext());
            /**
             * 如果BMapManager没有初始化则初始化BMapManager
             */
            mBMapMan.init(new MyGeneralListener());
            System.out.println("init");
        }
        setContentView(R.layout.get_baidu_map2);
        CharSequence titleLable = "定位功能";
        setTitle(titleLable);

        // 返回按钮监听
        backButton = (Button) findViewById(R.id.map_btn_back);
        backButton.setOnClickListener(this);

        // 地图初始化
        mMapView = (MyLocationMapView) findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);

        // 创建 弹出泡泡图层
        createPaopao();
        // 定位初始化
        mLocClient = new LocationClient(this);
        locData = new LocationData();
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型 bd09ll
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        // 定位图层初始化
        myLocationOverlay = new locationOverlay(mMapView);
        // 设置定位数据
        myLocationOverlay.setData(locData);
        // 添加定位图层
        mMapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableCompass();
        // 修改定位数据后刷新图层生效
        mMapView.refresh();

    }

    /**
     * 创建弹出泡泡图层
     */
    public void createPaopao() {
        viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText = (TextView) viewCache.findViewById(R.id.textcache);
        // 泡泡点击响应回调
        PopupClickListener popListener = new PopupClickListener() {
            @Override
            public void onClickedPopup(int index) {
                Log.v("click", "clickapoapo");
            }
        };
        pop = new PopupOverlay(mMapView, popListener);
        MyLocationMapView.pop = pop;
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            // 如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
            locData.direction = location.getDerect();
            // 更新定位数据
            myLocationOverlay.setData(locData);
            // LocationData viewData=new LocationData();
            // viewData.latitude=location.getLatitude();
            // viewData.longitude=location.getLongitude();
            // myLocationOverlay.setData(viewData);
            // SharedPreferences
            // sp=getApplicationContext().getSharedPreferences(name, mode)
            SharedPreferences sp = getApplicationContext().getSharedPreferences("GPS", MODE_PRIVATE);
            // 存入数据
            Editor editor = sp.edit();
            editor.putString("GPS_latitude", "" + locData.latitude);
            editor.putString("GPS_longitude", "" + locData.longitude);
            editor.commit();
            Toast.makeText(getApplicationContext(), "经度：" + (locData.latitude) + "   纬度：" + (locData.longitude), 0)
                    .show();
            // 更新图层数据执行刷新后生效
            mMapView.refresh();
            mMapController.animateTo(new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6)));
            myLocationOverlay.setLocationMode(LocationMode.FOLLOWING); // 跟随跟随模式
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
        }
    }

    /**
     * 继承MyLocationOverlay重写dispatchTap实现点击处理 2014-7-22下午5:33:13
     * 类locationOverlay
     * 
     * @author Mars zhang
     * 
     */
    public class locationOverlay extends MyLocationOverlay {

        public locationOverlay(MapView mapView) {
            super(mapView);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected boolean dispatchTap() {
            // TODO Auto-generated method stub
            // 处理点击事件,弹出泡泡
            popupText.setBackgroundResource(R.drawable.popup);
            popupText.setText("我的位置");
            pop.showPopup(BMapUtil.getBitmapFromView(popupText), new GeoPoint((int) (locData.latitude * 1e6),
                    (int) (locData.longitude * 1e6)), 8);
            return true;
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        mLocClient.stop(); // 5-16添加
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        if (mLocClient != null)
            mLocClient.stop();
        mMapView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMapView.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_btn_back: // 返回
                Intent intent = new Intent();
                GPSActivity.this.setResult(RESULT_OK, intent);
                GPSActivity.this.finish();
                break;
            default:
                break;
        }

    }

}
