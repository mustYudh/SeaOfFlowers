package com.hzrcht.seaofflowers.module.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;

import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.dynamic.fragment.DynamicFragment;
import com.hzrcht.seaofflowers.module.home.bean.HomeDataRefreshEvent;
import com.hzrcht.seaofflowers.module.home.fragment.HomeFragment;
import com.hzrcht.seaofflowers.module.home.presenter.HomePagePresenter;
import com.hzrcht.seaofflowers.module.home.presenter.HomePageViewer;
import com.hzrcht.seaofflowers.module.im.CustomMessageData;
import com.hzrcht.seaofflowers.module.login.activity.SelectLoginActivity;
import com.hzrcht.seaofflowers.module.message.fragment.MessageFragment;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.hzrcht.seaofflowers.module.mine.fragment.MineFragment;
import com.hzrcht.seaofflowers.utils.permissions.MorePermissionsCallBack;
import com.hzrcht.seaofflowers.utils.permissions.PermissionManager;
import com.nhbs.fenxiao.module.center.HomeCenterPopUpWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.PressHandle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yudenghao
 */
public class HomePageActivity extends BaseActivity implements HomePageViewer {
    private int num = 0;
    private PressHandle pressHandle = new PressHandle(this);
    private final static int REQ_PERMISSION_CODE = 0x1000;
    @PresenterLifeCycle
    HomePagePresenter mPresenter = new HomePagePresenter(this);
    private BottomNavigationView mBottomNavigationView;
    private Disposable subscribe;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_page_view);
    }

    @Override
    protected void loadData() {
        setCustomConfig();
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
                HomeCenterPopUpWindow homePopUpWindow = new HomeCenterPopUpWindow(HomePageActivity.this);
                homePopUpWindow.showPopupWindow();
            }
        });

        checkPermission();

        if (UserProfile.getInstance().getAnchorType() == 0) {
            //用户
            userOnline();
        }

        //设置消息监听器，收到新消息时，通过此监听器回调
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {//消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> msgs) {//收到新消息
                try {
                    num++;
                    if (mBottomNavigationView != null && mBottomNavigationView.mControl != null && mBottomNavigationView.mControl.badge_view != null) {
                        mBottomNavigationView.mControl.badge_view.setText(num + "");
                    }
                    TIMCustomElem elem = (TIMCustomElem) msgs.get(0).getElement(0);
                    Gson gson = new Gson();
                    CustomMessageData messageData = gson.fromJson(new String(elem.getData()), CustomMessageData.class);

                    switch (messageData.type) {
                        case "1":
                            //发起视频
                            break;
                        case "2":
                            //拒绝视频
                            break;
                    }
                    Log.e("aaaa", messageData.content + "...." + messageData.type);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                //消息的内容解析请参考消息收发文档中的消息解析说明
                return true; //返回true将终止回调链，不再调用下一个新消息监听器
            }
        });
    }

    /**
     * 检查权限
     */
    private boolean checkPermission() {
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
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(getActivity(),
                        (String[]) permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        ToastUtils.show("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
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

    public void login(boolean autoLogin) {
        UserProfile.getInstance().clean();
        getLaunchHelper().startActivity(SelectLoginActivity.class);
    }


    private void setCustomConfig() {
        //注册IM事件回调，这里示例为用户被踢的回调，更多事件注册参考文档
        TUIKit.setIMEventListener(new IMEventListener() {
            @Override
            public void onForceOffline() {
                ToastUtil.toastLongMessage("您的帐号已在其它终端登录");
                login(false);
                finish();
            }

            @Override
            public void onDisconnected(int code, String desc) {
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
            subscribe = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getIsAnchor();
    }

    private void userOnline() {
        subscribe = Observable.interval(0, 60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext(aLong -> {
                    mPresenter.userOnline();
                })
                .subscribe();
    }

    @Override
    public void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean) {
        if (userIsAnchorBean != null) {
            UserProfile.getInstance().setAnchorType(userIsAnchorBean.is_anchor);
            UserProfile.getInstance().setUserVip(userIsAnchorBean.is_vip);
        }
    }
}
