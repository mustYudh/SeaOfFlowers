package com.hzrcht.seaofflowers.module.im;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.APP;
import com.hzrcht.seaofflowers.R;
import com.tencent.qcloud.tim.uikit.modules.message.CustomMessageData;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.IOnCustomMessageDrawListener;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.yu.common.glide.ImageLoader;

public class CustomMessageDraw implements IOnCustomMessageDrawListener {
    /**
     * 自定义消息渲染时，会调用该方法，本方法实现了自定义消息的创建，以及交互逻辑
     *
     * @param parent 自定义消息显示的父View，需要把创建的自定义消息view添加到parent里
     * @param info   消息的具体信息
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onDraw(ICustomMessageViewGroup parent, MessageInfo info) {
        for (int i = 0; i < info.getTIMMessage().getElementCount(); i++) {
            try {
                View view = null;
                // 获取到自定义消息的json数据

                TIMCustomElem elem = (TIMCustomElem) info.getTIMMessage().getElement(i);
                Gson gson = new Gson();
                CustomMessageData
                    customMessageData = gson.fromJson(new String(elem.getData()), CustomMessageData.class);
                switch (customMessageData.type) {
                    case "0":
                        //礼物
                        String[] split = customMessageData.content.split(",");
                        view = LayoutInflater.from(APP.getInstance()).inflate(R.layout.item_chat_present, null, false);
                        // 把自定义消息view添加到TUIKit内部的父容器里
                        parent.addMessageContentView(view);
                        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
                        ImageView iv_present = view.findViewById(R.id.iv_present);
                        TextView tv_name = view.findViewById(R.id.tv_name);
                        TextView tv_price = view.findViewById(R.id.tv_price);
                        tv_name.setText(split[1] + "");
                        tv_price.setText(split[2] + "");
                        ImageLoader.getInstance().displayImage(iv_present, split[0] + "");
                        break;
                    case "1":
                        //发起视频
                        view = LayoutInflater.from(APP.getInstance()).inflate(R.layout.item_chat_video, null, false);
                        // 把自定义消息view添加到TUIKit内部的父容器里
                        parent.addMessageContentView(view);
                        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
                        TextView tv_name_video = view.findViewById(R.id.tv_name);
                        tv_name_video.setText(info.isSelf() ? "您发起了视频聊天" : "对方发起了视频聊天");
                        break;
                    case "2":
                        //拒绝视频
                        view = LayoutInflater.from(APP.getInstance()).inflate(R.layout.item_chat_video, null, false);
                        // 把自定义消息view添加到TUIKit内部的父容器里
                        parent.addMessageContentView(view);
                        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
                        TextView tv_name_video_other = view.findViewById(R.id.tv_name);
                        tv_name_video_other.setText(info.isSelf() ? "您取消了视频聊天" : "对方拒绝了您的视频聊天");
                        break;
                    case "3":
                        //视频结束
                        view = LayoutInflater.from(APP.getInstance()).inflate(R.layout.item_chat_video, null, false);
                        // 把自定义消息view添加到TUIKit内部的父容器里
                        parent.addMessageContentView(view);
                        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
                        TextView tv_name_video_end = view.findViewById(R.id.tv_name);
                        tv_name_video_end.setText(info.isSelf() ? "您结束了视频聊天" : "对方结束了视频聊天");
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
