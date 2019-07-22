package com.hzrcht.seaofflowers.module.mine.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserBillListBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineWithdrawDetailPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineWithdrawDetailViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineBalanceRvAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MineWithdrawDetailActivity extends BaseBarActivity implements MineWithdrawDetailViewer {

    @PresenterLifeCycle
    private MineWithdrawDetailPresenter mPresenter = new MineWithdrawDetailPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_withdraw_detail_view);
    }

    private MineBalanceRvAdapter adapter;
    private RecyclerView mBalance;
    private TimePickerView pvCustomTime;
    private TextView tv_year;
    private TextView tv_month;
    private int page = 1;
    private int pageSize = 10;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void loadData() {
        setTitle("提现明细");
        mBalance = bindView(R.id.rv_balance);
        mBalance.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout = bindView(R.id.refreshLayout);

        Calendar calendar = Calendar.getInstance();
        bindText(R.id.tv_year, calendar.get(Calendar.YEAR) + "");
        bindText(R.id.tv_month, (calendar.get(Calendar.MONTH) + 1) + "");
        tv_year = bindView(R.id.tv_year);
        tv_month = bindView(R.id.tv_month);

        adapter = new MineBalanceRvAdapter(R.layout.item_mine_balance, getActivity());
        mBalance.setAdapter(adapter);

        mPresenter.getUserBill(page + "", pageSize + "", tv_year.getText().toString().trim() + (tv_month.getText().toString().trim().length() == 1 ? ("0" + tv_month.getText().toString().trim()) : tv_month.getText().toString().trim()));

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            int i = 1;
            page += i;
            mPresenter.getUserBill(page + "", pageSize + "", tv_year.getText().toString().trim() + (tv_month.getText().toString().trim().length() == 1 ? ("0" + tv_month.getText().toString().trim()) : tv_month.getText().toString().trim()));

        });
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            page = 1;
            mPresenter.getUserBill(page + "", pageSize + "", tv_year.getText().toString().trim() + (tv_month.getText().toString().trim().length() == 1 ? ("0" + tv_month.getText().toString().trim()) : tv_month.getText().toString().trim()));
        });

        showTimeDialog();

        bindView(R.id.ll_select, view -> {
            pvCustomTime.show();
        });
    }

    @Override
    public void getUserBillSuccess(UserBillListBean userBillListBean) {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
        if (userBillListBean != null) {
            bindText(R.id.tv_all, userBillListBean.all + "");
            bindText(R.id.tv_expend, userBillListBean.expend + "");
            bindText(R.id.tv_income, userBillListBean.income + "");
            if (userBillListBean.rows != null && userBillListBean.rows.size() != 0) {

                if (page > 1) {
                    adapter.addData(userBillListBean.rows);
                } else {
                    adapter.setNewData(userBillListBean.rows);
                }
                bindView(R.id.ll_empty, false);
                bindView(R.id.rv_balance, true);
            } else {
                if (page > 1) {
                    ToastUtils.show("没有更多了");
                } else {
                    //空页面
                    bindView(R.id.ll_empty, true);
                    bindView(R.id.rv_balance, false);
                }
            }
        }
    }

    @Override
    public void getUserBillFail() {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
    }

    private void showTimeDialog() {
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                page = 1;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                bindText(R.id.tv_year, calendar.get(Calendar.YEAR) + "");
                bindText(R.id.tv_month, (calendar.get(Calendar.MONTH) + 1) + "");
                mPresenter.getUserBill(page + "", pageSize + "", tv_year.getText().toString().trim() + (tv_month.getText().toString().trim().length() == 1 ? ("0" + tv_month.getText().toString().trim()) : tv_month.getText().toString().trim()));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final DelayClickTextView tv_finish = (DelayClickTextView) v.findViewById(R.id.tv_finish);
                        DelayClickTextView tv_cancle = (DelayClickTextView) v.findViewById(R.id.tv_cancle);
                        tv_finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        tv_cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(16)
                .setGravity(Gravity.CENTER)
                .setTextColorCenter(Color.parseColor("#9897E7"))
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(2.6f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#9897E7"))

                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
