package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.request.PostRequest;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MinePersonalInfoPresenter extends BaseViewPresenter<MinePersonalInfoViewer> {

    public MinePersonalInfoPresenter(MinePersonalInfoViewer viewer) {
        super(viewer);
    }

    public void getUserInfo(String user_id) {
        XHttp.post(ApiServices.USERINFO)
                .accessToken(true)
                .params("user_id", user_id)
                .execute(AnchorUserInfoBean.class)
                .subscribeWith(new TipRequestSubscriber<AnchorUserInfoBean>() {
                    @Override
                    protected void onSuccess(AnchorUserInfoBean anchorUserInfoBean) {
                        assert getViewer() != null;
                        getViewer().getUserInfoSuccess(anchorUserInfoBean);
                    }
                });
    }


    public void attent(String anchor_id, int type, AnchorUserInfoBean anchorUserInfoBean) {
        XHttp.post(ApiServices.ATTENTCLICK)
                .accessToken(true)
                .params("anchor_id", anchor_id)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().attentSuccess(type, anchorUserInfoBean);
                    }
                });
    }

    public void getStateList(String user_id) {
        PostRequest post = XHttp.post(ApiServices.STATELIST);

        if (!TextUtils.isEmpty(user_id)) {
            post.params("user_id", user_id);
        }
        post.accessToken(true)
                .execute(MineDynamicBean.class)
                .subscribeWith(new TipRequestSubscriber<MineDynamicBean>() {
                    @Override
                    protected void onSuccess(MineDynamicBean mineDynamicBean) {
                        assert getViewer() != null;
                        getViewer().getStateListSuccess(mineDynamicBean);
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
                });
    }

    public void getPhotoAlbum(String user_id) {
        XHttp.post(ApiServices.GETPHOTOALBUM)
                .params("user_id", user_id)
                .accessToken(true)
                .execute(PhotoAlbumBean.class)
                .subscribeWith(new TipRequestSubscriber<PhotoAlbumBean>() {
                    @Override
                    protected void onSuccess(PhotoAlbumBean photoAlbumBean) {
                        assert getViewer() != null;
                        getViewer().getPhotoAlbumSuccess(photoAlbumBean);
                    }
                });
    }

    public void lookPhone(String anchor_id,AnchorUserInfoBean anchorUserInfoBean) {
        XHttp.post(ApiServices.LOOKPHONE)
                .params("anchor_id", anchor_id)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().lookPhoneSuccess(anchorUserInfoBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().lookPhoneFail(apiException.getCode(),apiException.getDisplayMessage());
                    }
                });
    }

    public void getSysMoney() {
        XHttp.post(ApiServices.SYSLANMU)
                .params("type", "4")
                .accessToken(true)
                .execute(SysMoneyBean.class)
                .subscribeWith(new TipRequestSubscriber<SysMoneyBean>() {
                    @Override
                    protected void onSuccess(SysMoneyBean sysMoneyBean) {
                        assert getViewer() != null;
                        getViewer().getSysMoneySuccess(sysMoneyBean);
                    }
                });
    }

    public void orderAdd(String type, String pay_id) {
        XHttp.post(ApiServices.ORDERADD)
                .params("type", type)
                .params("pay_id", pay_id)
                .accessToken(true)
                .execute(PayInfo.class)
                .subscribeWith(new TipRequestSubscriber<PayInfo>() {
                    @Override
                    protected void onSuccess(PayInfo payInfo) {
                        assert getViewer() != null;
                        getViewer().orderAddSuccess(payInfo);
                    }
                });
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

    public void liveStart(String anchor_id) {
        XHttp.post(ApiServices.LIVESTART)
                .params("anchor_id",anchor_id)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().liveStartSuccess();
                    }
                });
    }
}