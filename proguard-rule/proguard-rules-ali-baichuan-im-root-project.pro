-dontoptimize
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-allowaccessmodification
-printmapping map.txt
-optimizationpasses 7
-dontskipnonpubliclibraryclassmembers
-dontwarn com.alibaba.wireless.**
-dontwarn com.ut.mini.**
-dontwarn com.taobao.**
-dontwarn com.alibaba.sdk.android.**
-dontwarn com.alibaba.fastjson.**
-dontwarn org.apache.http.**
-dontwarn android.webkit.**
-dontwarn com.alibaba.mobileim.kit.chat.ChattingFragment
-dontwarn com.alibaba.mobileim.lib.model.provider.IMCursor
-dontwarn com.alibaba.sdk.android.**
-dontwarn com.google.gson.**
-dontwarn com.alibaba.mtl.appmonitor.**
-dontwarn com.alibaba.wxlib.util.**
-dontwarn com.alibaba.sdk.android.media.**


-dontwarn android.support.v4.**
-dontwarn com.alibaba.mobileim.YWUIAPI
-dontwarn com.alibaba.mobileim.WXAPI
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.os.IInterface
-keep public class * extends android.os.ResultReceiver
-keep public class * extends android.appwidget.AppWidgetProvider
-keep public class * extends android.webkit.*{*;}
-keep public class * extends android.widget.*{*;}
-keep public class * extends android.app.*{*;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepattributes  *Annotation*

-keep public class com.alibaba.mobileim.gingko.model.provider.WXProvider.DatabaseHelper { public void onDowngrade(android.database.sqlite.SQLiteDatabase,int,int);}
-keep public class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *;}



-keep class com.alibaba.mobileim.YWUIAPI{
    public <methods>;
}

-keep class com.alibaba.mobileim.YWSDK {*;}
-keep class com.alibaba.mobileim.YWIMKit {*;}
-keep class com.alibaba.mobileim.WXAPI {*;}
-keep class com.alibaba.mobileim.ui.chat.widget.ChattingRecordView {*;}
-keep interface com.alibaba.mobileim.IYWUIPushListener {*;}
-keep interface com.alibaba.mobileim.ui.** {*;}

-keep interface com.alibaba.mobileim.IYWConversationItemClickListener {*;}
-keep class com.alibaba.mobileim.aop.** {*;}


-keep class * implements java.io.Serializable {*;}
-keep interface com.alibaba.tcms.IPushManager {*;}
-keep interface com.alibaba.tcms.PushListener {*;}
-keep interface com.alibaba.vconn.ChannelConnectionListener {*;}
-keep class * implements com.alibaba.tcms.IPushManager {*;}
-keep class com.alibaba.tcms.service.TCMPush {*;}
-keep class com.alibaba.tcms.service.ITCMPushListener {*;}
-keep class com.alibaba.tcms.service.TCMSService {*;}
-keep class com.alibaba.tcms.TCMResult {*;}
-keep class com.alibaba.tcms.TCMSBroadcastReceiver {*;}
-keep class com.alibaba.tcms.DefaultClientManager {*;}
-keep class com.alibaba.tcms.NtfCommandVO {*;}
-keep class com.alibaba.tcms.client.ResultMessage {*;}
-keep class com.alibaba.tcms.client.ClientRegInfo {*;}
-keep class com.alibaba.tcms.action.param.** {*;}
-keep class com.alibaba.tcms.track.** {*;}
-keep class com.alibaba.tcms.utils.** {*;}
-keep class com.alibaba.vconn.** {*;}
-keep class com.alibaba.util.** {*;}
-keep class com.alibaba.tcms.notice.** {*;}
-keep class com.alibaba.tcms.env.** {*;}
-keep class com.alibaba.tcms.PushConstant {*;}
-keep class com.alibaba.tcms.PersistManager {*;}
-keep class com.alibaba.mobileim.channel.IMChannel {
    android.app.Application sApp;
}

-keep class * implements com.alibaba.mobileim.channel.itf.ItfPacker {*;}

-keep class com.alibaba.mobileim.channel.itf.mimsc.** {*;}

