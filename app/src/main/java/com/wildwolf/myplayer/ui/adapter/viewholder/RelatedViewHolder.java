package com.wildwolf.myplayer.ui.adapter.viewholder;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.component.ImageLoader;
import com.wildwolf.myplayer.model.bean.VideoInfo;

/**
 * Created by ${wild00wolf} on 2016/12/2.
 */
public class RelatedViewHolder extends BaseViewHolder<VideoInfo> {

    ImageView imgPicture;
    TextView tv_title;

    public RelatedViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_related);
        imgPicture = $(R.id.img_video);
        tv_title = $(R.id.tv_title);
        imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void setData(VideoInfo data) {
        tv_title.setText(data.title);
        ViewGroup.LayoutParams params = imgPicture.getLayoutParams();

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels / 3;
        params.height = (int) (width * 1.2);
        imgPicture.setLayoutParams(params);
        ImageLoader.load(getContext(),data.pic,imgPicture);
    }
}
