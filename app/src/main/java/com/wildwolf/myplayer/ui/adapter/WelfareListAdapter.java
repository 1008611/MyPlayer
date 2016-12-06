package com.wildwolf.myplayer.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wildwolf.myplayer.model.bean.GankItemBean;
import com.wildwolf.myplayer.ui.adapter.viewholder.WelfareListViewHolder;

/**
 * Created by ${wild00wolf} on 2016/12/6.
 */
public class WelfareListAdapter extends RecyclerArrayAdapter<GankItemBean>{

    public WelfareListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WelfareListViewHolder(parent);
    }
}
