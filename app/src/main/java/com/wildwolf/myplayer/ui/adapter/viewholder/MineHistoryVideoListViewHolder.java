package com.wildwolf.myplayer.ui.adapter.viewholder;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.component.ImageLoader;
import com.wildwolf.myplayer.model.bean.VideoType;

import butterknife.BindView;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class MineHistoryVideoListViewHolder extends BaseViewHolder<VideoType> {


    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public MineHistoryVideoListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_mine_history);
        imgVideo.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void setData(VideoType data) {
        tvTitle.setText(data.title);
        ViewGroup.LayoutParams params = imgVideo.getLayoutParams();

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels / 3;//宽度为屏幕宽度一半
//        int height = data.getHeight()*width/data.getWidth();//计算View的高度

        params.height = width;
        imgVideo.setLayoutParams(params);
        ImageLoader.load(getContext(), data.pic, imgVideo);
    }
}
