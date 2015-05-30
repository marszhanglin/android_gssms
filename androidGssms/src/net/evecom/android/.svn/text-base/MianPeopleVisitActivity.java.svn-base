/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.util.ArrayList;
import java.util.List;

import net.evecom.android.bean.T_PERSON_INTERVIEW;
import net.evecom.android.json.JsonMainPersonViewData;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 走访记录列表
 * 
 * @author Mars zhang
 * 
 */
public class MianPeopleVisitActivity extends Activity {
    /** 返回按钮 */
    private Button mBtnBack;
    /** MemberVariables */
    private ListView listView;
    /** MemberVariables */
    private ArrayList<T_PERSON_INTERVIEW> visits;
    /** MemberVariables */
    private Handler handler = null;
    /** MemberVariables */
    private String group_id;
    /** MemberVariables */
    private LinearLayout layoutSearch;
    /** MemberVariables */
    private RelativeLayout relativeLayout_bottom;// main_people_visit_bottom
    /** MemberVariables */
    private EditText xsEditText = null; // search_for_main_person_ry_name_edit
    /** MemberVariables */
    private EditText xmEditText = null; // search_for_main_person_ry_idcard_edit
    /** 成员变量 */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** 成员变量 */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** MemberVariables */
    private String main_person_vist_json = "";
    /** MemberVariables */
    public static MianPeopleVisitActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_people_visit);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        instance = this;
    }

    public void initView() {
        Intent intent = getIntent();
        group_id = intent.getStringExtra("group_id");
        mBtnBack = (Button) findViewById(R.id.one_group_contact_list_btn_back);
        xsEditText = (EditText) findViewById(R.id.search_for_main_person_ry_name_edit);
        xmEditText = (EditText) findViewById(R.id.search_for_main_person_ry_idcard_edit);
        listView = (ListView) findViewById(R.id.one_group_contact_list_listView);
        visits = new ArrayList<T_PERSON_INTERVIEW>();
        layoutSearch = (LinearLayout) findViewById(R.id.one_group_contact_search_lin);
        relativeLayout_bottom = (RelativeLayout) findViewById(R.id.main_people_visit_bottom);
        // 开一条子线程加载网络数据
        Runnable runnable = new Runnable() {
            public void run() {
                // http://localhost:8080/fzaj_android/servlet/
                // EventlistServlet?id=ab000708-8288-4117-9dd8-083e02830b1e&flag=number

                String path = HttpUtil.BASE_URL + "teventAndroid/KeyPersonViewlist";
                String entry = "areaId=" + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0")
                        + "&offset=" + visits.size() + "&pageSize="
                        + ShareUtil.getString(getApplicationContext(), "PageSize", "pagesize", "15");
                // xmlwebData解析网络中xml中的数据 path是请求地址
                try {
                    visits = JsonMainPersonViewData.getData(path, entry);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                        handler.sendMessage(handler.obtainMessage(1, visits));
                    }
                }
                // 当请求完毕时发送消息，并把list结合对象传递过去
                handler.sendMessage(handler.obtainMessage(0, visits));
            }
        };

        try {
            // 开启线程
            new Thread(runnable).start();
            // handler与线程之间的通信及数据处理
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(visits);
                    } else if (msg.what == 1) {
                        toast("请检查网络是否可用", 1);
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 绑定数据
    public void BinderListData(ArrayList<T_PERSON_INTERVIEW> list) {
        // //创建adapter对象
        // adapter=new ListViewAdapter(R.layout.item,this,person);
        // //将Adapter绑定到listview中
        // listView.setAdapter(adapter);
        MyAdapter bulletinAdapter = new MyAdapter(this, list);
        listView.setAdapter(bulletinAdapter);
    }

    /**
     * 返回
     * 
     * @param v
     */
    public void fh(View v) {
        finish();

    }

    /**
     * 返回
     * 
     * @param v
     */
    public void add(View v) {
        Intent intent = new Intent(this, MianPeopleVisitSearchForMianPersonActivity.class);
        startActivity(intent);

    }

    /**
     * 查询通讯录
     * 
     * @param v
     */
    public void find_contact_from_net(View v) {
        if (View.GONE == layoutSearch.getVisibility()) {
            layoutSearch.setVisibility(View.VISIBLE);
            relativeLayout_bottom.setVisibility(View.GONE);
        } else if (View.VISIBLE == layoutSearch.getVisibility()) {
            layoutSearch.setVisibility(View.GONE);
            relativeLayout_bottom.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 查询通讯录 请求数据 Q_t1.INTERVIEWTYPE_S_LK=xs&Q_t3.PERSONNAME_S_LK=xm
     * 
     * @param v
     */
    public void search_for_contact_btn_onclick(View v) {
        // 开一条子线程加载网络数据
        Runnable runnable = new Runnable() {
            public void run() {
                // http://localhost:8080/fzaj_android/servlet/
                // EventlistServlet?id=ab000708-8288-4117-9dd8-083e02830b1e&flag=number

                String path = HttpUtil.BASE_URL + "teventAndroid/KeyPersonViewlist";
                String entry = "areaId=" + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0")
                        + "&offset=" + 0 + "&pageSize="
                        + ShareUtil.getString(getApplicationContext(), "PageSize", "pagesize", "15")
                        + "&Q_t1.INTERVIEWTYPE_S_LK=" + xsEditText.getText().toString().trim()
                        + "&Q_t3.PERSONNAME_S_LK=" + xmEditText.getText().toString().trim();
                // xmlwebData解析网络中xml中的数据 path是请求地址
                try {
                    visits = JsonMainPersonViewData.getData(path, entry);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                        handler.sendMessage(handler.obtainMessage(1, visits));
                    }
                }
                // 当请求完毕时发送消息，并把list结合对象传递过去
                handler.sendMessage(handler.obtainMessage(0, visits));
            }
        };

        try {
            // 开启线程
            new Thread(runnable).start();
            // handler与线程之间的通信及数据处理
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(visits);
                    } else if (msg.what == 1) {
                        toast("请检查网络是否可用", 1);
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ContactAdapter 2014-7-22下午3:08:06 类
     * 
     * @author Mars zhang
     * 
     */
    public class MyAdapter extends BaseAdapter {
        /** context */
        private Context context;
        /** context */
        private LayoutInflater inflater;
        /** context */
        private List<T_PERSON_INTERVIEW> list;

        /**
         * 构造方法，参数list传递的就是这一组数据的信息
         * 
         * @param context
         * @param list
         */
        public MyAdapter(Context context, List<T_PERSON_INTERVIEW> list) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.list = list;
        }

        // 得到总的数量
        @Override
        public int getCount() {
            // System.out.println("getCount"+list==null?0:list.size());
            return list == null ? 0 : list.size();

        }

        // 根据ListView位置返回View
        @Override
        public Object getItem(int item) {
            // System.out.println("getItem"+item);
            return this.list.get(item);
        }

        // 根据ListView位置得到List中的ID
        @Override
        public long getItemId(int itemId) {
            // System.out.println("getItemId"+itemId);
            return itemId;
        }

        // 根据位置得到View对象 也就是对view进行操作
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {

                view = inflater.inflate(R.layout.mian_people_visit_list_item2, null);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MianPeopleVisitEditActivity.class);

                    intent.putExtra("PersonID", list.get(i).getPERSONID());
                    intent.putExtra("INTERVIEWID", list.get(i).getINTERVIEWID());
                    intent.putExtra("xmTV", list.get(i).getPERSONNAME());
                    intent.putExtra("sexTV", (list.get(i).getMALEDICTID().equals("1") ? "男" : "女"));
                    context.startActivity(intent);
                }
            });
            // {
            // "INTERVIEWEFFECT": "啊实打实大啊实打实大啊实打实大",
            // "PERSONNAME": "撒旦法师的",
            // "INTERVIEWTIME": "1985-08-05 11:00:17",
            // "INTERVIEWID": "141",
            // "INTERVIEWTYPE": "啊实打实大啊实打实大啊实打实大",
            // "BIRTH": "1992-08-08",
            // "MALEDICTID": "2",
            // "PERSONID": "460"
            // }
            TextView textViewname = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_name);
            TextView textViewsex = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_sex);
            TextView textViewbirth = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_birth);
            TextView textViewzfsj = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_zfsj);
            TextView textViewzfxs = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_zfxs);
            TextView textViewzfxg = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_zfxg);

            textViewname.setText("姓名：" + list.get(i).getPERSONNAME());
            textViewsex.setText("性别：" + (list.get(i).getMALEDICTID().equals("1") ? "男" : "女"));
            textViewbirth.setText("出身日期：" + list.get(i).getBIRTH());
            textViewzfsj.setText("走访时间：" + list.get(i).getINTERVIEWTIME());
            textViewzfxs.setText("走访形式：" + list.get(i).getINTERVIEWTYPE());
            textViewzfxg.setText("走访效果：" + list.get(i).getINTERVIEWEFFECT());
            return view;
        }
    }

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setTitle("提示信息");
        builder1.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder1.setMessage("" + errorMsg);
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder1.show();
    }

    /** 土司 */
    private void toast(String strMsg, int L1S0) {
        Toast.makeText(getApplicationContext(), strMsg, L1S0).show();
    }
}
