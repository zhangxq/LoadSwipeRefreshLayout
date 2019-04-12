package com.zhangxq.loadrefreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhangxq.loadrefreshlayout.base.OnScrolllistener;
import com.zhangxq.loadrefreshlayout.base.RefreshLayout;

public class ListRefreshLayout extends RefreshLayout {
    private ListView mListView;
    private OnScrolllistener onScrolllistener;

    /**
     * @param context
     */
    public ListRefreshLayout(Context context) {
        this(context, null);
    }

    public ListRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            findListView(this);
        }
    }

    private void findListView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() > 0) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View viewItem = viewGroup.getChildAt(i);
                    if (viewItem instanceof ListView) {
                        mListView = (ListView) viewItem;
                        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {
                                if (onScrolllistener != null) {
                                    onScrolllistener.onScrollStateChanged(absListView, i);
                                }
                            }

                            @Override
                            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                                if (isAutoLoad) {
                                    loadData();
                                }
                                if (onScrolllistener != null) {
                                    onScrolllistener.onScroll(absListView, i, i1, i2);
                                }
                            }
                        });
                        return;
                    } else {
                        findListView(viewItem);
                    }
                }
            }
        }
    }

    /**
     * 判断是否到了最底部
     */
    @Override
    protected boolean isReachBottom() {
        if (mListView == null) {
            return false;
        }
        int position = mListView.getLastVisiblePosition();
        int count = 0;
        if (mListView.getAdapter() != null) {
            count = mListView.getAdapter().getCount();
        }
        return position == count - 1;
    }

    @Override
    protected void showLoadView(boolean isShow) {
        if (mListView == null) return;
        if (isShow) {
            mListView.addFooterView(footView);
        } else {
            mListView.removeFooterView(footView);
        }
    }

    public void setCrollListener(OnScrolllistener listener) {
        this.onScrolllistener = listener;
    }
}