-keep class com.alibaba.mobileim.channel.itf.mpcsc.** {*;}
-keep class com.alibaba.mobileim.channel.service.InetIO {*;}
-keep interface com.alibaba.mobileim.aop.Advice {*;}
-keep class * implements com.alibaba.mobileim.aop.Advice {*;}
-keep class com.alibaba.tcms.util.** {*;}
-keep class com.alibaba.wxlib.** {*;}
-keep class com.alibaba.tcms.util.** {*;}
-keep class com.alibaba.mobileim.appmonitor.** {*;}
-keep class com.alibaba.tcms.LogMonitorListener {*;}
-keep class com.alibaba.tcms.LogMonitorManager {*;}
-keep interface com.alibaba.mobileim.kit.common.INotificationSetting {*;}
-keep class * implements com.alibaba.mobileim.kit.common.INotificationSetting {*;}
-keep class com.alibaba.mobileim.wxadapter.** {*;}
-keep class com.alibaba.mobileim.channel.itf.mimsc.ContactInfo {
<fields>;
<init>(...);
}

-keep interface com.alibaba.mobileim.gingko.model.base.IKeepClassForProguard {*;}
-keep class * implements com.alibaba.mobileim.gingko.model.base.IKeepClassForProguard {*;}

-keep class com.alibaba.mobileim.itfpack.** {*; }
-keep class com.alibaba.sqlcrypto.** {*; }
-keep class com.alibaba.mobileim.inetservice.**{*; }
-keep class * extends com.alibaba.mobileim.model.BaseUser {*;}
-keep class com.alibaba.mobileim.model.BaseUser {*;}
-keep class com.alibaba.mobileim.model.Contacts {*;}
-keep class com.alibaba.mobileim.model.ContactFriend {*;}
-keep class com.alibaba.mobileim.tts.AudioData{*; }
-keep class com.alibaba.mobileim.tts.TTS{*; }
-keep class com.alibaba.mobileim.tts.TTSManager{*; }
-keep public class com.alibaba.mobileim.gingko.model.message.FastReplyMessage {*;}
-keep public enum com.alibaba.mobileim.gingko.presenter.mtop.MtopServiceManager$**{ **[] $VALUES; public *;}
-keep class com.taobao.securityjni.**{*; }
-keep class com.taobao.security.**{*; }
-keep class com.ut.secbody.SecurityMatrix{*; }
-keepattributes  *Annotation*
-keep public class com.alibaba.mobileim.gingko.model.provider.WXContentProvider.DatabaseHelper { public void onDowngrade(android.database.sqlite.SQLiteDatabase,int,int);}
-keep public class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *;}
-keep class com.alibaba.mobileim.gingko.plugin.action.api.**{*; }



-keep class com.alibaba.mobileim.channel.IMChannel {
    android.app.Application sApp;
}

-keep class * implements com.alibaba.mobileim.channel.itf.ItfPacker {
  <fields>;
  *** getBlackList();
  *** setBlackList(...);
  *** setContact(...);
  *** setContactList(...);
  *** setGroupList(...);
  *** setInfo(...);
  *** setItems(...);
  *** setReadTimes(...);
  *** setReadTimesList(...);
  *** setRoomsId(...);
  *** setSessionList(...);
  *** setUserIds(...);
  *** setUserStatusList(...);
}


-keep class com.alibaba.mobileim.channel.itf.mpcsc.RoomInfo {
  <fields>;
  <init>(...);
  *** setMemberList(...);
  *** setMessages(...);
}

-keep class com.alibaba.mobileim.channel.itf.mimsc.ContactInfo {
<fields>;
<init>(...);
}
-keep class com.alibaba.mobileim.channel.itf.mimsc.LatentContact {<fields>;<init>(...);}
-keep class com.alibaba.mobileim.channel.itf.mimsc.UserGroup {<fields>;<init>(...); }
-keep class com.alibaba.mobileim.channel.itf.mimsc.FriendRecommendItem {<fields>;<init>(...);}
-keep class com.alibaba.mobileim.channel.itf.mpcsc.RoomUserInfo {<fields>;<init>(...);}
-keep class com.alibaba.mobileim.channel.itf.mimsc.UserStatus {<fields>;<init>(...); }
-keep class com.alibaba.mobileim.channel.itf.mimsc.ChgContactInfo {<fields>;<init>(...);}
-keep class com.alibaba.mobileim.channel.itf.mimsc.LogonSessionInfo {<fields>;<init>(...);}
-keep class com.alibaba.mobileim.channel.itf.mimsc.ReadTimes {<fields>;<init>(...);}

