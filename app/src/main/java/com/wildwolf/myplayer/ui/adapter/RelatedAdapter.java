package com.wildwolf.myplayer.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.ui.adapter.viewholder.RelatedViewHolder;

/**
 * Created by ${wild00wolf} on 2016/12/2.
 */
public class RelatedAdapter extends RecyclerArrayAdapter<VideoInfo>{

    public RelatedAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RelatedViewHolder(parent);
    }
}
