package com.hzrcht.seaofflowers.module.message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarFragment;
import com.hzrcht.seaofflowers.module.message.activity.MineCallActivity;
import com.hzrcht.seaofflowers.module.message.activity.SystemMessageActivity;
import com.hzrcht.seaofflowers.module.message.adapter.MessageRvAdapter;
import com.hzrcht.seaofflowers.module.message.fragment.presenter.MessagePresenter;
import com.hzrcht.seaofflowers.module.message.fragment.presenter.MessageViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends BaseBarFragment implements MessageViewer, View.OnClickListener {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private MessagePresenter mPresenter = new MessagePresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_message_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.fragment_message_bar;
    }

    @Override
    protected void loadData() {
        setTitle("消息");
        LinearLayout ll_mine_call = bindView(R.id.ll_mine_call);
        LinearLayout ll_system_message = bindView(R.id.ll_system_message);

        ll_mine_call.setOnClickListener(this);
        ll_system_message.setOnClickListener(this);
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        RecyclerView rv_msg = bindView(R.id.rv_msg);
        rv_msg.setLayoutManager(new LinearLayoutManager(getActivity()));
        MessageRvAdapter adapter = new MessageRvAdapter(R.layout.item_mine_message, list, getActivity());
        rv_msg.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_mine_call:
                getLaunchHelper().startActivity(MineCallActivity.class);
                break;
            case R.id.ll_system_message:
                getLaunchHelper().startActivity(SystemMessageActivity.class);
                break;
        }
    }
}
