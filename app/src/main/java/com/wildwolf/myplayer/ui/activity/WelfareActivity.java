package com.wildwolf.myplayer.ui.activity;

import android.os.Bundle;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.NewSwipeBackActivity;
import com.wildwolf.myplayer.base.SwipeBackActivity;
import com.wildwolf.myplayer.presenter.VideoListPresenter;
import com.wildwolf.myplayer.presenter.WelfareListPresenter;
import com.wildwolf.myplayer.ui.view.VideoListView;
import com.wildwolf.myplayer.ui.view.WelfareListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${wild00wolf} on 2016/12/6.
 */
public class WelfareActivity extends SwipeBackActivity {

    @BindView(R.id.welfare_list_view)
    WelfareListView welfareListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_list);
        unbinder = ButterKnife.bind(this);
        mPresenter = new WelfareListPresenter(welfareListView);
    }

}