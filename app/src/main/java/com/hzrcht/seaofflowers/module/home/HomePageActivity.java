package com.hzrcht.seaofflowers.module.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.hzrcht.seaofflowers.APP;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.dynamic.fragment.DynamicFragment;
import com.hzrcht.seaofflowers.module.event.DataSynVideoEvent;
import com.hzrcht.seaofflowers.module.event.DataSynVideoWaitEvent;
import com.hzrcht.seaofflowers.module.event.HomeDataRefreshEvent;
import com.hzrcht.seaofflowers.module.home.fragment.HomeFragment;
import com.hzrcht.seaofflowers.module.home.presenter.HomePagePresenter;
import com.hzrcht.seaofflowers.module.home.presenter.HomePageViewer;
import com.hzrcht.seaofflowers.module.im.CustomMessageData;
import com.hzrcht.seaofflowers.module.login.activity.SelectLoginActivity;
import com.hzrcht.seaofflowers.module.message.fragment.MessageFragment;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.hzrcht.seaofflowers.module.mine.fragment.MineFragment;
import com.hzrcht.seaofflowers.push.ThirdPushTokenMgr;
import com.hzrcht.seaofflowers.utils.ActivityManager;
import com.hzrcht.seaofflowers.utils.permissions.MorePermissionsCallBack;
import com.hzrcht.seaofflowers.utils.permissions.PermissionManager;
import com.nhbs.fenxiao.module.center.HomeCenterPopUpWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.tencent.imsdk.log.QLog;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit;
import com.tencent.qcloud.tim.uikit.utils.FileUtil;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.DensityUtils;
import com.yu.common.utils.PressHandle;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author yudenghao
 */
