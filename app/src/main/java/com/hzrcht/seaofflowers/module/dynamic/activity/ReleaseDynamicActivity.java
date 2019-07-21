package com.hzrcht.seaofflowers.module.dynamic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.EditText;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.dynamic.activity.presenter.ReleaseDynamicPresenter;
import com.hzrcht.seaofflowers.module.dynamic.activity.presenter.ReleaseDynamicViewer;
import com.hzrcht.seaofflowers.module.dynamic.adapter.GridAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.NoSlidingGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ReleaseDynamicActivity extends BaseBarActivity implements ReleaseDynamicViewer {
    private List<LocalMedia> allLocationSelectedPicture = new ArrayList<>();

    @PresenterLifeCycle
    private ReleaseDynamicPresenter mPresenter = new ReleaseDynamicPresenter(this);
    private NoSlidingGridView gvPhoto;
    private GridAdapter adapter;

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
                mPresenter.stateAdd(urlNo.toString(), mContent.getText().toString().trim(), "0");

            } else if (msg.what == 1002) {
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                ToastUtils.show("图片压缩失败,请重试");
            } else if (msg.what == 1003) {
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                imageFiles.clear();
                ToastUtils.show("图片上传失败,请重试");
            }
        }
    };
    private EditText mContent;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_release_dynamic_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_release_dynamic_view_bar;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        setTitle("发布动态");
        gvPhoto = bindView(R.id.gv_photo);
        mContent = bindView(R.id.et_content);
        adapter = new GridAdapter(getActivity(), allLocationSelectedPicture);
        gvPhoto.setAdapter(adapter);

        bindView(R.id.tv_commit, view -> {
            if (TextUtils.isEmpty(mContent.getText().toString().trim())) {
                ToastUtils.show("请输入您的想法");
                return;
            }
            if (allLocationSelectedPicture != null && allLocationSelectedPicture.size() == 0) {
                ToastUtils.show("至少选择一张图片");
                return;
            }
            loadDialog.show();
            if (allLocationSelectedPicture != null && allLocationSelectedPicture.size() != 0) {
                //有图片
                for (int i = 0; i < allLocationSelectedPicture.size(); i++) {
                    File imageFileCrmera = new File(allLocationSelectedPicture.get(i).getCompressPath());
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
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    allLocationSelectedPicture.put(count, compressPath);
                    for (int i = 0; i < selectList.size(); i++) {
                        allLocationSelectedPicture.add(selectList.get(i));
                    }
                    if (adapter != null) {
                        gvPhoto.setAdapter(adapter);
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void uploadImgSuccess(UploadImgBean uploadImgBean) {
        if (uploadImgBean != null) {
            imageFiles.add(uploadImgBean.url + "");

            if (imageFiles.size() == allLocationSelectedPicture.size()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1001);//向消息队列发送一个标记
                    }
                }).start();
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1003);//向消息队列发送一个标记
                }
            }).start();
        }
    }

    @Override
    public void uploadImgFail() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        imageFiles.clear();
        ToastUtils.show("图片上传失败,请重试");
    }

    @Override
    public void stateAddSuccess() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        imageFiles.clear();
        ToastUtils.show("发布成功,等待审核");
        finish();
    }

    @Override
    public void stateAddFail() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        imageFiles.clear();
        ToastUtils.show("发布失败");
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
