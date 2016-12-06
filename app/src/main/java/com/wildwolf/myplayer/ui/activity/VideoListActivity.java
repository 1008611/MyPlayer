package com.wildwolf.myplayer.ui.activity;

import android.os.Bundle;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.presenter.VideoInfoPresenter;
import com.wildwolf.myplayer.presenter.VideoListPresenter;
import com.wildwolf.myplayer.ui.view.VideoListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class VideoListActivity extends NewSwipeBackActivity {

    String mTitle = "";
    String mCatalogId = "";

    @BindView(R.id.video_list_view)
    VideoListView videoListView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void onBaseCreate() {
        getIntentData();
        mPresenter = new VideoListPresenter(videoListView,mCatalogId);
    }

    private void getIntentData() {
        mCatalogId = getIntent().getStringExtra("catalogId");
        mTitle = getIntent().getStringExtra("title");
        videoListView.setTitleName(mTitle);
    }


}
