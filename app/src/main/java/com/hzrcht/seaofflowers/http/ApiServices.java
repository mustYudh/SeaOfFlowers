package com.hzrcht.seaofflowers.http;

public class ApiServices {
    /****** 华为离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long HW_PUSH_BUZID = 6501;
    // 华为开发者联盟给应用分配的应用APPID
    public static final String HW_PUSH_APPID = "100961031"; // 见清单文件
    /****** 华为离线推送参数end ******/

    /****** 小米离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long XM_PUSH_BUZID = 5218;
    // 小米开放平台分配的应用APPID及APPKEY
    public static final String XM_PUSH_APPID = "2882303761517953663";
    public static final String XM_PUSH_APPKEY = "5421795368663";
    /****** 小米离线推送参数end ******/

    /****** 魅族离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long MZ_PUSH_BUZID = 6503;
    // 魅族开放平台分配的应用APPID及APPKEY
    public static final String MZ_PUSH_APPID = "1006972";
    public static final String MZ_PUSH_APPKEY = "18a3dd963a5645009d7434db1cbe3e90";
    /****** 魅族离线推送参数end ******/

    /****** vivo离线推送参数start ******/
    public static final long VIVO_PUSH_BUZID = 6499;
    // vivo开放平台分配的应用APPID及APPKEY
    public static final String VIVO_PUSH_APPID = "13892"; // 见清单文件
    public static final String VIVO_PUSH_APPKEY = "3c041c75-d595-41d4-8c4e-85426c09611f"; // 见清单文件
    /****** vivo离线推送参数end ******/

    public static final int SDKAPPID = 1400224024;

    public static final String BASEURL = "https://huahai.hzrcht.com";

    //验证码登录
    public static final String CODELOGIN = BASEURL + "/api/login/login";

    //发送验证码
    public static final String SENDVERCODE = BASEURL + "/api/code/index";

    //选择性别
    public static final String SELECTSEX = BASEURL + "/api/user/sex";

    //获取userSig
    public static final String GETUSERSIG = BASEURL + "/api/Im/userSig";

    //关注列表
    public static final String GETATTENTIONLIST = BASEURL + "/api/attent/index";

    //粉丝列表
    public static final String GETUSERLIST = BASEURL + "/api/user/userList";

    //主播列表
    public static final String GETANCHORLIST = BASEURL + "/api/user/anchorList";

    //banner列表
    public static final String GETBANNER = BASEURL + "/api/sys/banner";

    //关注和取消关注
    public static final String ATTENTCLICK = BASEURL + "/api/attent/click";

    //上传图片
    public static final String UPLOADIMG = BASEURL + "/api/upload/index";

    //添加相册
    public static final String ADDALBUM = BASEURL + "/api/img/add";

    //我的相册列表
    public static final String GETPHOTOALBUM = BASEURL + "/api/img/index";

    //相册删除
    public static final String IMGDEL = BASEURL + "/api/img/del";

    //用户信息
    public static final String MINEUSERINFO = BASEURL + "/api/user/info";

    //主播和用户详情
    public static final String USERINFO = BASEURL + "/api/user/userInfo";

    //动态列表
    public static final String STATELIST = BASEURL + "/api/state/index";

    //我的动态
    public static final String MINESTATELIST = BASEURL + "/api/user/state";

    //评论列表
    public static final String REVIEWLIST = BASEURL + "/api/state/reviewList";

    //动态点赞
    public static final String STATELIKE = BASEURL + "/api/state/like";

    //动态评论
    public static final String STATEREVIEW = BASEURL + "/api/state/review";

    //动态举报
    public static final String STATEREPORT = BASEURL + "/api/report/add";

    //动态删除
    public static final String STATEDEL = BASEURL + "/api/state/del";

    //动态发布
    public static final String STATEADD = BASEURL + "/api/state/add";

    //礼物柜
    public static final String GIFTINDEX = BASEURL + "/api/gift/index";

    //礼物列表
    public static final String SYSGIFT = BASEURL + "/api/sys/gift";

    //送礼
    public static final String GIFTGIVE = BASEURL + "/api/gift/give";

    //金额目录
    public static final String SYSLANMU = BASEURL + "/api/sys/lanmu";

    //订单支付
    public static final String ORDERADD = BASEURL + "/api/order/add";

    //用户提现账号
    public static final String USERACCOUNTS = BASEURL + "/api/user/accounts";

    //用户余额
    public static final String USERAMOUNT = BASEURL + "/api/user/amount";

    //获取形象标签
    public static final String SYSLABEL = BASEURL + "/api/sys/label";

    //用户修改/添加提现账号
    public static final String USERADDACC = BASEURL + "/api/user/addacc";

    //提现
    public static final String USERWITHDRAW = BASEURL + "/api/user/withdraw";

    //提现列表
    public static final String BILLWITHDRAW = BASEURL + "/api/bill/withdraw";

    //账户余额详情列表
    public static final String BILLINDEX = BASEURL + "/api/bill/index";

    //主播收费列表
    public static final String USERCHARGE = BASEURL + "/api/user/charge";

    //主播收费设置
    public static final String USEREDITCHARGE = BASEURL + "/api/user/editCharge";

    //用户配置修改
    public static final String USEREEDITCONFIG = BASEURL + "/api/user/editConfig";

    //用户提示音和震动
    public static final String USERCONFIG = BASEURL + "/api/user/config";

    //检测用户身份
    public static final String USERISANCHOR = BASEURL + "/api/user/isAnchor";

    //聊天扣钱
    public static final String CHATSTART = BASEURL + "/api/Chat/start";

    //查看手机号
    public static final String LOOKPHONE = BASEURL + "/api/Look/phone";

    //搜索
    public static final String USERSEARCH = BASEURL + "/api/user/search";

    //定时任务
    public static final String USERONLINE = BASEURL + "/api/user/online";

    //用户详情
    public static final String USERDETAIL = BASEURL + "/api/user/detail";

    //用户详情修改
    public static final String USEREDIT = BASEURL + "/api/user/edit";

    //重新绑定手机号
    public static final String USERPHONE = BASEURL + "/api/user/phone";

    //申请主播
    public static final String USERAUDIT = BASEURL + "/api/user/audit";

    //开始视频
    public static final String LIVESTART = BASEURL + "/api/live/start";

    //结束视频
    public static final String LIVEEND = BASEURL + "/api/live/end";

    //视频扣费
    public static final String LIVEPAYCOIN = BASEURL + "/api/live/payCoin";

    //获取微信用户信息
    public static final String WECHATINFO = "https://api.weixin.qq.com/sns/userinfo";

    //三方登录
    public static final String LOGINTHIRD = BASEURL + "/api/login/third";

    //意见反馈
    public static final String USERFEEDBACK = BASEURL + "/api/user/feedback";

    //审核中二维码
    public static final String SYSWECHAT = BASEURL + "/api/sys/wechat";

    //我的通话记录
    public static final String GETLIVELIST = BASEURL + "/api/live/liveList";

    //用户系统消息
    public static final String USERMESSAGE = BASEURL + "/api/user/message";

}
