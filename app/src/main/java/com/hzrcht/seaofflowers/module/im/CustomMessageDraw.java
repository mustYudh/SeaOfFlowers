package com.hzrcht.seaofflowers.module.im;

import android.util.Log;
import android.view.View;

import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMMessage;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.IOnCustomMessageDrawListener;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;

public class CustomMessageDraw implements IOnCustomMessageDrawListener {
    /**
     * 自定义消息渲染时，会调用该方法，本方法实现了自定义消息的创建，以及交互逻辑
     *
     * @param parent 自定义消息显示的父View，需要把创建的自定义消息view添加到parent里
     * @param info   消息的具体信息
     */
    @Override
    public void onDraw(ICustomMessageViewGroup parent, MessageInfo info) {
        View view = null;
        // 获取到自定义消息的json数据
        TIMCustomElem elem = (TIMCustomElem) info.getTIMMessage().getElement(0);
        TIMMessage timMessage = info.getTIMMessage();
        Log.e("aaaacccc",timMessage.toString()+"");


        // 自定义的json数据，需要解析成bean实例

//        final CustomMessageData customMessageData = new Gson().fromJson(elem.getData().toString(), CustomMessageData.class);
//        // 通过类型来创建不同的自定义消息展示view
//        switch (customMessageData.type) {
//            case CustomMessageData.TYPE_PRESENT:
//                view = LayoutInflater.from(APP.getInstance()).inflate(R.layout.item_chat_present, null, false);
//                // 把自定义消息view添加到TUIKit内部的父容器里
//                parent.addMessageContentView(view);
//                break;
//        }
//
//        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
//        ImageView iv_present = view.findViewById(R.id.iv_present);
//        TextView tv_name = view.findViewById(R.id.tv_name);
//        TextView tv_price = view.findViewById(R.id.tv_price);
//        tv_name.setText(customMessageData.title);
//        tv_price.setText(customMessageData.price);
//        ImageLoader.getInstance().displayImage(iv_present, customMessageData.url);
    }
}
