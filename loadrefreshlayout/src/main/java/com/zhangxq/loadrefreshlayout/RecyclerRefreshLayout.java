package com.zhangxq.loadrefreshlayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zhangxq.loadrefreshlayout.base.RefreshLayout;

public class RecyclerRefreshLayout extends RefreshLayout {
    private RecyclerView mRecyclerView;

    /**
     * @param context
     */
    public RecyclerRefreshLayout(Context context) {
        this(context, null);
    }

    public RecyclerRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mRecyclerView == null) {
            findListView(this);
        }
    }

    private void findListView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() > 0) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View viewItem = viewGroup.getChildAt(i);
                    if (viewItem instanceof RecyclerView) {
                        mRecyclerView = (RecyclerView) viewItem;
                        if (mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter() instanceof LoadRecyclerAdapter) {
                            ((LoadRecyclerAdapter) mRecyclerView.getAdapter()).setFootView(footView);
                        }
                        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                if (isAutoLoad) {
                                    loadData();
                                }
                            }

                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
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
        if (mRecyclerView == null) {
            return false;
        } else {
            LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            int position = lm.findLastVisibleItemPosition();
            int count = lm.getItemCount();
            return position > count - 2;
        }
    }

    @Override
    protected void showLoadView(boolean isShow) {
        if (mRecyclerView != null && mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter() instanceof LoadRecyclerAdapter) {
            LoadRecyclerAdapter adapter = (LoadRecyclerAdapter) mRecyclerView.getAdapter();
            if (isShow) {
                adapter.showFootView(true);
            } else {
                adapter.showFootView(false);
            }
        }
    }
}