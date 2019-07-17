package com.hzrcht.seaofflowers.module.home.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudenghao
 */
@SuppressLint("CheckResult")
public class HomePagePresenter extends BaseViewPresenter<HomePageViewer> {

    public HomePagePresenter(HomePageViewer viewer) {
        super(viewer);
    }

    public void getIsAnchor() {
        XHttp.post(ApiServices.USERISANCHOR)
                .accessToken(true)
                .execute(UserIsAnchorBean.class)
                .subscribeWith(new TipRequestSubscriber<UserIsAnchorBean>() {
                    @Override
                    protected void onSuccess(UserIsAnchorBean userIsAnchorBean) {
                        assert getViewer() != null;
                        getViewer().getIsAnchorSuccess(userIsAnchorBean);
                    }
                });
    }

    public void userOnline() {
        XHttp.post(ApiServices.USERONLINE)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        Log.e("aaaa", "在线任务执行");
                    }
                });
    }
}