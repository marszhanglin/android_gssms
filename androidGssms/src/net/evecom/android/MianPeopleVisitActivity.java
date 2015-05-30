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
 * �߷ü�¼�б�
 * 
 * @author Mars zhang
 * 
 */
public class MianPeopleVisitActivity extends Activity {
    /** ���ذ�ť */
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
    /** ��Ա���� */
    private static final int MESSAGETYPE_01 = 0x0001;// �����ж��Ƿ��ͳɹ�
    /** ��Ա���� */
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
        // ����activityʱ���Զ����������
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
        // ��һ�����̼߳�����������
        Runnable runnable = new Runnable() {
            public void run() {
                // http://localhost:8080/fzaj_android/servlet/
                // EventlistServlet?id=ab000708-8288-4117-9dd8-083e02830b1e&flag=number

                String path = HttpUtil.BASE_URL + "teventAndroid/KeyPersonViewlist";
                String entry = "areaId=" + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0")
                        + "&offset=" + visits.size() + "&pageSize="
                        + ShareUtil.getString(getApplicationContext(), "PageSize", "pagesize", "15");
                // xmlwebData����������xml�е����� path�������ַ
                try {
                    visits = JsonMainPersonViewData.getData(path, entry);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                        handler.sendMessage(handler.obtainMessage(1, visits));
                    }
                }
                // ���������ʱ������Ϣ������list��϶��󴫵ݹ�ȥ
                handler.sendMessage(handler.obtainMessage(0, visits));
            }
        };

        try {
            // �����߳�
            new Thread(runnable).start();
            // handler���߳�֮���ͨ�ż����ݴ���
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(visits);
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
    public void BinderListData(ArrayList<T_PERSON_INTERVIEW> list) {
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
        Intent intent = new Intent(this, MianPeopleVisitSearchForMianPersonActivity.class);
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
     * ��ѯͨѶ¼ �������� Q_t1.INTERVIEWTYPE_S_LK=xs&Q_t3.PERSONNAME_S_LK=xm
     * 
     * @param v
     */
    public void search_for_contact_btn_onclick(View v) {
        // ��һ�����̼߳�����������
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
                // xmlwebData����������xml�е����� path�������ַ
                try {
                    visits = JsonMainPersonViewData.getData(path, entry);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                        handler.sendMessage(handler.obtainMessage(1, visits));
                    }
                }
                // ���������ʱ������Ϣ������list��϶��󴫵ݹ�ȥ
                handler.sendMessage(handler.obtainMessage(0, visits));
            }
        };

        try {
            // �����߳�
            new Thread(runnable).start();
            // handler���߳�֮���ͨ�ż����ݴ���
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(visits);
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
        private List<T_PERSON_INTERVIEW> list;

        /**
         * ���췽��������list���ݵľ�����һ�����ݵ���Ϣ
         * 
         * @param context
         * @param list
         */
        public MyAdapter(Context context, List<T_PERSON_INTERVIEW> list) {
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

                view = inflater.inflate(R.layout.mian_people_visit_list_item2, null);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MianPeopleVisitEditActivity.class);

                    intent.putExtra("PersonID", list.get(i).getPERSONID());
                    intent.putExtra("INTERVIEWID", list.get(i).getINTERVIEWID());
                    intent.putExtra("xmTV", list.get(i).getPERSONNAME());
                    intent.putExtra("sexTV", (list.get(i).getMALEDICTID().equals("1") ? "��" : "Ů"));
                    context.startActivity(intent);
                }
            });
            // {
            // "INTERVIEWEFFECT": "��ʵ��ʵ��ʵ��ʵ��ʵ��ʵ��",
            // "PERSONNAME": "������ʦ��",
            // "INTERVIEWTIME": "1985-08-05 11:00:17",
            // "INTERVIEWID": "141",
            // "INTERVIEWTYPE": "��ʵ��ʵ��ʵ��ʵ��ʵ��ʵ��",
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

            textViewname.setText("������" + list.get(i).getPERSONNAME());
            textViewsex.setText("�Ա�" + (list.get(i).getMALEDICTID().equals("1") ? "��" : "Ů"));
            textViewbirth.setText("�������ڣ�" + list.get(i).getBIRTH());
            textViewzfsj.setText("�߷�ʱ�䣺" + list.get(i).getINTERVIEWTIME());
            textViewzfxs.setText("�߷���ʽ��" + list.get(i).getINTERVIEWTYPE());
            textViewzfxg.setText("�߷�Ч����" + list.get(i).getINTERVIEWEFFECT());
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
