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

import java.util.ArrayList;
import java.util.List;


public class MinePhotoAlbumActivity extends BaseBarActivity implements MinePhotoAlbumViewer {
    private List<String> list = new ArrayList<>();
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private MinePhotoAlbumPresenter mPresenter = new MinePhotoAlbumPresenter(this);

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
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561636421460&di=43f61c1c126bdbb1206b4dfd6eb3a3d3&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201310%2F19%2F20131019230954_4XXWU.jpeg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561636421459&di=47781777b4f7be0cd9330a9a48990bed&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201703%2F15%2F20170315013052_GsNPZ.thumb.700_0.jpeg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561636421459&di=9af4ea32b4d3bb399917134dac8c9d7a&imgtype=0&src=http%3A%2F%2Fpic19.nipic.com%2F20120302%2F2786001_164927058000_2.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561636421455&di=9da0a4dd0f2c970784978a783cf3609e&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201406%2F04%2F20140604174826_ZkUYy.thumb.600_0.jpeg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561636421454&di=30485b645b921990d7675b8e6bfb4ead&imgtype=0&src=http%3A%2F%2Fclubimg.club.vmall.com%2Fdata%2Fattachment%2Fforum%2F201906%2F10%2F2009469putbw9mdkhse4y9.jpg");
        LinearLayout ll_add = bindView(R.id.ll_add);

        RecyclerView rv_pic = bindView(R.id.rv_pic);
        rv_pic.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rv_pic.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 3, 3));
        MinePhotoAlbumRvAdapter adapter = new MinePhotoAlbumRvAdapter(R.layout.item_mine_photo_album, list, getActivity());
        rv_pic.setAdapter(adapter);


        ll_add.setOnClickListener(view -> {
            getLaunchHelper().startActivityForResult(MineSetUpAlbumActivity.class, 0);
        });

        mPresenter.getPhotoAlbum(page,pageSize);
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

    @Override
    public void getPhotoAlbumSuccess(PhotoAlbumBean photoAlbumBean) {

    }
}
