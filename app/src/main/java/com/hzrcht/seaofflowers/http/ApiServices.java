package com.hzrcht.seaofflowers.http;

public class ApiServices {
    public static final String BASEURL = "http://huahai.hzrcht.com";

    //验证码登录
    public static final String CODELOGIN = BASEURL + "/api/login/login";

    //发送验证码
    public static final String SENDVERCODE = BASEURL + "/api/code/index";

    //选择性别
    public static final String SELECTSEX = BASEURL + "/api/user/sex";

    //关注列表
    public static final String GETATTENTIONLIST = BASEURL + "/api/attent/index";

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

    //用户信息
    public static final String MINEUSERINFO = BASEURL + "/api/user/info";

    //主播和用户详情
    public static final String USERINFO = BASEURL + "/api/user/userInfo";

    //动态列表
    public static final String STATELIST = BASEURL + "/api/state/index";

    //动态点赞
    public static final String STATELIKE = BASEURL + "/api/state/like";

    //动态举报
    public static final String STATEREPORT = BASEURL + "/api/report/add";

    //动态删除
    public static final String STATEDEL = BASEURL + "/api/state/del";

    //动态发布
    public static final String STATEADD = BASEURL + "/api/state/add";

    //提现列表
    public static final String USERWITHDRAW = BASEURL + "/api/user/withdraw";

}
