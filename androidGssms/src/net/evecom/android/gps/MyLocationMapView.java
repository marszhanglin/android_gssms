/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.gps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupOverlay;

/**
 * 继承MapView重写onTouchEvent实现泡泡处理操作 2014-7-22下午4:37:50 类MyLocationMapView
 * 
 * @author Mars zhang
 * 
 */
public class MyLocationMapView extends MapView {
    /** MemberVariables */
    public static PopupOverlay pop = null;// 弹出泡泡图层，点击图标使用

    public MyLocationMapView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyLocationMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLocationMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!super.onTouchEvent(event)) {
            // 消隐泡泡
            if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
                pop.hidePop();
        }
        return true;
    }
}
