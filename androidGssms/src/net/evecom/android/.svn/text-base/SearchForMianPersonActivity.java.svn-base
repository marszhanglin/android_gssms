/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.util.ArrayList;
import java.util.List;

import net.evecom.android.bean.MianPerson;
import net.evecom.android.json.JsonSysOrgansData;
import net.evecom.android.util.HttpUtil;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 2014-7-22下午3:53:04 类SearchForQYActivity
 * 
 * @author Mars zhang
 * 
 */
public class SearchForMianPersonActivity extends Activity {
    /** 搜索文本框 */
    private EditText idCardedText;// search_for_qy_edit
    /** 搜索文本框 */
    private EditText nameText;// search_for_qy_edit
    /** 企业列表 */
    private ListView qyListView;// search_for_qy_listView
    /** 成员变量 */
    private Handler handler1 = null;
    /** 成员变量 */
    private List<MianPerson> mianPersons = new ArrayList<MianPerson>();
    /** 成员变量 */
    private SearchQyAdapter searchQyAdapter = null;
    /** 成员变量 */
    private FinalDb db = null;
    /** 分页 */
    private String temp = "15";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_main_person);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        temp = HttpUtil.getPageSize(this);
        initView();
        getExtra();
    }

    /**
     * 获取activity传递信息
     */
    private void getExtra() {
        getXmlAndSetList();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        idCardedText = (EditText) findViewById(R.id.search_for_main_person_ry_idcard_edit);
        nameText = (EditText) findViewById(R.id.search_for_main_person_ry_name_edit);
        qyListView = (ListView) findViewById(R.id.search_for_qy_listView);
    }

    /**
     * 搜索按钮监听
     * 
     * @param v
     */
    public void search_for_qy_btn_onclick(View v) {
        if ("".endsWith(idCardedText.getText().toString())) {
            /** 空格也让查 */
            getXmlAndSetList();
        } else {
            getXmlAndSetList();
        }
    }

    /**
     * 开一条子线程加载网络数据
     */
    private void getXmlAndSetList() {
        Runnable runnable = new Runnable() {
            public void run() {
                String path = null;
                path = HttpUtil.BASE_URL + "teventAndroid/abelSelectedGrid";
                // xmlwebData解析网络中xml中的数据 path是请求地址
                // http:localhost:8080/gssms/teventAndroid/abelSelectedGrid
                // ?clickAreaId=0&Q_t1.PERSONNAME_S_LK=卞&Q_t1.IDCARDNO_S_LK=37
                try {
                    mianPersons = JsonSysOrgansData.getData(path, "clickAreaId=0&Q_t1.PERSONNAME_S_LK="
                            + nameText.getText().toString().trim() + "&Q_t1.IDCARDNO_S_LK="
                            + idCardedText.getText().toString().trim() + "&pageSize=" + temp);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    if (null != e) {
                        e.printStackTrace();
                    }
                    handler1.sendMessage(handler1.obtainMessage(1, mianPersons));
                    return;
                }

                // 当请求完毕时发送消息，并把list结合对象传递过去
                handler1.sendMessage(handler1.obtainMessage(0, mianPersons));
            }
        };
        try {
            // 开启线程
            new Thread(runnable).start();
            // handler与线程之间的通信及数据处理
            handler1 = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(mianPersons);
                    } else if (msg.what == 1) {
                        toast("请检查网络是否可用");
                    }
                }
            };
        } catch (Exception e) {
            if (null != e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 绑定数据
     * 
     * @param list
     */
    public void BinderListData(List<MianPerson> list) {
        searchQyAdapter = new SearchQyAdapter(this, list);
        qyListView.setAdapter(searchQyAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(SearchForMianPersonActivity.this, MainPersonListActivity.class);
            setResult(R.layout.search_for_main_person, intent);
            finish();// 这步操作完他在跳转
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 匿名适ListView配器类
     * 
     * @author Mars zhang
     * 
     */
    public class SearchQyAdapter extends BaseAdapter implements ListAdapter {
        /** MemberVariables */
        private Context context;
        /** MemberVariables */
        private LayoutInflater inflater;
        /** MemberVariables */
        private List<MianPerson> list;

        public SearchQyAdapter(Context context, List<MianPerson> list) {
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
                view = inflater.inflate(R.layout.search_for_main_person_list_item2, null);
            }
            TextView textViewName = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_name);
            TextView textViewBirth = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_birth);
            TextView textViewSex = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_sex);
            TextView textViewAddress = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_address);
            textViewName.setText("姓名：" + list.get(i).getPERSONNAME());
            textViewBirth.setText("出身年月：" + list.get(i).getBIRTH());
            textViewSex.setText("性别：" + list.get(i).getMALEDICTID());
            textViewAddress.setText("家庭住址：" + list.get(i).getHOUSEADDR());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchForMianPersonActivity.this, MainPersonListActivity.class);
                    db = FinalDb.create(SearchForMianPersonActivity.this, true);
                    MianPerson mianPerson = db.findById(list.get(i).getPERSONID(), MianPerson.class);
                    System.out.println(mianPerson);
                    if (null != mianPerson) {
                        DialogToast("该人员已经添加！");
                        return;
                    }
                    db.save(list.get(i));
                    setResult(1, intent);
                    finish();// 这步操作完他在跳转
                    // Intent intent = new
                    // Intent(SearchForMianPersonActivity.this,
                    // EventAddActivity.class);
                    // intent.putExtra("PERSONNAME", "" +
                    // list.get(i).getPERSONNAME());
                    // intent.putExtra("PERSONID", "" +
                    // list.get(i).getPERSONID());
                    // setResult(1, intent);
                    // finish();// 这步操作完他在跳转
                }
            });
            return view;
        }
    }

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SearchForMianPersonActivity.this);
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
    private void toast(String strMsg) {
        Toast.makeText(getApplicationContext(), strMsg, 0).show();
    }

}
