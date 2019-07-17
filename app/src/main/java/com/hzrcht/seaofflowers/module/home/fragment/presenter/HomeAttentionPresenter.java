package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.NoTipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.HomeAttentionBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeAttentionPresenter extends BaseViewPresenter<HomeAttentionViewer> {

    public HomeAttentionPresenter(HomeAttentionViewer viewer) {
        super(viewer);
    }

    public void getAttentionList(int page, int pageSize) {
        XHttp.post(ApiServices.GETATTENTIONLIST)
                .accessToken(true)
                .params("page", page + "")
                .params("pageSize", pageSize + "")
                .execute(HomeAttentionBean.class)
                .subscribeWith(new NoTipRequestSubscriber<HomeAttentionBean>() {
                    @Override
                    protected void onSuccess(HomeAttentionBean homeAttentionBean) {
                        assert getViewer() != null;
                        getViewer().getAttentionListSuccess(homeAttentionBean);
                    }
                });
    }
}