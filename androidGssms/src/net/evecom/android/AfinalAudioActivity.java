/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.IOException;

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
 * 2014-7-22����3:35:29 ��UploadVideoActivity
 * 
 * @author Mars zhang
 * 
 */
public class AfinalAudioActivity extends Activity {
    /** ��Ƶ���ſμ� */
    private VideoView videoView;// ��Ƶ���ſμ�
    /** �ļ�filepath */
    private String filepath;
    /** �ļ�File_Id */
    private int File_Id = -1;
    /** ���ݿ� */
    private FinalDb db;
    /** ����״̬ */
    private boolean isplaying = false;
    /** ������������ */
    private MediaPlayer mPlayer = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        // ����activityʱ���Զ����������
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // ��ȡ�¼��б�����id����
        Intent intent = getIntent();
        filepath = intent.getStringExtra("URI");
        File_Id = intent.getIntExtra("File_Id", -1);
        videoView = (VideoView) findViewById(R.id.uploadvideo_video_view);
        /** ��Ƶһ�����Ͳ��� */
        if (!isAudioFile(filepath)) {
            Toast.makeText(getApplicationContext(), "���ļ�������Ƶ�뷵������ѡ��", 0).show();
        } else {
            playAudio(filepath);
        }
    }

    /** ���� */
    public void fh(View v) {
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

    /** ɾ�� */
    public void delete(View v) {
        if (-1 == File_Id) {
            return;
        }
        db = FinalDb.create(this);
        db.deleteById(FileManageBean.class, File_Id);
        Intent intent = new Intent();
        setResult(4, intent);
        finish();
    }

    /** ���� */
    public void play(View v) {
        if (!isVideoFile(filepath)) {
            Toast.makeText(getApplicationContext(), "���ļ�������Ƶ�뷵������ѡ��", 0).show();
        } else {
            playAudio(filepath);
        }
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

    /** ���� ��ֹͣ���� */
    public void playAudio(String path) {
        if (isAudioFile(path)) {
            if (!isplaying) {
                isplaying = true;
                mPlayer = new MediaPlayer();
                try {
                    toast("��ʼ����");
                    mPlayer.setDataSource(path);
                    mPlayer.prepare();
                    mPlayer.start();
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            toast("���Ž���");
                            isplaying = false;
                        }
                    });
                } catch (IOException e) {
                }
            } else {
                toast("ֹͣ����");
                isplaying = false;
                mPlayer.release();
                mPlayer = null;
            }
        }
    }

    /**
     * 2014-4-1 �ж��ļ�·���Ƿ�Ϊ .mp3��׺
     * 
     * @param filePath
     *            �ļ�·��
     * @return Boolean �Ƿ�Ϊ��Ƶ·��
     */
    private Boolean isAudioFile(String filePath) {
        // String[] imageFormatSet = new String[] { "mp3","asf", "3gp", ".rm"
        // ,"mvb","avi"};
        if (null == filePath || filePath.length() < 4) {
            return false;
        }
        String[] imageFormatSet = new String[] { "3gp" };
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
     * �������ʾ��Ϣ
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AfinalAudioActivity.this);
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

}