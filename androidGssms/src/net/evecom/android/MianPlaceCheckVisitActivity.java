/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.util.ArrayList;
import java.util.List;

import net.evecom.android.bean.T_PERSON_INSPECTION;
import net.evecom.android.json.JsonMainPlayCheckData;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
 * �ص������������ MianPlaceCheckVisitActivity
 * 
 * @author Mars zhang
 * 
 */
public class MianPlaceCheckVisitActivity extends Activity {
    /** ���ذ�ť */
    private Button mBtnBack;
    /** MemberVariables */
    private ListView listView;
    /** MemberVariables */
    private ArrayList<T_PERSON_INSPECTION> checks;
    /** MemberVariables */
    private Handler handler = null;
    /** MemberVariables */
    private LinearLayout layoutSearch;
    /** MemberVariables */
    private RelativeLayout relativeLayout_bottom;// main_people_visit_bottom
    /** MemberVariables */
    private EditText xsEditText = null; // search_for_main_person_ry_name_edit
    /** MemberVariables */
    private EditText xmEditText = null; // search_for_main_person_ry_idcard_edit
    /** ��Ա���� */
    private static final int MESSAGETYPE_01 = 0x0001;// �����ж��Ƿ��ͳɹ�
    /** ��Ա���� */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** MemberVariables */
    private String main_person_vist_json = "";
    /** MemberVariables */
    public static MianPlaceCheckVisitActivity instance = null;
    /** MemberVariables */
    private String[] jctypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_place_check_list);
        SharedPreferences sp = getSharedPreferences("MianPlaceCheckVisitActivity", MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("INSRESULT", "");
        editor.commit();
        // ����activityʱ���Զ����������
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        instance = this;
    }

    /**
     * initView
     */
    public void initView() {
        Intent intent = getIntent();
        mBtnBack = (Button) findViewById(R.id.one_group_contact_list_btn_back);
        xsEditText = (EditText) findViewById(R.id.search_for_main_person_ry_name_edit);
        xmEditText = (EditText) findViewById(R.id.search_for_main_person_ry_idcard_edit);
        xmEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jctypes = new String[] { "�ϸ�", "���ϸ�" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckVisitActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("��ѡ��")
                        .setItems(jctypes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                SharedPreferences sp = getSharedPreferences("MianPlaceCheckVisitActivity", 
                                        MODE_PRIVATE);
                                Editor editor = sp.edit();
                                switch (which) {
                                    case 0:
                                        editor.putString("INSRESULT", which + 1 + "");
                                        editor.commit();
                                        xmEditText.setText(jctypes[which]);
                                        break;
                                    case 1:
                                        editor.putString("INSRESULT", which + 1 + "");
                                        editor.commit();
                                        xmEditText.setText(jctypes[which]);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                dia.dismiss();
                            }
                        }).create();
                schDia.show();
            }
        });

        listView = (ListView) findViewById(R.id.one_group_contact_list_listView);
        checks = new ArrayList<T_PERSON_INSPECTION>();
        layoutSearch = (LinearLayout) findViewById(R.id.one_group_contact_search_lin);
        relativeLayout_bottom = (RelativeLayout) findViewById(R.id.main_people_visit_bottom);
        // ��һ�����̼߳�����������
        Runnable runnable = new Runnable() {
            public void run() {
                // http://localhost:8080/gssms/teventAndroid/KeyPlaceChecklist
//                ?PERSONID=1&Q_t1.INSPLACE_S_LK=&Q_t1.INSRESULT_S_LK
                String path = HttpUtil.BASE_URL + "teventAndroid/KeyPlaceChecklist";
                String entry = "PERSONID=" + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "1")
                        + "&offset=" + checks.size() + "&pageSize="
                        + ShareUtil.getString(getApplicationContext(), "PageSize", "pagesize", "15") + "&areaId="
                        + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0");
                // xmlwebData����������xml�е����� path�������ַ
                try {
                    checks = JsonMainPlayCheckData.getData(path, entry);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                        handler.sendMessage(handler.obtainMessage(1, checks));
                    }
                }
                // ���������ʱ������Ϣ������list��϶��󴫵ݹ�ȥ
                handler.sendMessage(handler.obtainMessage(0, checks));
            }
        };

        try {
            // �����߳�
            new Thread(runnable).start();
            // handler���߳�֮���ͨ�ż����ݴ���
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(checks);
                    } else if (msg.what == 1) {
                        toast("���������Ƿ����", 1);
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ������
    public void BinderListData(ArrayList<T_PERSON_INSPECTION> list) {
        // //����adapter����
        // adapter=new ListViewAdapter(R.layout.item,this,person);
        // //��Adapter�󶨵�listview��
        // listView.setAdapter(adapter);
        MyAdapter bulletinAdapter = new MyAdapter(this, list);
        listView.setAdapter(bulletinAdapter);
    }

    /**
     * ����
     * 
     * @param v
     */
    public void fh(View v) {
        finish();

    }

    /**
     * ����
     * 
     * @param v
     */
    public void add(View v) {
        Intent intent = new Intent(this, MianPlaceCheckSearchForMianPlaceActivity.class);
        startActivity(intent);

    }

    /**
     * ��ѯͨѶ¼
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
     * ��ѯ �������� Q_t1.INTERVIEWTYPE_S_LK=xs&Q_t3.PERSONNAME_S_LK=xm
     * 
     * @param v
     */
    public void search_for_contact_btn_onclick(View v) {
        // ��һ�����̼߳�����������
        Runnable runnable = new Runnable() {
            public void run() {
                // http://localhost:8080/gssms/teventAndroid/KeyPlaceChecklist
                // ?PERSONID=1&Q_t1.INSPLACE_S_LK=&Q_t1.INSRESULT_S_LK
                String path = HttpUtil.BASE_URL + "teventAndroid/KeyPlaceChecklist";
                String entry = "PERSONID=" + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "1")
                        + "&offset=" + 0
                        + "&pageSize="
                        + ShareUtil.getString(getApplicationContext(), "PageSize", "pagesize", "15")
                        + "&Q_t1.INSPLACE_S_LK="
                        + xsEditText.getText().toString().trim() // ��������
                        + "&Q_t1.INSRESULT_S_LK="
                        + ShareUtil.getString(getApplicationContext(), "MianPlaceCheckVisitActivity", "INSRESULT", "")
                        + "&areaId=" + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0"); // �����
                // xmlwebData����������xml�е����� path�������ַ
                try {
                    checks = JsonMainPlayCheckData.getData(path, entry);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                        handler.sendMessage(handler.obtainMessage(1, checks));
                    }
                }
                // ���������ʱ������Ϣ������list��϶��󴫵ݹ�ȥ
                handler.sendMessage(handler.obtainMessage(0, checks));
            }
        };

        try {
            // �����߳�
            new Thread(runnable).start();
            // handler���߳�֮���ͨ�ż����ݴ���
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(checks);
                    } else if (msg.what == 1) {
                        toast("���������Ƿ����", 1);
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ContactAdapter 2014-7-22����3:08:06 ��
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
        private List<T_PERSON_INSPECTION> list;

        /**
         * ���췽��������list���ݵľ�����һ�����ݵ���Ϣ
         * 
         * @param context
         * @param list
         */
        public MyAdapter(Context context, List<T_PERSON_INSPECTION> list) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.list = list;
        }

        // �õ��ܵ�����
        @Override
        public int getCount() {
            // System.out.println("getCount"+list==null?0:list.size());
            return list == null ? 0 : list.size();

        }

        // ����ListViewλ�÷���View
        @Override
        public Object getItem(int item) {
            // System.out.println("getItem"+item);
            return this.list.get(item);
        }

        // ����ListViewλ�õõ�List�е�ID
        @Override
        public long getItemId(int itemId) {
            // System.out.println("getItemId"+itemId);
            return itemId;
        }

        // ����λ�õõ�View���� Ҳ���Ƕ�view���в���
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {

                view = inflater.inflate(R.layout.mian_place_check_list_item1, null);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MianPlaceCheckEditActivity.class);

                    intent.putExtra("SYSID", list.get(i).getSYSID());
                    intent.putExtra("PersonID", list.get(i).getPERSONID());
                    intent.putExtra("INSPLACEID", list.get(i).getINSPLACEID());
                    intent.putExtra("INSRESULT", list.get(i).getINSRESULT());
                    intent.putExtra("INSPLACE", list.get(i).getINSPLACE());
                    intent.putExtra("RECTSTATE", list.get(i).getRECTSTATE());
                    intent.putExtra("INSDATE", list.get(i).getINSDATE());
                    context.startActivity(intent);

                    /**
                     * bug
                     */
                    // SharedPreferences sp = getSharedPreferences(
                    // "MianPlaceCheckVisitActivity", MODE_PRIVATE);
                    // Editor editor = sp.edit();
                    // editor.putString("INSRESULT", "");
                    // editor.commit();
                    /**
                     * bug
                     */
                }
            });
            // [{
            // "INSDATE": "2014-08-19",
            // "RECTSTATE": "1",
            // "INSPLACE": "df",
            // "INSRESULT": "sw",
            // "INSPLACEID": "91",
            // "PERSONID": "1",
            // "SYSID": "66"
            // }
            TextView textViewname = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_name);
            TextView textViewjcrq = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_jcrq);
            TextView textViewzgzt = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_zgzt);
            TextView textViewjcjg = (TextView) view.findViewById(R.id.mian_peolple_visit_list_item2_jcjg);

            textViewname.setText("��������:" + list.get(i).getINSPLACE());

            textViewjcrq.setText("������ڣ�" + list.get(i).getINSDATE());
            if (null != list.get(i).getRECTSTATE() && "1".equals(list.get(i).getRECTSTATE())) {
                textViewzgzt.setText("����״̬��" + "δ����");
            } else if (null != list.get(i).getRECTSTATE() && "2".equals(list.get(i).getRECTSTATE())) {
                textViewzgzt.setText("����״̬��" + "������");
            } else if (null != list.get(i).getRECTSTATE() && "3".equals(list.get(i).getRECTSTATE())) {
                textViewzgzt.setText("����״̬��" + "������");
            }
            String jcjg = list.get(i).getINSRESULT();
            if (null != jcjg && "1".equals(jcjg)) {
                textViewjcjg.setText("�������" + "�ϸ�");
            } else if (null != jcjg && "2".equals(jcjg)) {
                textViewjcjg.setText("�������" + "���ϸ�");
            }

            return view;
        }
    }

    /**
     * �������ʾ��Ϣ
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setTitle("��ʾ��Ϣ");
        builder1.setIcon(R.drawable.qq_dialog_default_icon);// ͼ��
        builder1.setMessage("" + errorMsg);
        builder1.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder1.show();
    }

    /** ��˾ */
    private void toast(String strMsg, int L1S0) {
        Toast.makeText(getApplicationContext(), strMsg, L1S0).show();
    }
}
