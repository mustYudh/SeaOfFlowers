package com.hzrcht.seaofflowers.module.dynamic.activity;

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
import com.hzrcht.seaofflowers.module.dynamic.activity.presenter.ReleaseDynamicPresenter;
import com.hzrcht.seaofflowers.module.dynamic.activity.presenter.ReleaseDynamicViewer;
import com.hzrcht.seaofflowers.module.dynamic.adapter.ReleaseDynamicPhotoAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserPhotoListBean;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.hzrcht.seaofflowers.utils.PhotoUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class ReleaseDynamicActivity extends BaseBarActivity implements ReleaseDynamicViewer, BaseQuickAdapter.OnItemChildClickListener {
    private List<UserPhotoListBean> picList = new ArrayList<>();
    private ReleaseDynamicPhotoAdapter adapter = new ReleaseDynamicPhotoAdapter();

    @PresenterLifeCycle
    private ReleaseDynamicPresenter mPresenter = new ReleaseDynamicPresenter(this);
    private RecyclerView mDynamic;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_release_dynamic_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_release_dynamic_view_bar;
    }

    @Override
    protected void loadData() {
        setTitle("发布动态");
        mDynamic = bindView(R.id.rv_dynamic);

        if (picList == null || picList.size() < 9) {
            UserPhotoListBean listBean = new UserPhotoListBean();
            listBean.canAdd = true;
            picList.add(listBean);
        }
        adapter.setNewData(picList);

        adapter.setOnItemChildClickListener(this);

        mDynamic.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        mDynamic.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 5, 3));
        mDynamic.setHasFixedSize(true);
        mDynamic.setFocusable(false);
        mDynamic.setAdapter(adapter);
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
                    if (list.size() > 9) {
                        list.remove(list.size() - 1);
                    }
                    if (list == null || list.size() < 9) {
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
        PhotoUtils.changeAvatar(getActivity(), adapter.getData(), 10, "上传您的照片,以供展示");
    }
}
