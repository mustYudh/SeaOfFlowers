package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineDynamicRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineDynamicPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineDynamicViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MineDynamicActivity extends BaseBarActivity implements MineDynamicViewer {
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    private List<String> picList = new ArrayList<>();
    @PresenterLifeCycle
    private MineDynamicPresenter presenter = new MineDynamicPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_dynamic_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_mine_dynamic_view_bar;
    }

    @Override
    protected void loadData() {
        setTitle("我的动态");
        RecyclerView rv_dynamic = bindView(R.id.rv_dynamic);
        rv_dynamic.setLayoutManager(new LinearLayoutManager(getActivity()));

        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293040&di=8f9de00e1862ebe58e47f7e1a519517c&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201307%2F20%2F153912fo4kxx5kjo6zv52v.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293038&di=83f23fd192c537f42afab17a732883c4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201707%2F24%2F20170724102404_wAjaP.png");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293040&di=ff3ae65767bd9954ef2e220abc2383e8&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F4%2F5696f5a8a6eb3.jpg%3Fdown");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293039&di=03d1fc1a8105f7d1e4ec80f6685a964a&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F010c2658ea1351a8012049efead301.jpg%401280w_1l_2o_100sh.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293039&di=410ac577370998d1813ee18a2a88b0f4&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F9vo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2F0df3d7ca7bcb0a463350c11e6863f6246b60afa3.jpg");

        for (int i = 0; i < 10; i++) {
            //title
            MineLocationDynamicBean mineLocationDynamicTitleBean = new MineLocationDynamicBean();
            mineLocationDynamicTitleBean.itemType = 0;
            list.add(mineLocationDynamicTitleBean);

            if (i != 0) {
                //pic
                MineLocationDynamicBean mineLocationDynamicPicBean = new MineLocationDynamicBean();
                mineLocationDynamicPicBean.pictures = picList;
                mineLocationDynamicPicBean.itemType = 1;
                list.add(mineLocationDynamicPicBean);
            } else {
                //video
                MineLocationDynamicBean mineLocationDynamicVideoBean = new MineLocationDynamicBean();
                mineLocationDynamicVideoBean.video_pict_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426510803&di=2bf93542ebc2b9a183695626fbffb5de&imgtype=0&src=http%3A%2F%2Fwww.desktx.cc%2Fd%2Ffile%2Fphone%2Fkatong%2F20161203%2F61ad328e4d8c741437ed00209a6bae35.jpg";
                mineLocationDynamicVideoBean.itemType = 2;
                list.add(mineLocationDynamicVideoBean);
            }

            //bottom
            MineLocationDynamicBean mineLocationDynamicBottomBean = new MineLocationDynamicBean();
            mineLocationDynamicBottomBean.itemType = 3;
            list.add(mineLocationDynamicBottomBean);
        }

        MineDynamicRvAdapter adapter = new MineDynamicRvAdapter(list, getActivity());
        rv_dynamic.setAdapter(adapter);
    }
}
