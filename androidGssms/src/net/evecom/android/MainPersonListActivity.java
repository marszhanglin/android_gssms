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
 * EventAdd�µ���Ҫ��Ա
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
    /** ���ݿ�������� */
    private FinalDb db;
    /** �Ƿ��һ�������Ա */
    private Boolean isFirstTime = false;

    // /**���id*/
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
            case 1:// ˢ����Ա���ݿ�
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
     * ˢ��ListView
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
            finish();// �ⲽ������������ת
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ���ݳ�ʼ��
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
     * ����
     * 
     * @param v
     */
    public void fh(View v) {
        Intent intent = new Intent();
        setResult(1, intent);
        this.finish();
    }

    /**
     * ������ListView������
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
            textViewName.setText("������" + list.get(i).getPERSONNAME());
            textViewBirth.setText("�������£�" + list.get(i).getBIRTH());
            textViewSex.setText("�Ա�" + list.get(i).getMALEDICTID());
            textViewAddress.setText("��ͥסַ��" + list.get(i).getHOUSEADDR());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // ���ɾ������Ա
                    View view = inflater.inflate(R.layout.main_person_dialog_info, null);
                    TextView textViewName = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_name);
                    TextView textViewBirth = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_birth);
                    TextView textViewSex = (TextView) view.findViewById(R.id.search_for_main_person_list_item2_sex);
                    TextView textViewAddress = (TextView) view
                            .findViewById(R.id.search_for_main_person_list_item2_address);
                    textViewName.setText("������" + list.get(i).getPERSONNAME());
                    textViewBirth.setText("�������£�" + list.get(i).getBIRTH());
                    textViewSex.setText("�Ա�" + list.get(i).getMALEDICTID());
                    textViewAddress.setText("��ͥסַ��" + list.get(i).getHOUSEADDR());

                    Dialog delDia = new AlertDialog.Builder(MainPersonListActivity.this)
                            .setIcon(R.drawable.qq_dialog_default_icon).setTitle("��Ա��Ϣ").setView(view)
                            .setPositiveButton("ɾ��", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    db = FinalDb.create(MainPersonListActivity.this, true);
                                    db.deleteById(MianPerson.class, list.get(i).getPERSONID());
                                    updateListView();
                                    dia.dismiss();
                                }
                            }).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
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