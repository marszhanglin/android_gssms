/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

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

import net.evecom.android.bean.OpinionPerson;
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
import android.widget.Toast;
/**
 * 
 * 描述PublicOpinionlistActivity
 * @author Mars zhang
 * @created 2014-11-5 上午11:01:05
 */
public class PublicOpinionlistActivity extends Activity {
    /** 来源类型数组 */
    private String[] lylxs;
    /** ListView */
    private ListView listView; // public_opinion_add_lysj_btn
    /** 成员变量 */
    private List<OpinionPerson> opinionPerson = new ArrayList<OpinionPerson>();
    /** 成员变量 */
    private OpinionAdapter opinionAdapter = null;
    /** sss */
    private String sss = "";
    /** 进度条 */
    private ProgressDialog progressDialog;
    /** MemberVariables */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** MemberVariables */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** handler */
    private Handler handler = null;
    /** 传递Id */
    private String Id;
    /** moreTextView */
    private TextView moreTextView;
    /** 正在加载进度条 */
    private LinearLayout loadProgressBar;
    /** MemberVariables */
    private Boolean search = false;
    /** 分页加载的数据的数量 */
    private int pageSize = 5;
    /** pageType */
    private final int pageType = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_opinion_add_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        initView();
        getXmlAndSetList();

    }

    /**
     * 初始化数据
     */
    private void init() {
        lylxs = new String[] { "短信录入", "网站录入", "热线电话", "审批转入", "走访录入", "手机录入" };
    }

    /**
     * 初始化界面
     */
    private void initView() {
        listView = (ListView) findViewById(R.id.public_opinion_add_list_listView);
        pageSize = Integer.parseInt(HttpUtil.getPageSize(this));
    }

    /**
     * 开一条子线程加载网络数据
     */
    private void getXmlAndSetList() {
        Runnable runnable = new Runnable() {
            public void run() {
                String strUrl = HttpUtil.BASE_URL + "teventAndroid/lookSocialAndroid?" + "areaId="
                        + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0") + "&personid="
                        + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "1") + "&start=" + 1
                        + "&end=" + pageSize;
                try {
                    sss = connServerForResult(strUrl);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String listopinionPerson = sss;
                try {
                    opinionPerson = getPersons(listopinionPerson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(handler.obtainMessage(0, opinionPerson));
            }
        };
        try {
            // 开启线程
            new Thread(runnable).start();
            // handler与线程之间的通信及数据处理
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(opinionPerson);
                    }

                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析json数据
     * 
     */
    public static List<OpinionPerson> getPersons(String jsonString) throws JSONException {
        List<OpinionPerson> list = new ArrayList<OpinionPerson>();

        JSONArray jsonArray = null;

        jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            OpinionPerson opinionPerson = new OpinionPerson();
            opinionPerson.setID(jsonObject2.getString("id"));
            opinionPerson.setEVENTNM(jsonObject2.getString("eventname"));
            opinionPerson.setSOURCEPEOPLE(jsonObject2.getString("sourcepeople"));
            opinionPerson.setSOURCEWAY(jsonObject2.getString("sourceway"));
            opinionPerson.setOCCURADDR(jsonObject2.getString("occuraddr"));
            list.add(opinionPerson);

        }
        return list;
    }

    /**
     * 
     * @param strUrl
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private String connServerForResult(String strUrl) throws ClientProtocolException, IOException {
        // HttpGet对象
        HttpGet httpRequest = new HttpGet(strUrl);
        String strResult = "";
        // HttpClient对象
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);// 设置超时时间
        HttpClient httpClient = new DefaultHttpClient(httpParams);

        // 获得HttpResponse对象
        HttpResponse httpResponse = httpClient.execute(httpRequest);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 取得返回的数据
            strResult = EntityUtils.toString(httpResponse.getEntity());
        }
        return strResult;
    }

    /**
     * 绑定数据
     * 
     * @param list
     */
    public void BinderListData(List<OpinionPerson> list) {
        opinionAdapter = new OpinionAdapter(getApplicationContext(), list);
        addPageMore();
        listView.setAdapter(opinionAdapter);
    }

    /**
     * 新增社情民意
     * 
     * @param v
     */
    public void public_opinion_add_list_btn_add(View v) {
        PublicOpinionlistActivity.this.finish();
        Intent intent = new Intent(getApplicationContext(), PublicOpinionAddActivity.class);
        startActivity(intent);
    }

    /**
     * 匿名适ListView配器类
     * 
     * @author SLQ shen
     */
    public class OpinionAdapter extends BaseAdapter implements ListAdapter {
        /** MemberVariables */
        private Context context;
        /** MemberVariables */
        private LayoutInflater inflater;
        /** MemberVariables */
        private List<OpinionPerson> list;

        public OpinionAdapter(Context context, List<OpinionPerson> list) {
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
                view = inflater.inflate(R.layout.public_opinion_list, null);
            }
            TextView textViewName = (TextView) view.findViewById(R.id.public_opinion_list_name_id);
            TextView textViewBirth = (TextView) view.findViewById(R.id.public_opinion_list_lawtype);
            TextView textViewSex = (TextView) view.findViewById(R.id.public_opinion_list_person);
            TextView textViewAddress = (TextView) view.findViewById(R.id.public_opinion_list_place);
            textViewName.setText("标题：" + list.get(i).getEVENTNM());
            if ("1".equals(list.get(i).getSOURCEWAY())) {
                textViewBirth.setText("来源方式：" + lylxs[0]);
            } else if ("2".equals(list.get(i).getSOURCEWAY())) {
                textViewBirth.setText("来源方式：" + lylxs[1]);
            } else if ("3".equals(list.get(i).getSOURCEWAY())) {
                textViewBirth.setText("来源方式：" + lylxs[2]);
            } else if ("4".equals(list.get(i).getSOURCEWAY())) {
                textViewBirth.setText("来源方式：" + lylxs[3]);
            } else if ("5".equals(list.get(i).getSOURCEWAY())) {
                textViewBirth.setText("来源方式：" + lylxs[4]);
            } else if ("6".equals(list.get(i).getSOURCEWAY())) {
                textViewBirth.setText("来源方式：" + lylxs[5]);
            }

            textViewSex.setText("来源人：" + list.get(i).getSOURCEPEOPLE());
            textViewAddress.setText("发生地点：" + list.get(i).getOCCURADDR());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Id = list.get(i).getID();
                    PublicOpinionlistActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(), PublicOpinionLookActivity.class);
                    intent.putExtra("Id", Id);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    /**
     * 返回
     * 
     * @param v
     */
    public void opinionfh(View v) {
        finish();
    }

    /** 土司 */
    private void toast(String strMsg) {
        Toast.makeText(getApplicationContext(), strMsg, 0).show();
    }

    /**
     * 在ListView中添加"加载更多"
     */
    private void addPageMore() {
        View view = LayoutInflater.from(this).inflate(R.layout.list_page_load, null);
        moreTextView = (TextView) view.findViewById(R.id.more_id);
        loadProgressBar = (LinearLayout) view.findViewById(R.id.load_id);
        listView.addFooterView(view);
        moreTextView.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏"加载更多"
                moreTextView.setVisibility(View.GONE);
                // 显示进度条
                loadProgressBar.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // //休眠3秒，用于模拟网络操作时间
                        // try {
                        // Thread.sleep(3000);
                        // } catch (InterruptedException e) {
                        // e.printStackTrace();
                        // }
                        // 加载模拟数据：下一页数据， 在正常情况下，上面的休眠是不需要，直接使用下面这句代码加载相关数据
                        if (null != opinionPerson && opinionPerson.size() >= pageSize && search == false) {
                            chageListView(opinionPerson.size() + 1, pageSize + opinionPerson.size());

                        }
                        Message mes = handler.obtainMessage(pageType);
                        handler2.sendMessage(mes);
                    }
                }).start();
            }
        });
    }

    /**
     * 加载下一页的数据 添加到尾部
     * 
     * @param pageStart
     *            加载起始
     * @param pageSize
     *            加载末尾
     * @throws JSONException
     */
    private void chageListView(int pageStart, int pageSize) {
        String strUrl = HttpUtil.BASE_URL + "teventAndroid/lookSocialAndroid?" + "areaId="
                + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0") + "&personid="
                + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "1") + "&start=" + pageStart
                + "&end=" + pageSize;
        String sss = "";
        try {
            sss = connServerForResult(strUrl);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String listopinionPerson = sss;

        List<OpinionPerson> opinionPersons = new ArrayList<OpinionPerson>();
        try {
            opinionPersons = getPersons(listopinionPerson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (OpinionPerson opinionPerson : opinionPersons) {
            this.opinionPerson.add(opinionPerson);
        }
        opinionPersons = null;
    }

    /** MemberVariables */
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case pageType:
                    // //改变适配器的数目
                    // adapter.count += pageSize;
                    // 通知适配器，发现改变操作
                    opinionAdapter.notifyDataSetChanged();
                    // 再次显示"加载更多"
                    moreTextView.setVisibility(View.VISIBLE);
                    // 再次隐藏“进度条”
                    loadProgressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
