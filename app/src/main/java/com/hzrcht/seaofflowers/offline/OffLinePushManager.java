package com.hzrcht.seaofflowers.offline;

/**
 * @author yudneghao
 * @date 2019-07-25
 */
public class OffLinePushManager {



  private volatile static OffLinePushManager singleton;

  private OffLinePushManager(boolean open) {
//    TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
//    //开启离线推送
//    settings.setEnabled(open);
//    //设置收到 C2C 离线消息时的提示声音，以把声音文件放在 res/raw 文件夹下为例
//    settings.setC2cMsgRemindSound(
//        Uri.parse("android.resource://" + ContextUtil.getPackageName() + "/" + R.raw.msg));
//    //设置收到群离线消息时的提示声音，以把声音文件放在 res/raw 文件夹下为例
//    settings.setGroupMsgRemindSound(
//        Uri.parse("android.resource://" + ContextUtil.getPackageName() + "/" + R.raw.msg));
//    TIMManager.getInstance().setOfflinePushSettings(settings);
  }


  private OffLinePushManager() {
  }

  public static OffLinePushManager getInstance(boolean open) {
    if (singleton == null) {
      synchronized (OffLinePushManager.class) {
        if (singleton == null) {
          singleton = new OffLinePushManager(open);
        }
      }
    }
    return singleton;
  }



}
