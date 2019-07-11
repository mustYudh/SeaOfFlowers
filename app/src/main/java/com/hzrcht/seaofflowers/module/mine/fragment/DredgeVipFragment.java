package com.hzrcht.seaofflowers.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.module.mine.adapter.MineSysVipGvAdapter;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.hzrcht.seaofflowers.module.mine.fragment.presenter.DredgeVipPresenter;
import com.hzrcht.seaofflowers.module.mine.fragment.presenter.DredgeVipViewer;
import com.hzrcht.seaofflowers.utils.PayUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.NoSlidingGridView;


public class DredgeVipFragment extends BaseFragment implements DredgeVipViewer {
    private int type = -1;
    private String type_id = "";
    @PresenterLifeCycle
    private DredgeVipPresenter mPresenter = new DredgeVipPresenter(this);
    private NoSlidingGridView gv_type;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_dredge_vip_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        gv_type = bindView(R.id.gv_type);
        bindView(R.id.rl_ali, view -> {
            bindView(R.id.iv_ali).setBackgroundResource(R.drawable.ic_circle_select);
            bindView(R.id.iv_wx).setBackgroundResource(R.drawable.ic_circle_normal);
            type = 2;
        });

        bindView(R.id.rl_wx, view -> {
            bindView(R.id.iv_wx).setBackgroundResource(R.drawable.ic_circle_select);
            bindView(R.id.iv_ali).setBackgroundResource(R.drawable.ic_circle_normal);
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
        mPresenter.getSysMoney();
    }

    @Override
    public void getSysMoneySuccess(SysMoneyBean sysMoneyBean) {
        if (sysMoneyBean != null) {
            if (sysMoneyBean.rows != null && sysMoneyBean.rows.size() != 0) {
                MineSysVipGvAdapter adapter = new MineSysVipGvAdapter(sysMoneyBean.rows, getActivity());
                gv_type.setAdapter(adapter);

                adapter.setOnItemChcekCheckListener(new MineSysVipGvAdapter.OnItemChcekCheckListener() {
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
    public void orderAddSuccess(PayInfo payInfo) {
        PayUtils.getInstance().pay(getActivity(), type, payInfo)
                .getPayResult(new PayUtils.PayCallBack() {
                    @Override
                    public void onPaySuccess(int type) {
                        ToastUtils.show("支付宝支付成功");
                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("支付失败，请重试");
                    }
                });
    }
}
