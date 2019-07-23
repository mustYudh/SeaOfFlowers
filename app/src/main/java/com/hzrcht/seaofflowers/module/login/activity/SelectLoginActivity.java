package com.hzrcht.seaofflowers.module.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SelectLoginPresenter;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SelectLoginViewer;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.hzrcht.seaofflowers.module.login.bean.WechatInfo;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;
import com.yu.share.AuthLoginHelp;
import com.yu.share.callback.AuthLoginCallback;

import java.util.Map;

public class SelectLoginActivity extends BaseActivity implements View.OnClickListener, AuthLoginCallback, SelectLoginViewer {
    @PresenterLifeCycle
    private SelectLoginPresenter mPresenter = new SelectLoginPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_login);
    }

    private AuthLoginHelp mAuthLoginHelp;

    @Override
    protected void loadData() {
        mAuthLoginHelp = new AuthLoginHelp(getActivity());
        mAuthLoginHelp.callback(this);
        DelayClickTextView tv_mobile = bindView(R.id.tv_mobile);
        DelayClickTextView tv_wechat = bindView(R.id.tv_wechat);
        DelayClickTextView tv_qq = bindView(R.id.tv_qq);

        tv_mobile.setOnClickListener(this);
        tv_wechat.setOnClickListener(this);
        tv_qq.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mobile:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), 1);
                break;
            case R.id.tv_wechat:
                boolean installWeChat =
                        UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN);
                if (installWeChat) {
                    mAuthLoginHelp.login(SHARE_MEDIA.WEIXIN);
                } else {
                    ToastUtils.show("请先安装微信");
                }
                break;
            case R.id.tv_qq:
                boolean installQQ = UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.QQ);
                if (installQQ) {
                    mAuthLoginHelp.login(SHARE_MEDIA.QQ);
                } else {
                    ToastUtils.show("请先安装QQ");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                int type = data.getIntExtra("type", 0);
                if (type == 0) {
                    startActivityForResult(new Intent(getActivity(), SelectGenderActivity.class), 1);
                } else {
                    getLaunchHelper().startActivity(HomePageActivity.class);
                    finish();
                }
                break;
            case 2:
                getLaunchHelper().startActivity(HomePageActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onStart(SHARE_MEDIA media) {

    }

    @Override
    public void onComplete(SHARE_MEDIA media, int i, Map<String, String> map) {
        String uuid = map.get("uid");
        String type = "";
        if (media == SHARE_MEDIA.QQ) {
            type = "QQ";
        } else if (media == SHARE_MEDIA.WEIXIN) {
            type = "wx";
            WechatInfo wechatInfo = new WechatInfo();
            wechatInfo.openid = map.get("openid");
            wechatInfo.city = map.get("city");
            wechatInfo.nickname = map.get("name");
            wechatInfo.language = map.get("language");
            wechatInfo.province = map.get("province");
            wechatInfo.country = map.get("country");
            wechatInfo.headimgurl = map.get("iconurl");
            wechatInfo.unionid = map.get("unionid");
            wechatInfo.sex = "男".equals(map.get("gender")) ? "1" : "0";

            Gson gson = new Gson();
//            mPresenter.loginWechat(map.get("access_token"),map.get("openid"));
            mPresenter.loginThird("1", map.get("unionid"), map.get("openid"), gson.toJson(wechatInfo));
            Log.e("三方登录", map.values().toString() + "..." + map.keySet().toString() + "..." + gson.toJson(wechatInfo));


        }

    }

    @Override
    public void onError(SHARE_MEDIA media, int i, Throwable throwable) {
        Log.e("三方登录", i + "");
    }

    @Override
    public void onCancel(SHARE_MEDIA media, int i) {
        Log.e("三方登录", i + "");
    }

    @Override
    public void getWxInfoSuccess(LoginBean loginBean) {
        if (loginBean != null) {
            if (loginBean.info != null) {
                UserProfile.getInstance().setToken(loginBean.token);
                UserProfile.getInstance().appLogin(loginBean);
                UserProfile.getInstance().setAnchorType(loginBean.info.type);
                if (loginBean.info.sex == 0) {
                    //设置性别
                    startActivityForResult(new Intent(getActivity(), SelectGenderActivity.class), 1);
                } else {
                    //已经设置过了
                    loadDialog.show();
                    loginIm(loginBean);
                }
            }
        }
    }


    private void loginIm(LoginBean loginBean) {
        // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(loginBean.info.id + "", loginBean.info.userSig + "", new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e("im", "登录失败了....." + code + "..." + desc);
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                UserProfile.getInstance().setApplogin(false);
            }

            @Override
            public void onSuccess() {
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                UserProfile.getInstance().setUserSig(loginBean.info.userSig);
                UserProfile.getInstance().setApplogin(true);
                Log.e("im", "登录成功了");
                getLaunchHelper().startActivity(HomePageActivity.class);
                finish();
            }
        });
    }
}
