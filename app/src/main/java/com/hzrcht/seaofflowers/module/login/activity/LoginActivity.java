package com.hzrcht.seaofflowers.module.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.login.activity.presenter.LoginPresenter;
import com.hzrcht.seaofflowers.module.login.activity.presenter.LoginViewer;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.hzrcht.seaofflowers.module.view.ClearEditText;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.countdown.RxCountDownAdapter;
import com.yu.common.login.LoginRedirectHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;

/**
 * @author yudenghao
 */
public class LoginActivity extends BaseBarActivity implements LoginViewer {
    private RxCountDown countDown;

    public static boolean pFlag = false;
    @PresenterLifeCycle
    private LoginPresenter mPresenter = new LoginPresenter(this);
    private DelayClickTextView tv_get_code;

    public static Intent callRedirectOtherActionIntent(Context context, String targetOther, Bundle bundle) {
        return LoginRedirectHelper.setRedirectData(context, LoginActivity.class, bundle, "",
                targetOther);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_login_view_bar;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        pFlag = true;
        setContentView(R.layout.activity_login_view);
    }

    @Override
    protected void loadData() {
        ClearEditText et_phone = bindView(R.id.et_phone);
        ClearEditText et_code = bindView(R.id.et_code);
        tv_get_code = bindView(R.id.tv_get_code);
        DelayClickTextView tv_login = bindView(R.id.tv_login);
        tv_get_code.setOnClickListener(view -> {
            if (TextUtils.isEmpty(et_phone.getText())) {
                ToastUtils.show("手机号输入不能为空");
            } else if (!et_phone.getText().toString().startsWith("1") || et_phone.getText().toString().length() != 11) {
                ToastUtils.show("检查手机号输入是否正确");
            } else {
                tv_get_code.setClickable(false);
                mPresenter.sendVerCode(et_phone.getText().toString(), countDown, tv_get_code);
            }
        });

        tv_login.setOnClickListener(view -> {
            if (TextUtils.isEmpty(et_phone.getText())) {
                ToastUtils.show("手机号输入不能为空");
                return;
            }
            if (!et_phone.getText().toString().startsWith("1") || et_phone.getText().toString().length() != 11) {
                ToastUtils.show("检查手机号输入是否正确");
                return;
            }
            if (TextUtils.isEmpty(et_code.getText())) {
                ToastUtils.show("验证码不能为空");
                return;
            }

            mPresenter.phoneLogin(et_phone.getText().toString(), et_code.getText().toString());
        });
    }

    @Override
    public Bundle getLoginExtraBundle() {
        return LoginRedirectHelper.getLoginExtraBundle(getActivity());

    }

    @Override
    public String getRedirectOtherAction() {
        return LoginRedirectHelper.getRedirectOtherAction(getActivity());
    }

    @Override
    public String getRedirectActivityClassName() {
        return LoginRedirectHelper.getRedirectActivityClassName(getActivity());
    }

    @Override
    protected void onPause() {
        super.onPause();
        pFlag = false;
    }

    @Override
    public void sendVerCodeSuccess() {
        countDown = new RxCountDown();
        countDown.start(60);
        countDown.setCountDownTimeListener(new RxCountDownAdapter() {

            @Override
            public void onStart() {
                super.onStart();
                tv_get_code.setClickable(false);
                tv_get_code.setText("60S");
            }

            @Override
            public void onNext(Integer time) {
                super.onNext(time);
                if (time == 0) {
                    tv_get_code.setClickable(true);
                    tv_get_code.setText("发送验证码");
                } else {
                    tv_get_code.setText(time + "S");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                tv_get_code.setClickable(true);
                tv_get_code.setText("发送验证码");
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
        ToastUtils.show("发送成功");
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        if (loginBean != null) {
            if (loginBean.info != null) {
                UserProfile.getInstance().setToken(loginBean.token);
                UserProfile.getInstance().appLogin(loginBean);
                UserProfile.getInstance().setAnchorType(loginBean.info.type);
                if (loginBean.info.sex == 0) {
                    //设置性别
                    Intent intent = new Intent();
                    intent.putExtra("type", 0);
                    setResult(1, intent);
                    finish();
                } else {
                    //已经设置过了
                    Intent intent = new Intent();
                    intent.putExtra("type", 1);
                    setResult(1, intent);
                    finish();
                }
            }
        }
    }
}
