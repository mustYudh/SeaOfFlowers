package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.PhoneVerificationPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.PhoneVerificationViewer;
import com.hzrcht.seaofflowers.module.view.ClearEditText;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.countdown.RxCountDownAdapter;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;


public class PhoneVerificationActivity extends BaseBarActivity implements PhoneVerificationViewer {
    private DelayClickTextView tv_get_code;
    private RxCountDown countDown;
    @PresenterLifeCycle
    private PhoneVerificationPresenter mPresenter = new PhoneVerificationPresenter(this);
    private ClearEditText et_phone;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_phone_verification_view);
    }

    @Override
    protected void loadData() {
        setTitle("手机号验证");
        et_phone = bindView(R.id.et_phone);
        ClearEditText et_code = bindView(R.id.et_code);
        tv_get_code = bindView(R.id.tv_get_code);
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

        bindView(R.id.tv_commit, view -> {
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

            mPresenter.bindUserPhone(et_phone.getText().toString(), et_code.getText().toString());
        });
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
    public void bindUserPhoneSuccess() {
        Intent intent = new Intent();
        intent.putExtra("MOBILE", et_phone.getText().toString().trim());
        setResult(1, intent);
        finish();
    }
}
