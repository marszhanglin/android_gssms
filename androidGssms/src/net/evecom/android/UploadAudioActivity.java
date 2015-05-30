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
 * 2014-7-22����3:35:29 ��UploadVideoActivity
 * 
 * @author Mars zhang
 * 
 */
public class UploadAudioActivity extends Activity implements OnClickListener {
    // �����ļ�����·��
    /** MemberVariables */
    private String FileName = null;
    /** MemberVariables */
    private boolean isplaying = false;
    /** MemberVariables */
    private boolean isrecording = false;
    // ������������
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
        // ����Cache��·��
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
        String filename = dateFormat.format(new Date());
        FileName += "/audiorecord" + filename + ".3gp";
    }

    /** ���� ��ֹͣ���� */
    public void play(View v) {
        if (isAudioFile(FileName)) {
            if (!isplaying) {
                if (isrecording) {
                    isrecording = false;// ֹͣ¼��
                    if (null != mRecorder) {
                        toast("ֹͣ¼��");
                        mRecorder.stop();
                        mRecorder.release();
                        mRecorder = null;
                    }
                }
                isplaying = true;
                mPlayer = new MediaPlayer();
                try {
                    toast("��ʼ����");
                    mPlayer.setDataSource(FileName);
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

    /** ¼�� */
    public void recording(View v) {
        hasRecord = true;
        isrecording = true;
        if (isplaying) {// �����ڲ���ʱ ���رղ���
            toast("���¿�ʼ¼����");
            isplaying = false;
            mPlayer.release();
            mPlayer = null;
        }
        toast("��ʼ¼��");
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

    /** ֹͣ¼�� */
    public void stop_recording(View v) {
        toast("ֹͣ¼��");
        isrecording = false;
        if (null != mRecorder) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    /**
     * ���
     * 
     * @param path
     */
    public void add(View v) {
        toast("ֹͣ����");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) { // ����ֻ����ذ�ťʱ����
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return false;
    }

    /**
     * ��ʼ¼�� 2014-8-3����5:00:33 ��startRecordListener
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
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 3gp��ʽ
            mRecorder.setOutputFile(FileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
            }
            mRecorder.start();
        }

    }

    // ֹͣ¼��
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

    // ����¼��
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

    // ֹͣ����¼��
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
     * ������Ƶ
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
     * �������ʾ��Ϣ
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UploadAudioActivity.this);
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
}