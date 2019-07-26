package com.hzrcht.seaofflowers.module.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.denghao.control.view.utils.UpdataCurrentFragment;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.mine.activity.ApplyAuthenticationActivity;
import com.hzrcht.seaofflowers.module.mine.activity.ChargeSettingActivity;
import com.hzrcht.seaofflowers.module.mine.activity.InAuthenticationActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineAttentionActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineBalanceActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineDynamicActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineHelpCenterActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineIntimacyActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineMineWithdrawActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MinePhotoAlbumActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineRechargeActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineRedactDataActivity;
import com.hzrcht.seaofflowers.module.mine.activity.SystemSettingsActivity;
import com.hzrcht.seaofflowers.module.mine.bean.MineUserInfoBean;
import com.hzrcht.seaofflowers.module.mine.fragment.presenter.MinePresenter;
import com.hzrcht.seaofflowers.module.mine.fragment.presenter.MineViewer;
import com.hzrcht.seaofflowers.module.view.MyOneLineView;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;

public class MineFragment extends BaseFragment implements MineViewer, View.OnClickListener, UpdataCurrentFragment {

    @PresenterLifeCycle
    private MinePresenter mPresenter = new MinePresenter(this);
    private ImageView mVip;
    private LinearLayout mLAge;
    private ImageView mIAge, mType;
    private LinearLayout mAuthentication;
    private MyOneLineView view_open_msg;
    private ImageView mHeadimg;

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
        LinearLayout ll_redact = bindView(R.id.ll_redact);
        mVip = bindView(R.id.iv_vip);
        mLAge = bindView(R.id.ll_age);
        mIAge = bindView(R.id.iv_age);
        mType = bindView(R.id.iv_type);
        mHeadimg = bindView(R.id.iv_headimg);


        DelayClickImageView iv_help = bindView(R.id.iv_help);
        LinearLayout ll_near = bindView(R.id.ll_near);
        LinearLayout ll_attention = bindView(R.id.ll_attention);
        LinearLayout ll_dynamic = bindView(R.id.ll_dynamic);
        LinearLayout ll_master = bindView(R.id.ll_master);
        LinearLayout ll_balance = bindView(R.id.ll_balance);
        LinearLayout ll_withdraw = bindView(R.id.ll_withdraw);
        LinearLayout ll_vip = bindView(R.id.ll_vip);
        LinearLayout ll_recharge = bindView(R.id.ll_recharge);
        mAuthentication = bindView(R.id.ll_authentication);
        LinearLayout ll_photo_album = bindView(R.id.ll_photo_album);
        MyOneLineView view_invitation_code = bindView(R.id.view_invitation_code);
        MyOneLineView view_accept_apprentice = bindView(R.id.view_accept_apprentice);
        view_open_msg = bindView(R.id.view_open_msg);
        MyOneLineView view_system_settings = bindView(R.id.view_system_settings);


        view_invitation_code.initMine(R.drawable.ic_invitation_code, "填写好友邀请码", true, true);
        view_accept_apprentice.initMine(R.drawable.ic_accept_apprentice, "收徒赚钱", true, true);
        view_open_msg.initMine(R.drawable.ic_open_msg, "开启勿扰", false, true);
        view_open_msg.showSwitchView(true);
        view_system_settings.initMine(R.drawable.ic_system_settings, "系统设置", true, true);

