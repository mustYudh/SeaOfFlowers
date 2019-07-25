package com.hzrcht.seaofflowers.module.login.activity.presenter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.bean.InviteIdBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.hzrcht.seaofflowers.module.login.bean.WechatInfo;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.callback.SimpleCallBack;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.request.PostRequest;
import com.yu.common.framework.BaseViewPresenter;

import static android.content.Context.CLIPBOARD_SERVICE;

@SuppressLint("CheckResult")
public class SelectLoginPresenter extends BaseViewPresenter<SelectLoginViewer> {
    public SelectLoginPresenter(SelectLoginViewer viewer) {
        super(viewer);
    }

    public void loginThird(String type, String unionid, String openid, String wxData) {
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

        PostRequest post = XHttp.post(ApiServices.LOGINTHIRD);
        if (!TextUtils.isEmpty(superiorId)) {
            post.params("superior_id", superiorId);
        }
        post.params("type", type)
                .params("unionid", unionid)
                .params("openid", openid)
                .params("data", wxData)
                .execute(LoginBean.class)
                .subscribeWith(new TipRequestSubscriber<LoginBean>() {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        assert getViewer() != null;
                        getViewer().getWxInfoSuccess(loginBean);
                    }
                });
    }


    public void loginWechat(String token, String openid) {
        XHttp.get("/sns/userinfo")
                .baseUrl("https://api.weixin.qq.com")
                .params("access_token", token)
                .params("openid", openid)
                .syncRequest(false)
                .onMainThread(true)
                .execute(new SimpleCallBack<WechatInfo>() {
                    @Override
                    public void onSuccess(WechatInfo response) throws Throwable {
                        Log.e("aaa", response.toString());
                    }

                    @Override
                    public void onError(ApiException e) {
                        Log.e("aaa", "失败");
                    }
                });
    }
}
