package com.hzrcht.seaofflowers.module.mine.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.im.CustomMessageDraw;
import com.hzrcht.seaofflowers.module.mine.activity.bean.LiveStartBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysGiftBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineChatPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineChatViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineSysGiftRvAdapter;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.component.NoticeLayout;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.CustomMessageData;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;

import java.math.BigDecimal;
import java.util.List;

@SuppressLint("SetTextI18n")
public class MineChatActivity extends BaseBarActivity
        implements MineChatViewer {
    @PresenterLifeCycle
    private MineChatPresenter mPresenter = new MineChatPresenter(this);
    private DialogUtils presentDialog;
    private String im_id;
    private NoticeLayout mNoticeLayout;
    private InputLayout inputLayout;
    private ChatLayout mChatLayout;
    private MessageLayout messageLayout;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_chat_view);
    }

    @Override
    protected void loadData() {
        Bundle bundle = getIntent().getExtras();
        im_id = bundle.getString("IM_ID");
        //从布局文件中获取聊天面板组件
        mChatLayout = bindView(R.id.chat_layout);

        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();

        /*
         * 需要聊天的基本信息
         */
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(TIMConversationType.C2C);
        chatInfo.setId(im_id);
        chatInfo.setChatName(im_id);
        mChatLayout.setChatInfo(chatInfo);

        //获取单聊面板的标题栏
        TitleBarLayout mTitleBar = mChatLayout.getTitleBar();
        mTitleBar.setVisibility(View.GONE);

        //顶部通知
        mNoticeLayout = mChatLayout.getNoticeLayout();
        mNoticeLayout.setBackgroundColor(getResources().getColor(R.color.white));

        mPresenter.getIsAnchor(im_id);

        //输入模块
        inputLayout = mChatLayout.getInputLayout();
        //        inputLayout.disableEmojiInput(true);
        inputLayout.disableAudioInput(true);
        inputLayout.disableMoreInput(true);
        inputLayout.hideSoftInput();

        //送礼
        inputLayout.setOnPresentClickListener(view -> mPresenter.getSysGift(mChatLayout));

        //渲染自定义消息
        messageLayout = mChatLayout.getMessageLayout();
        // 设置自定义的消息渲染时的回调
        messageLayout.setOnCustomMessageDrawListener(new CustomMessageDraw());

        messageLayout.setAvatarRadius(50);
        messageLayout.setAvatarSize(new int[]{40, 40});
//        messageLayout.setAvatar(R.drawable.ic_app_logo);
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                messageLayout.setRightAvatar(resource);
                messageLayout.setLeftAvatar(resource);

            }
        };
        Glide.with(getActivity()).load(UserProfile.getInstance().getUserImg()).into(simpleTarget);

        messageLayout.setOnItemClickListener(new MessageLayout.OnItemClickListener() {
            @Override
            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
                //长按消息    messageInfo消息载体
                mChatLayout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view);
            }

            @Override
            public void onUserIconClick(View view, int position, MessageInfo messageInfo) {

            }
        });
        messageLayout.setLoadMoreMessageHandler(() -> mChatLayout.loadMessages());

        messageLayout.setEmptySpaceClickListener(() -> inputLayout.hideSoftInput());
    }

    private SysGiftBean.ResultBean item = null;

    /**
     * 礼物列表
     */
    private void showPresentDialog(BigDecimal user_amount, List<SysGiftBean.ResultBean> rows,
                                   ChatLayout mChatLayout) {
        presentDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_present)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .build();
        presentDialog.show();

        RecyclerView rv_present = presentDialog.findViewById(R.id.rv_present);
        rv_present.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        //        CommItemDecoration horizontal = CommItemDecoration.createHorizontal(getActivity(), Color.parseColor("#626262"), 2);
        //        CommItemDecoration vertical = CommItemDecoration.createVertical(getActivity(), Color.parseColor("#626262"), 2);
        //        rv_present.addItemDecoration(horizontal);
        //        rv_present.addItemDecoration(vertical);
        TextView tv_coin = presentDialog.findViewById(R.id.tv_coin);
        tv_coin.setText("可用金币：" + user_amount);
        DelayClickTextView tv_commit = presentDialog.findViewById(R.id.tv_commit);
        DelayClickTextView tv_recharge = presentDialog.findViewById(R.id.tv_recharge);
        tv_recharge.setOnClickListener(view -> {
            if (presentDialog.isShowing()) {
                presentDialog.dismiss();
            }
            //充值
            Bundle bundle = new Bundle();
            bundle.putInt("TYPE", 0);
            getLaunchHelper().startActivity(MineRechargeActivity.class, bundle);
        });
        MineSysGiftRvAdapter adapter =
                new MineSysGiftRvAdapter(R.layout.item_sys_present, rows, getActivity());
        rv_present.setAdapter(adapter);

        adapter.setOnItemChcekCheckListener(resultBean -> {
            adapter.notifyDataSetChanged();
            item = resultBean;
        });
        tv_commit.setOnClickListener(view -> {
            if (presentDialog.isShowing()) {
                presentDialog.dismiss();
            }

            if (item != null) {
                mPresenter.sendGift(im_id, item.id + "", mChatLayout, item);
            }
        });
    }

    @Override
    public void getSysGiftSuccess(SysGiftBean sysGiftBean, ChatLayout mChatLayout) {
        if (sysGiftBean != null) {
            if (sysGiftBean.rows != null && sysGiftBean.rows.size() != 0) {
                showPresentDialog(sysGiftBean.user_amount, sysGiftBean.rows, mChatLayout);
            }
        } else {
            ToastUtils.show("获取礼物列表失败,请重试");
        }
    }

    @Override
    public void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean) {
        if (userIsAnchorBean != null) {
            //视频
            inputLayout.setOnVideoClickListener(
                    view -> mPresenter.liveStart(im_id + "", userIsAnchorBean));

            setTitle(userIsAnchorBean.nick_name);
        }

        //判断自己是否是vip
        if (UserProfile.getInstance().getUserVip()) {
            //是vip 畅聊
            inputLayout.mSendTextButton.setOnClickListener(view -> {
                if (inputLayout.mSendEnable) {
                    if (inputLayout.mMessageHandler != null) {
                        inputLayout.mMessageHandler.sendMessage(MessageInfoUtil.buildTextMessage(
                                inputLayout.mTextInput.getText().toString().trim()));
                    }
                    inputLayout.mTextInput.setText("");
                }
            });
        } else {
            if (userIsAnchorBean != null) {
                //判断自己是否是主播
                if (UserProfile.getInstance().getAnchorType() == 1) {
                    //自己非vip自己是主播
                    if (userIsAnchorBean.is_anchor == 1) {
                        //对方是主播
                        mNoticeLayout.alwaysShow(true);
                        // 设置通知主题
                        mNoticeLayout.getContent().setText("私聊每条扣" + userIsAnchorBean.lang_amount + "金币");
                        // 设置通知提醒文字
                        TextView contentExtra = mNoticeLayout.getContentExtra();
                        contentExtra.setTextColor(getResources().getColor(R.color.red));
                        contentExtra.setText("升级VIP免费畅玩");
                        // 设置通知的点击事件
                        mNoticeLayout.setOnNoticeClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt("TYPE", 1);
                            getLaunchHelper().startActivity(MineRechargeActivity.class, bundle);
                        });

                        //开启扣费
                        inputLayout.mSendTextButton.setOnClickListener(view -> {
                            if (inputLayout.mSendEnable) {
                                mPresenter.chatStart(im_id, inputLayout.mTextInput.getText().toString().trim());
                            }
                        });
                    } else {
                        //对方不是主播
                        inputLayout.mSendTextButton.setOnClickListener(view -> {
                            if (inputLayout.mSendEnable) {
                                if (inputLayout.mMessageHandler != null) {
                                    inputLayout.mMessageHandler.sendMessage(MessageInfoUtil.buildTextMessage(
                                            inputLayout.mTextInput.getText().toString().trim()));
                                }
                                inputLayout.mTextInput.setText("");
                            }
                        });
                    }
                } else {
                    //自己非vip自己不是主播
                    if (userIsAnchorBean.is_anchor == 1) {
                        //对方是主播
                        mNoticeLayout.alwaysShow(true);
                        // 设置通知主题
                        mNoticeLayout.getContent().setText("私聊每条扣" + userIsAnchorBean.lang_amount + "金币");
                        // 设置通知提醒文字
                        TextView contentExtra = mNoticeLayout.getContentExtra();
                        contentExtra.setTextColor(getResources().getColor(R.color.red));
                        contentExtra.setText("升级VIP免费畅玩");
                        // 设置通知的点击事件
                        mNoticeLayout.setOnNoticeClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt("TYPE", 1);
                            getLaunchHelper().startActivity(MineRechargeActivity.class, bundle);
                        });

                        //开启扣费
                        inputLayout.mSendTextButton.setOnClickListener(view -> {
                            if (inputLayout.mSendEnable) {
                                mPresenter.chatStart(im_id, inputLayout.mTextInput.getText().toString().trim());
                            }
                        });
                    } else {
                        //对方不是主播

                    }
                }
            }
        }
    }

    @Override
    public void chatStartSuccess() {
        if (inputLayout.mMessageHandler != null) {
            inputLayout.mMessageHandler.sendMessage(
                    MessageInfoUtil.buildTextMessage(inputLayout.mTextInput.getText().toString().trim()));
        }
        inputLayout.mTextInput.setText("");
    }

    @Override
    public void sendGiftSuccess(ChatLayout mChatLayout, SysGiftBean.ResultBean resultBean) {
        ToastUtils.show("打赏成功!");
        CustomMessageData customMessageData = new CustomMessageData();
        customMessageData.type = "0";
        customMessageData.content = resultBean.img + "," + resultBean.title + "," + resultBean.price;
        Gson gson = new Gson();
        String toJson = gson.toJson(customMessageData);
        MessageInfo info = MessageInfoUtil.buildCustomMessage(toJson);
        mChatLayout.sendMessage(info, false);
        item = null;
    }

    @Override
    public void liveStartSuccess(LiveStartBean liveStartBean, UserIsAnchorBean userIsAnchorBean) {
        if (liveStartBean != null) {
            Bundle bundleVideo = new Bundle();
            bundleVideo.putString("USER_ID", im_id + "");
            bundleVideo.putString("HEAD_IMG", userIsAnchorBean.head_img + "");
            bundleVideo.putString("NICK_NAME", userIsAnchorBean.nick_name + "");
            bundleVideo.putString("USER_AGE", userIsAnchorBean.age + "");
            bundleVideo.putString("IS_ATTENT", userIsAnchorBean.is_attent + "");
            bundleVideo.putString("LIVE_ID", liveStartBean.live_id + "");
            bundleVideo.putString("TYPE_IN", "1");
            getLaunchHelper().startActivity(TRTCMainActivity.class, bundleVideo);
        } else {
            ToastUtils.show("开启视频出错!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CurrentChatMember.getInstance().setUserId("");
        mChatLayout.exitChat();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(im_id)) {
            CurrentChatMember.getInstance().setUserId(im_id);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        CurrentChatMember.getInstance().setUserId("");
    }
}
