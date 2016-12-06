package com.wildwolf.myplayer.ui.adapter.viewholder;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.component.ImageLoader;
import com.wildwolf.myplayer.model.bean.GankItemBean;
import com.wildwolf.myplayer.model.bean.VideoType;

/**
 * Created by ${wild00wolf} on 2016/12/6.
 */
public class WelfareListViewHolder extends BaseViewHolder<GankItemBean> {

    ImageView imgPicture;

    public WelfareListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_related);
        imgPicture = $(R.id.img_video);
        imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void setData(GankItemBean data) {
        ViewGroup.LayoutParams params = imgPicture.getLayoutParams();
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels / 3;//宽度为屏幕宽度一半
//        int height = data.getHeight()*width/data.getWidth();//计算View的高度

        params.height = (int) (width * 1.1);
        imgPicture.setLayoutParams(params);
        ImageLoader.load(getContext(), data.getUrl(), imgPicture);
    }
}
