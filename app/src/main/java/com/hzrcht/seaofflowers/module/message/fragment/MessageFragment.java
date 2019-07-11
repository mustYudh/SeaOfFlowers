package com.hzrcht.seaofflowers.module.message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarFragment;
import com.hzrcht.seaofflowers.module.message.activity.MineCallActivity;
import com.hzrcht.seaofflowers.module.message.activity.SystemMessageActivity;
import com.hzrcht.seaofflowers.module.message.fragment.presenter.MessagePresenter;
import com.hzrcht.seaofflowers.module.message.fragment.presenter.MessageViewer;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends BaseBarFragment implements MessageViewer, View.OnClickListener {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private MessagePresenter mPresenter = new MessagePresenter(this);
    private DialogUtils cleanDialog;

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
        TextView tv_clean = bindView(R.id.tv_clean);
        LinearLayout ll_mine_call = bindView(R.id.ll_mine_call);
        LinearLayout ll_system_message = bindView(R.id.ll_system_message);

        ll_mine_call.setOnClickListener(this);
        ll_system_message.setOnClickListener(this);
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
//        RecyclerView rv_msg = bindView(R.id.rv_msg);
//        rv_msg.setLayoutManager(new LinearLayoutManager(getActivity()));
//        MessageRvAdapter adapter = new MessageRvAdapter(R.layout.item_mine_message, list, getActivity());
//        rv_msg.setAdapter(adapter);

        // 从布局文件中获取会话列表面板
        ConversationLayout conversationLayout = (ConversationLayout) findViewById(R.id.conversation_layout);
        // 会话列表面板的默认UI和交互初始化
        conversationLayout.initDefault();

        conversationLayout.getTitleBar().setVisibility(View.GONE);


        tv_clean.setOnClickListener(view -> {
            showCleanDialog();
        });
    }

    /**
     * 清空
     */
    private void showCleanDialog() {
        cleanDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_layout)
                .gravity(Gravity.CENTER)
                .cancelTouchout(true)
                .style(R.style.Dialog_NoAnimation)
                .addViewOnclick(R.id.cancle, view -> {
                    if (cleanDialog.isShowing()) {
                        cleanDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.down, view -> {
                    if (cleanDialog.isShowing()) {
                        cleanDialog.dismiss();
                    }

                })
                .build();
        cleanDialog.show();


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
