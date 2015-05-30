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
 * 2014-5-28 ����9:31:10 GPSActivity
 * 
 * @author Mars zhang
 * 
 */
public class GPSActivity extends Activity implements OnClickListener {
    /** MemberVariables */
    private Button backButton;
    // private Button upGPS=null;
    /** ��λ���s */
    LocationClient mLocClient;
    /** MemberVariables */
    LocationData locData = null;
    /** MemberVariables */
    public MyLocationListenner myListener = new MyLocationListenner();

    // ��λͼ��
    /** MemberVariables */
    locationOverlay myLocationOverlay = null;
    // ��������ͼ��
    /** MemberVariables */
    private PopupOverlay pop = null;// ��������ͼ�㣬����ڵ�ʱʹ��
    /** MemberVariables */
    private TextView popupText = null;// ����view
    /** MemberVariables */
    private View viewCache = null;

    // ��ͼ��أ�ʹ�ü̳�MapView��MyLocationMapViewĿ������дtouch�¼�ʵ�����ݴ���
    // ���������touch�¼���������̳У�ֱ��ʹ��MapView����
    /** MemberVariables */
    MyLocationMapView mMapView = null; // ��ͼView
    /** MemberVariables */
    private MapController mMapController = null;
    /** MemberVariables */
    BMapManager mBMapMan;
    /** MemberVariables */
    boolean isRequest = false;// �Ƿ��ֶ���������λ
    /** MemberVariables */
    boolean isFirstLoc = true;// �Ƿ��״ζ�λ

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application app = getApplication();
        if (mBMapMan == null) {
            mBMapMan = new BMapManager(getApplicationContext());
            /**
             * ���BMapManagerû�г�ʼ�����ʼ��BMapManager
             */
            mBMapMan.init(new MyGeneralListener());
            System.out.println("init");
        }
        setContentView(R.layout.get_baidu_map2);
        CharSequence titleLable = "��λ����";
        setTitle(titleLable);

        // ���ذ�ť����
        backButton = (Button) findViewById(R.id.map_btn_back);
        backButton.setOnClickListener(this);

        // ��ͼ��ʼ��
        mMapView = (MyLocationMapView) findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);

        // ���� ��������ͼ��
        createPaopao();
        // ��λ��ʼ��
        mLocClient = new LocationClient(this);
        locData = new LocationData();
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// ��gps
        option.setCoorType("bd09ll"); // ������������ bd09ll
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        // ��λͼ���ʼ��
        myLocationOverlay = new locationOverlay(mMapView);
        // ���ö�λ����
        myLocationOverlay.setData(locData);
        // ��Ӷ�λͼ��
        mMapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableCompass();
        // �޸Ķ�λ���ݺ�ˢ��ͼ����Ч
        mMapView.refresh();

    }

    /**
     * ������������ͼ��
     */
    public void createPaopao() {
        viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText = (TextView) viewCache.findViewById(R.id.textcache);
        // ���ݵ����Ӧ�ص�
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
     * ��λSDK��������
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            // �������ʾ��λ����Ȧ����accuracy��ֵΪ0����
            locData.accuracy = location.getRadius();
            // �˴��������� locData�ķ�����Ϣ, �����λ SDK δ���ط�����Ϣ���û������Լ�ʵ�����̹�����ӷ�����Ϣ��
            locData.direction = location.getDerect();
            // ���¶�λ����
            myLocationOverlay.setData(locData);
            // LocationData viewData=new LocationData();
            // viewData.latitude=location.getLatitude();
            // viewData.longitude=location.getLongitude();
            // myLocationOverlay.setData(viewData);
            // SharedPreferences
            // sp=getApplicationContext().getSharedPreferences(name, mode)
            SharedPreferences sp = getApplicationContext().getSharedPreferences("GPS", MODE_PRIVATE);
            // ��������
            Editor editor = sp.edit();
            editor.putString("GPS_latitude", "" + locData.latitude);
            editor.putString("GPS_longitude", "" + locData.longitude);
            editor.commit();
            Toast.makeText(getApplicationContext(), "���ȣ�" + (locData.latitude) + "   γ�ȣ�" + (locData.longitude), 0)
                    .show();
            // ����ͼ������ִ��ˢ�º���Ч
            mMapView.refresh();
            mMapController.animateTo(new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6)));
            myLocationOverlay.setLocationMode(LocationMode.FOLLOWING); // �������ģʽ
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
        }
    }

    /**
     * �̳�MyLocationOverlay��дdispatchTapʵ�ֵ������ 2014-7-22����5:33:13
     * ��locationOverlay
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
            // �������¼�,��������
            popupText.setBackgroundResource(R.drawable.popup);
            popupText.setText("�ҵ�λ��");
            pop.showPopup(BMapUtil.getBitmapFromView(popupText), new GeoPoint((int) (locData.latitude * 1e6),
                    (int) (locData.longitude * 1e6)), 8);
            return true;
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        mLocClient.stop(); // 5-16���
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // �˳�ʱ���ٶ�λ
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
            case R.id.map_btn_back: // ����
                Intent intent = new Intent();
                GPSActivity.this.setResult(RESULT_OK, intent);
                GPSActivity.this.finish();
                break;
            default:
                break;
        }

    }

}
