package com.wildwolf.myplayer.presenter.contract;

import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;
import com.wildwolf.myplayer.model.bean.VideoRes;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public interface ClassificationContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void showContent(VideoRes videoRes);

        void refreshFaild(String msg);
    }

    interface Presenter extends BasePresenter {
        void onRefresh();
    }
}
