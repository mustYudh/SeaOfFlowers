package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineSetUpAlbumPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineSetUpAlbumViewer;
import com.hzrcht.seaofflowers.utils.PhotoUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.List;

/**
 * 新建相册
 */
public class MineSetUpAlbumActivity extends BaseBarActivity implements MineSetUpAlbumViewer {

    @PresenterLifeCycle
    private MineSetUpAlbumPresenter mPresenter = new MineSetUpAlbumPresenter(this);
    private DelayClickImageView mImg;
    private String imgUrl = "";

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_set_up_album_view);
    }

    @Override
    protected void loadData() {
        setTitle("新建相册");
        mImg = bindView(R.id.iv_img);

        mImg.setOnClickListener(view -> {
            PhotoUtils.changeAvatar(getActivity(), "上传您的相册图片,以供展示");
        });

        DelayClickTextView tv_commit = bindView(R.id.tv_commit);
        tv_commit.setOnClickListener(view -> {


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
                    imgUrl = selectList.get(0).getCompressPath();
                    ImageLoader.getInstance().displayImage(mImg, selectList.get(0).getCompressPath());
                    break;
                default:
            }
        }
    }

    @Override
    public void uploadImgSuccess() {

    }
}