-keep class com.alibaba.mobileim.channel.itf.mpcsc.MpcsMessage {<fields>;<init>(...);}
-keep class com.alibaba.mobileim.channel.itf.mimsc.MsgItem {<fields>;<init>(...);}

-keep class com.alibaba.mobileim.channel.itf.mimsc.ImNtfFwdMsg {*; }
-keep class com.alibaba.mobileim.channel.itf.mimsc.MessageBody {*; }
-keep class com.alibaba.mobileim.channel.service.InetIO {*;}
-keep class com.alibaba.util.** {*; }
-keep class org.webrtc.voiceengine.WebRTCAudioDevice {*;}

-keep class com.taobao.joylabs.joysecurity.**{*;}

-keep class org.apache.commons.logging.** {*;}
-keep class com.vladium.** {*;}
-keep class com.taobao.tcc.** {*;}

-keep class com.ut.secbody.**{*;}
-keep class com.taobao.wireless.security.**{*;}

-keep public class * implements mtopsdk.mtop.domain.IMTOPDataObject {*;}
-keep public class mtopsdk.mtop.domain.MtopResponse
-keep public class mtopsdk.mtop.domain.MtopRequest
-keep class mtopsdk.mtop.domain.**{*;}
-keep class mtopsdk.common.util.**{*;}
-keep public class org.android.spdy.**{*; }
-keep class com.taobao.wswitch.**{*;}
-keep class mtopsdk.mtop.**{*;}
-keep class mtopsdk.mtop.xcommand.**{*;}
-keep class * extends mtopsdk.mtop.intf.MtopBuilder{*;}
-keep class com.alibaba.mobileim.gingko.mtop.lightservice.pojo.** {*;}
-keep class com.alibaba.mobileim.eventbus.lightservice.** {*;}
-keep class com.alibaba.mobileim.gingko.model.lightservice.** {*;}
-keepattributes  *Annotation*

-keep public class com.alibaba.mobileim.gingko.model.provider.WXProvider.DatabaseHelper { public void onDowngrade(android.database.sqlite.SQLiteDatabase,int,int);}
-keep public class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *;}



-keep class com.alibaba.mobileim.YWUIAPI{
    public <methods>;
}

-keep class com.alibaba.mobileim.YWSDK {*;}
-keep class com.alibaba.mobileim.YWIMKit {*;}
-keep class com.alibaba.mobileim.WXAPI {*;}
-keep class com.alibaba.mobileim.ui.chat.widget.ChattingRecordBar {*;}
-keep interface com.alibaba.mobileim.IYWUIPushListener {*;}
-keep interface com.alibaba.mobileim.ui.chat.widget.IChattingReply {*;}

-keep interface com.alibaba.mobileim.IYWConversationItemClickListener {*;}
-keep class com.alibaba.mobileim.aop.** {*;}
-keep class com.alibaba.mobileim.ui.chat.ChattingHandlerManager {*;}
-keep class com.alibaba.mobileim.ui.chat.AbstractCustomHandler {*;}
-keep class * implements com.alibaba.mobileim.contact.IYWContact {*;}
-keep class com.taobao.openimui.** {*;}


