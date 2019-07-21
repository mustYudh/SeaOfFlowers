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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MinePhotoAlbumActivity extends BaseBarActivity implements MinePhotoAlbumViewer {
    private int page = 1;
    private int pageSize = 10;
    private List<String> imgList = new ArrayList<>();
    @PresenterLifeCycle
    private MinePhotoAlbumPresenter mPresenter = new MinePhotoAlbumPresenter(this);
    private RecyclerView mPic;
    private MinePhotoAlbumRvAdapter adapter;
    private SmartRefreshLayout refreshLayout;

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
        refreshLayout = bindView(R.id.refreshLayout);
        mPic.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPic.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 3, 3));

        adapter = new MinePhotoAlbumRvAdapter(R.layout.item_mine_photo_album, getActivity());
        mPic.setAdapter(adapter);

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);

        ll_add.setOnClickListener(view -> {
            getLaunchHelper().startActivityForResult(MineSetUpAlbumActivity.class, 0);
        });

        mPresenter.getPhotoAlbum(page, pageSize);

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            int i = 1;
            page += i;
            mPresenter.getPhotoAlbum(page, pageSize);

        });
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            page = 1;
            mPresenter.getPhotoAlbum(page, pageSize);
        });

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
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }

        if (photoAlbumBean != null && photoAlbumBean.rows != null && photoAlbumBean.rows.size() != 0) {
            
            if (page > 1) {
                adapter.addData(photoAlbumBean.rows);
            } else {
                adapter.setNewData(photoAlbumBean.rows);
            }

            adapter.setOnItemCheckListener((id, position) -> {
                for (int i = 0; i < adapter.getData().size(); i++) {
                    imgList.add(adapter.getData().get(i).img_url);
                }
                Bundle bundle = new Bundle();
                bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", position);//第几张
                bundle.putInt("id", id);//图片id
                bundle.putStringArrayList("imageuri", (ArrayList<String>) imgList);
                Intent intent = new Intent(getActivity(), ViewBigImageActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            });

            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_pic, true);
        } else {
            if (page > 1) {
                ToastUtils.show("没有更多了");
            } else {
                bindView(R.id.ll_empty, true);
                bindView(R.id.rv_pic, false);
            }
        }
    }

    @Override
    public void getPhotoAlbumFail() {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
    }
}
