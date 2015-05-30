/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.evecom.android.util.AppInfoUtil;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import net.evecom.android.util.UpdateInfo;
import net.evecom.android.util.utilbean.XmlParser;
import net.evecom.android.view.wheel.OnWheelChangedListener;
import net.evecom.android.view.wheel.OnWheelScrollListener;
import net.evecom.android.view.wheel.WheelView;
import net.evecom.android.view.wheel.adapter.AbstractWheelTextAdapter;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.NotFoundException;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 2014-7-22����3:48:02 ��SystemSetingActivity
 * 
 * @author Mars zhang
 * 
 */
public class SystemSetingActivity extends Activity {
    /** ��Ա���� */
    protected static final int LOAD_MAIN = 1;
    /** ��Ա���� */
    protected static final int SHOW_UPDATA_DIALOG = 2;
    /** ��Ա���� */
    protected static final int SERVER_URL_ERROR = 3;
    /** ��Ա���� */
    protected static final int NETWORK_ERROR = 4;
    /** ��Ա���� */
    protected static final int XML_PARSE_ERROR = 5;
    /** ��Ա���� */
    protected static final int SERVER_ERROR = 6;
    /** updateInfo */
    private UpdateInfo updateInfo;
    /** ������ʾ�� */
    private AlertDialog dialogPress;
    /** ��ס���� */
    private CheckBox jzmmCheckBox;
    /** �Զ����� */
    private CheckBox zddrCheckBox;
    /** xml */
    private SharedPreferences spPhone;
    /** ��֤������������ */
    private boolean scrolling = false;
    /** �б���temp */
    private String temp = "15";
    /** GPSʱ��temp */
    private String gps_time_temp = "120";
    /** flow_size */
    private TextView flow_size = null; //
    /** flow_size_android */
    private TextView flow_size_android = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_seting);
        spPhone = getApplicationContext().getSharedPreferences("Phone", MODE_PRIVATE);

        flow_size = (TextView) findViewById(R.id.flow_size);
        long LJWG_Rx = (TrafficStats.getUidRxBytes(android.os.Process.myUid()) == TrafficStats.UNSUPPORTED ? 0
                : (TrafficStats.getUidRxBytes(android.os.Process.myUid()) / 1024));
        long LJWG_Tx = (TrafficStats.getUidTxBytes(android.os.Process.myUid()) == TrafficStats.UNSUPPORTED ? 0
                : (TrafficStats.getUidTxBytes(android.os.Process.myUid()) / 1024));
        long all = LJWG_Rx + LJWG_Tx + ShareUtil.getLong(getApplicationContext(), "FLOW", "LJWG", 0L);
        String s = "" + all / 1024.00;
        flow_size.setText(s.substring(0, s.indexOf(".") + 4) + "M");

        flow_size_android = (TextView) findViewById(R.id.flow_size_android);
        long ALL_Rx = (TrafficStats.getTotalRxBytes() == TrafficStats.UNSUPPORTED ? 0
                : (TrafficStats.getTotalRxBytes() / 1024));
        long ALL_Tx = (TrafficStats.getTotalTxBytes() == TrafficStats.UNSUPPORTED ? 0
                : (TrafficStats.getTotalTxBytes() / 1024));
        long all_android = ALL_Rx + ALL_Tx + ShareUtil.getLong(getApplicationContext(), "FLOW", "ALL_WIFI_GPRS", 0L);
        String s2 = "" + all_android / 1024.00;
        flow_size_android.setText(s2.substring(0, s2.indexOf(".") + 4) + "M");

        // flow_size_android.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // PhoneUtil.getandSaveCurrentImage(SystemSetingActivity.this);
        // }
        // });

        jzmmCheckBox = (CheckBox) findViewById(R.id.main_tab_setting_checkbox_jzmm);
        zddrCheckBox = (CheckBox) findViewById(R.id.main_tab_setting_checkbox_zddr);
        if ("1".equals(spPhone.getString("isJZMM", "0"))) {
            jzmmCheckBox.setChecked(true);
        }
        if ("1".equals(spPhone.getString("isZDDR", "0"))) {
            zddrCheckBox.setChecked(true);
        }

        // ��ס����
        jzmmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Editor editor = spPhone.edit();
                if (isChecked) {
                    editor.putString("isJZMM", "1");
                } else {
                    editor.putString("isJZMM", "0");
                }
                editor.commit();
            }
        });
        // �Զ���¼
        zddrCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Editor editor = spPhone.edit();
                if (isChecked) {
                    editor.putString("isZDDR", "1");
                } else {
                    editor.putString("isZDDR", "0");
                }
                editor.commit();
            }
        });

        // ����dialogע��
        dialogPress = new AlertDialog.Builder(this).setTitle("��������apk�ļ�").setMessage("���ؽ���:0/0")
                .setPositiveButton("ȷ��", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        /**
         * ��ҳ����
         */
        final WheelView country = (WheelView) findViewById(R.id.country);
        ArrayList list = new ArrayList();
        for (int i = 10; i < 100; i++) {
            list.add(i + "");
        }
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(this, list));
        country.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                // System.out.println("addScrollingListener");
            }

            public void onScrollingFinished(WheelView wheel) {
                // System.out.println("onScrollingFinished");
                SharedPreferences sp = getSharedPreferences("PageSize", MODE_PRIVATE);
                Editor editor = sp.edit();
                editor.putString("pagesize", temp);
                editor.commit();
                // System.out.println(temp+"----");
            }
        });
        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                temp = "" + (newValue + 10);
            }
        });
        SharedPreferences sp = getSharedPreferences("PageSize", MODE_PRIVATE);
        country.setCurrentItem(Integer.parseInt(sp.getString("pagesize", "15")) - 10);

        // /**
        // * ��ʱʱ�����ã�s�� Ĭ��120
        // */
        // final WheelView country_gps_time = (WheelView)
        // findViewById(R.id.country_gps_time);
        // ArrayList list_gps_time = new ArrayList();
        // for (int i = 10; i < 600; i++) {
        // list_gps_time.add(i + "");
        // }
        // country_gps_time.setVisibleItems(3);
        // country_gps_time.setViewAdapter(new CountryAdapter(this,
        // list_gps_time));
        // country_gps_time.addScrollingListener(new OnWheelScrollListener() {
        // public void onScrollingStarted(WheelView wheel) {
        // // System.out.println("addScrollingListener");
        // }
        //
        // public void onScrollingFinished(WheelView wheel) {
        // // System.out.println("onScrollingFinished");
        // SharedPreferences sp_gps_time = getSharedPreferences("GPS_TRACK",
        // MODE_PRIVATE);
        // Editor editor = sp_gps_time.edit();
        // editor.putString("TIME", gps_time_temp);
        // editor.commit();
        // }
        // });
        // country_gps_time.addChangingListener(new OnWheelChangedListener() {
        // public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // gps_time_temp = "" + (newValue + 120);
        // }
        // });
        // SharedPreferences sp_gps_time = getSharedPreferences("GPS_TRACK",
        // MODE_PRIVATE);
        // country_gps_time.setCurrentItem(Integer.parseInt(sp_gps_time.getString("TIME",
        // "120")) - 10);

    }

    /**
     * ���ذ�ť
     * 
     * @param v
     */
    public void main_tab_settings_back(View v) {
        finish();
    }

    /**
     * ������
     * 
     * @param v
     */
    public void main_tab_setting_check_updata_apk(View v) {
        getVersionInfo();
    }

    /**
     * ��ȡ����汾��Ϣ
     */
    private void getVersionInfo() {
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain(); // ��newһ������� �൱�ڴ�����Ϣ����
                final long startTime = System.currentTimeMillis(); // ��ʼʱ��
                try {
                    // URL url=new
                    // URL(getResources().getString(R.string.url)+"update.xml");
                    URL url = new URL(HttpUtil.BASE_URL + HttpUtil.UPDATE_VERSION_XML);
                    HttpURLConnection conn;
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {// ����ɹ�
                        InputStream is = conn.getInputStream();// �ɹ��ͻ�ȡ���ص�������
                        updateInfo = XmlParser.getUpdateInfo(is);
                        if (new AppInfoUtil().getAppversion(SystemSetingActivity.this)
                                .equals(updateInfo.getVersion())) {
                            msg.what = LOAD_MAIN; // �汾һ��
                        } else {
                            msg.what = SHOW_UPDATA_DIALOG;// �汾��һ��
                        }
                    } else {
                        msg.what = SERVER_ERROR;// ������ʧ��
                    }
                } catch (MalformedURLException e) {
                    msg.what = SERVER_URL_ERROR;
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    msg.what = SERVER_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    msg.what = NETWORK_ERROR;
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    msg.what = XML_PARSE_ERROR;
                    e.printStackTrace();
                } finally { // ������ζ�Ҫִ�еĲ���
                    System.out.println(msg.what);
                    handler2.sendMessage(msg);
                }
                super.run();
            }
        }.start();
    }

    /**
     * �½�һ����Ϣ������
     */
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("msg.what" + msg.what);
            switch (msg.what) {
                case LOAD_MAIN: // �汾�Ѿ������µ���
                    toast("�ð汾�Ѿ������� ");
                    break;
                case SHOW_UPDATA_DIALOG: // ��ʾ����
                    newDialog();
                    break;
                case SERVER_URL_ERROR: // ����·������
                    toast("���������Ƿ����");
                    break;
                case NETWORK_ERROR: // �������Ӵ���
                    toast("���������Ƿ����");
                    break;
                case XML_PARSE_ERROR: // XML��������
                    toast("���������Ƿ����");
                    break;
                case SERVER_ERROR: // ����������
                    toast("���������Ƿ����");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /** �������¶Ի��� */
    private void newDialog() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("��ʾ����").setMessage(updateInfo.getDescription())
                .setPositiveButton("��������", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downLoadApk();
                        dialog.dismiss();
                    }
                }).setNegativeButton("�´���˵", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /** �жϲ�����apk��װ�ļ� */
    private void downLoadApk() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// �ж��Ƿ���SD��
            FinalHttp fh = new FinalHttp();
            // ����һ����ʱ�ļ�
            File clear_temp = new File(Environment.getExternalStorageDirectory(), "temp.apk");
            deleteFile(clear_temp);
            File temp = new File(Environment.getExternalStorageDirectory(), "temp.apk");
            dialogPress.show();
            // ����download������ʼ����
            HttpHandler handler1 = fh.download(updateInfo.getApkurl(), // ���������ص�·��
                    temp.getAbsolutePath(), // ���Ǳ��浽���ص�·��
                    true, // true:�ϵ����� false:���ϵ�������ȫ�����أ�
                    new AjaxCallBack<File>() {
                        @Override
                        public void onLoading(long count, long current) {// ÿ��ص�һ��
                            System.out.println(current + "/" + count);
                            dialogPress.setMessage("���ؽ���:" + current / 1024 + "k/" + count / 1024 + "k");
                            super.onLoading(count, current);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            dialogPress.setMessage("���ظ���ʧ��");
                            toast(strMsg + "���ظ���ʧ��");
                            super.onFailure(t, errorNo, strMsg);
                        }

                        @Override
                        public void onSuccess(File t) {
                            System.out.println(t == null ? "null" : t.getAbsoluteFile().toString() + "���سɹ�");
                            dialogPress.dismiss();
                            toast("���سɹ�...�滻��װ...");
                            replaceAPK(t);
                        }

                        /**
                         * �滻��װAPK
                         */
                        private void replaceAPK(File t) {
                            // <activity
                            // android:name=".PackageInstallerActivity"
                            // android:configChanges="orientation|keyboardHidden"
                            // android:theme="@style/TallTitleBarTheme">
                            // <intent-filter>
                            // <action android:name="android.intent.action.VIEW"
                            // />
                            // <category
                            // android:name="android.intent.category.DEFAULT" />
                            // <data android:scheme="content" />
                            // <data android:scheme="file" />
                            // <data
                            // android:mimeType="application/vnd.android.package-archive"
                            // />
                            // </intent-filter>
                            // </activity>
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            // intent.setType("application/vnd.android.package-archive");
                            // intent.setData(Uri.fromFile(t));
                            intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
                            startActivity(intent);
                        }
                    });
        } else {
            toast("û��SD������ʧ��");
        }
    }

    /** ɾ���ļ� */
    public void deleteFile(File file) {
        if (file.exists()) { // �ж��ļ��Ƿ����
            if (file.isFile()) { // �ж��Ƿ����ļ�
                file.delete(); // delete()���� ��Ӧ��֪�� ��ɾ������˼;
                System.out.println("file.delete();");
            } else if (file.isDirectory()) { // �����������һ��Ŀ¼
                File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
                for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
                    this.deleteFile(files[i]); // ��ÿ���ļ� ������������е���
                }
            }
            file.delete();
            System.out.println("file.delete();");
        } else {
            System.out.println("�ļ������ڡ���");
        }
    }

    /** ��˾ */
    private void toast(String strMsg) {
        Toast.makeText(getApplicationContext(), strMsg, 0).show();
    }

    /**
     * ��ҳ������ 2014-7-22����5:56:27 ��CountryAdapter
     * 
     * @author Mars zhang
     * 
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        /** MemberVariables */
        ArrayList list;
        /** MemberVariables */
        int i = 0;

        protected CountryAdapter(Context context, ArrayList list) {
            super(context, R.layout.tempitem, NO_RESOURCE);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        // Countries flags
        // private int flags =R.drawable.tem_unit_dialog;

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            // ImageView img = (ImageView) view.findViewById(R.id.tempImag);
            // img.setImageResource(flags);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }

    }

}
