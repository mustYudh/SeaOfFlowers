package com.hzrcht.seaofflowers.module.message.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.message.activity.presenter.SystemMessagePresenter;
import com.hzrcht.seaofflowers.module.message.activity.presenter.SystemMessageViewer;
import com.hzrcht.seaofflowers.module.message.adapter.SystemMessageRvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class SystemMessageActivity extends BaseBarActivity implements SystemMessageViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private SystemMessagePresenter presenter = new SystemMessagePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_system_message_view);
    }

    @Override
    protected void loadData() {
        setTitle("系统消息");
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        RecyclerView rv_system_message = bindView(R.id.rv_system_message);
        rv_system_message.setLayoutManager(new LinearLayoutManager(getActivity()));
        SystemMessageRvAdapter adapter = new SystemMessageRvAdapter(R.layout.item_system_message, list, getActivity());
        rv_system_message.setAdapter(adapter);

    }
}
