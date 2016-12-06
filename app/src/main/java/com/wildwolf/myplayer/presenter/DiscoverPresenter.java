package com.wildwolf.myplayer.presenter;

import com.google.common.base.Preconditions;
import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.RxPresenter;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.model.net.RetrofitHelper;
import com.wildwolf.myplayer.model.net.VideoHttpResponse;
import com.wildwolf.myplayer.presenter.contract.DiscoverContract;
import com.wildwolf.myplayer.ui.view.DiscoverView;
import com.wildwolf.myplayer.utils.RxUtil;
import com.wildwolf.myplayer.utils.StringUtils;
import com.wildwolf.myplayer.utils.SystemUtils;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class DiscoverPresenter extends RxPresenter implements DiscoverContract.Presenter {

    DiscoverContract.View mView;
    final String catalogId = "402834815584e463015584e53843000b";

    int max = 90;
    int min = 1;

    public DiscoverPresenter(DiscoverContract.View three_view) {
        mView = Preconditions.checkNotNull(three_view);
        mView.setPresenter(this);
    }

    @Override
    public void getData() {
        getNextVideos();
    }

    private void getNextVideos() {
        Subscription subscription = RetrofitHelper.getVideoApi().getVideoList(catalogId, getNextPage() + "")
                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtil.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(VideoRes res) {
                        if (res != null) {
                            if (mView.isActive()) {
                                mView.showContent(res);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        if (mView.isActive())
                            mView.hidLoading();
                    }
                });
        addSubscribe(subscription);

    }

    private int getNextPage() {
        int page = mView.getLastPage();
        if (SystemUtils.isNetworkConnected()) {
            page = StringUtils.getRandomNumber(min, max);
            mView.setLastPage(page);
        }
        return page;
    }
}
