package com.wildwolf.myplayer.ui.activity;

import android.os.Bundle;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.SwipeBackActivity;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.presenter.VideoInfoPresenter;
import com.wildwolf.myplayer.ui.view.VideoInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public class VideoInfoActivity extends SwipeBackActivity {

    VideoInfo videoInfo;
    @BindView(R.id.video_info_view)
    VideoInfoView videoInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        unbinder = ButterKnife.bind(this);
        getIntentData();
        mPresenter = new VideoInfoPresenter(videoInfoView, videoInfo);
    }

    private void getIntentData() {
        videoInfo = (VideoInfo) getIntent().getSerializableExtra("videoInfo");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
