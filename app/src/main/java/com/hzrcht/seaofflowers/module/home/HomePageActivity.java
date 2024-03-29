package com.hzrcht.seaofflowers.module.home;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.dynamic.fragment.DynamicFragment;
import com.hzrcht.seaofflowers.module.home.bean.HomeDataRefreshEvent;
import com.hzrcht.seaofflowers.module.home.fragment.HomeFragment;
import com.hzrcht.seaofflowers.module.home.presenter.HomePagePresenter;
import com.hzrcht.seaofflowers.module.home.presenter.HomePageViewer;
import com.hzrcht.seaofflowers.module.message.fragment.MessageFragment;
import com.hzrcht.seaofflowers.module.mine.fragment.MineFragment;
import com.hzrcht.seaofflowers.utils.permissions.MorePermissionsCallBack;
import com.hzrcht.seaofflowers.utils.permissions.PermissionManager;
import com.tbruyelle.rxpermissions2.Permission;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.PressHandle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yudenghao
 */
public class HomePageActivity extends BaseActivity implements HomePageViewer {

    private PressHandle pressHandle = new PressHandle(this);

    @PresenterLifeCycle
    HomePagePresenter presenter = new HomePagePresenter(this);
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_page_view);
    }

    @Override
    protected void loadData() {
        EventBus.getDefault().register(this);
        mBottomNavigationView = bindView(R.id.bottom_navigation_view);
        List<TabItem> items = new ArrayList<>();
        items.add(new TabView(0, new HomeFragment()));
        items.add(new TabView(1, new DynamicFragment()));
        items.add(new TabView(2, null));
        items.add(new TabView(3, new MessageFragment()));
        items.add(new TabView(4, new MineFragment()));
        mBottomNavigationView.initControl(this).setPagerView(items, 0);
        mBottomNavigationView.getControl().setOnTabClickListener((position, view) -> {
            if (position == 2) {
                ToastUtils.show("弹出毛玻璃界面");
            }
        });

        checkPermission();
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        //检测权限
        PermissionManager.getInstance(this).checkMorePermission(new MorePermissionsCallBack() {
                                                                    @Override
                                                                    protected void permissionGranted(Permission permission) {
                                                                        // 用户已经同意该权限
                                                                    }

                                                                    @Override
                                                                    protected void permissionShouldShowRequestPermissionRationale(Permission permission) {
                                                                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                                                    }

                                                                    @Override
                                                                    protected void permissionRejected(Permission permission) {

                                                                    }
                                                                },
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        );
    }

    @Override
    public void onBackPressed() {
        if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
            super.onBackPressed();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HomeDataRefreshEvent event) {
        ToastUtils.show(event.showCenterTab.toString());
        bindView(R.id.center_tab, event.showCenterTab);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
