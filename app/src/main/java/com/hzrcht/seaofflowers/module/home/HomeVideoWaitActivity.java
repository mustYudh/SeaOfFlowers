package com.hzrcht.seaofflowers.module.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.hzrcht.seaofflowers.utils.permissions.MorePermissionsCallBack;
import com.hzrcht.seaofflowers.utils.permissions.PermissionManager;
import com.tbruyelle.rxpermissions2.Permission;
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

import java.util.ArrayList;
import java.util.List;


public class HomeVideoWaitActivity extends BaseActivity implements HomeVideoWaitViewer {

    @PresenterLifeCycle
    private HomeVideoWaitPresenter mPresenter = new HomeVideoWaitPresenter(this);
    private String nick_name = "";
    private String head_img = "";
    private String age = "0";
    private String is_attent = "0";
    private CircleImageView iv_headimg;
    private ImageView iv_bottom;
    private MediaPlayer mMediaPlayer;
    private final static int REQ_PERMISSION_CODE = 0x1000;

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

        //播放背景音乐
        mMediaPlayer = MediaPlayer.create(this, R.raw.receive);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        //对MediaPlayer对象添加事件监听，当播放完成时重新开始音乐播放
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play();
            }
        });


        Bundle bundle = getIntent().getExtras();
        String content = bundle.getString("CONTENT");
        if (content != null && !TextUtils.isEmpty(content)) {
            String[] split = content.split(",");

            //拒绝视频邀请
            bindView(R.id.iv_close, view -> {
                loadDialog.show();
                mPresenter.liveEnd(split, 0);
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

    //播放音乐的方法
    private void play() {
        try {
            mMediaPlayer.reset();//从新设置要播放的音乐
            mMediaPlayer = MediaPlayer.create(this, R.raw.receive);
            mMediaPlayer.start();//播放音乐
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
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

    /**
     * 检查权限
     */
    private boolean checkPermission() {
        //检测权限
        PermissionManager.getInstance(this).checkMorePermission(new MorePermissionsCallBack() {
                                                                    @Override
                                                                    protected void permissionGranted(Permission permission) {
                                                                        // 用户已经同意该权限
                                                                    }

                                                                    @Override
                                                                    protected void permissionShouldShowRequestPermissionRationale(Permission permission) {
                                                                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                                                    }

                                                                    @Override
                                                                    protected void permissionRejected(Permission permission) {

                                                                    }
                                                                },
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(getActivity(),
                        (String[]) permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        ToastUtils.show("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
    }
}
