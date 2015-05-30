/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.util.ArrayList;
import java.util.List;

import net.evecom.android.bean.MianPerson;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * EventAdd下的主要人员
 * 
 * @author Mars zhang
 * 
 */
public class MainPersonListActivity extends Activity {
    /** MemberVariables */
    private DangerAdapter dangerAdapter = null;
    /** MemberVariables */
    private ListView listView;
    /** MemberVariables */
    private List<MianPerson> mianPersons;
    /** 数据库操作对象 */
    private FinalDb db;
    /** 是否第一次添加人员 */
    private Boolean isFirstTime = false;

    // /**检查id*/
    // private String sysid;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_persion_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent intent = getIntent();
        isFirstTime = intent.getBooleanExtra("isFirstTime", false);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:// 刷新人员数据库
                updateListView();
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 刷新ListView
     */
    private void updateListView() {
        db = FinalDb.create(MainPersonListActivity.this, true);
        mianPersons.removeAll(mianPersons);
        mianPersons = db.findAll(MianPerson.class);
        dangerAdapter = new DangerAdapter(getApplicationContext(), mianPersons);
        listView.setAdapter(dangerAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(this, EventAddActivity.class);
            setResult(1, intent);
            finish();// 这步操作完他在跳转
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 数据初始化
     */
    private void init() {
        mianPersons = new ArrayList<MianPerson>();
        listView = (ListView) findViewById(R.id.add_danger_list_listView);
        db = FinalDb.create(getApplicationContext(), true);
        mianPersons = db.findAll(MianPerson.class);
        dangerAdapter = new DangerAdapter(getApplicationContext(), mianPersons);
        listView.setAdapter(dangerAdapter);
        if (isFirstTime) {
            Intent intent = new Intent(MainPersonListActivity.this, SearchForMianPersonActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    /**
     * add
     * 
     * @param v
     */
    public void add(View v) {
        Intent intent = new Intent(MainPersonListActivity.this, SearchForMianPersonActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * save
     * 
     * @param v
     */
    public void save(View v) {
        Intent intent = new Intent();
        setResult(1, intent);
        this.finish();
    }

    /**
     * 返回
     * 
     * @param v
     */
    public void fh(View v) {
        Intent intent = new Intent();
        setResult(1, intent);
        this.finish();
    }

    /**
     * 匿名适ListView配器类
     * 
     * @author Mars zhang
     */
    public class DangerAdapter extends BaseAdapter implements ListAdapter {
        /** MemberVariables */
        private Context context;
        /** MemberVariables */
        private LayoutInflater inflater;
        /** MemberVariables */
        private List<MianPerson> list;

        public DangerAdapter(Context context, List<MianPerson> list) {
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
                public void onClick(View v) { // 点击删除该人员
                    View view = inflater.inflate(R.layout.main_person_dialog_info, null);
                    TextView textViewName = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_name);
                    TextView textViewBirth = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_birth);
                    TextView textViewSex = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_sex);
                    TextView textViewAddress = (TextView) view
                            .findViewById(R.id.search_for_main_person_list_item2_address);
                    textViewName.setText("姓名：" + list.get(i).getPERSONNAME());
                    textViewBirth.setText("出身年月：" + list.get(i).getBIRTH());
                    textViewSex.setText("性别：" + list.get(i).getMALEDICTID());
                    textViewAddress.setText("家庭住址：" + list.get(i).getHOUSEADDR());

                    Dialog delDia = new AlertDialog.Builder(MainPersonListActivity.this)
                            .setIcon(R.drawable.qq_dialog_default_icon).setTitle("人员信息").setView(view)
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    db = FinalDb.create(MainPersonListActivity.this, true);
                                    db.deleteById(MianPerson.class, list.get(i).getPERSONID());
                                    updateListView();
                                    dia.dismiss();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    delDia.show();
                }
            });
            return view;
        }
    }

}