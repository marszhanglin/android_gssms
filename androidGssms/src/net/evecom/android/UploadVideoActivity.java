/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.File;

import net.evecom.android.uploadfile.FileUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * 
 * 2014-7-22����3:35:29 ��UploadVideoActivity
 * 
 * @author Mars zhang
 * 
 */
public class UploadVideoActivity extends Activity implements OnClickListener {
    /** �ϴ��ɹ� */
    private static final int MESSAGETYPE_01 = 0x0001;// �����ж��Ƿ��ͳɹ�
    /** �ϴ�ʧ�� */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** ���Ϸ��ķ��ذ�ť */
    private Button mBtnBack;// ���Ϸ��ķ��ذ�ť
    /** ¼��ť */
    private Button takeButton;// ¼��ť
    /** ��Ƶ����ѡ��ť */
    private Button pickButton;// ��Ƶ����ѡ��ť
    /** ����¼��ť */
    private Button playButton;// ����¼��ť
    /** ��Ƶ�ϴ���ť */
    private Button upButton;// ��Ƶ�ϴ���ť
    /** ��Ƶ���ſμ� */
    private VideoView videoView;// ��Ƶ���ſμ�
    /** �ϴ������� */
    private ProgressDialog progressDialog;// �ϴ�������
    /** �¼�id */
    private String id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadvideo);
        // ����activityʱ���Զ����������
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
        // ��������
        Editor editor = sp.edit();
        editor.putString("fileName", "");
        editor.commit();
    }

    public void initView() {
        mBtnBack = (Button) findViewById(R.id.uploadvideo_btn_back);
        mBtnBack.setOnClickListener(this);
        videoView = (VideoView) findViewById(R.id.uploadvideo_video_view);
        // /**
        // * ���հ�ť����¼�
        // */
        // takeButton = (Button) findViewById(R.id.uploadvideo_take_btn);
        // takeButton.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // // videoView.setVisibility(View.GONE);
        // Intent intent2 = new Intent();
        // intent2.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        // intent2.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // ����Ϊ������
        // startActivityForResult(intent2, 1);
        // }
        // });

        // /**
        // * ��Ƶ����ѡ��ť����¼�
        // */
        // pickButton = (Button) findViewById(R.id.uploadvideo_pick_btn);
        // pickButton.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // // videoView.setVisibility(View.GONE);
        // Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        // intent2.setType("*/*");
        // intent2.addCategory(Intent.CATEGORY_OPENABLE);
        // startActivityForResult(
        // Intent.createChooser(intent2, "��ѡ��һ���ļ�"), 2);
        // }
        // });

        // /**
        // * ��Ƶ���Ű�ť����¼�
        // */
        // playButton = (Button) findViewById(R.id.uploadvideo_play_btn);
        // playButton.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // SharedPreferences sp = getApplicationContext()
        // .getSharedPreferences("Video", MODE_PRIVATE);
        // String path = sp.getString("fileName", "none");
        // if (!isVideoFile(path)) {
        // Toast.makeText(getApplicationContext(),
        // "��δѡ�񱾵ػ�������Ƶ\n �������ѡ��", 0).show();
        // return;
        // }
        // playVideo(path);
        // }
        // });

        // /**
        // * ��Ƶ�ϴ���ť����¼�
        // */
        // upButton = (Button) findViewById(R.id.uploadvideo_up_btn);
        // upButton.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // SharedPreferences sp = getApplicationContext()
        // .getSharedPreferences("Video", MODE_PRIVATE);
        // String path = sp.getString("fileName", "none");
        // if (!isVideoFile(path)) {
        // Toast.makeText(getApplicationContext(),
        // "��δѡ�񱾵ػ�������Ƶ\n �������ѡ��", 0).show();
        // return;
        // }
        // // System.out.println(submit(path));
        // }
        // });

        // /** ��Ƶһ�����Ͳ��� */
        // SharedPreferences sp = getApplicationContext().getSharedPreferences(
        // "Video", MODE_PRIVATE);
        // String path = sp.getString("fileName", "none");
        // if (!isVideoFile(path)) {
        // Toast.makeText(getApplicationContext(), "��δѡ�񱾵ػ�������Ƶ\n �������ѡ��", 0)
        // .show();
        // } else {
        // playVideo(path);
        // }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(resultCode);

        switch (requestCode) {
            case 1: // �������ת����
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData(); // �õ�Uri
                    String fPath = uri2filePath(uri); // ת��Ϊ·��
                    // System.out.println(fPath);
                    int xm = 10;
                    File file = new File(fPath);
                    long fileLength = file.length();
                    if (null != file && fileLength / (1024 * 1024) > xm) {
                        Toast.makeText(this, "����Ƶ�ļ�����10M,������������Ƶ��ѡ����Ƶ�ļ�", Toast.LENGTH_SHORT).show();
                        file = null;
                        return;
                    }
                    file = null;
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
                    // ��������
                    Editor editor = sp.edit();
                    editor.putString("fileName", fPath);
                    editor.commit();
                }
                /** ��Ƶ���� */
                playVideo(getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE).getString("fileName",
                        "none"));
                break;
            case 2: // �ӱ���ѡ������ת����
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    if (!isVideoFile(path)) {
                        Toast.makeText(this, "��ѡ��Ĳ���ϵͳ֧�ֵ���Ƶ�ļ�,��ѡ��һ����Ƶ�ļ�", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        int xm = 100;
                        File file = new File(path);
                        long fileLength = file.length();
                        if (null != file && fileLength / (1024 * 1024) > xm) {
                            Toast.makeText(this, "��ѡ�����Ƶ�ļ�����10M,������ѡ����Ƶ�ļ�", Toast.LENGTH_SHORT).show();
                            file = null;
                            return;
                        }
                        file = null;
                    }
                    // System.out.println(path);
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
                    // ��������
                    Editor editor = sp.edit();
                    editor.putString("fileName", path);
                    editor.commit();
                }
                /** ��Ƶ���� */
                playVideo(getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE).getString("fileName",
                        "none"));

                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /** �� */
    public void uploadvideo_take_btn(View v) {
        Intent intent2 = new Intent();
        intent2.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent2.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // ����Ϊ������0 ��1
        intent2.addCategory("android.intent.category.DEFAULT");
        intent2.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 2 * 60);// ����¼��ʱ��
        // intent2.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 9*1024);
        startActivityForResult(intent2, 1);
    }

    /** ���� */
    public void uploadvideo_pick_btn(View v) {
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("*/*");
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent2, "��ѡ��һ���ļ�"), 2);
    }

    /** �� */
    public void uploadvideo_play_btn(View v) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
        String path = sp.getString("fileName", "none");
        if (!isVideoFile(path)) {
            Toast.makeText(getApplicationContext(), "��δѡ�񱾵ػ�������Ƶ\n �������ѡ��", 0).show();
            return;
        }
        playVideo(path);
    }

    /** ��� */
    public void uploadvideo_up_btn(View v) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
        String path = sp.getString("fileName", "none");
        if (!isVideoFile(path)) {
            // Toast.makeText(getApplicationContext(),
            // "��δѡ�񱾵ػ�������Ƶ,�������ѡ��", 0).show();
            DialogToast("��δѡ�񱾵ػ�������Ƶ,�������ѡ��");
            return;
        } else {
            // Intent intent = new Intent(UploadVideoActivity.this,
            // EventAddActivity.class);
            Intent intent = new Intent();
            intent.putExtra("filePath", path);
            setResult(R.layout.uploadvideo, intent);
            finish();
        }
    }

    /** ���� */
    public void fh(View v) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // ����ֻ����ذ�ťʱ����
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return false;
    }

    /** ��Uriת�����ļ�·�� */
    private String uri2filePath(Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
        return path;
    }

    /**
     * 2014-4-1 �ж��ļ�·���Ƿ�Ϊ .asf .mp4 .3gp .rm .rmvb .avi��׺
     * 
     * @param filePath
     *            �ļ�·��
     * @return Boolean �Ƿ�Ϊ��Ƶ·��
     */
    private Boolean isVideoFile(String filePath) {
        // String[] imageFormatSet = new String[] { "mp4","asf", "3gp", ".rm"
        // ,"mvb","avi"};
        if (null == filePath || filePath.length() < 4) {
            return false;
        }
        String[] imageFormatSet = new String[] { "mp4" };
        String endPath = filePath.substring(filePath.length() - 3, filePath.length());
        System.out.println(endPath);
        for (String item : imageFormatSet) {
            if (endPath.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * ������Ƶ
     * 
     * @param path
     */
    private void playVideo(String path) {
        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoPath(path);
        videoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // videoView.setVisibility(View.GONE);
            }
        });

        videoView.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uploadvideo_btn_back: // mBtnBack.setOnClickListener(this);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * �������ʾ��Ϣ
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UploadVideoActivity.this);
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
    private void toast(String strMsg) {
        Toast.makeText(getApplicationContext(), strMsg, 0).show();
    }
    // /**
    // * �ϴ��ļ�
    // */
    // private String submit(final String filename) {
    // final AlertDialog.Builder builder = new AlertDialog.Builder(
    // UploadVideoActivity.this);
    //
    // View login = LayoutInflater.from(this).inflate(R.layout.base_dialogin,
    // null);
    // final EditText description = (EditText) login
    // .findViewById(R.id.base_dialogin_edtext);
    // Dialog cusDia = new AlertDialog.Builder(this)
    // .setTitle("�������ϴ��ļ���")
    // .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia, int which) {
    // SharedPreferences sp = getApplicationContext()
    // .getSharedPreferences("Picture", MODE_PRIVATE);
    // // ��������
    // Editor editor = sp.edit();
    // editor.putString("description", description.getText()
    // .toString());
    // editor.commit();
    // builder.show();
    // dia.dismiss();// �رնԻ���
    // }
    // })
    // .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia, int which) {
    // builder.show();
    // dia.dismiss();// �رնԻ���
    // }
    // }).setView(login).create();
    // cusDia.show();
    //
    // // �ϴ��ļ�
    // // AlertDialog.Builder builder = new
    // // AlertDialog.Builder(UploadVideoActivity.this);
    // builder.setTitle("��ʾ��Ϣ");
    // builder.setIcon(R.drawable.qq_dialog_default_icon);// ͼ��
    // builder.setMessage("�Ƿ�ȷ��Ҫ�ϴ��ֳ���Ƶ��");
    // builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
    // // @Override
    // public void onClick(DialogInterface dialog, int which) {
    //
    // progressDialog = ProgressDialog.show(UploadVideoActivity.this,
    // "��ʾ", "�����ϴ������Ե�...");
    // new Thread() {
    // public void run() {
    // try {
    // Message msg_listData = new Message();
    // String furl = HttpUtil.BASE_URL
    // + "servlet/UploadFileServlet";
    // HashMap fhm = new HashMap();
    // File sfile = new File(filename);
    // Map sma = new HashMap();
    // // sma.put("sname",(String)fhm.get("fileName"));
    // // sma.put("fileDesc", "");
    // // sma.put("upid", "");gb
    // sma.put("tableid", id);
    // Map fma = new HashMap();
    // fma.put("file", sfile);
    // String sss = "";
    //
    // // �Ѿ���·�����ļ�����ֳ����·��
    // String s[] = Pattern.compile("/").split(filename);
    // try {
    // // �����ϴ������ϴ��ļ�
    // ArrayList<String> filenames = new ArrayList<String>();
    // filenames.add(s[s.length - 1]);
    // sss = PostFile.post(furl, sma, fma, filenames);
    // } catch (Exception e) {
    // }
    // if (sss == null || !sss.equals("1")) {
    // msg_listData.what = MESSAGETYPE_02;
    //
    // } else {
    // msg_listData.what = MESSAGETYPE_01;
    // }
    // handler.sendMessage(msg_listData);
    // } catch (Exception e) {
    // }
    // }
    //
    // }.start();
    //
    // }
    // });
    // builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
    // // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // }
    // });
    // // builder.show();
    // return "1";
    // }

    // /**
    // * ������ʹ��
    // */
    // private Handler handler = new Handler() {
    // public void handleMessage(Message message) {
    // switch (message.what) {
    // case MESSAGETYPE_01:
    // // ˢ��UI����ʾ���ݣ����رս�����
    // progressDialog.dismiss(); // �رս�����
    // Toast.makeText(getApplicationContext(), "�ϴ��ɹ�", 0).show();
    // break;
    // case MESSAGETYPE_02:
    // progressDialog.dismiss();
    // Toast.makeText(getApplicationContext(), "�ϴ�ʧ�ܣ����������Ƿ����", 0)
    // .show();
    // break;
    // default:
    // break;
    //
    // }
    // }
    // };
}