package com.wildwolf.myplayer.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.ui.adapter.viewholder.RecommentViewHolder;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public class RecommendAdapter extends RecyclerArrayAdapter<VideoInfo> {

    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommentViewHolder(parent);
    }
}
