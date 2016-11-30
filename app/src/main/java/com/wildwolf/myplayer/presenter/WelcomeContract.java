package com.wildwolf.myplayer.presenter;


import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;

import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/11/30.
 */

public interface WelcomeContract {
    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showContent(List<String> list);

        void jumpToMain();
    }

    interface Presenter extends BasePresenter {
        void getWelcomeData();
    }
}
