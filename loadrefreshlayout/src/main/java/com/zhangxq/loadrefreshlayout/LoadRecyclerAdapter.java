package com.zhangxq.loadrefreshlayout;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by zhangxiaoqi on 2019/4/3.
 */

public abstract class LoadRecyclerAdapter extends RecyclerView.Adapter {
    private View footView;
    private int footerCount;
    private int dataSize;
    private Handler handler = new Handler();

    public void showFootView(boolean isShow) {
        if (isShow) {
            footerCount = 1;
        } else {
            footerCount = 0;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void setFootView(View footView) {
        this.footView = footView;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return onCreateItemViewHolder();
        } else {
            return new MyFooterViewHolder(footView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == 1 ? 1 : gridManager.getSpanCount();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof LoadRecyclerAdapter.MyFooterViewHolder)) {
            onBindItemViewHolder(holder, position);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateItemViewHolder();

    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return dataSize + footerCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == dataSize) {
            return -1;
        } else {
            return 1;
        }
    }

    class MyFooterViewHolder extends RecyclerView.ViewHolder {
        MyFooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
