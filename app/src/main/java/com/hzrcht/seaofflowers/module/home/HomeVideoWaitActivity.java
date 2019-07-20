package com.hzrcht.seaofflowers.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.event.DataSynVideoWaitEvent;
import com.hzrcht.seaofflowers.module.home.bean.HomePayCoinBean;
import com.hzrcht.seaofflowers.module.home.presenter.HomeVideoWaitPresenter;
import com.hzrcht.seaofflowers.module.home.presenter.HomeVideoWaitViewer;
import com.hzrcht.seaofflowers.module.im.CustomMessageData;
import com.hzrcht.seaofflowers.module.mine.activity.MineRechargeActivity;
import com.hzrcht.seaofflowers.module.mine.activity.TRTCMainActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMValueCallBack;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class HomeVideoWaitActivity extends BaseActivity implements HomeVideoWaitViewer {

    @PresenterLifeCycle
    private HomeVideoWaitPresenter mPresenter = new HomeVideoWaitPresenter(this);
    private String nick_name = "";
    private String head_img = "";
    private String age = "0";
    private String is_attent = "0";
    private CircleImageView iv_headimg;
    private ImageView iv_bottom;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_video_wait_view);
    }

    @Override
    protected void loadData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        iv_headimg = bindView(R.id.iv_headimg);
        iv_bottom = bindView(R.id.iv_bottom);
        //注册EventBus
        EventBus.getDefault().register(this);

        Bundle bundle = getIntent().getExtras();
        String content = bundle.getString("CONTENT");
        if (content != null && !TextUtils.isEmpty(content)) {
            String[] split = content.split(",");

            //拒绝视频邀请
            bindView(R.id.iv_close, view -> {
                loadDialog.show();
                mPresenter.liveEnd(split,0);
            });

            //加入视频聊天
            bindView(R.id.iv_open, view -> {
                mPresenter.livePayCoin(split);
            });
            mPresenter.getIsAnchor(split[0] + "");
        } else {
            bindView(R.id.iv_open, view -> {
                ToastUtils.show("视频出问题了");
                finish();
            });

            bindView(R.id.iv_close, view -> {
                finish();
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(DataSynVideoWaitEvent event) {
        finish();
    }

    @Override
    public void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean) {
        if (userIsAnchorBean != null) {
            nick_name = userIsAnchorBean.nick_name;
            head_img = userIsAnchorBean.head_img;
            age = userIsAnchorBean.age;
            is_attent = userIsAnchorBean.is_attent + "";

            bindText(R.id.tv_name, userIsAnchorBean.nick_name);

            ImageLoader.getInstance().displayImage(iv_headimg, userIsAnchorBean.head_img, userIsAnchorBean.sex == 1 ? R.drawable.ic_man_normal : R.drawable.ic_woman_normal);
            ImageLoader.getInstance().displayImage(iv_bottom, userIsAnchorBean.head_img, userIsAnchorBean.sex == 1 ? R.drawable.ic_man_normal : R.drawable.ic_woman_normal);
        }
    }

    @Override
    public void livePayCoinSuccess(HomePayCoinBean homePayCoinBean, String[] split) {
        Bundle bundleVideo = new Bundle();
        bundleVideo.putString("USER_ID", split[0] + "");
        bundleVideo.putString("HEAD_IMG", head_img);
        bundleVideo.putString("NICK_NAME", nick_name);
        bundleVideo.putString("USER_AGE", age);
        bundleVideo.putString("IS_ATTENT", is_attent);
        bundleVideo.putString("LIVE_ID", split[4] + "");
        bundleVideo.putString("TYPE_IN", "0");
        getLaunchHelper().startActivity(TRTCMainActivity.class, bundleVideo);
        finish();
    }

    @Override
    public void livePayCoinFail(int code, String msg, String[] split) {
        ToastUtils.show(msg);
        loadDialog.show();
        mPresenter.liveEnd(split, code);
    }


    @Override
    public void liveEndSuccess(String[] split, int rechargeCode) {
        TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, split[0]);//会话类型：单聊
        CustomMessageData customMessageData = new CustomMessageData();
        customMessageData.type = "2";
        customMessageData.content = "no_accept";
        Gson gson = new Gson();
        String toJson = gson.toJson(customMessageData);

        //构造一条消息
        TIMMessage msg = new TIMMessage();

        //向 TIMMessage 中添加自定义内容
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(toJson.getBytes());      //自定义 byte[]

        //将 elem 添加到消息
        if (msg.addElement(elem) != 0) {
            return;
        }

        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                Log.e("自定义消息发送", "send message failed. code: " + code + " errmsg: " + desc + "..." + toJson);
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                if (rechargeCode == 10000) {
                    //充值
                    Bundle bundleVip = new Bundle();
                    bundleVip.putInt("TYPE", 1);
                    getLaunchHelper().startActivityForResult(MineRechargeActivity.class, bundleVip, 1);
                }
                finish();
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("自定义消息发送", "SendMsg ok....." + toJson);
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                if (rechargeCode == 10000) {
                    //充值
                    Bundle bundleVip = new Bundle();
                    bundleVip.putInt("TYPE", 1);
                    getLaunchHelper().startActivityForResult(MineRechargeActivity.class, bundleVip, 1);
                }
                finish();
            }
        });

    }

    @Override
    public void liveEndFail(String msg, int rechargeCode) {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        ToastUtils.show(msg);
        if (rechargeCode == 10000) {
            //充值
            Bundle bundleVip = new Bundle();
            bundleVip.putInt("TYPE", 1);
            getLaunchHelper().startActivityForResult(MineRechargeActivity.class, bundleVip, 1);
        }
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
