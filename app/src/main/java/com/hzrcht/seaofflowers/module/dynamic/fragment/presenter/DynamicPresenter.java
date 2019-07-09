package com.hzrcht.seaofflowers.module.dynamic.fragment.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.request.PostRequest;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class DynamicPresenter extends BaseViewPresenter<DynamicViewer> {

    public DynamicPresenter(DynamicViewer viewer) {
        super(viewer);
    }

    public void getStateList(String user_id, int page, int pageSize) {
        PostRequest post = XHttp.post(ApiServices.STATELIST);

        if (!TextUtils.isEmpty(user_id)) {
            post.params("user_id", user_id);
        }

        post.accessToken(true)
                .params("page", page + "")
                .params("pageSize", pageSize + "")
                .execute(MineDynamicBean.class)
                .subscribeWith(new TipRequestSubscriber<MineDynamicBean>() {
                    @Override
                    protected void onSuccess(MineDynamicBean mineDynamicBean) {
                        assert getViewer() != null;
                        getViewer().getStateListSuccess(mineDynamicBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }

    public void stateLike(String state_id, MineLocationDynamicBean item) {
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

    public void getReviewList(String state_id, MineLocationDynamicBean item, int page, int pageSize) {
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

    public void stateReview(String state_id, String title, MineLocationDynamicBean item) {
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