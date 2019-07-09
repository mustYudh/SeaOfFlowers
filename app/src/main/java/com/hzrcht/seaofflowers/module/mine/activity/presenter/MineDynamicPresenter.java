package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.hzrcht.seaofflowers.module.mine.bean.MineLocationUserDynamicBean;
import com.hzrcht.seaofflowers.module.mine.bean.MineUserDynamicBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MineDynamicPresenter extends BaseViewPresenter<MineDynamicViewer> {

    public MineDynamicPresenter(MineDynamicViewer viewer) {
        super(viewer);
    }

    public void getStateList(int page, int pageSize) {
        XHttp.post(ApiServices.MINESTATELIST)
                .accessToken(true)
                .params("page", page + "")
                .params("pageSize", pageSize + "")
                .execute(MineUserDynamicBean.class)
                .subscribeWith(new TipRequestSubscriber<MineUserDynamicBean>() {
                    @Override
                    protected void onSuccess(MineUserDynamicBean mineUserDynamicBean) {
                        assert getViewer() != null;
                        getViewer().getStateListSuccess(mineUserDynamicBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }

    public void stateLike(String state_id, MineLocationUserDynamicBean item) {
        XHttp.post(ApiServices.STATELIKE)
                .accessToken(true)
                .params("state_id", state_id)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().stateLikeSuccess(item);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }

    public void stateDel(String state_id, int position) {
        XHttp.post(ApiServices.STATEDEL)
                .accessToken(true)
                .params("id", state_id)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().stateDelSuccess(position);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }

    public void getReviewList(String state_id, MineLocationUserDynamicBean item, int page, int pageSize) {
        XHttp.post(ApiServices.REVIEWLIST)
                .accessToken(true)
                .params("state_id", state_id + "")
                .params("page", page + "")
                .params("pageSize", pageSize + "")
                .execute(ReviewListBean.class)
                .subscribeWith(new TipRequestSubscriber<ReviewListBean>() {
                    @Override
                    protected void onSuccess(ReviewListBean reviewListBean) {
                        assert getViewer() != null;
                        getViewer().getReviewListSuccess(reviewListBean, item, state_id);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }

    public void stateReview(String state_id, String title, MineLocationUserDynamicBean item) {
        XHttp.post(ApiServices.STATEREVIEW)
                .accessToken(true)
                .params("state_id", state_id)
                .params("title", title)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().stateReviewSuccess(item);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }
}