package com.hzrcht.seaofflowers.module.im;

public class CustomMessageData {
    final static int TYPE_PRESENT = 1;
    // 自定义消息类型，根据业务可能会有很多种
    int type = TYPE_PRESENT;
    public String title;
    public String url;
    public String price;

}
