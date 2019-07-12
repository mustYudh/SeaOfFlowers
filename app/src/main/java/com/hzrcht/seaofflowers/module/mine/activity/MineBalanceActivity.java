package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserBillListBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineBalancePresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineBalanceViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineBalanceRvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 账号余额
 */
public class MineBalanceActivity extends BaseBarActivity implements MineBalanceViewer {
    private int type = 1;
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private MineBalancePresenter mPresenter = new MineBalancePresenter(this);
    private MineBalanceRvAdapter adapter;
    private RecyclerView mBalance;
    private List<UserBillListBean.RowsBean> list = new ArrayList<>();

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_balance_view);
    }

    @Override
    protected void loadData() {
        setTitle("账号余额");

        mBalance = bindView(R.id.rv_balance);
        mBalance.setLayoutManager(new LinearLayoutManager(getActivity()));


        Calendar calendar = Calendar.getInstance();
        bindText(R.id.tv_year, calendar.get(Calendar.YEAR) + "");
        bindText(R.id.tv_month, (calendar.get(Calendar.MONTH) + 1) + "");
        TextView tv_year = bindView(R.id.tv_year);
        TextView tv_month = bindView(R.id.tv_month);
        mPresenter.getUserBill(page + "", pageSize + "", tv_year.getText().toString().trim() + (tv_month.getText().toString().trim().length() == 1 ? ("0" + tv_month.getText().toString().trim()) : tv_month.getText().toString().trim()), type);

        bindView(R.id.ll_left, view -> {
            type = 1;
            bindView(R.id.view_left, true);
            bindView(R.id.view_right, false);

        });

        bindView(R.id.ll_right, view -> {
            type = 2;
            bindView(R.id.view_left, false);
            bindView(R.id.view_right, true);
        });

    }

    @Override
    public void getUserBillSuccess(UserBillListBean userBillListBean) {
        if (userBillListBean != null) {
            bindText(R.id.tv_all, userBillListBean.all + "");
            bindText(R.id.tv_expend, userBillListBean.expend + "");
            bindText(R.id.tv_income, userBillListBean.income + "");
            if (userBillListBean.rows != null && userBillListBean.rows.size() != 0) {
                if (page > 1) {

                } else {
                    list.clear();
                }
                list.addAll(userBillListBean.rows);

                adapter = new MineBalanceRvAdapter(R.layout.item_mine_balance, userBillListBean.rows, getActivity());
                mBalance.setAdapter(adapter);
            }
        }
    }
}
