/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 
 * 2014-7-22下午3:35:29 类UploadVideoActivity
 * 
 * @author Mars zhang
 * 
 */
public class UploadAudioActivity extends Activity implements OnClickListener {
    // 语音文件保存路径
    /** MemberVariables */
    private String FileName = null;
    /** MemberVariables */
    private boolean isplaying = false;
    /** MemberVariables */
    private boolean isrecording = false;
    // 语音操作对象
    /** MemberVariables */
    private MediaPlayer mPlayer = null;
    /** MemberVariables */
    private MediaRecorder mRecorder = null;
    /** MemberVariables */
    private boolean hasRecord = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadaudio);
        // 设置Cache的路径
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
        String filename = dateFormat.format(new Date());
        FileName += "/audiorecord" + filename + ".3gp";
    }

    /** 播放 或停止播放 */
    public void play(View v) {
        if (isAudioFile(FileName)) {
            if (!isplaying) {
                if (isrecording) {
                    isrecording = false;// 停止录音
                    if (null != mRecorder) {
                        toast("停止录音");
                        mRecorder.stop();
                        mRecorder.release();
                        mRecorder = null;
                    }
                }
                isplaying = true;
                mPlayer = new MediaPlayer();
                try {
                    toast("开始播放");
                    mPlayer.setDataSource(FileName);
                    mPlayer.prepare();
                    mPlayer.start();
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            toast("播放结束");
                            isplaying = false;
                        }
                    });
                } catch (IOException e) {
                }
            } else {
                toast("停止播放");
                isplaying = false;
                mPlayer.release();
                mPlayer = null;
            }
        }
    }

    /** 录音 */
    public void recording(View v) {
        hasRecord = true;
        isrecording = true;
        if (isplaying) {// 当还在播放时 、关闭播放
            toast("重新开始录音！");
            isplaying = false;
            mPlayer.release();
            mPlayer = null;
        }
        toast("开始录音");
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(FileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            System.out.println(e);
        }
        mRecorder.start();
    }

    /** 停止录音 */
    public void stop_recording(View v) {
        toast("停止录音");
        isrecording = false;
        if (null != mRecorder) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    /**
     * 添加
     * 
     * @param path
     */
    public void add(View v) {
        toast("停止播放");
        isplaying = false;
        if (null != mPlayer) {
            mPlayer.release();
        }
        mPlayer = null;
        if (!hasRecord) {
            FileName = "";
        }
        // Intent intent = new Intent(UploadAudioActivity.this,
        // EventAddActivity.class);
        Intent intent = new Intent();
        intent.putExtra("filePath", FileName);
        setResult(R.layout.uploadpictrue, intent);
        finish();
    }

    /**
     * fh
     * 
     * @param path
     */
    public void fh(View v) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
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

    /**
     * 开始录音 2014-8-3下午5:00:33 类startRecordListener
     * 
     * @author Mars zhang
     * 
     */
    class startRecordListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 3gp格式
            mRecorder.setOutputFile(FileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
            }
            mRecorder.start();
        }

    }

    // 停止录音
    /** MemberVariables */
    class stopRecordListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

    }

    // 播放录音
    /** MemberVariables */
    class startPlayListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(FileName);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
            }
        }

    }

    // 停止播放录音
    /** MemberVariables */
    class stopPlayListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPlayer.release();
            mPlayer = null;
        }

    }

    /**
     * 播放音频
     * 
     * @param path
     */
    private void playAudio(String path) {
        if (null == path || path.length() < 4) {
            return;
        }
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {

        }
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UploadAudioActivity.this);
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

    /**
     * 2014-4-1 判断文件路径是否为 .mp3后缀
     * 
     * @param filePath
     *            文件路径
     * @return Boolean 是否为视频路径
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
}