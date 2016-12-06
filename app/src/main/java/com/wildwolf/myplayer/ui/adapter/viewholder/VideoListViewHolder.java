package com.wildwolf.myplayer.ui.adapter.viewholder;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.component.ImageLoader;
import com.wildwolf.myplayer.model.bean.VideoType;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class VideoListViewHolder extends BaseViewHolder<VideoType> {
    ImageView imgPicture;
    TextView tv_title;

    public VideoListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_related);
        imgPicture = $(R.id.img_video);
        tv_title = $(R.id.tv_title);
        imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void setData(VideoType data) {
        tv_title.setText(data.title);
        ViewGroup.LayoutParams params = imgPicture.getLayoutParams();

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels / 3;//宽度为屏幕宽度一半
//        int height = data.getHeight()*width/data.getWidth();//计算View的高度

        params.height = (int) (width * 1.1);
        imgPicture.setLayoutParams(params);
        ImageLoader.load(getContext(),data.pic,imgPicture);
    }
}
