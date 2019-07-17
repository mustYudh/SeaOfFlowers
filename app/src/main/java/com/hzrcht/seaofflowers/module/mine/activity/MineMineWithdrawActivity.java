package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserAccountsBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineWithdrawPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineWithdrawViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineSysMoneyGvAdapter;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.hzrcht.seaofflowers.module.mine.bean.UserAmountBean;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;
import com.yu.common.ui.NoSlidingGridView;

/**
 * 提现
 */
public class MineMineWithdrawActivity extends BaseBarActivity implements MineWithdrawViewer {
    private String type_id = "";
    private int type = 1;
    @PresenterLifeCycle
    private MineWithdrawPresenter mPresenter = new MineWithdrawPresenter(this);
    private DelayClickTextView mBind;
    private NoSlidingGridView gv_type;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_withdraw_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_withdraw_view_bar;
    }

    @Override
    protected void loadData() {
        mBind = bindView(R.id.tv_bind);
        LinearLayout mAli = bindView(R.id.ll_ali);
        LinearLayout mWxchat = bindView(R.id.ll_wxchat);
        View mViewAli = bindView(R.id.view_ali);
        View mViewWx = bindView(R.id.view_wx);

        mAli.setOnClickListener(view -> {
            mViewAli.setVisibility(View.VISIBLE);
            mViewWx.setVisibility(View.INVISIBLE);
            type = 1;
            mPresenter.getUserAccounts();
        });

        mWxchat.setOnClickListener(view -> {
            mViewAli.setVisibility(View.INVISIBLE);
            mViewWx.setVisibility(View.VISIBLE);
            type = 2;
            mPresenter.getUserAccounts();
        });


        gv_type = bindView(R.id.gv_type);
        mPresenter.getSysMoney();
        mPresenter.getUserAccounts();
        mPresenter.getUserAmount();
        bindView(R.id.tv_commit, view -> {
            if (TextUtils.isEmpty(type_id)) {
                ToastUtils.show("请选择提现选项");
                return;
            }
            mPresenter.userWithdraw(type, type_id);
        });
        bindView(R.id.action_back, view -> {
            setResult(1);
            finish();
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(1);
            finish();
        }
        return true;
    }
    @Override
    public void getUserAccountsSuccess(UserAccountsBean userAccountsBean) {
        if (userAccountsBean != null) {
            if (type == 1) {
                //支付宝账号
                if (userAccountsBean.ali_num != null && !TextUtils.isEmpty(userAccountsBean.ali_num)) {
                    //有
                    bindText(R.id.tv_amount, userAccountsBean.ali_num);
                    bindText(R.id.tv_bind, "立即修改");
                } else {
                    //无
                    bindText(R.id.tv_amount, "您还未绑定提现账号哦");
                    bindText(R.id.tv_bind, "立即绑定");
                }
                mBind.setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", type);
                    bundle.putString("NUMTYPE", userAccountsBean.ali_num);
                    bundle.putString("NAMETYPE", userAccountsBean.ali_name);
                    LauncherHelper.from(getActivity()).startActivityForResult(BindWithdrawAccountActivity.class, bundle, 1);
                });
            } else {
                //微信账号
                if (userAccountsBean.wechat_num != null && !TextUtils.isEmpty(userAccountsBean.wechat_num)) {
                    //有
                    bindText(R.id.tv_amount, userAccountsBean.wechat_num);
                    bindText(R.id.tv_bind, "立即修改");
                } else {
                    //无
                    bindText(R.id.tv_amount, "您还未绑定提现账号哦");
                    bindText(R.id.tv_bind, "立即绑定");
                }
                mBind.setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", type);
                    bundle.putString("NUMTYPE", userAccountsBean.wechat_num);
                    bundle.putString("NAMETYPE", userAccountsBean.wechat_name);
                    LauncherHelper.from(getActivity()).startActivityForResult(BindWithdrawAccountActivity.class, bundle, 1);
                });

            }
        }
    }

    @Override
    public void getSysMoneySuccess(SysMoneyBean sysMoneyBean) {
        if (sysMoneyBean != null) {
            if (sysMoneyBean.rows != null && sysMoneyBean.rows.size() != 0) {
                MineSysMoneyGvAdapter adapter = new MineSysMoneyGvAdapter(sysMoneyBean.rows, getActivity());
                gv_type.setAdapter(adapter);

                adapter.setOnItemChcekCheckListener(new MineSysMoneyGvAdapter.OnItemChcekCheckListener() {
                    @Override
                    public void setOnItemChcekCheckClick(String val, String id) {
                        adapter.notifyDataSetChanged();
                        type_id = id;
                        bindText(R.id.tv_count, val + "金币");
                    }
                });
            }
        }
    }

    @Override
    public void userWithdrawSuccess() {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    @Override
    public void getUserAmountSuccess(UserAmountBean amountBean) {
        if (amountBean != null) {
            bindText(R.id.tv_coin, amountBean.withdrawal + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                mPresenter.getUserAccounts();
                break;
        }
    }
}
