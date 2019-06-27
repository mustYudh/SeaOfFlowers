package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRedactDataPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRedactDataViewer;
import com.hzrcht.seaofflowers.module.view.ClearEditText;
import com.hzrcht.seaofflowers.module.view.MyOneLineView;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.hzrcht.seaofflowers.utils.PhotoUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.CircleImageView;
import com.yu.common.ui.NoSlidingGridView;

import java.util.ArrayList;
import java.util.List;


public class MineRedactDataActivity extends BaseBarActivity implements MineRedactDataViewer, View.OnClickListener {
    private List<String> workList = new ArrayList<>();
    @PresenterLifeCycle
    private MineRedactDataPresenter presenter = new MineRedactDataPresenter(this);
    private MyOneLineView view_work;
    private MyOneLineView view_mobile;
    private DialogUtils dataDialog;
    private List<LocalMedia> allLocationSelectedPicture = new ArrayList<>();
    private GridAdapter adapter;
    private NoSlidingGridView gv_pic;
    private MyOneLineView view_height;
    private MyOneLineView view_nickname;
    private MyOneLineView view_age;
    private MyOneLineView view_weight;
    private int picType = 0;
    private CircleImageView iv_headimg;
    private MyOneLineView view_signature;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_redact_data_view);
    }

    @Override
    protected void loadData() {
        workList.add("网红");
        workList.add("模特");
        workList.add("白领");
        workList.add("护士");
        workList.add("空姐");
        workList.add("学生");
        workList.add("健身教练");
        setTitle("编辑资料");
        gv_pic = bindView(R.id.gv_pic);
        RelativeLayout rl_headimg = bindView(R.id.rl_headimg);
        iv_headimg = bindView(R.id.iv_headimg);
        view_nickname = bindView(R.id.view_nickname);
        view_work = bindView(R.id.view_work);
        view_mobile = bindView(R.id.view_mobile);
        view_height = bindView(R.id.view_height);
        view_age = bindView(R.id.view_age);
        view_weight = bindView(R.id.view_weight);
        MyOneLineView view_constellation = bindView(R.id.view_constellation);
        MyOneLineView view_city = bindView(R.id.view_city);
        view_signature = bindView(R.id.view_signature);
        MyOneLineView view_label = bindView(R.id.view_label);

        view_nickname.init("昵称").setTextRight("聊友:21327").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_work.init("职业").setTextRight("网红").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_mobile.init("手机号").setTextRight("15158816233").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_height.init("身高").setTextRight("160CM").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_age.init("年龄").setTextRight("20岁").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_weight.init("体重").setTextRight("40kg").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_constellation.init("星座").setTextRight("双子座").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_city.init("城市").setTextRight("杭州市").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_signature.init("个性签名").setTextRight("请输入个性签名(选填)").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_label.init("形象标签").setTextRight("请输入形象标签(选填)").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);

        view_nickname.setOnClickListener(this);
        view_work.setOnClickListener(this);
        view_mobile.setOnClickListener(this);
        view_height.setOnClickListener(this);
        view_age.setOnClickListener(this);
        view_weight.setOnClickListener(this);
        view_constellation.setOnClickListener(this);
        view_city.setOnClickListener(this);
        view_signature.setOnClickListener(this);
        view_label.setOnClickListener(this);
        rl_headimg.setOnClickListener(this);

        adapter = new GridAdapter();
        gv_pic.setAdapter(adapter);

    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_nickname:
                showDataDialog(0);
                break;
            case R.id.view_work:
                initWordData();
                break;
            case R.id.view_mobile:
                Intent intentMobile = new Intent(this, PhoneVerificationActivity.class);
                startActivityForResult(intentMobile, 0);
                break;
            case R.id.rl_headimg:
                picType = 1;
                PhotoUtils.changeAvatar(getActivity());
                break;
            case R.id.view_height:
                initWordData();
                break;
            case R.id.view_age:
                initWordData();
                break;
            case R.id.view_weight:
                initWordData();
                break;
            case R.id.view_constellation:
                initWordData();
                break;
            case R.id.view_city:
                initWordData();
                break;
            case R.id.view_signature:
                showDataDialog(1);
                break;
            case R.id.view_label:

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (picType == 0) {
                        for (int i = 0; i < selectList.size(); i++) {
                            allLocationSelectedPicture.add(selectList.get(i));
                        }
                        if (adapter != null) {
                            gv_pic.setAdapter(adapter);
                        }
                    } else {
                        ImageLoader.getInstance().displayImage(iv_headimg, selectList.get(0).getCompressPath());
                    }
                    break;
            }
        }
    }

    private void initWordData() {
        OptionsPickerView JiGouOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                view_work.setTextRight(workList.get(options1));
            }
        })

                .setSubmitText("完成")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(Color.parseColor("#9897E7"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                //.setLinkage(false)//设置是否联动，默认true
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();

        JiGouOptions.setNPicker(workList, null, null);
        JiGouOptions.show();
    }

    private ClearEditText et_content;

    private void showDataDialog(int type) {
        dataDialog = new DialogUtils.Builder(this).view(R.layout.dialog_mine_data)
                .gravity(Gravity.CENTER)
                .cancelTouchout(true)
                .style(R.style.Dialog_NoAnimation)
                .addViewOnclick(R.id.iv_cancle, view -> {
                    if (dataDialog.isShowing()) {
                        dataDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.tv_commit, view -> {
                    if (dataDialog.isShowing()) {
                        dataDialog.dismiss();
                    }
                    switch (type) {
                        case 0:
                            //昵称
                            view_nickname.setTextRight(et_content.getText().toString().trim());
                            break;
                        case 1:
                            //个人签名
                            view_signature.setTextRight(et_content.getText().toString().trim());
                            break;
                    }
                })
                .build();
        dataDialog.show();
        TextView tv_title = dataDialog.findViewById(R.id.tv_title);
        et_content = dataDialog.findViewById(R.id.et_content);
        switch (type) {
            case 0:
                //昵称
                tv_title.setText("编辑昵称");
                break;
            case 1:
                //个人签名
                tv_title.setText("编辑个人签名");
                break;
        }

    }


    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (allLocationSelectedPicture.size() == 4) {
                return allLocationSelectedPicture.size();
            }
            return allLocationSelectedPicture.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View view, final ViewGroup parent) {
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.gv_item, null);
                holder = new ViewHolder();
                holder.iv_picture_item = (ImageView) view.findViewById(R.id.iv_picture_item);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (position == allLocationSelectedPicture.size()) {
                if (position == 4) {
                    holder.iv_picture_item.setVisibility(View.GONE);
                } else {
                    holder.iv_picture_item.setVisibility(View.VISIBLE);
                }

                holder.iv_picture_item.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_mine_pic_add));
                holder.iv_picture_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picType = 0;
                        PhotoUtils.changeAvatar(getActivity(), allLocationSelectedPicture);
                    }
                });
            } else {
                ImageLoader.getInstance().displayImage(holder.iv_picture_item, allLocationSelectedPicture.get(position).getCompressPath() + "");
//                holder.ivDemo.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_mine_pic_add));

            }

            return view;
        }
    }

    public class ViewHolder {
        public ImageView iv_picture_item;
    }


}
