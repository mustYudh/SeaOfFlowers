package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePhotoAlbumPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePhotoAlbumViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MinePhotoAlbumRvAdapter;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MinePhotoAlbumActivity extends BaseBarActivity implements MinePhotoAlbumViewer {
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private MinePhotoAlbumPresenter mPresenter = new MinePhotoAlbumPresenter(this);
    private RecyclerView mPic;
    private List<PhotoAlbumBean.RowsBean> list = new ArrayList<>();
    private MinePhotoAlbumRvAdapter adapter;

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

        bindView(R.id.action_back, view -> {
            setResult(1);
            finish();
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(1);
            finish();
        }
        return true;
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
                if (page > 1) {

                } else {
                    list.clear();
                }
                list.addAll(photoAlbumBean.rows);
                if (adapter == null) {
                    adapter = new MinePhotoAlbumRvAdapter(R.layout.item_mine_photo_album, list, getActivity());
                    mPic.setAdapter(adapter);
                } else {
                    adapter.setNewData(list);
                }

                adapter.setOnItemCheckListener(new MinePhotoAlbumRvAdapter.OnItemCheckListener() {
                    @Override
                    public void setOnItemCheckClick(int id, int position, ArrayList<String> list) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                        bundle.putInt("code", position);//第几张
                        bundle.putInt("id", id);//图片id
                        bundle.putStringArrayList("imageuri", list);
                        Intent intent = new Intent(getActivity(), ViewBigImageActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 1);
                    }
                });

                bindView(R.id.ll_empty, false);
                bindView(R.id.rv_pic, true);
            } else {
                if (page > 1) {

                } else {
                    bindView(R.id.ll_empty, true);
                    bindView(R.id.rv_pic, false);
                }
            }
        }
    }
}
