package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePhotoAlbumPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePhotoAlbumViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MinePhotoAlbumRvAdapter;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MinePhotoAlbumActivity extends BaseBarActivity implements MinePhotoAlbumViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private MinePhotoAlbumPresenter presenter = new MinePhotoAlbumPresenter(this);

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
        for (int i = 0; i < 7; i++) {
            list.add("");
        }
        LinearLayout ll_add = bindView(R.id.ll_add);

        RecyclerView rv_pic = bindView(R.id.rv_pic);
        rv_pic.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rv_pic.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 3, 3));
        MinePhotoAlbumRvAdapter adapter = new MinePhotoAlbumRvAdapter(R.layout.item_mine_photo_album, list, getActivity());
        rv_pic.setAdapter(adapter);


        ll_add.setOnClickListener(view -> {
            getLaunchHelper().startActivityForResult(MineSetUpAlbumActivity.class, 0);
        });
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
    }
}
