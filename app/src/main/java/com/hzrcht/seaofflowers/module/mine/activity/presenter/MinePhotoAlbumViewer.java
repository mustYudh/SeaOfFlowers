package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.yu.common.mvp.Viewer;


public interface MinePhotoAlbumViewer extends Viewer {
    void getPhotoAlbumSuccess(PhotoAlbumBean photoAlbumBean);
}
