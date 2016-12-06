package com.wildwolf.myplayer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.component.ImageLoader;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.model.bean.VideoType;
import com.wildwolf.myplayer.utils.JumpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class SwipeDeckAdapter extends BaseAdapter{

    private List<VideoType> data;
    private Context context;
    private LayoutInflater inflater;
    private VideoInfo videoInfo;

    public SwipeDeckAdapter(List<VideoType> data, Context context) {
        this.data =data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder =null;
        if (view==null){
            view = inflater.inflate(R.layout.card_item,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        ImageLoader.load(context, data.get(position).pic, holder.offerImage);
        String intro = "\t\t\t" + data.get(position).description;
        holder.tvIntroduction.setText(intro);
        holder.tv_title.setText(data.get(position).title);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchData(data.get(position));
                JumpUtil.go2VideoInfoActivity(context, videoInfo);
            }
        });
        return view;

    }

    static class ViewHolder {
        @BindView(R.id.offer_image)
        RoundedImageView offerImage;
        @BindView(R.id.tv_introduction)
        TextView tvIntroduction;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.tv_title)
        TextView tv_title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void switchData(VideoType videoType) {
        if (videoInfo == null)
            videoInfo = new VideoInfo();
        videoInfo.title = videoType.title;
        videoInfo.dataId = videoType.dataId;
        videoInfo.pic = videoType.pic;
        videoInfo.airTime = videoType.airTime;
        videoInfo.score = videoType.score;
    }

}