        //点击事件
        ll_redact.setOnClickListener(this);
        ll_attention.setOnClickListener(this);
        ll_balance.setOnClickListener(this);
        view_invitation_code.setOnClickListener(this);
        view_accept_apprentice.setOnClickListener(this);
        view_system_settings.setOnClickListener(this);
        ll_withdraw.setOnClickListener(this);
        ll_photo_album.setOnClickListener(this);
        ll_dynamic.setOnClickListener(this);
        ll_master.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        ll_recharge.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        ll_near.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_redact:
                //资料设置
                getLaunchHelper().startActivityForResult(MineRedactDataActivity.class, 1);
                break;
            case R.id.ll_attention:
                //我的关注
                getLaunchHelper().startActivityForResult(MineAttentionActivity.class, 1);
                break;
            case R.id.ll_balance:
                //账号余额
                getLaunchHelper().startActivityForResult(MineBalanceActivity.class, 1);
                break;
            case R.id.view_invitation_code:
                //填写邀请码
                ToastUtils.show("功能开发中!");
                break;
            case R.id.view_accept_apprentice:
                //收徒
                ToastUtils.show("功能开发中!");
                break;
            case R.id.view_system_settings:
                //系统设置
                getLaunchHelper().startActivity(SystemSettingsActivity.class);
                break;
            case R.id.ll_withdraw:
                //提现
                getLaunchHelper().startActivityForResult(MineMineWithdrawActivity.class, 1);
                break;
            case R.id.ll_photo_album:
                //相册
                getLaunchHelper().startActivityForResult(MinePhotoAlbumActivity.class, 1);
                break;
            case R.id.ll_dynamic:
                //动态
                getLaunchHelper().startActivityForResult(MineDynamicActivity.class, 1);
                break;
            case R.id.ll_master:
                //师徒
                ToastUtils.show("功能开发中!");
                break;
            case R.id.iv_help:
                //帮助中心
                getLaunchHelper().startActivity(MineHelpCenterActivity.class);
                break;
            case R.id.ll_recharge:
                //充值
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE", 0);
                getLaunchHelper().startActivityForResult(MineRechargeActivity.class, bundle, 1);
                break;
            case R.id.ll_vip:
                //充值
                Bundle bundleVip = new Bundle();
                bundleVip.putInt("TYPE", 1);
                getLaunchHelper().startActivityForResult(MineRechargeActivity.class, bundleVip, 1);
                break;
            case R.id.ll_near:
                Bundle bundleNear = new Bundle();
                bundleNear.putString("USER_ID", UserProfile.getInstance().getUserId() + "");
                LauncherHelper.from(getActivity()).startActivity(MineIntimacyActivity.class, bundleNear);
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
                bindText(R.id.tv_id, "ID:" + mineUserInfoBean.userInfo.id);
                bindText(R.id.tv_sign, TextUtils.isEmpty(mineUserInfoBean.userInfo.sign) ? "这个人很懒，什么都没留下~" : mineUserInfoBean.userInfo.sign);
                bindText(R.id.tv_type, mineUserInfoBean.userInfo.type == 0 ? "申请主播" : "收费设置");
                mVip.setImageResource(mineUserInfoBean.is_vip ? R.drawable.ic_vip : R.drawable.ic_vip_no);
                mIAge.setImageResource(mineUserInfoBean.userInfo.sex == 1 ? R.drawable.ic_man_logo : R.drawable.ic_woman_logo);
                mType.setImageResource(mineUserInfoBean.userInfo.type == 0 ? R.drawable.ic_apply : R.drawable.ic_charge);
                mLAge.setBackgroundResource(mineUserInfoBean.userInfo.sex == 1 ? R.drawable.shape_mine_man : R.drawable.shape_mine_woman);
                ImageLoader.getInstance().displayImage(mHeadimg, mineUserInfoBean.userInfo.head_img, mineUserInfoBean.userInfo.sex == 1 ? R.drawable.ic_man_normal : R.drawable.ic_woman_normal, mineUserInfoBean.userInfo.sex == 1 ? R.drawable.ic_man_normal : R.drawable.ic_woman_normal);
                mAuthentication.setOnClickListener(view -> {
                    if (UserProfile.getInstance().getUserSex() == 1) {
                        ToastUtils.show("男性不能申请主播");
                        return;
                    }
                    if (mineUserInfoBean.userInfo.type == 0) {
                        //认证主播
                        if (mineUserInfoBean.is_auth == 0) {
                            getLaunchHelper().startActivityForResult(InAuthenticationActivity.class, 1);
                        } else {
                            getLaunchHelper().startActivityForResult(ApplyAuthenticationActivity.class, 1);
                        }
                    } else {
                        //收费设置
                        getLaunchHelper().startActivity(ChargeSettingActivity.class);
                    }
                });

                UserProfile.getInstance().setAnchorType(mineUserInfoBean.userInfo.type);
            }
            UserProfile.getInstance().setUserVip(mineUserInfoBean.is_vip);

            bindText(R.id.tv_img, mineUserInfoBean.img + "");
            bindText(R.id.tv_state, mineUserInfoBean.state + "");
            bindText(R.id.tv_attent, mineUserInfoBean.attent + "");
            bindText(R.id.tv_friend, mineUserInfoBean.friend + "");
            view_open_msg.setSwichButton(mineUserInfoBean.disturb);

            view_open_msg.setSwichlistener(switchStatus -> {
                mPresenter.userEditConfig(mineUserInfoBean);
            });
        }
    }

    @Override
    public void userEditConfigSuccess(MineUserInfoBean mineUserInfoBean) {
        view_open_msg.setSwichButton(!mineUserInfoBean.disturb);
        mineUserInfoBean.disturb = !mineUserInfoBean.disturb;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                mPresenter.userInfo();
                break;
        }
    }


    @Override
    public void update(Bundle bundle) {
        mPresenter.userInfo();
    }
}
