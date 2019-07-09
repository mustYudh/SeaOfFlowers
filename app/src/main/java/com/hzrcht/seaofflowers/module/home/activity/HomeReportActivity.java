package com.hzrcht.seaofflowers.module.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.activity.presenter.HomeReportPresenter;
import com.hzrcht.seaofflowers.module.home.activity.presenter.HomeReportViewer;
import com.hzrcht.seaofflowers.module.home.adapter.HomeReportAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.NoSlidingGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeReportActivity extends BaseBarActivity implements HomeReportViewer {
    private NoSlidingGridView gvPhoto;
    private List<LocalMedia> allLocationSelectedPicture = new ArrayList<>();
    private List<RelativeLayout> rlList = new ArrayList<>();
    private List<ImageView> ivList = new ArrayList<>();
    private Map<Integer, Boolean> ivMap = new HashMap();
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

                final StringBuilder titleNo = new StringBuilder();
                if (ivMap.size() != 0) {
                    for (int i = 0; i < ivMap.size(); i++) {
                        if (ivMap.get(i)) {
                            titleNo.append((i + 1) + ",");
                        }
                    }
                }

                String substring = titleNo.toString().trim().substring(0, (titleNo.toString().length() - 1));

                mPresenter.report(anchor_id, state_id, urlNo.toString(), substring);

            } else if (msg.what == 1002) {
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                ToastUtils.show("图片压缩失败,请重试");
            } else if (msg.what == 1003) {
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                ToastUtils.show("图片上传失败,请重试");
            }
        }
    };
    private HomeReportAdapter adapter;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_report_view);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        setTitle("举报");
        Bundle bundle = getIntent().getExtras();
        anchor_id = bundle.getString("ANCHOR_ID");
        state_id = bundle.getString("STATE_ID");
        gvPhoto = bindView(R.id.gv_pic);
        adapter = new HomeReportAdapter(getActivity(), allLocationSelectedPicture);
        gvPhoto.setAdapter(adapter);

        RelativeLayout rl1 = bindView(R.id.rl1);
        RelativeLayout rl2 = bindView(R.id.rl2);
        RelativeLayout rl3 = bindView(R.id.rl3);
        RelativeLayout rl4 = bindView(R.id.rl4);
        RelativeLayout rl5 = bindView(R.id.rl5);

        iv1 = bindView(R.id.iv1);
        iv2 = bindView(R.id.iv2);
        iv3 = bindView(R.id.iv3);
        iv4 = bindView(R.id.iv4);
        iv5 = bindView(R.id.iv5);

        rlList.add(rl1);
        rlList.add(rl2);
        rlList.add(rl3);
        rlList.add(rl4);
        rlList.add(rl5);

        ivList.add(iv1);
        ivList.add(iv2);
        ivList.add(iv3);
        ivList.add(iv4);
        ivList.add(iv5);

        rl1.setOnClickListener(view -> {
            setTypeCheck(rl1);
        });
        rl2.setOnClickListener(view -> {
            setTypeCheck(rl2);
        });
        rl3.setOnClickListener(view -> {
            setTypeCheck(rl3);
        });
        rl4.setOnClickListener(view -> {
            setTypeCheck(rl4);
        });
        rl5.setOnClickListener(view -> {
            setTypeCheck(rl5);
        });
        for (int i = 0; i < 5; i++) {
            ivMap.put(i, false);
        }


        bindView(R.id.tv_report, view -> {
            if (!ivMap.values().contains(true)) {
                ToastUtils.show("至少选择一项理由");
                return;
            }

            if (allLocationSelectedPicture != null && allLocationSelectedPicture.size() == 0) {
                ToastUtils.show("至少选择一张图片");
                return;
            }
            loadDialog.show();
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

    //点击不同对象不同的风格
    private void setTypeCheck(RelativeLayout rlType) {
        for (int i = 0; i < rlList.size(); i++) {
            RelativeLayout relativeLayout = rlList.get(i);
            if (relativeLayout.equals(rlType)) {
                if (!ivMap.get(i)) {
                    ivMap.put(i, true);
                    ivList.get(i).setImageResource(R.drawable.ic_circle_select);
                } else {
                    ivMap.put(i, false);
                    ivList.get(i).setImageResource(R.drawable.ic_circle_normal);
                }
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
    public void reportSuccess() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        imageFiles.clear();
        ToastUtils.show("投诉成功");
        finish();
    }

    @Override
    public void reportFail() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        imageFiles.clear();
        ToastUtils.show("投诉失败");
    }
}
