package com.hzrcht.seaofflowers.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.mine.activity.ApplyAuthenticationActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineAttentionActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineBalanceActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineDynamicActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineMineWithdrawActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MinePhotoAlbumActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MinePresentActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineRedactDataActivity;
import com.hzrcht.seaofflowers.module.mine.activity.SystemSettingsActivity;
import com.hzrcht.seaofflowers.module.mine.bean.MineUserInfoBean;
import com.hzrcht.seaofflowers.module.mine.fragment.presenter.MinePresenter;
import com.hzrcht.seaofflowers.module.mine.fragment.presenter.MineViewer;
import com.hzrcht.seaofflowers.module.view.MyOneLineView;
import com.yu.common.mvp.PresenterLifeCycle;

public class MineFragment extends BaseFragment implements MineViewer, View.OnClickListener {

    @PresenterLifeCycle
    private MinePresenter mPresenter = new MinePresenter(this);
    private ImageView mVip;
    private LinearLayout mLAge;
    private ImageView mIAge, mType;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mPresenter.userInfo();
        ImageView iv_redact = bindView(R.id.iv_redact);
        mVip = bindView(R.id.iv_vip);
        mLAge = bindView(R.id.ll_age);
        mIAge = bindView(R.id.iv_age);
        mType = bindView(R.id.iv_type);


        LinearLayout ll_attention = bindView(R.id.ll_attention);
        LinearLayout ll_dynamic = bindView(R.id.ll_dynamic);
        LinearLayout ll_master = bindView(R.id.ll_master);
        LinearLayout ll_balance = bindView(R.id.ll_balance);
        LinearLayout ll_withdraw = bindView(R.id.ll_withdraw);
        LinearLayout ll_authentication = bindView(R.id.ll_authentication);
        LinearLayout ll_photo_album = bindView(R.id.ll_photo_album);
        MyOneLineView view_invitation_code = bindView(R.id.view_invitation_code);
        MyOneLineView view_accept_apprentice = bindView(R.id.view_accept_apprentice);
        MyOneLineView view_open_msg = bindView(R.id.view_open_msg);
        MyOneLineView view_system_settings = bindView(R.id.view_system_settings);


        view_invitation_code.initMine(R.drawable.ic_invitation_code, "填写好友邀请码", true, true);
        view_accept_apprentice.initMine(R.drawable.ic_accept_apprentice, "收徒赚钱", true, true);
        view_open_msg.initMine(R.drawable.ic_open_msg, "开启勿扰", false, true);
        view_open_msg.showSwitchView(true);
        view_open_msg.setSwichButton(true);
        view_system_settings.initMine(R.drawable.ic_system_settings, "系统设置", true, true);

        //点击事件
        iv_redact.setOnClickListener(this);
        ll_attention.setOnClickListener(this);
        ll_balance.setOnClickListener(this);
        ll_authentication.setOnClickListener(this);
        view_system_settings.setOnClickListener(this);
        ll_withdraw.setOnClickListener(this);
        ll_photo_album.setOnClickListener(this);
        ll_dynamic.setOnClickListener(this);
        ll_master.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_redact:
                //资料设置
                getLaunchHelper().startActivity(MineRedactDataActivity.class);
                break;
            case R.id.ll_attention:
                //我的关注
                getLaunchHelper().startActivity(MineAttentionActivity.class);
                break;
            case R.id.ll_balance:
                //账号余额
                getLaunchHelper().startActivity(MineBalanceActivity.class);
                break;
            case R.id.ll_authentication:
                //认证主播
                getLaunchHelper().startActivity(ApplyAuthenticationActivity.class);
//                getLaunchHelper().startActivity(InAuthenticationActivity.class);
                break;
            case R.id.view_system_settings:
                //系统设置
                getLaunchHelper().startActivity(SystemSettingsActivity.class);
                break;
            case R.id.ll_withdraw:
                //提现
                getLaunchHelper().startActivity(MineMineWithdrawActivity.class);
                break;
            case R.id.ll_photo_album:
                //相册
                getLaunchHelper().startActivity(MinePhotoAlbumActivity.class);
                break;
            case R.id.ll_dynamic:
                //动态
                getLaunchHelper().startActivity(MineDynamicActivity.class);
                break;
            case R.id.ll_master:
                //师徒
                getLaunchHelper().startActivity(MinePresentActivity.class);
                break;
        }
    }

    @Override
    public void userInfoSuccess(MineUserInfoBean mineUserInfoBean) {
        if (mineUserInfoBean != null) {
            if (mineUserInfoBean.userInfo != null) {
                bindText(R.id.tv_balance, mineUserInfoBean.userInfo.amount + "");
                bindText(R.id.tv_withdrawal, mineUserInfoBean.userInfo.withdrawal + "");
                bindText(R.id.tv_nickname, mineUserInfoBean.userInfo.nick_name);
                bindText(R.id.tv_age, mineUserInfoBean.userInfo.age + "");
                bindText(R.id.tv_id, mineUserInfoBean.userInfo.id + "");
                bindText(R.id.tv_sign, TextUtils.isEmpty(mineUserInfoBean.userInfo.sign) ? "这个人很懒，什么都没留下~" : mineUserInfoBean.userInfo.sign);
                bindText(R.id.tv_type, mineUserInfoBean.userInfo.type == 0 ? "申请主播" : "收费设置");
                mVip.setImageResource(mineUserInfoBean.is_vip ? R.drawable.ic_vip : R.drawable.ic_vip_no);
                mIAge.setImageResource(mineUserInfoBean.userInfo.sex == 1 ? R.drawable.ic_man_logo : R.drawable.ic_woman_logo);
                mType.setImageResource(mineUserInfoBean.userInfo.type == 0 ? R.drawable.ic_apply : R.drawable.ic_charge);
                mLAge.setBackgroundResource(mineUserInfoBean.userInfo.sex == 1 ? R.drawable.shape_mine_man : R.drawable.shape_mine_woman);
            }
            bindText(R.id.tv_img, mineUserInfoBean.img + "");
            bindText(R.id.tv_state, mineUserInfoBean.state + "");
            bindText(R.id.tv_attent, mineUserInfoBean.attent + "");
            bindText(R.id.tv_friend, mineUserInfoBean.friend + "");
        }
    }
}
