/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/**
 * 
 * 2014-7-22下午4:05:46 海康监控 类HCnetActivity
 * 
 * @author Mars zhang
 * 
 */
public class HCnetActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hcnet);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * fh
     * 
     * @param v
     */
    public void fh(View v) {

    }
    // /**
    // * 加载网络xml
    // *
    // * @author zhanglin
    // *
    // */
    // public class XmlwebData {
    // /** MemberVariables */
    // private ArrayList<PictureManageBean> list = null;
    //
    // public ArrayList<PictureManageBean> getData(final String path) {
    // try {
    // URL url = new URL(path);
    // PictureManageBean image_ = null;
    // HttpURLConnection conn = (HttpURLConnection) url
    // .openConnection();
    // conn.setRequestMethod("GET");
    // conn.setConnectTimeout(5000);
    // if (conn.getResponseCode() == 200) {
    // InputStream inputstream = conn.getInputStream();
    // XmlPullParser xml = Xml.newPullParser();
    // xml.setInput(inputstream, "UTF-8");
    // int event = xml.getEventType();
    // while (event != XmlPullParser.END_DOCUMENT) {
    // switch (event) {
    // // 开始解析文档
    // case XmlPullParser.START_DOCUMENT:
    // list = new ArrayList<PictureManageBean>();
    // break;
    // case XmlPullParser.START_TAG:
    // String value = xml.getName();
    // if (value.equals("image")) {
    // image_ = new PictureManageBean();
    // } else if (value.equals("ImageURI")) {
    // image_.setPicture_URL(HttpUtil.BASE_URL
    // // + "/attachFiles/CALAMITIES_EVENT/"
    // + xml.nextText());
    // } else if (value.equals("Picture_by1")) {
    //
    // image_.setPicture_by1(xml.nextText());// 备用字段用来储存服务器端的图片数据对应的id
    // }
    //
    // break;
    // case XmlPullParser.END_TAG:
    // if (xml.getName().equals("image")) {
    // list.add(image_);
    // // image_ = null;
    // }
    // break;
    // default:
    // break;
    // }
    // // 解析下一个对象
    // event = xml.next();
    // }
    // return list;
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // return null;
    //
    // }
    //
    // }

    // /**成功 */
    // private static final int MESSAGETYPE_01 = 0x0001;//
    // /**失败 */
    // private static final int MESSAGETYPE_02 = 0x0002;
    // final Message msg_listData = new Message();
    // msg_listData.what=MESSAGETYPE_01;
    // handler4.sendMessage(msg_listData);
    // /**线程管理者*/
    // private Handler handler4 = new Handler() {
    // public void handleMessage(Message message) {
    // switch (message.what) {
    // case MESSAGETYPE_01:
    // break;
    // case MESSAGETYPE_02:
    // break;
    // default:
    // break;
    // }
    // }
    // };

    // /**
    // * ListView配器类
    // *
    // * @author Mars zhang
    // *
    // */
    // public class UploadPictureAdapter extends BaseAdapter implements
    // ListAdapter {
    // /** MemberVariables */
    // private Context context;
    // /** MemberVariables */
    // private LayoutInflater inflater;
    // /** MemberVariables */
    // private List<PictureManageBean> list;
    //
    // public UploadPictureAdapter(Context context,
    // ArrayList<PictureManageBean> list) {
    // this.context = context;
    // inflater = LayoutInflater.from(context);
    // this.list = list;
    // }
    //
    // @Override
    // public int getCount() {
    // return list == null ? 0 : list.size();
    //
    // }
    //
    // @Override
    // public Object getItem(int item) {
    // return this.list.get(item);
    // }
    //
    // @Override
    // public long getItemId(int itemId) {
    // return itemId;
    // }
    //
    // @Override
    // public View getView(final int i, View view, ViewGroup viewGroup) {
    // if (null == view) {
    // view = inflater.inflate(R.layout.search_for_qy_list_item, null);
    // }
    // TextView textViewTitle = (TextView) view
    // .findViewById(R.id.search_for_qy_list_item_tv);
    // String s[] = Pattern.compile("/").split(
    // list.get(i).getPicture_URL());
    // textViewTitle.setText("" + s[s.length - 1]);
    // view.setOnClickListener(new View.OnClickListener() {
    // @Override
    // public void onClick(View arg0) {
    // toast(list.get(i).getPicture_URL());
    // Intent intent = new Intent(getApplicationContext(),
    // AfnailPictureActivity.class);
    // intent.putExtra("URI", list.get(i).getPicture_URL());
    // startActivity(intent);
    // }
    // });
    // return view;
    // }
    // }

    // /**
    // * 含文件修改提交 你懂的
    // */
    // private String submit() {
    // final AlertDialog.Builder builder = new AlertDialog.Builder(
    // AddDangerListEditActivity.this);
    // builder.setTitle("提示信息");
    // builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
    // builder.setMessage("是否确定要保存隐患记录？");
    // builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    // // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // progressDialog = ProgressDialog.show(
    // AddDangerListEditActivity.this, "提示", "正在上传，请稍等...");
    // try {
    // final Message msg_listData = new Message();
    // String furl = HttpUtil.BASE_URL
    // + "servlet/HiddenDangerServlet?flag=UPHiddendangerAndfile_update_file"
    // + "&hiddenDangerid=" + hiddenid;
    // formSubmit();
    // AjaxParams params = new AjaxParams();
    // int i = 0;
    // for (PictureManageBean picture : pictures) {
    // if ("0".equals(picture.getPicture_Flag())) { // 只上传本地图片
    // params.put("profile_picture" + (i++), new File(
    // picture.getPicture_URL())); // 上传文件
    // }
    //
    // }
    // FinalHttp fh = new FinalHttp();
    // if (i > 0) {
    // fh.post(furl, params, new AjaxCallBack<String>() {
    // @Override
    // public void onLoading(long count, long current) {
    // System.out.println(current + "/" + count);
    // }
    //
    // @Override
    // public void onFailure(Throwable t, int errorNo,
    // String strMsg) {
    // super.onFailure(t, errorNo, strMsg);
    // System.out.println("onFailure");
    // }
    //
    // @Override
    // public void onSuccess(String t) {
    // super.onSuccess(t);
    // System.out.println(t == null ? "null" : t
    // .toString());
    // if (t == null || t.equals("")) {
    // msg_listData.what = MESSAGETYPE_02;
    // } else {
    // msg_listData.what = MESSAGETYPE_01;
    // SharedPreferences sp = getSharedPreferences(
    // "Dangers_submit", MODE_PRIVATE);
    // Editor editor = sp.edit();
    // editor.putString("dangerids",
    // sp.getString("dangerids", "") + "@"
    // + t);
    // System.out.println(sp.getString(
    // "dangerids", ""));
    // editor.commit();
    // }
    //
    // }
    // });
    // }
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // }
    //
    // private void formSubmit() {
    // new Thread() {
    // public void run() {
    // try {
    // Message msg_listData = new Message();
    // String furl = HttpUtil.BASE_URL
    // + "servlet/HiddenDangerServlet?flag=UPHiddendangerAndfile_update_form"
    // + "&hiddenDangerid=" + hiddenid;
    // HashMap fhm = new HashMap();
    // Map sma = new HashMap();
    // sma.put("AQYH", aqyhEditText.getText().toString());
    // if ("重大隐患"
    // .equals(yhjbEditText.getText().toString())) {
    // sma.put("YHJB",
    // "402883e74517048f0145171084ea0005");
    // } else if ("一般隐患".equals(yhjbEditText.getText()
    // .toString())) {
    // sma.put("YHJB",
    // "402883e74517048f01451710396b0003");
    // }
    // if ("生产设备设施".equals(yhlbEditText.getText()
    // .toString())) {
    // sma.put("YHLB",
    // "402883e7456d605101456e5abf880024");
    // } else if ("特种设备现场管理".equals(yhlbEditText.getText()
    // .toString())) {
    // sma.put("YHLB",
    // "402883e7456d605101456e5b25450026");
    // } else if ("资质管理".equals(yhlbEditText.getText()
    // .toString())) {
    // sma.put("YHLB",
    // "402883e7456d605101456e5a0af30020");
    // } else if ("安全生产管理机构及人员".equals(yhlbEditText
    // .getText().toString())) {
    // sma.put("YHLB",
    // "402883e7456d605101456e5a5f060022");
    // }
    // sma.put("ZGSX", zgsxDateBtn.getText().toString());
    // sma.put("FCSX", fcsxDateBtn.getText().toString());
    // sma.put("FCDD", yhddEditText.getText().toString());
    // sma.put("FCBW", yhbwEditText.getText().toString());
    // sma.put("FCMS", yhmsEditText.getText().toString());
    // sma.put("HIDDEN_TYPE", jclx_tpye);
    // String sss = "";
    // try {
    // sss = PostFile.post(furl, sma, null, null);
    // } catch (Exception e) {
    // msg_listData.what = MESSAGETYPE_02;
    // }
    // if (sss == null) {
    // msg_listData.what = MESSAGETYPE_02;
    // } else {
    // msg_listData.what = MESSAGETYPE_01;
    // }
    // handler.sendMessage(msg_listData);
    // } catch (Exception e) {
    // }
    // }
    // }.start();
    // }
    // });
    // builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    // // @Override
    // public void onClick(DialogInterface dialog, int which) {
    //
    // dialog.dismiss();
    // }
    // });
    // builder.show();
    // return "1";
    // }

    // /**
    // * 错误填报提示信息
    // *
    // * @param errorMsg
    // */
    // private void DialogToast(String errorMsg) {
    // AlertDialog.Builder builder1 = new AlertDialog.Builder(
    // AllDangerListEditActivity.this);
    // builder1.setTitle("提示信息");
    // builder1.setIcon(R.drawable.qq_dialog_default_icon);// 图标
    // builder1.setMessage("" + errorMsg);
    // builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    // // @Override
    // public void onClick(DialogInterface dialog, int which) {
    //
    // }
    // });
    // builder1.show();
    // }

    //
    // /** 土司 */
    // private void toast(String strMsg) {
    // Toast.makeText(getApplicationContext(), strMsg, 0).show();
    // }
}
