package com.hzrcht.seaofflowers.module.home;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hzrcht.seaofflowers.APP;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.keep.BackgroundService;
import com.hzrcht.seaofflowers.keep.GrayService;
import com.hzrcht.seaofflowers.module.dynamic.fragment.DynamicFragment;
import com.hzrcht.seaofflowers.module.event.DataSynVideoEvent;
import com.hzrcht.seaofflowers.module.event.DataSynVideoWaitEvent;
import com.hzrcht.seaofflowers.module.event.HomeDataRefreshEvent;
import com.hzrcht.seaofflowers.module.home.fragment.HomeFragment;
import com.hzrcht.seaofflowers.module.home.presenter.HomePagePresenter;
import com.hzrcht.seaofflowers.module.home.presenter.HomePageViewer;
import com.hzrcht.seaofflowers.module.login.activity.SelectLoginActivity;
import com.hzrcht.seaofflowers.module.message.fragment.MessageFragment;
import com.hzrcht.seaofflowers.module.mine.activity.CurrentChatMember;
import com.hzrcht.seaofflowers.module.mine.activity.MineChatActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.hzrcht.seaofflowers.module.mine.fragment.MineFragment;
import com.hzrcht.seaofflowers.utils.ActivityManager;
import com.hzrcht.seaofflowers.utils.permissions.MorePermissionsCallBack;
import com.hzrcht.seaofflowers.utils.permissions.PermissionManager;
import com.nhbs.fenxiao.module.center.HomeCenterPopUpWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;
import com.tencent.qcloud.tim.uikit.modules.message.CustomMessageData;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;
import com.tencent.qcloud.tim.uikit.utils.FileUtil;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.utils.DensityUtils;
import com.yu.common.utils.PressHandle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yudenghao
 */
public class HomePageActivity extends BaseActivity
    implements HomePageViewer, ConversationManagerKit.MessageUnreadWatcher {
  private PressHandle pressHandle = new PressHandle(this);
  private final static int REQ_PERMISSION_CODE = 0x1000;
  @PresenterLifeCycle HomePagePresenter mPresenter = new HomePagePresenter(this);
  private BottomNavigationView mBottomNavigationView;
  private Disposable subscribe;
  private HomeCenterPopUpWindow mHomePopUpWindow;
  private FrameLayout.LayoutParams linearParams;
  private int mUnreadTotal = 0;

  private final static String BLACK_WAKE_ACTION = "com.wake.black";

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
          try {
            filterMessage(getActivity(), msg);
          } catch (Exception e) {

          }

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
                    Bundle bundle = new Bundle();
                    bundle.putString("CONTENT", messageData.content);
                    //                                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                      getLaunchHelper().startActivity(HomeVideoWaitActivity.class,bundle);
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

    // 未读消息监视器
    ConversationManagerKit.getInstance().addUnreadWatcher(this);
    GroupChatManagerKit.getInstance();

    List<TIMConversation> TIMConversations = TIMManagerExt.getInstance().getConversationList();
    for (TIMConversation conversation : TIMConversations) {
      TIMConversationExt timConversationExt = new TIMConversationExt(conversation);
      mUnreadTotal += timConversationExt.getUnreadMessageNum();
    }

    showMessageCount(mUnreadTotal);
    keepServices();

//    OffLinePushManager.getInstance(true);
//    ThirdPushTokenMgr.getInstance().setIsLogin(true);
//    ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
  }

  private void keepServices() {
    try {
      //Intent whiteIntent = new Intent(getApplicationContext(), WhiteService.class);
      //startService(whiteIntent);
      Intent grayIntent = new Intent(getApplicationContext(), GrayService.class);
      startService(grayIntent);
      Intent blackIntent = new Intent();
      blackIntent.setAction(BLACK_WAKE_ACTION);
      sendBroadcast(blackIntent);
      Intent bgIntent = new Intent(getApplicationContext(), BackgroundService.class);
      startService(bgIntent);
    } catch (Exception e) {
      e.printStackTrace();
    }
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

  @Override public void updateUnread(int count) {
    Log.e("======>count", count + "");
    showMessageCount(count);
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

  private void filterMessage(Context context, TIMMessage message) {
    if (message != null) {
      TIMConversation conversation = message.getConversation();
      if (conversation != null) {
        TIMConversationType type = conversation.getType();
        boolean isGroup = type == TIMConversationType.Group;
        MessageInfo msg = MessageInfoUtil.TIMMessage2MessageInfo(message, isGroup);
        Log.e("======>消息", msg.toString());
        if (msg != null && !msg.isRead() && !msg.isSelf()) {
          Object extra = msg.getExtra();
          String userId = msg.getFromUser();
          if (extra != null && !TextUtils.isEmpty(userId)) {
            BigInteger b = new BigInteger(message.getMsgId());
            showNotificationManager(message,context, extra.toString(), userId, b.intValue(), conversation);
          }
        }
      }
    }
  }

  private void showNotificationManager(TIMMessage message,Context context, String content, String userId,
      int messageID, TIMConversation conversation) {
    TIMUserProfile profile =
        TIMFriendshipManager.getInstance().queryUserProfile(conversation.getPeer());
    String nickName = profile.getNickName();
    String header = profile.getFaceUrl();
    Intent notificationIntent = null;
      Bundle bundle = new Bundle();
      //判断消息类型
      TIMElemType elemType = message.getElement(0).getType();
      if (elemType == TIMElemType.Custom){
          //自定义消息
          TIMCustomElem elem = (TIMCustomElem) message.getElement(0);
          Gson gson = new Gson();
          CustomMessageData messageData = gson.fromJson(new String(elem.getData()), CustomMessageData.class);
          if ("1".equals(messageData.type)){
              notificationIntent = new Intent(context, HomeVideoWaitActivity.class);
              bundle.putString("CONTENT", messageData.content);
          }else {
              notificationIntent = new Intent(context, MineChatActivity.class);
              bundle.putString("IM_ID", userId);
          }
      }else {
          notificationIntent = new Intent(context, MineChatActivity.class);
          bundle.putString("IM_ID", userId);
      }
    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    notificationIntent.putExtras(bundle);
    NotificationManager mNotificationManager =
        (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, messageID, notificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT);
    mBuilder.setContentTitle(nickName)
        .setContentText(content)
        .setContentIntent(pendingIntent)
        .setTicker(nickName + ":" + context)
        .setWhen(System.currentTimeMillis())
        .setDefaults(Notification.DEFAULT_ALL)
        .setSmallIcon(R.mipmap.ic_launcher);
    Notification notify = mBuilder.build();
    notify.flags |= Notification.FLAG_AUTO_CANCEL;
    Log.e("======>", CurrentChatMember.getInstance().gerUserId() + "=====" + userId);
    if (!CurrentChatMember.getInstance().gerUserId().equals(userId)) {
      assert mNotificationManager != null;
      mNotificationManager.notify(messageID, notify);
    }
  }
}
