/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import net.evecom.android.bean.FileManageBean;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * 
 * 2014-7-22下午3:35:29 类UploadVideoActivity
 * 
 * @author Mars zhang
 * 
 */
public class AfinalVideoActivity extends Activity {
    /** 视频播放课件 */
    private VideoView videoView;// 视频播放课件
    /** 文件filepath */
    private String filepath;
    /** 文件File_Id */
    private int File_Id = -1;
    /** 数据库 */
    private FinalDb db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // 获取事件列表传来的id数据
        Intent intent = getIntent();
        filepath = intent.getStringExtra("URI");
        File_Id = intent.getIntExtra("File_Id", -1);
        videoView = (VideoView) findViewById(R.id.uploadvideo_video_view);
        /** 视频一进来就播放 */
        if (!isVideoFile(filepath)) {
            Toast.makeText(getApplicationContext(), "该文件不是视频请返回重新选择！", 0).show();
        } else {
            playVideo(filepath);
        }
    }

    /** 返回 */
    public void fh(View v) {
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

    /** 删除 */
    public void delete(View v) {
        if (-1 == File_Id) {
            return;
        }
        db = FinalDb.create(this);
        db.deleteById(FileManageBean.class, File_Id);
        // Intent intent = new Intent(this, EventAddActivity.class);
        Intent intent = new Intent();
        setResult(4, intent);
        finish();
    }

    /** 播放 */
    public void play(View v) {
        if (!isVideoFile(filepath)) {
            Toast.makeText(getApplicationContext(), "该文件不是视频请返回重新选择！", 0).show();
        } else {
            playVideo(filepath);
        }
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
        String[] imageFormatSet = new String[] { "mp4", "3gp" };
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

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AfinalVideoActivity.this);
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