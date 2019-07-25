package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.home.activity.HomeReportActivity;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.BannerInfoViewHolder;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineContentRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineInfoDataRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineInfoDynamicRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.LiveStartBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysGiftBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePersonalInfoPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePersonalInfoViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineSysGiftRvAdapter;
import com.hzrcht.seaofflowers.module.mine.adapter.MineSysMoneyGvAdapter;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.hzrcht.seaofflowers.module.view.APPScrollView;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.hzrcht.seaofflowers.utils.PayUtils;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;
import com.yu.common.ui.DelayClickTextView;
import com.yu.common.ui.NoSlidingGridView;
import com.yu.common.ui.Res;
import com.yu.common.utils.DensityUtil;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MinePersonalInfoActivity extends BaseBarActivity
        implements MinePersonalInfoViewer, View.OnClickListener, APPScrollView.OnScrollListener {
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    private List<LinearLayout> llList = new ArrayList<>();
    private List<LinearLayout> llRootList = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private MZBannerView mBanner;
    private LinearLayout ll_first, ll_second, ll_third, mIntimacy, mPresent;
    private DialogUtils reportDialog, shareDialog, checkDialog, rechargeDialog, commentDialog;
    private final static int alphaThreshold = DensityUtil.dip2px(200);
    @PresenterLifeCycle
    private MinePersonalInfoPresenter mPresenter =
            new MinePersonalInfoPresenter(this);
    private TextView mMobile, mAge;
    private String user_id;
    private FlexboxLayout mFlexboxSelf;
    private LinearLayout mLabel;
    private ImageView mCollect;
    private LinearLayout mLCollect, mLOnline;
    private RecyclerView mDynamic;
    private int page = 0;
    private int pageSize = 10;
    private MineInfoDynamicRvAdapter adapter;
    private LinearLayout mPresentRoot;
    private RecyclerView rv_video_photo;
    private MineInfoDataRvAdapter photoAdapter;
    private TextView tv_check_mobile;
    private boolean is_vip = false;
    private LinearLayout mIntimacyRoot;
    private String nick_name = "";
    private String head_img = "";
    private String age = "";
    private String is_attent = "0";
    private DialogUtils presentDialog;
    private RelativeLayout action_bar;
    private APPScrollView scroll_view;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_personal_info_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_mine_personal_info_view_bar;
    }

    @Override
    protected void loadData() {
        mPresenter.getIsAnchor();
        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getString("USER_ID");
        scroll_view = bindView(R.id.scroll_view);
        action_bar = bindView(R.id.action_bar);
        mBanner = bindView(R.id.banner);
        LinearLayout ll_root_frist = bindView(R.id.ll_root_frist);
        LinearLayout ll_root_second = bindView(R.id.ll_root_second);
        LinearLayout ll_root_third = bindView(R.id.ll_root_third);
        ll_first = bindView(R.id.ll_first);
        ll_second = bindView(R.id.ll_second);
        ll_third = bindView(R.id.ll_third);
        TextView tv_first = bindView(R.id.tv_first);
        TextView tv_second = bindView(R.id.tv_second);
        TextView tv_third = bindView(R.id.tv_third);
        View view_first = bindView(R.id.view_first);
        View view_second = bindView(R.id.view_second);
        View view_third = bindView(R.id.view_third);
        ImageView iv_share = bindView(R.id.iv_share);
        LinearLayout ll_add = bindView(R.id.ll_add);
        LinearLayout ll_video = bindView(R.id.ll_video);
        LinearLayout ll_send_present = bindView(R.id.ll_send_present);
        bindView(R.id.back_name, false);
        mIntimacy = bindView(R.id.ll_intimacy);
        mPresent = bindView(R.id.ll_present);
        mIntimacyRoot = bindView(R.id.ll_intimacy_root);
        mPresentRoot = bindView(R.id.ll_present_root);
        mFlexboxSelf = bindView(R.id.flexbox);
        mMobile = bindView(R.id.tv_mobile);
        tv_check_mobile = bindView(R.id.tv_check_mobile);
        mLCollect = bindView(R.id.ll_collect);
        mLOnline = bindView(R.id.ll_online);
        mLabel = bindView(R.id.ll_label);
        mAge = bindView(R.id.tv_age);

        mCollect = bindView(R.id.iv_collect);

        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        mIntimacyRoot.setOnClickListener(this);
        mPresentRoot.setOnClickListener(this);
        ll_video.setOnClickListener(this);
        ll_send_present.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        scroll_view.setOnScrollChangedCallback(this);

        llRootList.add(ll_root_frist);
        llRootList.add(ll_root_second);
        llRootList.add(ll_root_third);
        llList.add(ll_first);
        llList.add(ll_second);
        llList.add(ll_third);
        tvList.add(tv_first);
        tvList.add(tv_second);
        tvList.add(tv_third);
        viewList.add(view_first);
        viewList.add(view_second);
        viewList.add(view_third);

        mDynamic = bindView(R.id.rv_dynamic);
        mDynamic.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_video_photo = bindView(R.id.rv_video_photo);
        rv_video_photo.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_video_photo.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 4, 2));
        setTypeCheck(ll_first);

        mPresenter.getUserInfo(user_id);
        mPresenter.getStateList(user_id);
        mPresenter.getPhotoAlbum(user_id);
    }

    @Override
    public boolean isImmersionBar() {
        return true;
    }

    /**
     * banner 初始化
     */
    private void initBanner(List<String> xbanner) {
        String json = new Gson().toJson(xbanner);
        Gson gson = new Gson();

        if (mBanner != null) {
            mBanner.setDuration(500);
            mBanner.setDelayedTime(3000);
            mBanner.setCanLoop(true);
            mBanner.setPages(xbanner, new MZHolderCreator() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new BannerInfoViewHolder();
                }
            });
            mBanner.start();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_first:
                setTypeCheck(ll_first);
                break;
            case R.id.ll_second:
                setTypeCheck(ll_second);
                break;
            case R.id.ll_third:
                setTypeCheck(ll_third);
                break;
            case R.id.iv_share:
                showShareDialog();
                break;
            case R.id.ll_intimacy_root:
                Bundle bundle = new Bundle();
                bundle.putString("USER_ID", user_id + "");
                LauncherHelper.from(getActivity()).startActivity(MineIntimacyActivity.class, bundle);
                break;
            case R.id.ll_present_root:
                Bundle bundlePresent = new Bundle();
                bundlePresent.putString("USER_ID", user_id + "");
                LauncherHelper.from(getActivity()).startActivity(MinePresentActivity.class, bundlePresent);
                break;
            case R.id.ll_video:
                mPresenter.liveStart(user_id);
                break;
            case R.id.ll_send_present:
                mPresenter.getSysGift();
                break;
            case R.id.ll_add:
                showReportDialog("0", user_id, "0");
                break;
        }
    }

    /**
     * 举报弹窗
     */
    private void showReportDialog(String type, String anchor_id, String state_id) {
        reportDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_normal)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .settext("请选择", R.id.tv_title)
                .settext("举报", R.id.tv_bottom)
                .addViewOnclick(R.id.tv_cancle, view -> {
                    if (reportDialog.isShowing()) {
                        reportDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.tv_bottom, view -> {
                    if (reportDialog.isShowing()) {
                        reportDialog.dismiss();
                    }
                    //举报动态
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE", type);
                    bundle.putString("ANCHOR_ID", anchor_id);
                    bundle.putString("STATE_ID", state_id);
                    getLaunchHelper().startActivity(HomeReportActivity.class, bundle);
                })
                .build();
        reportDialog.show();
        reportDialog.findViewById(R.id.tv_top).setVisibility(View.GONE);
        reportDialog.findViewById(R.id.view_middle).setVisibility(View.GONE);
    }

    /**
     * 分享弹窗
     */
    private void showShareDialog() {
        shareDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_share)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .addViewOnclick(R.id.tv_cancle, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.ll_qq, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.ll_qzone, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.ll_wx, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.ll_wx_friend, view -> {
                    if (shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                })
                .build();
        shareDialog.show();
    }

    /**
     * 查看手机号弹窗
     */
    private void showCleckDialog(AnchorUserInfoBean anchorUserInfoBean) {
        checkDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_layout)
                .gravity(Gravity.CENTER)
                .cancelTouchout(true)
                .style(R.style.Dialog_NoAnimation)
                .settext("本次查看将消费您" + anchorUserInfoBean.look_amount + "金币", R.id.dialog_content)
                .settext("确定", R.id.cancle)
                .settext("取消", R.id.down)
                .addViewOnclick(R.id.cancle, view -> {
                    if (checkDialog.isShowing()) {
                        checkDialog.dismiss();
                    }
                    loadDialog.show();
                    mPresenter.lookPhone(user_id, anchorUserInfoBean);
                })
                .addViewOnclick(R.id.down, view -> {
                    if (checkDialog.isShowing()) {
                        checkDialog.dismiss();
                    }
                })
                .build();
        checkDialog.show();
        checkDialog.findViewById(R.id.dialog_title).setVisibility(View.VISIBLE);
    }

    private String type_id = "";
    private int type = 2;

    /**
     * 余额不足弹窗
     */
    private void showRechargeDialog(List<SysMoneyBean.RowsBean> rows) {
        rechargeDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_recharge)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .build();
        rechargeDialog.show();
        LinearLayout ll_vip = rechargeDialog.findViewById(R.id.ll_vip);
        DelayClickTextView tv_vip = rechargeDialog.findViewById(R.id.tv_vip);
        DelayClickTextView tv_more = rechargeDialog.findViewById(R.id.tv_more);
        RelativeLayout rl_ali = rechargeDialog.findViewById(R.id.rl_ali);
        RelativeLayout rl_wx = rechargeDialog.findViewById(R.id.rl_wx);
        ImageView iv_ali = rechargeDialog.findViewById(R.id.iv_ali);
        ImageView iv_wx = rechargeDialog.findViewById(R.id.iv_wx);
        DelayClickTextView down = rechargeDialog.findViewById(R.id.down);
        NoSlidingGridView gv_type = rechargeDialog.findViewById(R.id.gv_type);
        MineSysMoneyGvAdapter adapter = new MineSysMoneyGvAdapter(rows, getActivity());
        gv_type.setAdapter(adapter);

        adapter.setOnItemChcekCheckListener(new MineSysMoneyGvAdapter.OnItemChcekCheckListener() {
            @Override
            public void setOnItemChcekCheckClick(String val, String id) {
                type_id = id;
                adapter.notifyDataSetChanged();
            }
        });

        rl_ali.setOnClickListener(view -> {
            type = 2;
            iv_ali.setImageResource(R.drawable.ic_circle_select);
            iv_wx.setImageResource(R.drawable.ic_circle_normal);
        });

        rl_wx.setOnClickListener(view -> {
            type = 1;
            iv_ali.setImageResource(R.drawable.ic_circle_normal);
            iv_wx.setImageResource(R.drawable.ic_circle_select);
        });

        down.setOnClickListener(view -> {
            if (TextUtils.isEmpty(type_id)) {
                ToastUtils.show("请选择充值选项");
                return;
            }
            mPresenter.orderAdd(type + "", type_id);
        });

        if (!is_vip) {
            ll_vip.setVisibility(View.VISIBLE);
        } else {
            ll_vip.setVisibility(View.GONE);
        }

        tv_vip.setOnClickListener(view -> {
            if (rechargeDialog.isShowing()) {
                rechargeDialog.dismiss();
            }
            Bundle bundle = new Bundle();
            bundle.putInt("TYPE", 1);
            getLaunchHelper().startActivity(MineRechargeActivity.class, bundle);
        });

        tv_more.setOnClickListener(view -> {
            if (rechargeDialog.isShowing()) {
                rechargeDialog.dismiss();
            }
            Bundle bundle = new Bundle();
            bundle.putInt("TYPE", 0);
            getLaunchHelper().startActivity(MineRechargeActivity.class, bundle);
        });

        rechargeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                type_id = "";
            }
        });
    }

    /**
     * 评论弹窗
     */
    private void showCommentDialog(ReviewListBean reviewListBean, MineLocationDynamicBean item,
                                   String state_id) {
        commentDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_comment)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .build();
        commentDialog.show();

        TextView tv_count = commentDialog.findViewById(R.id.tv_count);
        RecyclerView rv_content = commentDialog.findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (reviewListBean != null) {
            if (reviewListBean.rows != null && reviewListBean.rows.size() != 0) {
                MineContentRvAdapter contentRvAdapter =
                        new MineContentRvAdapter(R.layout.item_mine_dynamic_content, reviewListBean.rows,
                                getActivity());
                rv_content.setAdapter(contentRvAdapter);
                tv_count.setText("评论" + reviewListBean.rows.size());
            } else {
                tv_count.setText("评论0");
            }
        }
        EditText et_content = commentDialog.findViewById(R.id.et_content);
        LinearLayout ll_submit = commentDialog.findViewById(R.id.ll_submit);
        ll_submit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(et_content.getText().toString().trim())) {
                ToastUtils.show("请输入评论内容");
                return;
            }
            mPresenter.stateReview(state_id, et_content.getText().toString().trim(), item);
        });
    }

    //点击不同对象不同的风格
    private void setTypeCheck(LinearLayout llType) {
        for (int i = 0; i < llList.size(); i++) {
            LinearLayout linearLayout = llList.get(i);
            if (linearLayout.equals(llType)) {
                tvList.get(i).setTextSize(20);
                tvList.get(i).getPaint().setFakeBoldText(true);
                viewList.get(i).setVisibility(View.VISIBLE);
                llRootList.get(i).setVisibility(View.VISIBLE);
            } else {
                tvList.get(i).setTextSize(15);
                tvList.get(i).getPaint().setFakeBoldText(false);
                viewList.get(i).setVisibility(View.INVISIBLE);
                llRootList.get(i).setVisibility(View.GONE);
            }
        }
    }

    /**
     * 关注成功
     */
    @Override
    public void attentSuccess(int type, AnchorUserInfoBean anchorUserInfoBean) {
        if (type == 0) {
            ToastUtils.show("关注成功");
            anchorUserInfoBean.is_attent = 1;
        } else {
            ToastUtils.show("取消关注成功");
            anchorUserInfoBean.is_attent = 0;
        }
        mCollect.setImageResource(
                type == 0 ? R.drawable.ic_info_collect : R.drawable.ic_info_collect_normal);
    }

    @Override
    public void getUserInfoSuccess(AnchorUserInfoBean anchorUserInfoBean) {
        if (anchorUserInfoBean != null) {
            nick_name = anchorUserInfoBean.nick_name;
            head_img = anchorUserInfoBean.head_img;
            age = anchorUserInfoBean.age;
            is_attent = anchorUserInfoBean.is_attent + "";
            TextView title = bindView(R.id.action_title);
            title.setText(anchorUserInfoBean.nick_name);
            title.setTextColor(Res.color(R.color.app_main_color));
            bindView(R.id.action_bar_center_actions, false);
            bindText(R.id.tv_nickname, anchorUserInfoBean.nick_name);
            bindText(R.id.tv_sign,
                    (anchorUserInfoBean.sign != null && !TextUtils.isEmpty(anchorUserInfoBean.sign))
                            ? anchorUserInfoBean.sign : "我就是不一样的烟火");
            bindText(R.id.tv_fans_count,
                    (anchorUserInfoBean.fans != null) ? anchorUserInfoBean.fans + "" : "0");
            bindText(R.id.tv_video_amout, anchorUserInfoBean.video_amount + "");
            bindText(R.id.tv_city,
                    (anchorUserInfoBean.city != null && !TextUtils.isEmpty(anchorUserInfoBean.city))
                            ? anchorUserInfoBean.city : "未知");
            bindText(R.id.tv_star,
                    (anchorUserInfoBean.star != null && !TextUtils.isEmpty(anchorUserInfoBean.star))
                            ? anchorUserInfoBean.star : "未知");
            bindText(R.id.tv_work,
                    (anchorUserInfoBean.work != null && !TextUtils.isEmpty(anchorUserInfoBean.work))
                            ? anchorUserInfoBean.work : "未知");
            bindText(R.id.tv_time, "最后登录:" + anchorUserInfoBean.last_login);
            bindText(R.id.tv_id, "ID:" + anchorUserInfoBean.id);
            bindText(R.id.tv_weight,
                    (anchorUserInfoBean.kg != null && !TextUtils.isEmpty(anchorUserInfoBean.kg)) ?
                            anchorUserInfoBean.kg
                                    + "kg" : "未知");
            bindText(R.id.tv_height,
                    (anchorUserInfoBean.hight != null && !TextUtils.isEmpty(anchorUserInfoBean.hight)) ?
                            anchorUserInfoBean.hight
                                    + "cm" : "未知");
            bindText(R.id.tv_listen, anchorUserInfoBean.listen + "%");
            mAge.setBackgroundResource(
                    anchorUserInfoBean.sex == 1 ? R.drawable.shape_age_blue : R.drawable.shape_age_red);
            bindText(R.id.tv_age, anchorUserInfoBean.age + "");
            bindView(R.id.tv_age,
                    (anchorUserInfoBean.age != null && !TextUtils.isEmpty(anchorUserInfoBean.age)));
            //聊天
            bindView(R.id.ll_chat, view -> {
                Bundle bundle = new Bundle();
                bundle.putString("IM_ID", anchorUserInfoBean.user_id);
                bundle.putString("IM_NAME", anchorUserInfoBean.nick_name);
                bundle.putString("LANG_AMOUNT", anchorUserInfoBean.lang_amount + "");
                getLaunchHelper().startActivity(MineChatActivity.class, bundle);
            });

            if (anchorUserInfoBean.lable != null && anchorUserInfoBean.lable.size() != 0) {
                mLabel.setVisibility(View.VISIBLE);
                for (int i = 0; i < anchorUserInfoBean.lable.size(); i++) {
                    View view = View.inflate(getActivity(), R.layout.item_info_self, null);
                    TextView tvLabel = view.findViewById(R.id.tv_label);
                    tvLabel.setText(anchorUserInfoBean.lable.get(i));
                    mFlexboxSelf.addView(view);
                }
            } else {
                mLabel.setVisibility(View.VISIBLE);
            }

            //banner
            if (anchorUserInfoBean.cover != null && anchorUserInfoBean.cover.size() != 0) {
                bindView(R.id.iv_banner, false);
                bindView(R.id.banner, true);
                initBanner(anchorUserInfoBean.cover);
            } else {
                bindView(R.id.iv_banner, true);
                bindView(R.id.banner, false);
            }

            //是否关注
            mCollect.setImageResource(
                    anchorUserInfoBean.is_attent == 0 ? R.drawable.ic_info_collect_normal
                            : R.drawable.ic_info_collect);
            mLCollect.setOnClickListener(view -> {
                mPresenter.attent(user_id, anchorUserInfoBean.is_attent, anchorUserInfoBean);
            });

            //在线状态
            switch (anchorUserInfoBean.online_type) {
                case 1:
                    //在线
                    bindText(R.id.tv_online, "在线");
                    mLOnline.setBackgroundResource(R.drawable.shape_info_online_on);
                    break;
                case 2:
                    //离线
                    bindText(R.id.tv_online, "离线");
                    mLOnline.setBackgroundResource(R.drawable.shape_info_online_off);
                    break;
                case 3:
                    //通话中
                    bindText(R.id.tv_online, "通话中");
                    mLOnline.setBackgroundResource(R.drawable.shape_info_online_ing);
                    break;
            }

            //我的礼物
            mPresent.removeAllViews();
            if (anchorUserInfoBean.gift != null && anchorUserInfoBean.gift.size() != 0) {
                for (int i = 0; i < anchorUserInfoBean.gift.size(); i++) {
                    View view = View.inflate(getActivity(), R.layout.item_info_linearlayout, null);
                    CircleImageView iv_gift = view.findViewById(R.id.iv_gift);
                    ImageLoader.getInstance()
                            .displayImage(iv_gift, anchorUserInfoBean.gift.get(i).img, R.drawable.ic_placeholder,
                                    R.drawable.ic_placeholder_error);
                    mPresent.addView(view);
                }
                mPresentRoot.setVisibility(View.VISIBLE);
            } else {
                mPresentRoot.setVisibility(View.VISIBLE);
            }

            //与我亲密
            mIntimacy.removeAllViews();
            if (anchorUserInfoBean.near != null && anchorUserInfoBean.near.size() != 0) {
                for (int i = 0; i < anchorUserInfoBean.near.size(); i++) {
                    View view = View.inflate(getActivity(), R.layout.item_info_linearlayout, null);
                    CircleImageView iv_gift = view.findViewById(R.id.iv_gift);
                    ImageLoader.getInstance()
                            .displayImage(iv_gift, anchorUserInfoBean.near.get(i).head_img,
                                    R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
                    mIntimacy.addView(view);
                }
                mIntimacyRoot.setVisibility(View.VISIBLE);
            } else {
                mIntimacyRoot.setVisibility(View.VISIBLE);
            }

            //手机号是否被查看
            bindText(R.id.tv_mobile,
                    anchorUserInfoBean.is_look == 0 ? "手机号：***********" : "手机号：" + anchorUserInfoBean.phone);
            bindView(R.id.tv_check_mobile, anchorUserInfoBean.is_look == 0);
            bindView(R.id.tv_check_mobile, view -> {
                showCleckDialog(anchorUserInfoBean);
            });
        }
    }

    /**
     * 动态列表
     */
    @Override
    public void getStateListSuccess(MineDynamicBean mineDynamicBean) {
        if (mineDynamicBean != null) {
            if (mineDynamicBean.rows != null && mineDynamicBean.rows.size() != 0) {
                if (page > 1) {

                } else {
                    list.clear();
                }

                for (int i = 0; i < mineDynamicBean.rows.size(); i++) {
                    MineDynamicBean.RowsBean rowsBean = mineDynamicBean.rows.get(i);
                    //title
                    MineLocationDynamicBean mineLocationDynamicTitleBean = new MineLocationDynamicBean();
                    mineLocationDynamicTitleBean.userInfo = rowsBean.userInfo;
                    mineLocationDynamicTitleBean.title = rowsBean.title;
                    mineLocationDynamicTitleBean.create_at = rowsBean.create_at;
                    mineLocationDynamicTitleBean.itemType = 0;
                    list.add(mineLocationDynamicTitleBean);

                    if (rowsBean.is_video == 0) {
                        //pic
                        MineLocationDynamicBean mineLocationDynamicPicBean = new MineLocationDynamicBean();
                        mineLocationDynamicPicBean.imgs = rowsBean.imgs;
                        mineLocationDynamicPicBean.itemType = 1;
                        list.add(mineLocationDynamicPicBean);
                    } else {
                        //video
                        MineLocationDynamicBean mineLocationDynamicVideoBean = new MineLocationDynamicBean();
                        mineLocationDynamicVideoBean.video_pict_url =
                                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426510803&di=2bf93542ebc2b9a183695626fbffb5de&imgtype=0&src=http%3A%2F%2Fwww.desktx.cc%2Fd%2Ffile%2Fphone%2Fkatong%2F20161203%2F61ad328e4d8c741437ed00209a6bae35.jpg";
                        mineLocationDynamicVideoBean.itemType = 2;
                        list.add(mineLocationDynamicVideoBean);
                    }

                    //bottom
                    MineLocationDynamicBean mineLocationDynamicBottomBean = new MineLocationDynamicBean();
                    mineLocationDynamicBottomBean.like_count = rowsBean.like_count;
                    mineLocationDynamicBottomBean.is_like = rowsBean.is_like;
                    mineLocationDynamicBottomBean.review_count = rowsBean.review_count;
                    mineLocationDynamicBottomBean.is_attent = rowsBean.is_attent;
                    mineLocationDynamicBottomBean.id = rowsBean.id;
                    mineLocationDynamicBottomBean.userInfo = rowsBean.userInfo;

                    mineLocationDynamicBottomBean.itemType = 3;
                    list.add(mineLocationDynamicBottomBean);
                }
                if (adapter == null) {
                    adapter = new MineInfoDynamicRvAdapter(list, getActivity());
                    mDynamic.setAdapter(adapter);
                } else {
                    adapter.setNewData(list);
                }

                adapter.setOnItemDetailsDoCilckListener(
                        new MineInfoDynamicRvAdapter.OnItemDetailsDoCilckListener() {
                            @Override
                            public void onItemDetailsReportClick(String anchor_id, String state_id) {
                                showReportDialog("1", anchor_id, state_id);
                            }

                            @Override
                            public void onItemDetailsCommentClick(int state_id, MineLocationDynamicBean item) {
                                mPresenter.getReviewList(state_id + "", item, 1, 1000);
                            }

                            @Override
                            public void onItemDetailsLikeClick(int state_id, MineLocationDynamicBean item) {
                                mPresenter.stateLike(state_id + "", item);
                            }
                        });
                bindView(R.id.rl_dynamic, true);
                bindView(R.id.ll_empty, false);
            } else {
                if (page > 1) {

                } else {
                    //空页面
                    bindView(R.id.rl_dynamic, false);
                    bindView(R.id.ll_empty, true);
                }
            }
        }
    }

    /**
     * 动态点赞
     */
    @Override
    public void stateLikeSuccess(MineLocationDynamicBean item) {
        if (item.is_like == 0) {
            ToastUtils.show("点赞成功");
            item.is_like = 1;
            item.like_count = item.like_count + 1;
        } else {
            ToastUtils.show("取消点赞成功");
            item.is_like = 0;
            item.like_count = item.like_count - 1;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stateDelSuccess(int position) {
        adapter.getData().remove(position);
        adapter.getData().remove(position - 1);
        adapter.getData().remove(position - 2);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getReviewListSuccess(ReviewListBean reviewListBean, MineLocationDynamicBean item,
                                     String state_id) {
        showCommentDialog(reviewListBean, item, state_id);
    }

    @Override
    public void stateReviewSuccess(MineLocationDynamicBean item) {
        if (commentDialog.isShowing()) {
            commentDialog.dismiss();
        }
        item.review_count = item.review_count + 1;
        ToastUtils.show("评论成功，等待审核");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getPhotoAlbumSuccess(PhotoAlbumBean photoAlbumBean) {
        if (photoAlbumBean != null) {
            if (photoAlbumBean.rows != null && photoAlbumBean.rows.size() != 0) {
                //视频/照片
                photoAdapter =
                        new MineInfoDataRvAdapter(R.layout.item_info_data, photoAlbumBean.rows, getActivity());
                rv_video_photo.setAdapter(photoAdapter);
                bindView(R.id.rv_video_photo, true);
                bindView(R.id.ll_empty_photo, false);
            } else {
                bindView(R.id.rv_video_photo, false);
                bindView(R.id.ll_empty_photo, true);
            }
        }
    }

    @Override
    public void lookPhoneSuccess(AnchorUserInfoBean anchorUserInfoBean) {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        tv_check_mobile.setVisibility(View.GONE);
        mMobile.setText("手机号：" + anchorUserInfoBean.phone);
    }

    @Override
    public void lookPhoneFail(int code, String msg) {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        if (code == 10000) {
            mPresenter.getSysMoney();
        } else {
            ToastUtils.show(msg);
        }
    }

    @Override
    public void getSysMoneySuccess(SysMoneyBean sysMoneyBean) {
        if (sysMoneyBean != null && sysMoneyBean.rows != null && sysMoneyBean.rows.size() != 0) {
            showRechargeDialog(sysMoneyBean.rows);
        }
    }

    @Override
    public void orderAddSuccess(PayInfo payInfo) {
        if (rechargeDialog.isShowing()) {
            rechargeDialog.dismiss();
        }
        PayUtils.getInstance()
                .pay(getActivity(), type, payInfo)
                .getPayResult(new PayUtils.PayCallBack() {
                    @Override
                    public void onPaySuccess(int type) {

                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("支付失败，请重试");
                    }
                });
    }

    @Override
    public void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean) {
        if (userIsAnchorBean != null) {
            is_vip = userIsAnchorBean.is_vip;
        }
    }

    @Override
    public void liveStartSuccess(LiveStartBean liveStartBean) {
        if (liveStartBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString("USER_ID", user_id + "");
            bundle.putString("HEAD_IMG", head_img);
            bundle.putString("NICK_NAME", nick_name);
            bundle.putString("USER_AGE", age);
            bundle.putString("IS_ATTENT", is_attent);
            bundle.putString("LIVE_ID", liveStartBean.live_id + "");
            bundle.putString("TYPE_IN", "1");
            getLaunchHelper().startActivity(TRTCMainActivity.class, bundle);
        } else {
            ToastUtils.show("开启视频出错!");
        }
    }

    private SysGiftBean.ResultBean item = null;

    @Override
    public void sendGiftSuccess(SysGiftBean.ResultBean resultBean) {
        ToastUtils.show("打赏成功!");
        item = null;
    }

    @Override
    public void getSysGiftSuccess(SysGiftBean sysGiftBean) {
        if (sysGiftBean != null) {
            if (sysGiftBean.rows != null && sysGiftBean.rows.size() != 0) {
                showPresentDialog(sysGiftBean.user_amount, sysGiftBean.rows);
            }
        } else {
            ToastUtils.show("获取礼物列表失败,请重试");
        }
    }


    /**
     * 礼物列表
     */
    private void showPresentDialog(BigDecimal user_amount, List<SysGiftBean.ResultBean> rows) {
        presentDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_present)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .build();
        presentDialog.show();

        RecyclerView rv_present = presentDialog.findViewById(R.id.rv_present);
        rv_present.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        //        CommItemDecoration horizontal = CommItemDecoration.createHorizontal(getActivity(), Color.parseColor("#626262"), 2);
        //        CommItemDecoration vertical = CommItemDecoration.createVertical(getActivity(), Color.parseColor("#626262"), 2);
        //        rv_present.addItemDecoration(horizontal);
        //        rv_present.addItemDecoration(vertical);
        TextView tv_coin = presentDialog.findViewById(R.id.tv_coin);
        tv_coin.setText("可用金币：" + user_amount);
        DelayClickTextView tv_commit = presentDialog.findViewById(R.id.tv_commit);
        DelayClickTextView tv_recharge = presentDialog.findViewById(R.id.tv_recharge);
        tv_recharge.setOnClickListener(view -> {
            if (presentDialog.isShowing()) {
                presentDialog.dismiss();
            }
            //充值
            Bundle bundle = new Bundle();
            bundle.putInt("TYPE", 0);
            getLaunchHelper().startActivity(MineRechargeActivity.class, bundle);
        });
        MineSysGiftRvAdapter adapter =
                new MineSysGiftRvAdapter(R.layout.item_sys_present, rows, getActivity());
        rv_present.setAdapter(adapter);

        adapter.setOnItemChcekCheckListener(resultBean -> {
            adapter.notifyDataSetChanged();
            item = resultBean;
        });
        tv_commit.setOnClickListener(view -> {
            if (presentDialog.isShowing()) {
                presentDialog.dismiss();
            }

            if (item != null) {
                mPresenter.sendGift(user_id, item.id + "", item);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.start();//开始轮播
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.pause();//暂停轮播
        }
    }

    @Override
    public void onDestroy() {
        if (mBanner != null) {
            mBanner.pause();//暂停轮播
        }
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void scrollHeight(int h) {
        float alpha = (h * 1.0f / alphaThreshold);
        if (alpha > 1) {
            alpha = 1;
        }
        setAlpha(alpha, Res.color(R.color.white));
    }

    @Override
    public void actionUp(int h) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setAlpha(float alpha, int color) {
        bindView(R.id.action_bar_center_actions, alpha > 0);
        bindView(R.id.back_name, alpha > 0);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = hsv[0];
        hsv[1] = hsv[1];
        hsv[2] = hsv[2];
        int darkerColor = Color.HSVToColor((int) (alpha * 255), hsv);
        bindView(R.id.action_bar).setBackgroundColor(darkerColor);
        bindView(R.id.back_name).setAlpha(alpha);
        ImageView imageView = bindView(R.id.ll_add_icon);
        ImageView backIcon = bindView(R.id.back_icon);
        ColorStateList csl =
                getResources().getColorStateList(alpha > 0 ? R.color.app_main_color : R.color.white);
        imageView.setImageTintList(csl);
        backIcon.setImageTintList(csl);
        imageView.setAlpha(alpha == 0 ? 1 : alpha);
        backIcon.setAlpha(alpha == 0 ? 1 : alpha);
        bindView(R.id.action_bar_center_actions).setAlpha(alpha);
    }
}
