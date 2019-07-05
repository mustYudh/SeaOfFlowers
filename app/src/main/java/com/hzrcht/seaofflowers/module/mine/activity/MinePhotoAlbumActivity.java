package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePhotoAlbumPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePhotoAlbumViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MinePhotoAlbumRvAdapter;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;


public class MinePhotoAlbumActivity extends BaseBarActivity implements MinePhotoAlbumViewer {
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private MinePhotoAlbumPresenter mPresenter = new MinePhotoAlbumPresenter(this);
    private RecyclerView mPic;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_photo_album_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_mine_photo_album_view_bar;
    }

    @Override
    protected void loadData() {
        setTitle("我的相册");
        LinearLayout ll_add = bindView(R.id.ll_add);

        mPic = bindView(R.id.rv_pic);
        mPic.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPic.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 3, 3));


        ll_add.setOnClickListener(view -> {
            getLaunchHelper().startActivityForResult(MineSetUpAlbumActivity.class, 0);
        });

        mPresenter.getPhotoAlbum(page, pageSize);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                mPresenter.getPhotoAlbum(page, pageSize);
                break;
        }
    }

    @Override
    public void getPhotoAlbumSuccess(PhotoAlbumBean photoAlbumBean) {
        if (photoAlbumBean != null) {
            if (photoAlbumBean.rows != null && photoAlbumBean.rows.size() != 0) {
                MinePhotoAlbumRvAdapter adapter = new MinePhotoAlbumRvAdapter(R.layout.item_mine_photo_album, photoAlbumBean.rows, getActivity());
                mPic.setAdapter(adapter);
            }
        }

    }
}
