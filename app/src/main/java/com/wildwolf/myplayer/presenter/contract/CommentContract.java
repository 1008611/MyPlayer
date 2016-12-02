package com.wildwolf.myplayer.presenter.contract;

import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;
import com.wildwolf.myplayer.model.bean.VideoType;

import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/12/2.
 */

public interface CommentContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void refreshFaild(String msg);

        void showContent(List<VideoType> list);

        void showMoreContent(List<VideoType> list);
    }

    interface Presenter extends BasePresenter {
        void onRefresh();

        void loadMore();

        void setMediaId(String id);
    }
}
