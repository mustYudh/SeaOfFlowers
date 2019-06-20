package com.hzrcht.seaofflowers.module.dynamic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.dynamic.adapter.DynamicListRvAdapter;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.DynamicPresenter;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.DynamicViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class DynamicFragment extends BaseFragment implements DynamicViewer, View.OnClickListener {
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    private List<String> picList = new ArrayList<>();
    private List<LinearLayout> llList = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    @PresenterLifeCycle
    private DynamicPresenter mPresenter = new DynamicPresenter(this);
    private LinearLayout ll_recommend;
    private LinearLayout ll_attention;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_dynamic_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561056265171&di=524ab0b1f12275928f2d2a8c9be7c62e&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fbmiddle%2F005XWbeUgy6QwbUP4QE58%26690");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561651140&di=56f2028087f099f5de6e2c1c4dd24679&imgtype=jpg&er=1&src=http%3A%2F%2Fimg3.doubanio.com%2Fview%2Fnote%2Flarge%2Fpublic%2Fp10795580.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561056421309&di=de8807bbf04e86ae86f87f1f6412725d&imgtype=0&src=http%3A%2F%2Fimg1.ph.126.net%2FpumBecovKk8yQh_4fmTHXA%3D%3D%2F6608266093563198550.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561056421309&di=6d056f54e432d458f3b8a900cfc6cf9f&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F35ecc4cf414a5732b4821df0450c70af535d645e5e1e6-bgk6aj_fw658");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561056421306&di=7d7d3f041d0c268e6924deedc50fe6db&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F001Zmh0Rgy6Okaj9Oycb8%26690");
        ll_recommend = bindView(R.id.ll_recommend);
        ll_attention = bindView(R.id.ll_attention);
        TextView tv_recommend = bindView(R.id.tv_recommend);
        TextView tv_attention = bindView(R.id.tv_attention);
        View view_recommend = bindView(R.id.view_recommend);
        View view_attention = bindView(R.id.view_attention);
        RecyclerView rv_dynamic = bindView(R.id.rv_dynamic);

        llList.add(ll_recommend);
        llList.add(ll_attention);
        tvList.add(tv_recommend);
        tvList.add(tv_attention);
        viewList.add(view_recommend);
        viewList.add(view_attention);

        setTypeCheck(ll_recommend);

        ll_recommend.setOnClickListener(this);
        ll_attention.setOnClickListener(this);

        rv_dynamic.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                mineLocationDynamicVideoBean.video_pict_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561057487494&di=df15679a6d7ad807dc17201bb6c2bde2&imgtype=0&src=http%3A%2F%2Fimg1.ph.126.net%2FOJdD2mRk9IpwxZqk-4EK8Q%3D%3D%2F6608257297468902784.jpg";
                mineLocationDynamicVideoBean.itemType = 2;
                list.add(mineLocationDynamicVideoBean);
            }

            //bottom
            MineLocationDynamicBean mineLocationDynamicBottomBean = new MineLocationDynamicBean();
            mineLocationDynamicBottomBean.itemType = 3;
            list.add(mineLocationDynamicBottomBean);
        }

        DynamicListRvAdapter adapter = new DynamicListRvAdapter(list, getActivity());
        rv_dynamic.setAdapter(adapter);

    }

    //点击不同对象不同的风格
    private void setTypeCheck(LinearLayout llType) {
        for (int i = 0; i < llList.size(); i++) {
            LinearLayout linearLayout = llList.get(i);
            if (linearLayout.equals(llType)) {
                tvList.get(i).setTextSize(20);
                viewList.get(i).setVisibility(View.VISIBLE);
            } else {
                tvList.get(i).setTextSize(16);
                viewList.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_recommend:
                setTypeCheck(ll_recommend);
                break;
            case R.id.ll_attention:
                setTypeCheck(ll_attention);
                break;
        }
    }
}
