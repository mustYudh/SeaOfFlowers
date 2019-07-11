package com.hzrcht.seaofflowers.module.login.activity.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.action.BaseActionHelper;
import com.hzrcht.seaofflowers.bean.InviteIdBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.request.PostRequest;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.ui.DelayClickTextView;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author yudenghao
 */
public class LoginPresenter extends BaseViewPresenter<LoginViewer> {

    public LoginPresenter(LoginViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void sendVerCode(String number, RxCountDown countDown, DelayClickTextView textView) {
        XHttp.post(ApiServices.SENDVERCODE)
                .params("type", "Login")
                .params("phone", number)
                .accessToken(false)
                .execute(LoginBean.class)
                .subscribeWith(new TipRequestSubscriber<LoginBean>() {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        assert getViewer() != null;
                        getViewer().sendVerCodeSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        textView.setClickable(true);

                        super.onError(apiException);
                        countDown.stop();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void phoneLogin(String number, String code) {
        // 获取剪贴板数据
        String content = null;
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        try {
            ClipData data = cm.getPrimaryClip();
            if (data != null && data.getItemCount() > 0) {
                ClipData.Item item = data.getItemAt(0);
                content = item.getText().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String superiorId = null;
        if (content != null) {
            if (content.contains("huahai_invite_id")) {
                Gson gson = new Gson();
                InviteIdBean inviteIdBean = gson.fromJson(content, InviteIdBean.class);
                if (inviteIdBean.huahai_invite_id != null) {
                    superiorId = inviteIdBean.huahai_invite_id.toString();
                }
            }
        }

        PostRequest request = XHttp.post(ApiServices.CODELOGIN);
        if (!TextUtils.isEmpty(superiorId)) {
            request.params("superior_id", superiorId);
        }
        request.params("code", code)
                .params("phone", number)
                .execute(LoginBean.class)
                .subscribeWith(new TipRequestSubscriber<LoginBean>() {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        assert getViewer() != null;
                        getViewer().loginSuccess(loginBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);
                    }
                });
    }

    public void login() {
        afterLoginSuccess();
    }

    private void afterLoginSuccess() {
        if (getViewer() == null) {
            return;
        }
        Bundle loginExtraBundle = getViewer().getLoginExtraBundle();
        String redirectActivityClassName = getViewer().getRedirectActivityClassName();
        String redirectOtherAction = getViewer().getRedirectOtherAction();
        if (loginExtraBundle == null) {
            LauncherHelper.from(getActivity()).startActivity(HomePageActivity.class);
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
            return;
        }
        if (!TextUtils.isEmpty(redirectActivityClassName)) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(getActivity(), redirectActivityClassName));
            intent.putExtras(loginExtraBundle);
            getLauncherHelper().startActivity(intent);
            getActivity().finish();
            return;
        }
        if (!TextUtils.isEmpty(redirectOtherAction)) {
            switch (redirectOtherAction) {
                case BaseActionHelper.LINK_URL:
                    BaseActionHelper.with(getActivity())
                            .handleAction(loginExtraBundle.getString(BaseActionHelper.LINK_URL), false);
                    getActivity().finish();
                    break;
                default:
                    getActivity().finish();
                    break;
            }
        }
    }
}