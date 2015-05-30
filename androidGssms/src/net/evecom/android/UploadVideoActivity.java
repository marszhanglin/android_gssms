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
 * 2014-7-22下午3:35:29 类UploadVideoActivity
 * 
 * @author Mars zhang
 * 
 */
public class UploadVideoActivity extends Activity implements OnClickListener {
    /** 上传成功 */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** 上传失败 */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** 左上方的返回按钮 */
    private Button mBtnBack;// 左上方的返回按钮
    /** 录像按钮 */
    private Button takeButton;// 录像按钮
    /** 视频本地选择按钮 */
    private Button pickButton;// 视频本地选择按钮
    /** 播放录像按钮 */
    private Button playButton;// 播放录像按钮
    /** 视频上传按钮 */
    private Button upButton;// 视频上传按钮
    /** 视频播放课件 */
    private VideoView videoView;// 视频播放课件
    /** 上传进度条 */
    private ProgressDialog progressDialog;// 上传进度条
    /** 事件id */
    private String id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadvideo);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putString("fileName", "");
        editor.commit();
    }

    public void initView() {
        mBtnBack = (Button) findViewById(R.id.uploadvideo_btn_back);
        mBtnBack.setOnClickListener(this);
        videoView = (VideoView) findViewById(R.id.uploadvideo_video_view);
        // /**
        // * 拍照按钮点击事件
        // */
        // takeButton = (Button) findViewById(R.id.uploadvideo_take_btn);
        // takeButton.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // // videoView.setVisibility(View.GONE);
        // Intent intent2 = new Intent();
        // intent2.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        // intent2.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // 设置为低质量
        // startActivityForResult(intent2, 1);
        // }
        // });

        // /**
        // * 视频本地选择按钮点击事件
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
        // Intent.createChooser(intent2, "请选择一个文件"), 2);
        // }
        // });

        // /**
        // * 视频播放按钮点击事件
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
        // "还未选择本地或拍摄视频\n 请拍摄或选择", 0).show();
        // return;
        // }
        // playVideo(path);
        // }
        // });

        // /**
        // * 视频上传按钮点击事件
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
        // "还未选择本地或拍摄视频\n 请拍摄或选择", 0).show();
        // return;
        // }
        // // System.out.println(submit(path));
        // }
        // });

        // /** 视频一进来就播放 */
        // SharedPreferences sp = getApplicationContext().getSharedPreferences(
        // "Video", MODE_PRIVATE);
        // String path = sp.getString("fileName", "none");
        // if (!isVideoFile(path)) {
        // Toast.makeText(getApplicationContext(), "还未选择本地或拍摄视频\n 请拍摄或选择", 0)
        // .show();
        // } else {
        // playVideo(path);
        // }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(resultCode);

        switch (requestCode) {
            case 1: // 从相机跳转回来
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData(); // 得到Uri
                    String fPath = uri2filePath(uri); // 转化为路径
                    // System.out.println(fPath);
                    int xm = 10;
                    File file = new File(fPath);
                    long fileLength = file.length();
                    if (null != file && fileLength / (1024 * 1024) > xm) {
                        Toast.makeText(this, "您视频文件超过10M,请重新拍摄视频或选择视频文件", Toast.LENGTH_SHORT).show();
                        file = null;
                        return;
                    }
                    file = null;
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
                    // 存入数据
                    Editor editor = sp.edit();
                    editor.putString("fileName", fPath);
                    editor.commit();
                }
                /** 视频播放 */
                playVideo(getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE).getString("fileName",
                        "none"));
                break;
            case 2: // 从本地选择器跳转回来
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    if (!isVideoFile(path)) {
                        Toast.makeText(this, "您选择的不是系统支持的视频文件,请选择一个视频文件", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        int xm = 100;
                        File file = new File(path);
                        long fileLength = file.length();
                        if (null != file && fileLength / (1024 * 1024) > xm) {
                            Toast.makeText(this, "您选择的视频文件超过10M,请重新选择视频文件", Toast.LENGTH_SHORT).show();
                            file = null;
                            return;
                        }
                        file = null;
                    }
                    // System.out.println(path);
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
                    // 存入数据
                    Editor editor = sp.edit();
                    editor.putString("fileName", path);
                    editor.commit();
                }
                /** 视频播放 */
                playVideo(getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE).getString("fileName",
                        "none"));

                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /** 拍 */
    public void uploadvideo_take_btn(View v) {
        Intent intent2 = new Intent();
        intent2.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent2.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // 设置为低质量0 高1
        intent2.addCategory("android.intent.category.DEFAULT");
        intent2.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 2 * 60);// 设置录像时间
        // intent2.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 9*1024);
        startActivityForResult(intent2, 1);
    }

    /** 本地 */
    public void uploadvideo_pick_btn(View v) {
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("*/*");
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent2, "请选择一个文件"), 2);
    }

    /** 播 */
    public void uploadvideo_play_btn(View v) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
        String path = sp.getString("fileName", "none");
        if (!isVideoFile(path)) {
            Toast.makeText(getApplicationContext(), "还未选择本地或拍摄视频\n 请拍摄或选择", 0).show();
            return;
        }
        playVideo(path);
    }

    /** 添加 */
    public void uploadvideo_up_btn(View v) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Video", MODE_PRIVATE);
        String path = sp.getString("fileName", "none");
        if (!isVideoFile(path)) {
            // Toast.makeText(getApplicationContext(),
            // "还未选择本地或拍摄视频,请拍摄或选择", 0).show();
            DialogToast("还未选择本地或拍摄视频,请拍摄或选择");
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

    /** 返回 */
    public void fh(View v) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // 点击手机返回按钮时操作
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return false;
    }

    /** 把Uri转化成文件路径 */
    private String uri2filePath(Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
        return path;
    }

    /**
     * 2014-4-1 判断文件路径是否为 .asf .mp4 .3gp .rm .rmvb .avi后缀
     * 
     * @param filePath
     *            文件路径
     * @return Boolean 是否为视频路径
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
     * 播放视频
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
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UploadVideoActivity.this);
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
    // /**
    // * 上传文件
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
    // .setTitle("请描述上传文件？")
    // .setPositiveButton("确定", new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia, int which) {
    // SharedPreferences sp = getApplicationContext()
    // .getSharedPreferences("Picture", MODE_PRIVATE);
    // // 存入数据
    // Editor editor = sp.edit();
    // editor.putString("description", description.getText()
    // .toString());
    // editor.commit();
    // builder.show();
    // dia.dismiss();// 关闭对话框
    // }
    // })
    // .setNegativeButton("取消", new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia, int which) {
    // builder.show();
    // dia.dismiss();// 关闭对话框
    // }
    // }).setView(login).create();
    // cusDia.show();
    //
    // // 上传文件
    // // AlertDialog.Builder builder = new
    // // AlertDialog.Builder(UploadVideoActivity.this);
    // builder.setTitle("提示信息");
    // builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
    // builder.setMessage("是否确定要上传现场视频？");
    // builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    // // @Override
    // public void onClick(DialogInterface dialog, int which) {
    //
    // progressDialog = ProgressDialog.show(UploadVideoActivity.this,
    // "提示", "正在上传，请稍等...");
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
    // // 把绝对路径的文件名拆分出相对路径
    // String s[] = Pattern.compile("/").split(filename);
    // try {
    // // 调用上传工具上传文件
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
    // builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    // // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // }
    // });
    // // builder.show();
    // return "1";
    // }

    // /**
    // * 进度条使用
    // */
    // private Handler handler = new Handler() {
    // public void handleMessage(Message message) {
    // switch (message.what) {
    // case MESSAGETYPE_01:
    // // 刷新UI，显示数据，并关闭进度条
    // progressDialog.dismiss(); // 关闭进度条
    // Toast.makeText(getApplicationContext(), "上传成功", 0).show();
    // break;
    // case MESSAGETYPE_02:
    // progressDialog.dismiss();
    // Toast.makeText(getApplicationContext(), "上传失败，请检查网络是否可用", 0)
    // .show();
    // break;
    // default:
    // break;
    //
    // }
    // }
    // };
}