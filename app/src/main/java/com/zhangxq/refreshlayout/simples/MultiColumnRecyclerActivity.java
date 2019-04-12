package com.zhangxq.refreshlayout.simples;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhangxq.loadrefreshlayout.LoadRecyclerAdapter;
import com.zhangxq.loadrefreshlayout.RecyclerRefreshLayout;
import com.zhangxq.loadrefreshlayout.base.OnLoadListener;
import com.zhangxq.refreshlayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiaoqi on 2019/4/12.
 */

public class MultiColumnRecyclerActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadListener {
    private RecyclerRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private List<String> datas = new ArrayList<>();
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);

        adapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        down();
    }

    private void down() {
        datas.clear();
        for (int i = 0; i < 30; i++) {
            datas.add("测试数据" + i);
        }
        adapter.setDataSize(datas.size());
    }

    private void up() {
        int size = datas.size();
        for (int i = size; i < size + 30; i++) {
            datas.add("测试数据" + i);
        }
        adapter.setDataSize(datas.size());
    }

    @Override
    public void onRefresh() {
        refreshLayout.setLoadEnable(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                down();
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                up();
                refreshLayout.setLoading(false);
            }
        }, 1000);
    }

    private class RecyclerAdapter extends LoadRecyclerAdapter {

        public RecyclerAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateItemViewHolder() {
            return new MyViewholder(LayoutInflater.from(MultiColumnRecyclerActivity.this).inflate(R.layout.view_list_item, null));
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewholder myViewholder = (MyViewholder) holder;
            myViewholder.tvTest.setText(datas.get(position));
            if (position % 2 == 0) {
                myViewholder.tvTest.setBackgroundColor(0xffabcdef);
            } else {
                myViewholder.tvTest.setBackgroundColor(0xffffffff);
            }
        }
    }

    private class MyViewholder extends RecyclerView.ViewHolder {
        TextView tvTest;

        MyViewholder(View itemView) {
            super(itemView);
            tvTest = itemView.findViewById(R.id.tvTest);
        }
    }
}
