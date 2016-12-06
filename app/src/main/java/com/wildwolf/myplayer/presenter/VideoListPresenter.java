package com.wildwolf.myplayer.presenter;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.RxPresenter;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.model.net.RetrofitHelper;
import com.wildwolf.myplayer.model.net.VideoHttpResponse;
import com.wildwolf.myplayer.presenter.contract.VideoListContract;
import com.wildwolf.myplayer.ui.view.VideoListView;
import com.wildwolf.myplayer.utils.RxUtil;
import com.wildwolf.myplayer.utils.StringUtils;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class VideoListPresenter extends RxPresenter implements VideoListContract.Presenter {

    @NonNull
    final VideoListContract.View mView;
    int page = 1;
    String catalogId = "";

    public VideoListPresenter(VideoListContract.View videoListView, String mCatalogId) {
        mView = Preconditions.checkNotNull(videoListView);
        mView.setPresenter(this);
        this.catalogId = mCatalogId;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        page = 1;
        getVideoList(catalogId);
    }

    private void getVideoList(String catalogId) {
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getVideoList(catalogId, page + "")
                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtil.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(VideoRes res) {
                        if (res != null) {
                            if (mView.isActive()) {
                                if (page == 1) {
                                    mView.showContent(res.list);
                                } else {
                                    mView.showMoreContent(res.list);
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (page > 1) {
                            page--;
                        }
                        mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void loadMore() {
        page++;
        getVideoList(catalogId);
    }
}
