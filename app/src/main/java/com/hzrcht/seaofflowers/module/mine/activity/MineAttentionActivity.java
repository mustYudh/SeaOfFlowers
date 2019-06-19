package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.adapter.MineAttentionRvAdapter;

import java.util.ArrayList;
import java.util.List;

public class MineAttentionActivity extends BaseBarActivity {
    private List<String> list = new ArrayList<>();

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_attention);
    }

    @Override
    protected void loadData() {
        setTitle("我的关注");
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        RecyclerView rv_attention = bindView(R.id.rv_attention);
        rv_attention.setLayoutManager(new LinearLayoutManager(getActivity()));
        MineAttentionRvAdapter adapter = new MineAttentionRvAdapter(R.layout.item_mine_attention, list, getActivity());
        rv_attention.setAdapter(adapter);
    }
}
