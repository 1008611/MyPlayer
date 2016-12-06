package com.wildwolf.myplayer.presenter.contract;

import android.content.Context;

import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.BaseView;
import com.wildwolf.myplayer.model.bean.GankItemBean;
import com.wildwolf.myplayer.model.bean.VideoType;

import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/12/6.
 */

public interface WelfareListContract {
    interface View extends BaseView<WelfareListContract.Presenter> {

        boolean isActive();

        void refreshFaild(String msg);

        void loadMoreFaild(String msg);

        Context getContext();

        void showContent(List<GankItemBean> list);

        void showMoreContent(List<GankItemBean> list);
    }

    interface Presenter extends BasePresenter {
        void onRefresh();

        void loadMore();
    }
}

