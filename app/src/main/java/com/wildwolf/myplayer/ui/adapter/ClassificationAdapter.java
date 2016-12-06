package com.wildwolf.myplayer.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.ui.adapter.viewholder.ClassificationViewHolder;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class ClassificationAdapter extends RecyclerArrayAdapter<VideoInfo> {

    public ClassificationAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassificationViewHolder(parent);
    }
}
