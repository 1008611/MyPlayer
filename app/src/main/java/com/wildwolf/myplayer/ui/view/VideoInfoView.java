package com.wildwolf.myplayer.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.RootView;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.presenter.contract.VideoInfoConteact;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */

public class VideoInfoView extends RootView<VideoInfoConteact.Presenter> implements VideoInfoConteact.View {

    public VideoInfoView(Context context) {
        super(context);
    }

    public VideoInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_video_info_view,this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void showContent(VideoRes videoRes) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void hideloading() {

    }

    @Override
    public void collected() {

    }

    @Override
    public void disCollect() {

    }

    @Override
    public void setPresenter(VideoInfoConteact.Presenter presenter) {

    }

    @Override
    public void showError(String msg) {

    }
}
