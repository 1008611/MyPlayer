package com.wildwolf.myplayer.presenter.contract;

import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
