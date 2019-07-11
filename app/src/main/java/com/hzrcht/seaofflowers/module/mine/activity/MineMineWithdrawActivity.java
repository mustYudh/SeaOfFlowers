package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserAccountsBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineWithdrawPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineWithdrawViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineSysMoneyGvAdapter;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
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


        mBind.setOnClickListener(view -> {
            LauncherHelper.from(getActivity()).startActivity(BindAliPayActivity.class);
        });

        gv_type = bindView(R.id.gv_type);
        mPresenter.getSysMoney();
        mPresenter.getUserAccounts();
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
                    public void setOnItemChcekCheckClick(String key, String id) {
                        adapter.notifyDataSetChanged();
                        type_id = id;
                        bindText(R.id.tv_count, key + "金币");
                    }
                });
            }
        }
    }
}
