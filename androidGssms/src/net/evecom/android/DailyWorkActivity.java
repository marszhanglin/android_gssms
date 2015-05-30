/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import net.evecom.android.log.DailyLogListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 
 * 2014-7-22下午3:40:54 类ToolThreeActivity
 * 
 * @author Mars zhang
 * 
 */
public class DailyWorkActivity extends Activity implements OnClickListener {
    /** 返回按钮 */
    private Button mBtnBack;
    /** 成员变量 */
    private Handler handler = null;
    /** 成员变量 */
    private String flag;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_work);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
    }

    public void initView() {
        mBtnBack = (Button) findViewById(R.id.law_check_three_btn_back);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.law_check_three_btn_back: // mBtnBack.setOnClickListener(this);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 工作日志
     * 
     * @param v
     */
    public void daily_work_gzrz(View v) {
        Intent intent = new Intent(this, DailyLogListActivity.class);
        startActivity(intent);
    }

    /**
     * 
     * 人员走访
     * 
     * @param v
     */
    public void daily_work_ryzf(View v) {
        Intent intent = new Intent(this, MianPeopleVisitActivity.class);
        startActivity(intent);
    }

    /**
     * 日常检查
     * 
     * @param v
     */
    public void daily_work_rcjc(View v) {
        Intent intent = new Intent(this, MianPlaceCheckVisitActivity.class);
        startActivity(intent);
    }

    /**
     * 社情民意
     * 
     * @param v
     */
    public void daily_work_sqmy(View v) {
        // Intent intent = new Intent(ToolThreeActivity.this,
        // ContactGroupActivity.class);
        Intent intent = new Intent(DailyWorkActivity.this, PublicOpinionlistActivity.class);
        startActivity(intent);
    }

    /**
     * 返回
     */
    public void fh(View v) {
        this.finish();
    }

}