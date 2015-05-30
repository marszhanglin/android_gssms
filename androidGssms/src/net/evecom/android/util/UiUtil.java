/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 2014-5-28 ����9:05:27 UiUtil������
 * 
 * @author Mars zhang
 * 
 */
public class UiUtil {
    /**
     * 
     * ��̬����ListView�ĸ߶� ���scrollView��Ƕ��listView����ʾȫ��item
     * һ��Adapter��getView�������ص�View�ı�����LinearLayout���
     * ����Ϊֻ��LinearLayout����measure()���� ��ΪListView������Adapter֮��ʹ�� �����������߶Ⱦ�����Ļ�ĸ߶�
     * 
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
