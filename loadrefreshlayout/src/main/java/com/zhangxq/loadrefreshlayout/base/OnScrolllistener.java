package com.zhangxq.loadrefreshlayout.base;

import android.widget.AbsListView;

/**
 * 由于内部监听了OnScrollListener，导致外部监听滚动事件不起作用，采用接口方式将onScroll事件传出去
 */
public interface OnScrolllistener {
    void onScroll(AbsListView absListView, int i, int i1, int i2);
    void onScrollStateChanged(AbsListView absListView, int i);
}