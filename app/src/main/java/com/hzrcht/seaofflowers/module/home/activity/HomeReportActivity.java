package com.hzrcht.seaofflowers.module.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.activity.presenter.HomeReportPresenter;
import com.hzrcht.seaofflowers.module.home.activity.presenter.HomeReportViewer;
import com.hzrcht.seaofflowers.module.home.adapter.ReportSelectPhotoAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MarginDecoration;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserPhotoListBean;
import com.hzrcht.seaofflowers.utils.PhotoUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeReportActivity extends BaseBarActivity implements HomeReportViewer, BaseQuickAdapter.OnItemChildClickListener {
    private RecyclerView gv_pic;
    private ReportSelectPhotoAdapter adapter = new ReportSelectPhotoAdapter();
    List<UserPhotoListBean> picList = new ArrayList<>();
    @PresenterLifeCycle
    private HomeReportPresenter mPresenter = new HomeReportPresenter(this);
    private String anchor_id;
    private String state_id;
    private ArrayList<String> imageFiles = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("CheckResult")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //得到这个消息标记开始处理消息
            if (msg.what == 1001) {
                //处理消息
                final StringBuilder urlNo = new StringBuilder();
                if (imageFiles.size() != 0) {
                    for (int i = 0; i < imageFiles.size(); i++) {
                        urlNo.append(imageFiles.get(i));
                        if (i < imageFiles.size() - 1) {
                            urlNo.append(",");
                        }
                    }
                }
                mPresenter.report(anchor_id, state_id, urlNo.toString(), "1");

            } else if (msg.what == 1002) {


            }
        }
    };


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_report_view);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        Bundle bundle = getIntent().getExtras();
        anchor_id = bundle.getString("ANCHOR_ID");
        state_id = bundle.getString("STATE_ID");
        gv_pic = bindView(R.id.gv_pic);

        if (picList == null || picList.size() < 5) {
            UserPhotoListBean listBean = new UserPhotoListBean();
            listBean.canAdd = true;
            picList.add(listBean);
        }
        adapter.setNewData(picList);

        adapter.setOnItemChildClickListener(this);

        gv_pic.setLayoutManager(new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        gv_pic.addItemDecoration(new MarginDecoration(getActivity(), 5, 4));
        gv_pic.setNestedScrollingEnabled(false);
        gv_pic.setHasFixedSize(true);
        gv_pic.setFocusable(false);
        gv_pic.setAdapter(adapter);

        bindView(R.id.tv_report, view -> {
            Log.e("aaaa", "走了吗图片上传" + adapter.getData().size());
            int count = adapter.getData().size();
            for (int i = 0; i < (count == 5 ? count : count - 1); i++) {
                File imageFileCrmera = new File(adapter.getData().get(i).url);
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
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        handler.sendEmptyMessage(1002);//向消息队列发送一个标记
                                    }
                                }).start();
                            }
                        });
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<UserPhotoListBean> currentData = adapter.getData();
                    currentData.remove(currentData.size() - 1);
                    List<UserPhotoListBean> list = new ArrayList<>(currentData);
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    selectList.stream()
                            .filter(media -> !TextUtils.isEmpty(media.getCompressPath()))
                            .forEach(media -> {
                                UserPhotoListBean bean = new UserPhotoListBean();
                                bean.url = media.getCompressPath();
                                list.add(bean);
                            });
                    if (list.size() > 5) {
                        list.remove(list.size() - 1);
                    }
                    if (list == null || list.size() < 5) {
                        UserPhotoListBean listBean = new UserPhotoListBean();
                        listBean.canAdd = true;
                        list.add(listBean);
                    }
                    adapter.setNewData(list);

                    break;
                default:
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        PhotoUtils.changeAvatar(getActivity(), adapter.getData(), 6, "上传您的证据照片,以供参考");
    }

    @Override
    public void uploadImgSuccess(UploadImgBean uploadImgBean) {
        if (uploadImgBean != null) {
            imageFiles.add(uploadImgBean.url + "");

            if (imageFiles.size() == (adapter.getData().size() == 5 ? adapter.getData().size() : (adapter.getData().size() - 1))) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1001);//向消息队列发送一个标记
                    }
                }).start();
            }
        }
    }

    @Override
    public void reportSuccess() {
        ToastUtils.show("投诉成功");
        finish();
    }
}
