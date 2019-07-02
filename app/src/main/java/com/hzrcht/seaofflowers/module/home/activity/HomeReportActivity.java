package com.hzrcht.seaofflowers.module.home.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.activity.presenter.HomeReportPresenter;
import com.hzrcht.seaofflowers.module.home.activity.presenter.HomeReportViewer;
import com.hzrcht.seaofflowers.module.home.adapter.ReportSelectPhotoAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MarginDecoration;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserPhotoListBean;
import com.hzrcht.seaofflowers.utils.PhotoUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class HomeReportActivity extends BaseBarActivity implements HomeReportViewer, BaseQuickAdapter.OnItemChildClickListener {
    private RecyclerView gv_pic;
    private ReportSelectPhotoAdapter adapter = new ReportSelectPhotoAdapter();
    List<UserPhotoListBean> list = new ArrayList<>();
    @PresenterLifeCycle
    private HomeReportPresenter presenter = new HomeReportPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_report_view);
    }

    @Override
    protected void loadData() {
        if (list == null || list.size() < 5) {
            UserPhotoListBean listBean = new UserPhotoListBean();
            listBean.canAdd = true;
            list.add(listBean);
        }
        adapter.setNewData(list);

        gv_pic = bindView(R.id.gv_pic);
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
}
