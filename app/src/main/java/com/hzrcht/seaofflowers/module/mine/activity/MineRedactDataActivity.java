package com.hzrcht.seaofflowers.module.mine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineRedactDataGvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineSysLabelGvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.CityJsonBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysLabelBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserDetailBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRedactDataPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRedactDataViewer;
import com.hzrcht.seaofflowers.module.view.ClearEditText;
import com.hzrcht.seaofflowers.module.view.MyOneLineView;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.hzrcht.seaofflowers.utils.MineGetJsonDataUtil;
import com.hzrcht.seaofflowers.utils.PhotoUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.CircleImageView;
import com.yu.common.ui.NoSlidingGridView;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MineRedactDataActivity extends BaseBarActivity implements MineRedactDataViewer, View.OnClickListener {
    private List<String> workList = new ArrayList<>();
    private List<String> weightList = new ArrayList<>();
    private List<String> heightList = new ArrayList<>();
    private List<String> ageList = new ArrayList<>();
    private List<String> constellationList = new ArrayList<>();
    private List<String> allLocationSelectedPicture = new ArrayList<>();
    @PresenterLifeCycle
    private MineRedactDataPresenter mPresenter = new MineRedactDataPresenter(this);
    private MyOneLineView view_work;
    private MyOneLineView view_mobile;
    private DialogUtils dataDialog, photoDelDialog;
    private NoSlidingGridView gv_pic;
    private MyOneLineView view_height;
    private MyOneLineView view_nickname;
    private MyOneLineView view_age;
    private MyOneLineView view_weight;
    private int picType = 0;
    private CircleImageView iv_headimg;
    private MyOneLineView view_signature;
    private MyOneLineView view_constellation;
    private DialogUtils labelDialog;
    private List<SysLabelBean.RowsBean> labelList = new ArrayList<>();
    private MyOneLineView view_city;
    private MyOneLineView view_label;
    private String coverName = "";
    private String head_img = "";
    private String nick_name = "";
    private String work = "";
    private String phone = "";
    private String age = "";
    private String kg = "";
    private String star = "";
    private String city = "";
    private String sign = "";
    private String hight = "";
    private String lableName = "";
    private MineRedactDataGvAdapter adapter;
    private int audit = 1;

    private List<CityJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private static boolean isLoaded = false;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_redact_data_view);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        initJsonData();
        mPresenter.getUserDetail();
        mPresenter.getSysLabel();
        for (int i = 150; i < 205; i++) {
            if (i % 5 == 0) {
                heightList.add(i + "");
            }
        }

        for (int i = 18; i < 61; i++) {
            ageList.add(i + "");
        }

        for (int i = 40; i < 105; i++) {
            if (i % 5 == 0) {
                weightList.add(i + "");
            }
        }


        constellationList.add("白羊座");
        constellationList.add("金牛座");
        constellationList.add("双子座");
        constellationList.add("巨蟹座");
        constellationList.add("狮子座");
        constellationList.add("处女座");
        constellationList.add("天秤座");
        constellationList.add("天蝎座");
        constellationList.add("射手座");
        constellationList.add("魔蝎座");
        constellationList.add("水瓶座");
        constellationList.add("双鱼座");

        workList.add("网红");
        workList.add("模特");
        workList.add("白领");
        workList.add("护士");
        workList.add("空姐");
        workList.add("学生");
        workList.add("健身教练");
        workList.add("医生");
        workList.add("客服");
        workList.add("老师");
        workList.add("其他");
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
        view_constellation = bindView(R.id.view_constellation);
        view_city = bindView(R.id.view_city);
        view_signature = bindView(R.id.view_signature);
        view_label = bindView(R.id.view_label);

        view_nickname.init("昵称")
                .setTextRight("聊友:21327")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_work.init("职业")
                .setTextRight("网红")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_mobile.init("手机号")
                .setTextRight("15158816233")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_height.init("身高")
                .setTextRight("160CM")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_age.init("年龄")
                .setTextRight("20岁")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_weight.init("体重")
                .setTextRight("40kg")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_constellation.init("星座")
                .setTextRight("双子座")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_city.init("城市")
                .setTextRight("杭州市")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_signature.init("个性签名")
                .setTextRight("请输入个性签名(选填)")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);
        view_label.init("形象标签")
                .setTextRight("请输入形象标签(选填)")
                .showRightText(true)
                .showArrow(true)
                .setTextRightColor(R.color.color_CCCCCC)
                .setIvRightIcon(R.drawable.ic_arrow_gray);

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

        adapter = new MineRedactDataGvAdapter(getActivity(), allLocationSelectedPicture);
        gv_pic.setAdapter(adapter);
        adapter.setOnItemChcekCheckListener(new MineRedactDataGvAdapter.OnItemChcekCheckListener() {
            @Override
            public void setOnItemChcekCheckClick() {
                picType = 0;
                PhotoUtils.changeAvatarOther(getActivity(), allLocationSelectedPicture, 4, "上传您的主播封面,以供主播页展示");
            }

            @Override
            public void setOnItemChcekCheckDelClick(int position) {
                showPhotoDelDialog(position);
            }
        });
        bindView(R.id.tv_commit, view -> {
            if (audit == 0) {
                ToastUtils.show("审核中，无法修改");
                return;
            }
            if (allLocationSelectedPicture.size() == 0) {
                ToastUtils.show("请至少上传一张封面图");
                return;
            }

            if (TextUtils.isEmpty(head_img)) {
                ToastUtils.show("请上传头像");
                return;
            }

            if (TextUtils.isEmpty(nick_name)) {
                ToastUtils.show("请填写昵称");
                return;
            }

            if (TextUtils.isEmpty(work)) {
                ToastUtils.show("请填写职业");
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                ToastUtils.show("请填写手机号码");
                return;
            }

            if (TextUtils.isEmpty(hight)) {
                ToastUtils.show("请选择身高");
                return;
            }

            if (TextUtils.isEmpty(age)) {
                ToastUtils.show("请选择年龄");
                return;
            }

            if (TextUtils.isEmpty(kg)) {
                ToastUtils.show("请选择体重");
                return;
            }

            if (TextUtils.isEmpty(star)) {
                ToastUtils.show("请选择星座");
                return;
            }

            if (TextUtils.isEmpty(city)) {
                ToastUtils.show("请选择城市");
                return;
            }

            loadDialog.show();
            final StringBuilder urlNo = new StringBuilder();
            if (allLocationSelectedPicture.size() != 0) {
                for (int i = 0; i < allLocationSelectedPicture.size(); i++) {
                    urlNo.append(allLocationSelectedPicture.get(i));
                    if (i < allLocationSelectedPicture.size() - 1) {
                        urlNo.append(",");
                    }
                }
            }

            mPresenter.setUserDetail(urlNo.toString().trim(), head_img, nick_name, work, phone, age, kg, star, city, sign, hight, lableName);
        });
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_nickname:
                showDataDialog(0);
                break;
            case R.id.view_work:
                initConfigData(0);
                break;
            case R.id.view_mobile:
                getLaunchHelper().startActivityForResult(PhoneVerificationActivity.class, 1);
                break;
            case R.id.rl_headimg:
                picType = 1;
                PhotoUtils.changeAvatar(getActivity(), "上传您的头像,以供展示");
                break;
            case R.id.view_height:
                initConfigData(1);
                break;
            case R.id.view_age:
                initConfigData(2);
                break;
            case R.id.view_weight:
                initConfigData(3);
                break;
            case R.id.view_constellation:
                initConfigData(4);
                break;
            case R.id.view_city:
                if (isLoaded) {
                    showPickerView();
                } else {
                    ToastUtils.show("获取城市数据失败");
                    initJsonData();
                }
                break;
            case R.id.view_signature:
                showDataDialog(1);
                break;
            case R.id.view_label:
                if (labelList != null && labelList.size() != 0) {
                    showLabelDialog();
                } else {
                    ToastUtils.show("获取标签失败");
                }
                break;
            default:
        }
    }

    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 1:
                String mobile = data.getStringExtra("MOBILE");
                phone = mobile;
                view_mobile.setTextRight(mobile);
                break;
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    if (picType == 0) {
                        loadDialog.show();
                        // 图片选择结果回调
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    allLocationSelectedPicture.put(count, compressPath);
//                        for (int i = 0; i < selectList.size(); i++) {
//                            allLocationSelectedPicture.add(selectList.get(i).getCompressPath());
//                        }
//                        if (adapter != null) {
//                            gv_pic.setAdapter(adapter);
//                        }
                        File imageFileCrmera = new File(selectList.get(0).getCompressPath());
                        /** 上传图片*/
                        new Compressor(getActivity())
                                .compressToFileAsFlowable(imageFileCrmera)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<File>() {
                                    @Override
                                    public void accept(File file) {
                                        mPresenter.uploadCover(file);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        throwable.printStackTrace();
//                                        showError(throwable.getMessage());
                                        if (loadDialog.isShowing()) {
                                            loadDialog.dismiss();
                                        }
                                        ToastUtils.show("封面图片压缩失败,请重试");
                                    }
                                });
                    } else {
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                        ImageLoader.getInstance().displayImage(iv_headimg, selectList.get(0).getCompressPath());
                        File imageFileCrmera = new File(selectList.get(0).getCompressPath());
                        /** 上传图片*/
                        new Compressor(getActivity())
                                .compressToFileAsFlowable(imageFileCrmera)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<File>() {
                                    @Override
                                    public void accept(File file) {
                                        mPresenter.uploadImg(file);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        throwable.printStackTrace();
//                                        showError(throwable.getMessage());
                                        ToastUtils.show("头像压缩失败,请重试");
                                    }
                                });
                    }
                    break;
                default:
            }
        }
    }

    private void initConfigData(int type) {
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                switch (type) {
                    case 0:
                        view_work.setTextRight(workList.get(options1));
                        work = workList.get(options1);
                        break;
                    case 1:
                        view_height.setTextRight(heightList.get(options1) + "CM");
                        hight = heightList.get(options1);
                        break;
                    case 2:
                        view_age.setTextRight(ageList.get(options1) + "岁");
                        age = ageList.get(options1);
                        break;
                    case 3:
                        view_weight.setTextRight(weightList.get(options1) + "kg");
                        kg = weightList.get(options1);
                        break;
                    case 4:
                        view_constellation.setTextRight(constellationList.get(options1));
                        star = constellationList.get(options1);
                        break;
                }
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
        switch (type) {
            case 0:
                options.setNPicker(workList, null, null);
                break;
            case 1:
                options.setNPicker(heightList, null, null);
                break;
            case 2:
                options.setNPicker(ageList, null, null);
                break;
            case 3:
                options.setNPicker(weightList, null, null);
                break;
            case 4:
                options.setNPicker(constellationList, null, null);
                break;
        }

        options.show();
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
                            nick_name = et_content.getText().toString().trim();
                            break;
                        case 1:
                            //个人签名
                            view_signature.setTextRight(et_content.getText().toString().trim());
                            sign = et_content.getText().toString().trim();
                            break;
                        default:
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
                et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                break;
            case 1:
                //个人签名
                tv_title.setText("编辑个人签名");
                et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
                break;
            default:
        }
    }

    private void showPhotoDelDialog(int position) {
        photoDelDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_normal)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .settext("请选择", R.id.tv_title)
                .settext("删除", R.id.tv_bottom)
                .addViewOnclick(R.id.tv_cancle, view -> {
                    if (photoDelDialog.isShowing()) {
                        photoDelDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.tv_bottom, view -> {
                    if (photoDelDialog.isShowing()) {
                        photoDelDialog.dismiss();
                    }
                    allLocationSelectedPicture.remove(position);
                    if (adapter != null) {
                        gv_pic.setAdapter(adapter);
                    }

                })
                .build();
        photoDelDialog.show();
        photoDelDialog.findViewById(R.id.tv_top).setVisibility(View.GONE);
        photoDelDialog.findViewById(R.id.view_middle).setVisibility(View.GONE);
    }

    @Override
    public void getSysLabelSuccess(SysLabelBean sysLabelBean) {
        if (sysLabelBean != null && sysLabelBean.rows != null) {
            labelList = sysLabelBean.rows;
        }
    }

    @Override
    public void getUserDetailSuccess(UserDetailBean userDetailBean) {
        if (userDetailBean != null) {
            view_nickname.setTextRight((userDetailBean.nick_name != null && !TextUtils.isEmpty(userDetailBean.nick_name)) ? userDetailBean.nick_name : "请设置昵称");
            view_work.setTextRight((userDetailBean.work != null && !TextUtils.isEmpty(userDetailBean.work)) ? userDetailBean.work : "请设置职业");
            view_mobile.setTextRight((userDetailBean.phone != null && !TextUtils.isEmpty(userDetailBean.phone)) ? userDetailBean.phone : "请设置手机号");
            view_height.setTextRight((userDetailBean.hight != null && !TextUtils.isEmpty(userDetailBean.hight)) ? userDetailBean.hight + "CM" : "请设置身高");
            view_age.setTextRight((userDetailBean.age != null && !TextUtils.isEmpty(userDetailBean.age)) ? userDetailBean.age + "岁" : "请设置年龄");
            view_weight.setTextRight((userDetailBean.kg != null && !TextUtils.isEmpty(userDetailBean.kg)) ? userDetailBean.kg + "kg" : "请设置体重");
            view_constellation.setTextRight((userDetailBean.star != null && !TextUtils.isEmpty(userDetailBean.star)) ? userDetailBean.star : "请设置星座");
            view_city.setTextRight((userDetailBean.city != null && !TextUtils.isEmpty(userDetailBean.city)) ? userDetailBean.city : "请设置城市");
            view_signature.setTextRight((userDetailBean.sign != null && !TextUtils.isEmpty(userDetailBean.sign)) ? userDetailBean.sign : "请输入个人签名(选填)");
            ImageLoader.getInstance().displayImage(iv_headimg, userDetailBean.head_img, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
            final StringBuilder label = new StringBuilder();
            if (userDetailBean.lable != null && userDetailBean.lable.size() != 0) {
                for (int i = 0; i < userDetailBean.lable.size(); i++) {
                    label.append(userDetailBean.lable.get(i));
                    if (i < userDetailBean.lable.size() - 1) {
                        label.append(",");
                    }
                }
                view_label.setTextRight(label.toString().trim());
                lableName = label.toString().trim();
            } else {
                view_label.setTextRight("请输入形象标签(选填)");
                lableName = "";
            }
            final StringBuilder cover = new StringBuilder();
            if (userDetailBean.cover != null && userDetailBean.cover.size() != 0) {
                for (int i = 0; i < userDetailBean.cover.size(); i++) {
                    cover.append(userDetailBean.cover.get(i));
                    if (i < userDetailBean.cover.size() - 1) {
                        cover.append(",");
                    }
                }
                coverName = cover.toString().trim();
                allLocationSelectedPicture.clear();
                allLocationSelectedPicture.addAll(userDetailBean.cover);
                if (adapter != null) {
                    gv_pic.setAdapter(adapter);
                }
            } else {
                coverName = "";
                allLocationSelectedPicture.clear();
            }

            work = userDetailBean.work;
            phone = userDetailBean.phone;
            head_img = userDetailBean.head_img;
            nick_name = userDetailBean.nick_name;
            age = userDetailBean.age + "";
            kg = userDetailBean.kg + "";
            hight = userDetailBean.hight + "";
            star = userDetailBean.star;
            city = userDetailBean.city;
            sign = userDetailBean.sign;

            audit = userDetailBean.audit;
        }
    }

    @Override
    public void setUserDetailSuccess() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        ToastUtils.show("修改资料成功");
        setResult(1);
        finish();
    }

    @Override
    public void uploadImgSuccess(UploadImgBean uploadImgBean) {
        if (uploadImgBean != null) {
            head_img = uploadImgBean.url;
        }
    }

    @Override
    public void uploadCoverSuccess(UploadImgBean uploadImgBean) {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        if (uploadImgBean != null) {
            allLocationSelectedPicture.add(uploadImgBean.url + "");
            if (adapter != null) {
                gv_pic.setAdapter(adapter);
            }
        } else {
            ToastUtils.show("封面图片上传失败,请重试");
        }
    }

    @Override
    public void uploadImgFail() {

    }

    @Override
    public void uploadCoverFail() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        ToastUtils.show("封面图片上传失败,请重试");
    }


    private String temp = "";

    /**
     * 形象label弹窗
     */
    private void showLabelDialog() {
        labelDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_label)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .addViewOnclick(R.id.tv_cancle, view -> {
                    if (labelDialog.isShowing()) {
                        labelDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.tv_down, view -> {
                    if (labelDialog.isShowing()) {
                        labelDialog.dismiss();
                    }
                    lableName = temp;
                    if (TextUtils.isEmpty(temp)) {
                        view_label.setTextRight("请输入形象标签(选填)");
                    } else {
                        view_label.setTextRight(lableName);
                    }
                })
                .build();
        labelDialog.show();
        GridView gv_label = labelDialog.findViewById(R.id.gv_label);
        MineSysLabelGvAdapter adapter = new MineSysLabelGvAdapter(labelList, getActivity());
        gv_label.setAdapter(adapter);

        adapter.setOnItemChcekCheckListener(new MineSysLabelGvAdapter.OnItemChcekCheckListener() {

            @Override
            public void setOnItemChcekCheckClick(List<SysLabelBean.RowsBean> list) {
                adapter.notifyDataSetChanged();
                StringBuilder titleNo = new StringBuilder();
                if (list.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isIs_select) {
                            titleNo.append((list.get(i).title) + ",");
                        }
                    }
                    if (titleNo.toString().length() > 0) {
                        temp = titleNo.toString().trim().substring(0, (titleNo.toString().length() - 1));
                    } else {
                        temp = "";
                    }
                }
            }
        });
    }


    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";
//
//                String opt3tx = options2Items.size() > 0
//                        && options3Items.get(options1).size() > 0
//                        && options3Items.get(options1).get(options2).size() > 0 ?
//                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx;
                view_city.setTextRight(tx);
                city = tx;
            }
        })

                .setTitleText("")
                .setDividerColor(Color.TRANSPARENT)
                .setTextColorCenter(Color.parseColor("#333333")) //设置选中项文字颜色
                .setContentTextSize(20)
                .setSubmitColor(Color.parseColor("#9897E7"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new MineGetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<CityJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<CityJsonBean> parseData(String result) {//Gson 解析
        ArrayList<CityJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
