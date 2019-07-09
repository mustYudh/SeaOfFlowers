package com.hzrcht.seaofflowers.module.dynamic.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.utils.PhotoUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yu.common.glide.ImageLoader;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Activity context;
    private List<LocalMedia> list;

    public GridAdapter(Activity context, List<LocalMedia> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list.size() == 9) {
            return list.size();
        }
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.gv_item_release, null);
            holder = new ViewHolder();
            holder.ivDemo = (ImageView) convertView.findViewById(R.id.iv_picture_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == list.size()) {
            if (position == 9) {
                holder.ivDemo.setVisibility(View.GONE);
            }
            for (int i = 0; i < list.size(); i++) {
                LocalMedia media = list.get(i);
                if (PictureMimeType.isPictureType(media.getPictureType()) == PictureConfig.TYPE_VIDEO) {
                    holder.ivDemo.setVisibility(View.GONE);
                }
            }
            holder.ivDemo.setImageBitmap(BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.ic_upload_release));
            holder.ivDemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoUtils.changeAvatar(context, list, 9, "上传您的照片,以供展示");
                }
            });
        } else {
         
            ImageLoader.getInstance().displayImage(holder.ivDemo, list.get(position).getPath(), R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView ivDemo;
    }
}
