package com.wildwolf.myplayer.presenter.contract;

import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;
import com.wildwolf.myplayer.model.bean.VideoRes;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */

public interface RecommendContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void showContent(VideoRes videoRes);

        void refreshFailed(String msg);

        void stopBanner(boolean stop);
    }

    interface Presenter extends BasePresenter {
        void onRefresh();
    }
}
