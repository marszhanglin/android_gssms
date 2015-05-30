/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 
 * 2014-8-25上午8:42:24 类AfnailPictureFromWebActivity
 * 
 * @author Mars zhang
 * 
 */
public class AfnailPictureFromWebActivity extends Activity {
    /** MemberVariables */
    private ImageView imageView;
    /** MemberVariables */
    private FinalBitmap fb;
    /** MemberVariables */
    private String URI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_view_from_web);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        imageView = (ImageView) findViewById(R.id.test1_picture);
        Intent intent = getIntent();
        URI = intent.getStringExtra("URI");
        fb = FinalBitmap.create(this);
        fb.display(imageView, URI);
    }

    /**
     * 返回按钮点击事件
     * 
     * @param v
     */
    public void fh(View v) { // 标题栏 返回按钮
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
