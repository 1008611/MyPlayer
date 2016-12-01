package com.wildwolf.myplayer.presenter.contract;

import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;
import com.wildwolf.myplayer.model.bean.VideoRes;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public interface VideoInfoConteact {
    interface View extends BaseView<Presenter> {
        void showContent(VideoRes videoRes);

        boolean isActive();

        void hideloading();

        void collected();

        void disCollect();
    }

    interface Presenter extends BasePresenter {
        void getDetailData(String dataId);

        void collect();

        void insertRecord();
    }
}
