package com.wildwolf.myplayer.ui.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.component.ImageLoader;
import com.wildwolf.myplayer.model.bean.VideoInfo;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public class RecommentViewHolder extends BaseViewHolder<VideoInfo> {

    ImageView imgPicture;
    TextView tv_title;

    public RecommentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_video);
        imgPicture = $(R.id.img_video);
        tv_title = $(R.id.tv_title);
        imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void setData(VideoInfo data) {
        tv_title.setText(data.title);
        ImageLoader.load(getContext(), data.pic, imgPicture);
    }
}