-keep class * implements java.io.Serializable {*;}
-keep interface com.alibaba.tcms.IPushManager {*;}
-keep interface com.alibaba.tcms.PushListener {*;}
-keep interface com.alibaba.vconn.ChannelConnectionListener {*;}
-keep class * implements com.alibaba.tcms.IPushManager {*;}
-keep class com.alibaba.tcms.service.TCMPush {*;}
-keep class com.alibaba.tcms.service.ITCMPushListener {*;}
-keep class com.alibaba.tcms.service.TCMSService {*;}
-keep class com.alibaba.tcms.TCMResult {*;}
-keep class com.alibaba.tcms.TCMSBroadcastReceiver {*;}
-keep class com.alibaba.tcms.DefaultClientManager {*;}
-keep class com.alibaba.tcms.NtfCommandVO {*;}
-keep class com.alibaba.tcms.client.ResultMessage {*;}
-keep class com.alibaba.tcms.client.ClientRegInfo {*;}
-keep class com.alibaba.tcms.action.param.** {*;}
-keep class com.alibaba.tcms.track.** {*;}
-keep class com.alibaba.tcms.utils.** {*;}
-keep class com.alibaba.vconn.** {*;}
-keep class com.alibaba.util.** {*;}
-keep class com.alibaba.tcms.notice.** {*;}
-keep class com.alibaba.tcms.env.** {*;}
-keep class com.alibaba.tcms.PushConstant {*;}
-keep class com.alibaba.tcms.PersistManager {*;}
-keep class com.alibaba.mobileim.channel.IMChannel {
    android.app.Application sApp;
}

#aliusersdk begin ͳһ��¼
-keep  class * extends com.alipay.aliusergw.core.model.comm.ToString{*;}
-keep  class com.alipay.aliusergw.biz.shared.rpc.model.**{*;}

-keep class com.ali.user.mobile.common.api.AliUserLogin{*;}
-keep class com.ali.user.**{*;}
-keep class com.ali.user.**.*$*{*;}
-keep class com.alipay.aliusergw.biz.shared.processer.login.**{*;}
-keep class com.ali.user.mobile.core.dataprovider.**{*;}

-keep  class * extends com.alipay.aliusergw.core.model.comm.ToString
-keep  class com.alipay.aliusergw.biz.shared.rpc.model.**{*;}

-keep  class android.**{*;}
-keep  class android.app.*$*{*;}
-keep  class * extends android.app.Application{*;}
-keep  class * extends android.app.Activity
-keep  class * extends android.app.Service
-keep  class * extends android.os.IInterface
-keep  class * extends android.content.BroadcastReceiver
-keep  class * extends android.content.ContentProvider
-keep  class * extends android.appwidget.AppWidgetProvider
-keep  class * extends android.support.v4.app.Fragment{*;}
-keep  class * extends android.widget.**{*;}
-keep  class * extends android.view.View{*;}
-keep  class * implements android.os.Handler.Callback{*;}
-keep  class * implements android.os.IBinder{*;}
-keep  class * extends com.taobao.business.BaseRemoteBusiness{*;}
-keep  class * implements java.io.Serializable
-keep  class * implements Handler.Callback{*;}
-keep  interface * extends android.os.IInterface{*;}
-keep  interface **{*;}

-keep class android.support.** { *; }
-keep class * extends android.support.**{*;}
-keep public class * extends android.support.**
-keep public class * extends android.app.Fragment

-keep class org.apache.http.HttpResponse{*;}
-keep  class org.apache.http.**{*;}
-keep  public class org.apache.http.**{*;}
-keep  class external.org.apache.commons.lang3.**{*;}

-keep  class com.xiaomi.**{*;}
-keep  class * extends android.app.Application
-keep  class org.osgi.**{*;}
-keep  class org.apache.**{*;}
-keep  class com.taobao.statistic.**{*;}
-keep  public class com.taobao.statistic.NDKTBSEngine{*;}
-keep  public class org.json.**{*;}


#usertrack
-keep class com.taobao.android.service.Services$1{*;}
-keep  class com.taobao.statistic.**{*;}
-keep  public class com.ut.device.**{*;}
-keep  class org.usertrack.**{*;}
-keep  public class com.ta.utdid2.**{*;}

#mtopsdk
-keep public class * implements mtopsdk.mtop.domain.IMTOPDataObject {*;}
-keep public class mtopsdk.mtop.domain.MtopResponse
-keep public class mtopsdk.mtop.domain.MtopRequest
-keep class mtopsdk.mtop.domain.**{*;}
-keep class mtopsdk.common.util.**{*;}
-keep class com.taobao.tao.connectorhelper.*
-keep public class org.android.spdy.**{*; }
-keep class mtop.sys.newDeviceId.Request{*;}

