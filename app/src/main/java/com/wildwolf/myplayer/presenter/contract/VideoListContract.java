package com.wildwolf.myplayer.presenter.contract;

import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;
import com.wildwolf.myplayer.model.bean.VideoType;

import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public interface VideoListContract {
    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showTitle(String title);

        void refreshFaild(String msg);

        void loadMoreFaild(String msg);

        void showContent(List<VideoType> list);

        void showMoreContent(List<VideoType> list);
    }

    interface Presenter extends BasePresenter {

        void onRefresh();

        void loadMore();

    }
}
