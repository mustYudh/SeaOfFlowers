package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.home.activity.HomeReportActivity;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.BannerInfoViewHolder;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineContentRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineInfoDataRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineInfoDynamicRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePersonalInfoPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePersonalInfoViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineSysMoneyGvAdapter;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
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
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MinePersonalInfoActivity extends BaseActivity implements MinePersonalInfoViewer, View.OnClickListener {
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    private List<LinearLayout> llList = new ArrayList<>();
    private List<LinearLayout> llRootList = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private MZBannerView mBanner;
    private LinearLayout ll_first, ll_second, ll_third, mIntimacy, mPresent;
    private DialogUtils reportDialog, shareDialog, checkDialog, rechargeDialog, commentDialog;

    @PresenterLifeCycle
    private MinePersonalInfoPresenter mPresenter = new MinePersonalInfoPresenter(this);
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

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_personal_info_view);
    }

    @Override
    protected void loadData() {
        mPresenter.getIsAnchor();
        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getString("USER_ID");
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
        mIntimacy = bindView(R.id.ll_intimacy);
        mPresent = bindView(R.id.ll_present);
        LinearLayout mIntimacyRoot = bindView(R.id.ll_intimacy_root);
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

        mIntimacy.removeAllViews();
        for (int i = 0; i < 6; i++) {
            View view = View.inflate(getActivity(), R.layout.item_info_linearlayout, null);
            mIntimacy.addView(view);
        }


        mPresenter.getUserInfo(user_id);
        mPresenter.getStateList(user_id);
        mPresenter.getPhotoAlbum(user_id);


    }

    /**
     * banner 初始化
     *
     * @param xbanner
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
        }
    }

    /**
     * 举报弹窗
     */
    private void showReportDialog(String anchor_id, String state_id) {
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
                    Bundle bundle = new Bundle();
                    bundle.putString("ANCHOR_ID", anchor_id);
                    bundle.putString("STATE_ID", state_id);
                    LauncherHelper.from(getActivity()).startActivity(HomeReportActivity.class, bundle);
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
    private void showCommentDialog(ReviewListBean reviewListBean, MineLocationDynamicBean item, String state_id) {
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
                MineContentRvAdapter contentRvAdapter = new MineContentRvAdapter(R.layout.item_mine_dynamic_content, reviewListBean.rows, getActivity());
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
        mCollect.setImageResource(type == 0 ? R.drawable.ic_info_collect : R.drawable.ic_info_collect_normal);

    }

    @Override
    public void getUserInfoSuccess(AnchorUserInfoBean anchorUserInfoBean) {
        if (anchorUserInfoBean != null) {
            bindText(R.id.tv_nickname, anchorUserInfoBean.nick_name);
            bindText(R.id.tv_sign, TextUtils.isEmpty(anchorUserInfoBean.sign) ? "我就是不一样的烟火" : anchorUserInfoBean.sign);
            bindText(R.id.tv_fans_count, anchorUserInfoBean.fans + "");
            bindText(R.id.tv_video_amout, anchorUserInfoBean.video_amount + "");
            bindText(R.id.tv_city, anchorUserInfoBean.city);
            bindText(R.id.tv_star, anchorUserInfoBean.star);
            bindText(R.id.tv_work, anchorUserInfoBean.work);
            bindText(R.id.tv_time, "最后登录:" + anchorUserInfoBean.last_login);
            bindText(R.id.tv_id, "ID:" + anchorUserInfoBean.id);
            bindText(R.id.tv_weight, anchorUserInfoBean.kg + "kg");
            bindText(R.id.tv_height, anchorUserInfoBean.hight + "cm");
            bindText(R.id.tv_listen, anchorUserInfoBean.listen + "%");
            mAge.setBackgroundResource(anchorUserInfoBean.sex == 1 ? R.drawable.shape_age_blue : R.drawable.shape_age_red);
            bindText(R.id.tv_age, anchorUserInfoBean.age + "");
            bindView(R.id.tv_age, !TextUtils.isEmpty(anchorUserInfoBean.age));
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
                mLabel.setVisibility(View.GONE);
            }

            //banner
            if (anchorUserInfoBean.cover != null) {
                initBanner(anchorUserInfoBean.cover);
            }

            //是否关注
            mCollect.setImageResource(anchorUserInfoBean.is_attent == 0 ? R.drawable.ic_info_collect_normal : R.drawable.ic_info_collect);
            mLCollect.setOnClickListener(view -> {
                mPresenter.attent(user_id, anchorUserInfoBean.is_attent, anchorUserInfoBean);
            });

            //在线状态
            switch (anchorUserInfoBean.online_type) {
                case 0:
                    //离线
                    bindText(R.id.tv_online, "离线");
                    mLOnline.setBackgroundResource(R.drawable.shape_info_online_off);
                    break;
                case 1:
                    //在线
                    bindText(R.id.tv_online, "在线");
                    mLOnline.setBackgroundResource(R.drawable.shape_info_online_on);
                    break;
                case 2:
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
                    ImageLoader.getInstance().displayImage(iv_gift, anchorUserInfoBean.gift.get(i).img, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
                    mPresent.addView(view);
                }
                mPresentRoot.setVisibility(View.VISIBLE);
            } else {
                mPresentRoot.setVisibility(View.GONE);
            }

            //手机号是否被查看
            bindText(R.id.tv_mobile, anchorUserInfoBean.is_look == 0 ? "手机号：***********" : "手机号：" + anchorUserInfoBean.phone);
            bindView(R.id.tv_check_mobile, anchorUserInfoBean.is_look == 0);
            bindView(R.id.tv_check_mobile, view -> {
                showCleckDialog(anchorUserInfoBean);
            });
        }
    }

    /**
     * 动态列表
     *
     * @param mineDynamicBean
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
                        mineLocationDynamicVideoBean.video_pict_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426510803&di=2bf93542ebc2b9a183695626fbffb5de&imgtype=0&src=http%3A%2F%2Fwww.desktx.cc%2Fd%2Ffile%2Fphone%2Fkatong%2F20161203%2F61ad328e4d8c741437ed00209a6bae35.jpg";
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

                adapter.setOnItemDetailsDoCilckListener(new MineInfoDynamicRvAdapter.OnItemDetailsDoCilckListener() {
                    @Override
                    public void onItemDetailsReportClick(String anchor_id, String state_id) {
                        showReportDialog(anchor_id, state_id);
                    }

                    @Override
                    public void onItemDetailsCommentClick(int state_id, MineLocationDynamicBean item) {
                        mPresenter.getReviewList(state_id + "", item, 1, 10);
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
     *
     * @param item
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
    public void getReviewListSuccess(ReviewListBean reviewListBean, MineLocationDynamicBean item, String state_id) {
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
                photoAdapter = new MineInfoDataRvAdapter(R.layout.item_info_data, photoAlbumBean.rows, getActivity());
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
        mMobile.setText("手机号：" + anchorUserInfoBean.phone);
    }

    @Override
    public void lookPhoneFail(int code, String msg) {

        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        if (code == 10000) {
            mPresenter.getSysMoney();
        }else {
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
        PayUtils.getInstance().pay(getActivity(), type, payInfo)
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
        super.onDestroy();
        if (mBanner != null) {
            mBanner.pause();//暂停轮播
        }
    }
}
