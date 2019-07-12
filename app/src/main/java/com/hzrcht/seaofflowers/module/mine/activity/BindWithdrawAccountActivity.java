package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.BindWithdrawAccountPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.BindWithdrawAccountViewer;
import com.hzrcht.seaofflowers.module.view.ClearEditText;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class BindWithdrawAccountActivity extends BaseBarActivity implements BindWithdrawAccountViewer {

    @PresenterLifeCycle
    private BindWithdrawAccountPresenter mPresenter = new BindWithdrawAccountPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_withdraw_account_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_bind_withdraw_account_view_bar;
    }

    @Override
    protected void loadData() {
        Bundle bundle = getIntent().getExtras();
        int type = bundle.getInt("TYPE");
        String numtype = bundle.getString("NUMTYPE");
        String nametype = bundle.getString("NAMETYPE");
        ClearEditText et_name = bindView(R.id.et_name);
        ClearEditText et_account = bindView(R.id.et_account);
        if (type == 1) {
            //支付宝
            bindText(R.id.tv_type, "支付宝账号");
        } else {
            //微信
            bindText(R.id.tv_type, "微信账号");
        }

        et_name.setText(nametype);
        et_name.setSelection(et_name.getText().toString().trim().length());
        et_account.setText(numtype);
        et_account.setSelection(et_account.getText().toString().trim().length());

        bindView(R.id.tv_commit, view -> {
            if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                ToastUtils.show("您的真实姓名不能为空");
                return;
            }

            if (TextUtils.isEmpty(et_account.getText().toString().trim())) {
                ToastUtils.show("您的提现账号不能为空");
                return;
            }


            mPresenter.userAddacc(type, et_name.getText().toString().trim(), et_account.getText().toString().trim());
        });
    }

    @Override
    public void userAddaccSuccess() {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }
}
