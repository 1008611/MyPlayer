package com.wildwolf.myplayer.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wildwolf.myplayer.model.bean.VideoType;
import com.wildwolf.myplayer.ui.adapter.viewholder.MineHistoryVideoListViewHolder;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class MineHistoryVideoListAdapter extends RecyclerArrayAdapter<VideoType> {
    public MineHistoryVideoListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MineHistoryVideoListViewHolder(parent);
    }
}
