/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.util.ArrayList;
import java.util.List;

import net.evecom.android.bean.EOS_DICT_ENTRY;
import net.evecom.android.bean.T_PERSON_PERSON;
import net.evecom.android.json.JsonMainPersonKeyPersonData;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import net.evecom.android.view.wheel.OnWheelChangedListener;
import net.evecom.android.view.wheel.OnWheelScrollListener;
import net.evecom.android.view.wheel.WheelView;
import net.evecom.android.view.wheel.adapter.AbstractWheelTextAdapter;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
 * MianPeopleVisitActivity--->MianPeopleVisitSearchForMianPersonActivity
 * 2014-8-18上午9:00:23 类MianPeopleVisitSearchForMianPersonActivity
 * 
 * @author Mars zhang
 * 
 */
public class MianPeopleVisitSearchForMianPersonActivity extends Activity {
    /** 搜索文本框 */
    private EditText idCardedText;// search_for_qy_edit
    /** 搜索文本框 */
    private EditText nameText;// search_for_qy_edit
    /** 搜索文本框 */
    private EditText ryxzText;// search_for_main_person_ry_ryxz_edit
    /** 企业列表 */
    private ListView qyListView;// search_for_qy_listView
    /** 成员变量 */
    private Handler handler1 = null;
    /** 成员变量 */
    private List<T_PERSON_PERSON> mianPersons = new ArrayList<T_PERSON_PERSON>();
    /** 成员变量 */
    private SearchQyAdapter searchQyAdapter = null;
    /** MemberVariables */
    private FinalDb db = null;
    /** MemberVariables */
    private List<EOS_DICT_ENTRY> mainTypeDict_ENTRYs = new ArrayList<EOS_DICT_ENTRY>();
    /** Sysindex */
    private int Sysindex;
    /** 被选择的隐患类别 */
    private EOS_DICT_ENTRY DICT_ENTRY = new EOS_DICT_ENTRY("", "");
    /** MemberVariables */
    public static MianPeopleVisitSearchForMianPersonActivity instance = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_people_visit_search_for_main_person);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initRYLX();
        initView();
        getExtra();
        instance = this;
    }

    /**
     * 填充人员类型数据
     */
    private void initRYLX() {
        EOS_DICT_ENTRY entry1 = new EOS_DICT_ENTRY("1", "社区矫正人员");
        EOS_DICT_ENTRY entry2 = new EOS_DICT_ENTRY("2", "刑释解教人员");
        EOS_DICT_ENTRY entry3 = new EOS_DICT_ENTRY("3", "精神病人");
        EOS_DICT_ENTRY entry4 = new EOS_DICT_ENTRY("4", "吸毒人员");
        EOS_DICT_ENTRY entry5 = new EOS_DICT_ENTRY("5", "艾滋病人员");
        EOS_DICT_ENTRY entry6 = new EOS_DICT_ENTRY("6", "危险品从业人员");
        EOS_DICT_ENTRY entry7 = new EOS_DICT_ENTRY("7", "重点上访人员");
        EOS_DICT_ENTRY entry8 = new EOS_DICT_ENTRY("8", "重点青少年");
        EOS_DICT_ENTRY entry9 = new EOS_DICT_ENTRY("9", "其他人员");
        EOS_DICT_ENTRY entry10 = new EOS_DICT_ENTRY("10", "老年人");
        EOS_DICT_ENTRY entry11 = new EOS_DICT_ENTRY("11", "残疾人");
        EOS_DICT_ENTRY entry12 = new EOS_DICT_ENTRY("12", "失业人员");
        EOS_DICT_ENTRY entry13 = new EOS_DICT_ENTRY("13", "育龄妇女");
        EOS_DICT_ENTRY entry14 = new EOS_DICT_ENTRY("14", "志愿者");
        EOS_DICT_ENTRY entry15 = new EOS_DICT_ENTRY("15", "低保人员");
        EOS_DICT_ENTRY entry16 = new EOS_DICT_ENTRY("16", "医疗人员");
        EOS_DICT_ENTRY entry17 = new EOS_DICT_ENTRY("17", "服兵役人员");
        EOS_DICT_ENTRY entry18 = new EOS_DICT_ENTRY("18", "党员");
        mainTypeDict_ENTRYs.add(entry1);
        mainTypeDict_ENTRYs.add(entry2);
        mainTypeDict_ENTRYs.add(entry3);
        mainTypeDict_ENTRYs.add(entry4);
        mainTypeDict_ENTRYs.add(entry5);
        mainTypeDict_ENTRYs.add(entry6);
        mainTypeDict_ENTRYs.add(entry7);
        mainTypeDict_ENTRYs.add(entry8);
        mainTypeDict_ENTRYs.add(entry10);
        mainTypeDict_ENTRYs.add(entry11);
        mainTypeDict_ENTRYs.add(entry12);
        mainTypeDict_ENTRYs.add(entry13);
        mainTypeDict_ENTRYs.add(entry14);
        mainTypeDict_ENTRYs.add(entry15);
        mainTypeDict_ENTRYs.add(entry16);
        mainTypeDict_ENTRYs.add(entry17);
        mainTypeDict_ENTRYs.add(entry18);
        mainTypeDict_ENTRYs.add(entry9);
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
        ryxzText = (EditText) findViewById(R.id.search_for_main_person_ry_ryxz_edit);
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
     * 人员类型选择
     * 
     * @param v
     */
    public void rylx(View v) {
        View messageView = LayoutInflater.from(MianPeopleVisitSearchForMianPersonActivity.this).inflate(
                R.layout.base_dialog_info2, null);
        // 分页设置
        final WheelView country = (WheelView) messageView.findViewById(R.id.country);
        country.setVisibleItems(4);
        country.setViewAdapter(new CountryAdapter(MianPeopleVisitSearchForMianPersonActivity.this,
                mainTypeDict_ENTRYs));
        country.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {

            }

            public void onScrollingFinished(WheelView wheel) {
                DICT_ENTRY = mainTypeDict_ENTRYs.get(Sysindex);
            }
        });
        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Sysindex = newValue;
            }
        });
        country.setCurrentItem(0);
        DICT_ENTRY = mainTypeDict_ENTRYs.get(0);
        Dialog delDia = new AlertDialog.Builder(MianPeopleVisitSearchForMianPersonActivity.this)
                .setIcon(R.drawable.qq_dialog_default_icon).setTitle("请选择人员类型！").setView(messageView)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        ryxzText.setText(DICT_ENTRY.getDICTNAME());
                        dia.dismiss();
                    }
                }).create();
        delDia.show();
    }

    /**
     * 开一条子线程加载网络数据
     */
    private void getXmlAndSetList() {
        Runnable runnable = new Runnable() {
            public void run() {
                String path = null;
                // http://localhost:8080/gssms/teventAndroid
                ///KeyPersonViewlist?areaId=0&Q_t1.IDCARDNO_S_LK=&Q_t1.PERSONNAME_S_LK=&keyPersonType=
                path = HttpUtil.BASE_URL + "teventAndroid/KeyPersonlist";
                // xmlwebData解析网络中xml中的数据 path是请求地址
                // http:localhost:8080/gssms/teventAndroid/abelSelectedGrid
                // ?clickAreaId=0&Q_t1.PERSONNAME_S_LK=卞&Q_t1.IDCARDNO_S_LK=37
                try {
                    mianPersons = JsonMainPersonKeyPersonData.getData(
                            path,
                            "areaId=" + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0")
                                    + "&Q_t1.IDCARDNO_S_LK=" + idCardedText.getText().toString().trim()
                                    + "&Q_t1.PERSONNAME_S_LK=" + nameText.getText().toString().trim()
                                    + "&keyPersonType=" + DICT_ENTRY.getDICTID() + "&pageSize="
                                    + ShareUtil.getString(getApplicationContext(), "PageSize", "pagesize", "15"));
                } catch (Exception e) {
                    // System.out.println(e.toString());
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
    public void BinderListData(List<T_PERSON_PERSON> list) {
        searchQyAdapter = new SearchQyAdapter(this, list);
        qyListView.setAdapter(searchQyAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(MianPeopleVisitSearchForMianPersonActivity.this, MainPersonListActivity.class);
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
        private List<T_PERSON_PERSON> list;

        public SearchQyAdapter(Context context, List<T_PERSON_PERSON> list) {
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

        /*
         * { "PERSONNAME": "哈哈", "PERSONPHONENO": "", "PHOTOURL": "", "KEYTYPE":
         * "刑释解教人员，低保", "HOUSEADDR": "", "IDCARDNO": "350825199111111112",
         * "MALEDICTID": "1", "PERSONID": "474" }
         */
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = inflater.inflate(R.layout.mian_people_visit_search_for_main_list_item, null);
            }
            TextView textViewName = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_name);
            TextView textViewsex = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_sex);
            TextView textViewid_card = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_id_card);
            TextView textViewaddr = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_addr);
            TextView textViewphone = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_phone);
            TextView textViewryxz = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_ryxz);
            textViewName.setText("姓名：" + list.get(i).getPERSONNAME());
            textViewsex.setText("性别：" + (list.get(i).getMALEDICTID().equals("1") ? "男" : "女"));
            textViewid_card.setText("身份证号码：" + list.get(i).getIDCARDNO());
            textViewaddr.setText("住址：" + list.get(i).getHOUSEADDR());
            textViewphone.setText("手机：" + list.get(i).getPERSONPHONENO());
            textViewryxz.setText("人员性质：" + list.get(i).getKEYTYPE());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MianPeopleVisitAddActivity.class);
                    intent.putExtra("xmTV", list.get(i).getPERSONNAME());
                    intent.putExtra("sexTV", (list.get(i).getMALEDICTID().equals("1") ? "男" : "女"));
                    intent.putExtra("idcardTV", list.get(i).getIDCARDNO());
                    intent.putExtra("addrTV", list.get(i).getHOUSEADDR());
                    intent.putExtra("phoneTV", list.get(i).getPERSONPHONENO());
                    intent.putExtra("PersonID", list.get(i).getPERSONID());
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    /**
     * wheel适配器 2014-7-22下午5:56:27 类CountryAdapter 查找网格使用的
     * 
     * @author Mars zhang
     * 
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        /** MemberVariables */
        List<EOS_DICT_ENTRY> list;
        /** MemberVariables */
        int i = 0;

        protected CountryAdapter(Context context, List list) {
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
            return list.get(index).getDICTNAME() + "";
        }

    }

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MianPeopleVisitSearchForMianPersonActivity.this);
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
