package com.hzrcht.seaofflowers.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.module.mine.adapter.MineSysMoneyGvAdapter;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.hzrcht.seaofflowers.module.mine.bean.UserAmountBean;
import com.hzrcht.seaofflowers.module.mine.fragment.presenter.RechargeCoinPresenter;
import com.hzrcht.seaofflowers.module.mine.fragment.presenter.RechargeCoinViewer;
import com.hzrcht.seaofflowers.utils.PayUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.NoSlidingGridView;


public class RechargeCoinFragment extends BaseFragment implements RechargeCoinViewer {
    private int type = 2;
    private String type_id = "";
    @PresenterLifeCycle
    private RechargeCoinPresenter mPresenter = new RechargeCoinPresenter(this);
    private NoSlidingGridView gv_type;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recharge_coin_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        gv_type = bindView(R.id.gv_type);
        ImageView iv_ali = bindView(R.id.iv_ali);
        ImageView iv_wx = bindView(R.id.iv_wx);
        bindView(R.id.rl_ali, view -> {
            iv_ali.setImageResource(R.drawable.ic_circle_select);
            iv_wx.setImageResource(R.drawable.ic_circle_normal);
            type = 2;
        });

        bindView(R.id.rl_wx, view -> {
            iv_wx.setImageResource(R.drawable.ic_circle_select);
            iv_ali.setImageResource(R.drawable.ic_circle_normal);
            type = 1;
        });

        bindView(R.id.tv_commit, view -> {
            if (TextUtils.isEmpty(type_id)) {
                ToastUtils.show("请选择支付选项");
                return;
            }

            if (type == -1) {
                ToastUtils.show("请选择支付方式");
                return;
            }

            mPresenter.orderAdd(type + "", type_id);

        });
        mPresenter.getUserAmount();
        mPresenter.getSysMoney();
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
                    }
                });
            }
        }

    }

    @Override
    public void getUserAmountSuccess(UserAmountBean amountBean) {
        if (amountBean != null) {
            bindText(R.id.tv_coin, amountBean.amount + "");
        }
    }

    @Override
    public void orderAddSuccess(PayInfo payInfo) {
        PayUtils.getInstance().pay(getActivity(), type, payInfo)
                .getPayResult(new PayUtils.PayCallBack() {
                    @Override
                    public void onPaySuccess(int type) {
                        mPresenter.getUserAmount();
                        Log.e("aaaa", "支付成功了吗");
                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("支付失败，请重试");
                    }
                });
    }
}
