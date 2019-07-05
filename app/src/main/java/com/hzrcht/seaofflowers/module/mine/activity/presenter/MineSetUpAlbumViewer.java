package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.yu.common.mvp.Viewer;


public interface MineSetUpAlbumViewer extends Viewer {
    void uploadImgSuccess(UploadImgBean uploadImgBean);

    void addAlbumSuccess();
}
