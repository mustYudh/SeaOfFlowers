package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.im.CustomMessageDraw;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineChatPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineChatViewer;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.component.NoticeLayout;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayoutUI;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class MineChatActivity extends BaseBarActivity implements MineChatViewer {
    @PresenterLifeCycle
    private MineChatPresenter mPresenter = new MineChatPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_chat_view);
    }

    @Override
    protected void loadData() {
        Bundle bundle = getIntent().getExtras();
        String im_id = bundle.getString("IM_ID");
        String im_name = bundle.getString("IM_NAME");

        //从布局文件中获取聊天面板组件
        ChatLayout mChatLayout = bindView(R.id.chat_layout);

        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();

        // TODO 通过api设置ChatLayout各种属性的样例
//        ChatLayoutHelper.customizeChatLayout(getActivity(), mChatLayout);

        /*
         * 需要聊天的基本信息
         */
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(TIMConversationType.C2C);
        chatInfo.setId(im_id);
        chatInfo.setChatName(im_name);
        mChatLayout.setChatInfo(chatInfo);

        //获取单聊面板的标题栏
        TitleBarLayout mTitleBar = mChatLayout.getTitleBar();
        mTitleBar.setVisibility(View.GONE);
        setTitle(im_name);

        //顶部通知
        NoticeLayout mNoticeLayout = mChatLayout.getNoticeLayout();
        mNoticeLayout.setBackgroundColor(getResources().getColor(R.color.white));
        // 可以使通知区域一致展示
        mNoticeLayout.alwaysShow(true);
        // 设置通知主题
        mNoticeLayout.getContent().setText("私聊每条扣0.1金币");
        // 设置通知提醒文字
        TextView contentExtra = mNoticeLayout.getContentExtra();
        contentExtra.setTextColor(getResources().getColor(R.color.red));
        contentExtra.setText("升级VIP免费畅玩");
        // 设置通知的点击事件
        mNoticeLayout.setOnNoticeClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toastShortMessage("赏白银五千两");
            }
        });

        //输入模块
        InputLayout inputLayout = mChatLayout.getInputLayout();
        inputLayout.disableEmojiInput(true);
        inputLayout.disableAudioInput(true);
        //送礼
        inputLayout.setOnPresentClickListener(new InputLayoutUI.OnPresentClickListener() {
            @Override
            public void onPresentClick(View view) {
                ToastUtils.show("点击了礼物");
//                Gson gson = new Gson();
//                CustomMessageData customMessageData = new CustomMessageData();
//                String data = gson.toJson(customMessageData);
                String data = "http://hdbos.test.upcdn.net/DS/image/20180123/biipcq38ul7zk4ytjrvk.png,测试发送礼物礼物,140";
                MessageInfo info = MessageInfoUtil.buildCustomMessage(data);
                mChatLayout.sendMessage(info, false);
            }
        });

        //视频
        inputLayout.setOnVideoClickListener(new InputLayoutUI.OnVideoClickListener() {
            @Override
            public void onVideoClick(View view) {
                ToastUtils.show("点击了视频");
            }
        });

        //渲染自定义消息
//        MessageInfo info = MessageInfoUtil.buildCustomMessage("{\"text\": \"这是一个带链接的测试消息，可点击查看\",\"url\": \"https://cloud.tencent.com\"}");
        MessageLayout messageLayout = mChatLayout.getMessageLayout();
        // 设置自定义的消息渲染时的回调
        messageLayout.setOnCustomMessageDrawListener(new CustomMessageDraw());

    }
}