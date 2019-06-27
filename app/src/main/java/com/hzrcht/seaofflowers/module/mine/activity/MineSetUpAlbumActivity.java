package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineSetUpAlbumPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineSetUpAlbumViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * 新建相册
 */
public class MineSetUpAlbumActivity extends BaseBarActivity implements MineSetUpAlbumViewer {

    @PresenterLifeCycle
    private MineSetUpAlbumPresenter presenter = new MineSetUpAlbumPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_set_up_album_view);
    }

    @Override
    protected void loadData() {
        setTitle("新建相册");
    }
}