##Login
#login4android
-keep public class com.taobao.login4android.**{*;}
-keep class * implements com.taobao.android.nav.Nav.NavPreprocessor{*;}
-keep  class * extends android.os.IInterface
-keep  interface * extends android.os.IInterface{*;}
#ssologin
-keep public class com.taobao.android.ssologin.**{*;}
-keep public class com.taobao.android.sso.**{*;}
-keep public class com.taobao.android.ssologinwrapper.**{*;}
#aliusersdk
-keep class com.ali.user.mobile.common.api.AliUserLogin{*;}
-keep class com.ali.user.**{*;}
-keep class com.ali.user.**.*$*{*;}
-keep class com.alipay.aliusergw.biz.shared.rpc.**{*;}

-keep  class * extends com.alipay.aliusergw.core.model.comm.ToString{*;}
-keep  class com.alipay.aliusergw.biz.shared.rpc.model.**{*;}
-keep class com.alipay.aliusergw.biz.shared.processer.login.**{*;}
-keep class com.ali.user.mobile.core.dataprovider.**{*;}

#alipay security
-keep class com.alipay.aliusergw.core.model.comm.ToString{*;}
-keep class com.alipay.mobilesecuritysdk.datainfo.**{*;}
-keep class com.alipay.android.monitor.MonitorGlobalInit
-keep class com.alibaba.fastjson.**{*;}
#infsword
-keep class com.taobao.infsword.**{*;}
-keep class com.taobao.infsword.client.IAntiTrojan$*{*;}
#umid
-keep class com.taobao.dp.**{*;}
-keep class com.taobao.dp.bean.TDMessage{*;}
-keep class com.taobao.dp.bean.DeviceInfo{*;}
-keep class com.taobao.dp.bean.ReqData{*;}
-keep class com.taobao.dp.bean.ServiceData{*;}
-keep class com.taobao.dp.DeviceSecuritySDK{*;}
-keep class com.taobao.dp.data.MastiffContentProvider{*;}
-keep class com.taobao.dp.http.DefaultUrlRequestService{
    *** sendRequest(...);
}
#apse
-keep class com.alipay.mobile.security.senative.APSE {
    public native <methods>;
}

##securityguard
-keep class com.taobao.wireless.security.**{*;}
-keep class com.ut.secbody.**{*;}
-keep public class com.taobao.securityjni.**{*;}
-keep public class com.taobao.security.**{*;}


-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

-keep class * extends android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

-keep class com.alibaba.mobileim.channel.event.** {*; }
-keep class com.alibaba.mobileim.lib.presenter.aop.** {*;}
-keep class * implements com.alibaba.mobileim.lib.presenter.aop.IAccountAdvice {*;}



-keep class * implements com.alibaba.mobileim.channel.itf.ItfPacker {*;}

-keep class com.alibaba.mobileim.channel.itf.mimsc.** {*;}

-keep class com.alibaba.mobileim.channel.itf.mpcsc.** {*;}
-keep class com.alibaba.mobileim.channel.service.InetIO {*;}
-keep interface com.alibaba.mobileim.aop.Advice {*;}
-keep class * implements com.alibaba.mobileim.aop.Advice {*;}

-keep class com.alibaba.wxlib.** {*;}
-keep class com.alibaba.tcms.util.** {*;}

-keep class com.tencent.mm.sdk.** {
   *;
}

-keep class com.alibaba.mobileim.contact.IYWOnlineContact {*;}
-keep class * implements com.alibaba.mobileim.contact.IYWOnlineContact {*;}
-keep class com.alibaba.mobileim.kit.common.YWAsyncBaseAdapter {*;}
-keep class com.alibaba.mobileim.kit.contact.YWContactHeadLoadHelper {*;}
-keep class com.alibaba.mobileim.channel.util.YWLog {*;}
-keep class com.alibaba.mobileim.fundamental.widget.YWAlertDialog {*;}
-keep class com.alibaba.mobileim.fundamental.widget.refreshlist.YWPullToRefreshBase {*;}
-keep class com.alibaba.mobileim.fundamental.widget.refreshlist.YWPullToRefreshListView {*;}

-keep class com.taobao.media.** {*;}

-keep class  com.alibaba.sdk.android.media.**{*;}