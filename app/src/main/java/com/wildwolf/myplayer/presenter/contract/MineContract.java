package com.wildwolf.myplayer.presenter.contract;

import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;
import com.wildwolf.myplayer.model.bean.VideoType;

import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public interface MineContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void showContent(List<VideoType> list);
    }

    interface Presenter extends BasePresenter {
        void getHistoryData();

        void delAllHistory();
    }
}
