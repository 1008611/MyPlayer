package com.wildwolf.myplayer.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wildwolf.myplayer.model.bean.VideoType;
import com.wildwolf.myplayer.ui.adapter.viewholder.CommentViewHolder;

/**
 * Created by ${wild00wolf} on 2016/12/2.
 */
public class CommentAdapter extends RecyclerArrayAdapter<VideoType> {
    public CommentAdapter(Context mContext) {
        super(mContext);

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(parent);
    }
}