public class HomePageActivity extends BaseActivity
    implements HomePageViewer {
  private PressHandle pressHandle = new PressHandle(this);
  private final static int REQ_PERMISSION_CODE = 0x1000;
  @PresenterLifeCycle HomePagePresenter mPresenter = new HomePagePresenter(this);
  private BottomNavigationView mBottomNavigationView;
  private Disposable subscribe;
  private HomeCenterPopUpWindow mHomePopUpWindow;
  private FrameLayout.LayoutParams linearParams;
  private int mUnreadTotal = 0;


  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_home_page_view);
  }

  @Override protected void loadData() {
    setCustomConfig();
    FileUtil.initPath();
    EventBus.getDefault().register(this);
    mBottomNavigationView = bindView(R.id.bottom_navigation_view);
    List<TabItem> items = new ArrayList<>();
    items.add(new TabView(0, new HomeFragment()));
    items.add(new TabView(1, new DynamicFragment()));
    //        items.add(new TabView(2, null));
    items.add(new TabView(2, new MessageFragment()));
    items.add(new TabView(3, new MineFragment()));
    mBottomNavigationView.initControl(this).setPagerView(items, 0);
    //        mBottomNavigationView.getControl().setOnTabClickListener((position, view) -> {
    //            if (position == 2) {
    //                mHomePopUpWindow = new HomeCenterPopUpWindow(HomePageActivity.this);
    //                mHomePopUpWindow.showPopupWindow();
    //            }
    //        });

    checkPermission();


    if (UserProfile.getInstance().getAnchorType() == 0) {
      //用户
      userOnline();
    }
    //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
    linearParams =
        (FrameLayout.LayoutParams) mBottomNavigationView.mControl.badge_view.getLayoutParams();

    //设置消息监听器，收到新消息时，通过此监听器回调
    TIMManager.getInstance().addMessageListener(new TIMMessageListener() {//消息监听器
      @Override public boolean onNewMessages(List<TIMMessage> msgs) {//收到新消息
        int msgUnreadTotal = 0;
        for (TIMMessage msg : msgs) {
          TIMConversationExt timConversationExt = new TIMConversationExt(msg.getConversation());
          msgUnreadTotal += timConversationExt.getUnreadMessageNum();
        }
        showMessageCount(msgUnreadTotal);
        try {
          if (msgs != null && msgs.size() != 0) {
            TIMElem elemAll = msgs.get(0).getElement(0);
            if (elemAll.getType() == TIMElemType.Custom) {
              TIMCustomElem elem = (TIMCustomElem) msgs.get(0).getElement(0);
              Gson gson = new Gson();
              CustomMessageData messageData =
                  gson.fromJson(new String(elem.getData()), CustomMessageData.class);
              Log.e("视频信息", msgs.size() + "..." + messageData.content + "....." + messageData.type);
              switch (messageData.type) {
                case "1":
                  long time = getSecondTimestamp(new Date(System.currentTimeMillis())) - msgs.get(0)
                      .timestamp();
                  if (time < 30) {
                    //发起视频
                    Intent intent = new Intent(getActivity(), HomeVideoWaitActivity.class);
                    intent.putExtra("CONTENT", messageData.content);
                    //                                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                  }
                  break;
                case "2":
                  //拒绝视频
                  EventBus.getDefault().post(new DataSynVideoWaitEvent());
                  EventBus.getDefault().post(new DataSynVideoEvent());
                  break;
                default:
              }
            }
          }
        } catch (JsonSyntaxException e) {
          e.printStackTrace();
        }
        //消息的内容解析请参考消息收发文档中的消息解析说明
        return false; //返回true将终止回调链，不再调用下一个新消息监听器
      }
    });

    TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
    //开启离线推送
    settings.setEnabled(true);
    //设置收到 C2C 离线消息时的提示声音，以把声音文件放在 res/raw 文件夹下为例
    //settings.setC2cMsgRemindSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dudulu));
    //设置收到群离线消息时的提示声音，以把声音文件放在 res/raw 文件夹下为例
    //settings.setGroupMsgRemindSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dudulu));
    TIMManager.getInstance().setOfflinePushSettings(settings);


    GroupChatManagerKit.getInstance();

    //推送
    if (IMFunc.isBrandVivo()) {
      // vivo 离线推送
      PushClient.getInstance(getApplicationContext()).turnOnPush(new IPushActionListener() {
        @Override public void onStateChanged(int state) {
          if (state == 0) {
            String regId = PushClient.getInstance(getApplicationContext()).getRegId();
            QLog.i("推送", "vivopush open vivo push success regId = " + regId);
            ThirdPushTokenMgr.getInstance().setThirdPushToken(regId);
            ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
          } else {
            // 根据 vivo 推送文档说明，state = 101表示该 vivo 机型或者版本不支持 vivo 推送，详情请参考 vivo 推送常见问题汇总
            QLog.i("推送", "vivopush open vivo push fail state = " + state);
          }
        }
      });
    }

    if (IMFunc.isBrandHuawei()) {
      // 华为离线推送
      HMSAgent.connect(this, new ConnectHandler() {
        @Override public void onConnect(int rst) {
          QLog.i("推送", "huawei push HMS connect end:" + rst);
        }
      });
      getHuaWeiPushToken();
    }
    List<TIMConversation> TIMConversations = TIMManagerExt.getInstance().getConversationList();
    Log.e("======>测试数据",TIMConversations.size() + "");
    for (TIMConversation conversation : TIMConversations) {
      TIMConversationExt timConversationExt = new TIMConversationExt(conversation);
      mUnreadTotal += timConversationExt.getUnreadMessageNum();
    }

    showMessageCount(mUnreadTotal);
  }

  private void getHuaWeiPushToken() {
    HMSAgent.Push.getToken(new GetTokenHandler() {
      @Override public void onResult(int rtnCode) {
        Log.e("推送", "huawei push get token: end" + rtnCode);
      }
    });
  }

  /**
   * 检查权限
   */
  private boolean checkPermission() {
    //检测权限
    PermissionManager.getInstance(this).checkMorePermission(new MorePermissionsCallBack() {
                                                              @Override protected void permissionGranted(Permission permission) {
                                                                // 用户已经同意该权限
                                                              }

                                                              @Override
                                                              protected void permissionShouldShowRequestPermissionRationale(Permission permission) {
                                                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                                              }

                                                              @Override protected void permissionRejected(Permission permission) {

                                                              }
                                                            }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      List<String> permissions = new ArrayList<>();
      if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(),
          Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
      }
      if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(),
          Manifest.permission.CAMERA)) {
        permissions.add(Manifest.permission.CAMERA);
      }
      if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(),
          Manifest.permission.RECORD_AUDIO)) {
        permissions.add(Manifest.permission.RECORD_AUDIO);
      }
      if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(),
          Manifest.permission.READ_EXTERNAL_STORAGE)) {
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
      }
      if (permissions.size() != 0) {
        ActivityCompat.requestPermissions(getActivity(),
            (String[]) permissions.toArray(new String[0]), REQ_PERMISSION_CODE);
        return false;
      }
    }

    return true;
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
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

  @Override public void onBackPressed() {
    if (mHomePopUpWindow != null && mHomePopUpWindow.isShowing()) {
      mHomePopUpWindow.dismiss();
    } else {
      if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
        super.onBackPressed();
      }
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onEvent(HomeDataRefreshEvent event) {
    ToastUtils.show(event.showCenterTab.toString());
    //        bindView(R.id.center_tab, event.showCenterTab);
  }

  private void setCustomConfig() {
    //注册IM事件回调，这里示例为用户被踢的回调，更多事件注册参考文档
    TUIKit.setIMEventListener(new IMEventListener() {
      @Override public void onForceOffline() {
        ToastUtil.toastLongMessage("您的帐号已在其它终端登录");
        UserProfile.getInstance().clean();
        Intent intent = new Intent(APP.getInstance(), SelectLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LauncherHelper.from(APP.getInstance()).startActivity(intent);
        ActivityManager.getInstance().exit();
      }

      @Override public void onDisconnected(int code, String desc) {
      }
    });
  }

  @Override protected void onDestroy() {
    EventBus.getDefault().unregister(this);
    if (subscribe != null && !subscribe.isDisposed()) {
      subscribe.dispose();
      subscribe = null;
    }
    super.onDestroy();
  }

  @Override protected void onResume() {
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

  @Override public void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean) {
    if (userIsAnchorBean != null) {
      UserProfile.getInstance().setAnchorType(userIsAnchorBean.is_anchor);
      UserProfile.getInstance().setUserVip(userIsAnchorBean.is_vip);
      UserProfile.getInstance().setUserName(userIsAnchorBean.nick_name);
    }
  }

  public static int getSecondTimestamp(Date date) {
    if (null == date) {
      return 0;
    }
    String timestamp = String.valueOf(date.getTime() / 1000);
    return Integer.valueOf(timestamp);
  }



  private void showMessageCount(int count) {
    if (count == 0) {
      mBottomNavigationView.mControl.badge_view.setVisibility(View.GONE);
    } else {
      mBottomNavigationView.mControl.badge_view.setVisibility(View.VISIBLE);
      if (count > 99) {
        if (mBottomNavigationView != null
            && mBottomNavigationView.mControl != null
            && mBottomNavigationView.mControl.badge_view != null) {
          linearParams.width = DensityUtils.dp2px(getActivity(), 35);
          linearParams.height = DensityUtils.dp2px(getActivity(), 18);
          mBottomNavigationView.mControl.badge_view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
          mBottomNavigationView.mControl.badge_view.setText("99+");
        }
      } else {
        if (count < 10) {
          if (mBottomNavigationView != null
              && mBottomNavigationView.mControl != null
              && mBottomNavigationView.mControl.badge_view != null) {
            linearParams.width = DensityUtils.dp2px(getActivity(), 18);
            linearParams.height = DensityUtils.dp2px(getActivity(), 18);
          }
        } else {
          if (mBottomNavigationView != null
              && mBottomNavigationView.mControl != null
              && mBottomNavigationView.mControl.badge_view != null) {
            linearParams.width = DensityUtils.dp2px(getActivity(), 27);
            linearParams.height = DensityUtils.dp2px(getActivity(), 18);
          }
        }
        mBottomNavigationView.mControl.badge_view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        mBottomNavigationView.mControl.badge_view.setText(count + "");
      }
    }
  }
}
