package com.hzrcht.seaofflowers.module.message.fragment;

import android.content.Intent;
import android.net.Uri;
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
import com.hzrcht.seaofflowers.module.mine.activity.MineChatActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserChargeBean;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class MessageFragment extends BaseBarFragment implements MessageViewer, View.OnClickListener {
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
        LinearLayout ll_online_service = bindView(R.id.ll_online_service);

        ll_mine_call.setOnClickListener(this);
        ll_system_message.setOnClickListener(this);
        ll_online_service.setOnClickListener(this);

        // 从布局文件中获取会话列表面板
        ConversationLayout conversationLayout = (ConversationLayout) findViewById(R.id.conversation_layout);
        // 会话列表面板的默认UI和交互初始化
        conversationLayout.initDefault();

        conversationLayout.getTitleBar().setVisibility(View.GONE);
        ConversationListLayout conversationList = conversationLayout.getConversationList();
        conversationList.enableItemRoundIcon(false);
        conversationList.setOnItemClickListener((view, position, messageInfo) -> mPresenter.getUserCharge(messageInfo.getId(), messageInfo));

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
            case R.id.ll_online_service:
                try {
                    //跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=2592974828";//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show("请检查是否安装QQ");
                }
                break;
        }
    }


    @Override
    public void getUserChargeSuccess(UserChargeBean userChargeBean, ConversationInfo messageInfo) {
        if (userChargeBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString("IM_ID", messageInfo.getId());
            bundle.putString("IM_NAME", messageInfo.getId());
            bundle.putString("LANG_AMOUNT", userChargeBean.lang_amount + "");
            getLaunchHelper().startActivity(MineChatActivity.class, bundle);
        }
    }
}
