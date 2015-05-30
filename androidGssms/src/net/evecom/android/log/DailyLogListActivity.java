/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.evecom.android.R;
import net.evecom.android.bean.DailyLogPerson;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * ����DailyLogListActivity
 * @author Mars zhang
 * @created 2014-11-5 ����10:49:06
 */
public class DailyLogListActivity extends Activity {
    /** ��Դ�������� */
    private String[] lylxs;
    /** ListView */
    private ListView listView;
    /** sss */
    private static String sss = "";
    /** ������ */
    private ProgressDialog progressDialog;
    /** handler */
    private static Handler handler = null;
    /** handler */
    private static Handler handler1 = null;
    /** ��Ա���� */
    private List<DailyLogPerson> dailyLogPerson = new ArrayList<DailyLogPerson>();
    /** ��Ա���� */
    private DailyLogAdapter dailyLogAdapter = null;

    /** moreTextView */
    private TextView moreTextView;
    /** ���ڼ��ؽ����� */
    private LinearLayout loadProgressBar;
    /** MemberVariables */
    private Boolean search = false;
    /** ��ҳ���ص����ݵ����� */
    private int pageSize = 5;
    /** pageType */
    private final int pageType = 1;
    /** ChildTree */
    private static String ChildTree = "";
    /** �������� */
    private TextView textViewGrid;
    /** �������� */
    static String areaName = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_log_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        initView();
        getXmlAndSetList();

    }

    /**
     * ��ʼ������
     */
    private void init() {
        lylxs = new String[] { "�ճ�����", "��������" };
    }

    /**
     * ��ʼ������
     */
    private void initView() {
        listView = (ListView) findViewById(R.id.daily_log_add_list_listView);
        pageSize = Integer.parseInt(HttpUtil.getPageSize(this));
    }

    /**
     * ��һ�����̼߳�����������
     */
    private void getXmlAndSetList() {
        Runnable runnable = new Runnable() {
            public void run() {
                String strUrl = HttpUtil.BASE_URL + "teventAndroid/lookDailyLogAndroid?" + "areaId="
                        + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0")
                        // + "&personid=" +
                        // ShareUtil.getString(getApplicationContext(),
                        // "SESSION", "EMPID", "1")
                        // + "&personname=" +
                        // ShareUtil.getString(getApplicationContext(),
                        // "SESSION", "EMPNAME", "ϵͳ����Ա")
                        + "&start=" + 1 + "&end=" + pageSize;
                try {
                    sss = connServerForResult(strUrl);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String listdailyLogPerson = sss;
                try {
                    dailyLogPerson = getPersons(listdailyLogPerson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(handler.obtainMessage(0, dailyLogPerson));
            }
        };
        try {
            // �����߳�
            new Thread(runnable).start();
            // handler���߳�֮���ͨ�ż����ݴ���
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(dailyLogPerson);
                    }

                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ����json����
     * 
     */
    public static List<DailyLogPerson> getPersons(String jsonString) throws JSONException {
        List<DailyLogPerson> list = new ArrayList<DailyLogPerson>();
        List<DailyLogPerson> list2 = new ArrayList<DailyLogPerson>();

        JSONArray jsonArray = null;

        jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            DailyLogPerson dailyLogPerson = new DailyLogPerson();
            dailyLogPerson.setSYSID(jsonObject2.getString("sysid"));
            dailyLogPerson.setATTA1(jsonObject2.getString("atta1"));
            dailyLogPerson.setLOGTYPE(jsonObject2.getString("logtype"));
            dailyLogPerson.setLOGDATE(jsonObject2.getString("logdate"));
            dailyLogPerson.setAREAID(jsonObject2.getString("areaid"));
            // String areaname = dailyLogPerson.getAREAID();
            // Areaname(areaname);
            // dailyLogPerson.setAREAID(areaName);
            list.add(dailyLogPerson);
            String areaname = list.get(i).getAREAID();
            Areaname(areaname);
            dailyLogPerson.setAREANAME(areaName);
            list2.add(dailyLogPerson);
        }
        return list;
    }

    /**
     * ��һ�����̼߳�����������
     * 
     * @return
     */
    private static void Areaname(final String areaId) {
        // final String areaname[]=null;
        // Runnable runnable = new Runnable() {
        // public void run() {
        // Message msg_listData3 = new Message();
        String strUrl = HttpUtil.BASE_URL + "teventAndroid/queryChildTreeName?" + "areaId=" + areaId;
        String sss = "";
        try {
            sss = connServerForResult(strUrl);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChildTree = sss;
        // handler1.sendMessage(handler1.obtainMessage(0, ChildTree));
        // }
        // };

        try {
            // �����߳�
            // new Thread(runnable).start();
            // handler���߳�֮���ͨ�ż����ݴ���
            // handler = new Handler() {
            // public void handleMessage(Message msg) {
            // if (msg.what == 0) {
            BinderListData1(ChildTree);

            // }

            // }
            // };

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ������
     * 
     * @param ChildTree
     * @return
     * @throws JSONException
     */
    public static void BinderListData1(String ChildTree) {
        // String areaname="";
        try {

            JSONArray jsonArray = null;

            jsonArray = new JSONArray(ChildTree);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                areaName = jsonObject2.getString("areaname");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @param strUrl
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private static String connServerForResult(String strUrl) throws ClientProtocolException, IOException {
        // HttpGet����
        HttpGet httpRequest = new HttpGet(strUrl);
        String strResult = "";
        // HttpClient����
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);// ���ó�ʱʱ��
        HttpClient httpClient = new DefaultHttpClient(httpParams);

        // ���HttpResponse����
        HttpResponse httpResponse = httpClient.execute(httpRequest);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // ȡ�÷��ص�����
            strResult = EntityUtils.toString(httpResponse.getEntity());
        }
        return strResult;
    }

    /**
     * ������
     * 
     * @param dailyLogPerson2
     */
    public void BinderListData(List<DailyLogPerson> dailyLogPerson2) {
        dailyLogAdapter = new DailyLogAdapter(getApplicationContext(), dailyLogPerson2);
        addPageMore();
        listView.setAdapter(dailyLogAdapter);
    }

    /**
     * ������־
     * 
     * @param v
     */
    public void daily_log_add_list_btn_add(View v) {
        DailyLogListActivity.this.finish();
        Intent intent = new Intent(getApplicationContext(), DailyLogAddActivity.class);
        startActivity(intent);
    }

    /**
     * ������ListView������
     * 
     * @author SLQ shen
     */
    public class DailyLogAdapter extends BaseAdapter implements ListAdapter {
        /** MemberVariables */
        private Context context;
        /** MemberVariables */
        private LayoutInflater inflater;
        /** MemberVariables */
        private List<DailyLogPerson> list;

        public DailyLogAdapter(Context context, List<DailyLogPerson> list) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();

        }

        @Override
        public Object getItem(int item) {
            return this.list.get(item);
        }

        @Override
        public long getItemId(int itemId) {
            return itemId;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = inflater.inflate(R.layout.daily_log_listview, null);
            }
            TextView textViewName = (TextView) view.findViewById(R.id.daily_log_list_name_id);
            textViewGrid = (TextView) view.findViewById(R.id.daily_log_list_grid);
            TextView textViewLawtype = (TextView) view.findViewById(R.id.daily_log_list_lawtype);
            TextView textViewTime = (TextView) view.findViewById(R.id.daily_log_list_time);
            textViewName.setText("ժҪ��" + list.get(i).getATTA1());
            if ("1".equals(list.get(i).getLOGTYPE())) {
                textViewLawtype.setText("�������ͣ�" + lylxs[0]);
            } else if ("2".equals(list.get(i).getLOGTYPE())) {
                textViewLawtype.setText("�������ͣ�" + lylxs[1]);
            }
            textViewTime.setText("�������ڣ�" + list.get(i).getLOGDATE());
            // Areaname(list.get(i).getAREAID());
            textViewGrid.setText("��������" + list.get(i).getAREANAME());
            // textViewGrid.setText("��������"+areaName);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sysid = list.get(i).getSYSID();
                    String areaname = list.get(i).getAREANAME();
                    DailyLogListActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(), DailyLogLookActivity.class);
                    intent.putExtra("sysid", sysid);
                    intent.putExtra("areaname", areaname);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    /**
     * ����
     * 
     * @param v
     */
    public void logfh(View v) {
        finish();
    }

    /**
     * ��ListView�����"���ظ���"
     */
    private void addPageMore() {
        View view = LayoutInflater.from(this).inflate(R.layout.list_page_load, null);
        moreTextView = (TextView) view.findViewById(R.id.more_id);
        loadProgressBar = (LinearLayout) view.findViewById(R.id.load_id);
        listView.addFooterView(view);
        moreTextView.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ����"���ظ���"
                moreTextView.setVisibility(View.GONE);
                // ��ʾ������
                loadProgressBar.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // //����3�룬����ģ���������ʱ��
                        // try {
                        // Thread.sleep(3000);
                        // } catch (InterruptedException e) {
                        // e.printStackTrace();
                        // }
                        // ����ģ�����ݣ���һҳ���ݣ� ����������£�����������ǲ���Ҫ��ֱ��ʹ����������������������
                        if (null != dailyLogPerson && dailyLogPerson.size() >= pageSize && search == false) {

                            chageListView(dailyLogPerson.size() + 1, pageSize + dailyLogPerson.size());

                        }
                        Message mes = handler.obtainMessage(pageType);
                        handler2.sendMessage(mes);
                    }
                }).start();
            }
        });
    }

    /**
     * ������һҳ������ ��ӵ�β��
     * 
     * @param pageStart
     *            ������ʼ
     * @param pageSize
     *            ����ĩβ
     * @throws JSONException
     */
    private void chageListView(int pageStart, int pageSize) {
        String strUrl = HttpUtil.BASE_URL + "teventAndroid/lookDailyLogAndroid?" + "areaId="
                + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0")
                // + "&personid=" + ShareUtil.getString(getApplicationContext(),
                // "SESSION", "EMPID", "1")
                // + "&personname=" +
                // ShareUtil.getString(getApplicationContext(), "SESSION",
                // "EMPNAME", "ϵͳ����Ա")
                + "&start=" + pageStart + "&end=" + pageSize;
        String sss = "";
        try {
            sss = connServerForResult(strUrl);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String listdailyLogPerson = sss;
        List<DailyLogPerson> dailyLogPersons = new ArrayList<DailyLogPerson>();
        try {
            dailyLogPersons = getPersons(listdailyLogPerson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (DailyLogPerson dailyLogPerson : dailyLogPersons) {
            this.dailyLogPerson.add(dailyLogPerson);
        }
        dailyLogPersons = null;
    }

    /** MemberVariables */
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case pageType:
                    // //�ı�����������Ŀ
                    // adapter.count += pageSize;
                    // ֪ͨ�����������ָı����
                    dailyLogAdapter.notifyDataSetChanged();
                    // �ٴ���ʾ"���ظ���"
                    moreTextView.setVisibility(View.VISIBLE);
                    // �ٴ����ء���������
                    loadProgressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
