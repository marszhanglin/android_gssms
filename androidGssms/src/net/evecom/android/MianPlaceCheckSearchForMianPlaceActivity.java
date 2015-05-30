/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.util.ArrayList;
import java.util.List;

import net.evecom.android.bean.EOS_DICT_ENTRY;
import net.evecom.android.bean.T_PLACE_PLACE;
import net.evecom.android.json.JsonMainPlaceKeyPlaceData;
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
 * MianPlaceCheckVisitActivity--->MianPlaceCheckSearchForMianPlaceActivity
 * 2014-8-18上午9:00:23 类MianPlaceCheckSearchForMianPlaceActivity
 * 
 * @author Mars zhang
 * 
 */
public class MianPlaceCheckSearchForMianPlaceActivity extends Activity {
    /** 搜索文本框 */
    private EditText nameText;// search_for_qy_edit
    /** 搜索文本框 */
    private EditText ryxzText;// search_for_main_person_ry_ryxz_edit
    /** 企业列表 */
    private ListView qyListView;// search_for_qy_listView
    /** 成员变量 */
    private Handler handler1 = null;
    /** 成员变量 */
    private List<T_PLACE_PLACE> mianPlaces = new ArrayList<T_PLACE_PLACE>();
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
    public static MianPlaceCheckSearchForMianPlaceActivity instance = null;
    /** MemberVariables */
    private String type = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_place_check_search_for_main_place);
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
        EOS_DICT_ENTRY entry1 = new EOS_DICT_ENTRY("1", "安全生产重点");
        EOS_DICT_ENTRY entry2 = new EOS_DICT_ENTRY("2", "消防安全重点");
        EOS_DICT_ENTRY entry3 = new EOS_DICT_ENTRY("3", "治安重点");
        EOS_DICT_ENTRY entry4 = new EOS_DICT_ENTRY("4", "学校");
        EOS_DICT_ENTRY entry5 = new EOS_DICT_ENTRY("5", "医院");
        EOS_DICT_ENTRY entry6 = new EOS_DICT_ENTRY("6", "危险化学品单位");
        EOS_DICT_ENTRY entry7 = new EOS_DICT_ENTRY("7", "网吧");
        EOS_DICT_ENTRY entry8 = new EOS_DICT_ENTRY("8", "公共场所");
        EOS_DICT_ENTRY entry9 = new EOS_DICT_ENTRY("9", "公共复杂场所");
        EOS_DICT_ENTRY entry10 = new EOS_DICT_ENTRY("10", "其他场所");
        mainTypeDict_ENTRYs.add(entry1);
        mainTypeDict_ENTRYs.add(entry2);
        mainTypeDict_ENTRYs.add(entry3);
        mainTypeDict_ENTRYs.add(entry4);
        mainTypeDict_ENTRYs.add(entry5);
        mainTypeDict_ENTRYs.add(entry6);
        mainTypeDict_ENTRYs.add(entry7);
        mainTypeDict_ENTRYs.add(entry8);
        mainTypeDict_ENTRYs.add(entry9);
        mainTypeDict_ENTRYs.add(entry10);

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

        nameText = (EditText) findViewById(R.id.search_for_main_person_ry_name_edit);
        ryxzText = (EditText) findViewById(R.id.search_for_main_person_ry_ryxz_edit);
        ryxzText.setVisibility(View.GONE);
        qyListView = (ListView) findViewById(R.id.search_for_qy_listView);
    }

    /**
     * 搜索按钮监听
     * 
     * @param v
     */
    public void search_for_qy_btn_onclick(View v) {
        getXmlAndSetList();
    }

    /**
     * 场所类型选择
     * 
     * @param v
     */
    public void rylx(View v) {
        View messageView = LayoutInflater.from(MianPlaceCheckSearchForMianPlaceActivity.this).inflate(
                R.layout.base_dialog_info2, null);
        // 分页设置
        final WheelView country = (WheelView) messageView.findViewById(R.id.country);
        country.setVisibleItems(4);
        country.setViewAdapter(new CountryAdapter(MianPlaceCheckSearchForMianPlaceActivity.this, mainTypeDict_ENTRYs));
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
        Dialog delDia = new AlertDialog.Builder(MianPlaceCheckSearchForMianPlaceActivity.this)
                .setIcon(R.drawable.qq_dialog_default_icon).setTitle("请选择场所类型！").setView(messageView)
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
                // http://localhost:8080/gssms/teventAndroid/KeyPlacelist
                // ?Q_t1.PLACENAME_S_LK=&Q_t1.PLACETYPED_S_LK=
                path = HttpUtil.BASE_URL + "teventAndroid/KeyPlacelist";
                // xmlwebData解析网络中xml中的数据 path是请求地址
                // http:localhost:8080/gssms/teventAndroid/abelSelectedGrid
                // ?clickAreaId=0&Q_t1.PERSONNAME_S_LK=卞&Q_t1.IDCARDNO_S_LK=37
                try {
                    mianPlaces = JsonMainPlaceKeyPlaceData
                            .getData(
                                    path,
                                    "&Q_t1.PLACENAME_S_LK="
                                            + nameText.getText().toString().trim()
                                            + "&Q_t1.PLACETYPED_S_EQ="
                                            + DICT_ENTRY.getDICTID()
                                            + "&areaId="
                                            + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0")
                                            + "&pageSize="
                                            + ShareUtil
                                                    .getString(getApplicationContext(), "PageSize", "pagesize", "15"));
                } catch (Exception e) {
                    // System.out.println(e.toString());
                    if (null != e) {
                        e.printStackTrace();
                    }
                    handler1.sendMessage(handler1.obtainMessage(1, mianPlaces));
                    return;
                }

                // 当请求完毕时发送消息，并把list结合对象传递过去
                handler1.sendMessage(handler1.obtainMessage(0, mianPlaces));
            }
        };
        try {
            // 开启线程
            new Thread(runnable).start();
            // handler与线程之间的通信及数据处理
            handler1 = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(mianPlaces);
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
    public void BinderListData(List<T_PLACE_PLACE> list) {
        searchQyAdapter = new SearchQyAdapter(this, list);
        qyListView.setAdapter(searchQyAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(MianPlaceCheckSearchForMianPlaceActivity.this, MainPersonListActivity.class);
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
        private List<T_PLACE_PLACE> list;

        public SearchQyAdapter(Context context, List<T_PLACE_PLACE> list) {
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

        // [{
        // "PLACEADDR": "fdsg",
        // "PLACETYPED": "7",
        // "PLACENAME": "fdsg",
        // "PLACEID": "93"
        // },
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = inflater.inflate(R.layout.mian_place_visit_search_for_main_list_item, null);
            }
            TextView textViewName = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_name);
            TextView textViewcslb = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_cslb);
            TextView textViewcsdz = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_csdz);

            textViewName.setText("场所名称:" + list.get(i).getPLACENAME());
            type = list.get(i).getPLACETYPED();
            if (null != type && "1".equals(type)) {
                textViewcslb.setText("场所类别：" + "安全生产重点");
                type = "安全生产重点";
            } else if (null != type && "2".equals(type)) {
                textViewcslb.setText("场所类别：" + "消防安全重点");
                type = "消防安全重点";
            } else if (null != type && "3".equals(type)) {
                textViewcslb.setText("场所类别：" + "治安重点");
                type = "治安重点";
            } else if (null != type && "4".equals(type)) {
                textViewcslb.setText("场所类别：" + "学校");
                type = "学校";
            } else if (null != type && "5".equals(type)) {
                textViewcslb.setText("场所类别：" + "医院");
                type = "医院";
            } else if (null != type && "6".equals(type)) {
                textViewcslb.setText("场所类别：" + "危险化学品单位");
                type = "危险化学品单位";
            } else if (null != type && "7".equals(type)) {
                textViewcslb.setText("场所类别：" + "网吧");
                type = "网吧";
            } else if (null != type && "8".equals(type)) {
                textViewcslb.setText("场所类别：" + "公共场所");
                type = "公共场所";
            } else if (null != type && "9".equals(type)) {
                textViewcslb.setText("场所类别：" + "公共复杂场所");
                type = "公共复杂场所";
            } else if (null != type && "10".equals(type)) {
                textViewcslb.setText("场所类别：" + "其他场所");
                type = "其他场所";
            }
            textViewcsdz.setText("场所地址：" + list.get(i).getPLACEADDR());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MianPlaceCheckAddActivity.class);
                    intent.putExtra("csmcTV", list.get(i).getPLACENAME());
                    intent.putExtra("cslbTV", type);
                    intent.putExtra("csdzTV", list.get(i).getPLACEADDR());

                    intent.putExtra("PLACEID", list.get(i).getPLACEID());
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MianPlaceCheckSearchForMianPlaceActivity.this);
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
