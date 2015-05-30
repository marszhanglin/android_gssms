/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

/**
 * 
 * 2014-7-22下午4:06:14 类Exit
 * 
 * @author Mars zhang
 * 
 */
public class Exit extends Activity {
    /** MemberVariables */
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_dialog);
        // dialog=new MyDialog(this);
        layout = (LinearLayout) findViewById(R.id.exit_layout);
        layout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void exitbutton1(View v) {
        this.finish();
    }

    public void exitbutton0(View v) {
        this.finish();
        MainOneActivity.instance.finish();// 关闭Main 这个Activity

    }

}
