package com.wildwolf.myplayer.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;
import com.wildwolf.myplayer.base.BasePresenter;
import com.wildwolf.myplayer.base.RxPresenter;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.model.net.RetrofitHelper;
import com.wildwolf.myplayer.model.net.VideoHttpResponse;
import com.wildwolf.myplayer.presenter.contract.RecommendContract;
import com.wildwolf.myplayer.ui.view.RecommendView;
import com.wildwolf.myplayer.utils.RxUtil;
import com.wildwolf.myplayer.utils.StringUtils;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public class RecommendPresenter extends RxPresenter implements RecommendContract.Presenter {

    RecommendContract.View mView;
    int page = 0;

    public RecommendPresenter(@NonNull RecommendContract.View oneView) {
        mView = Preconditions.checkNotNull(oneView);
        mView.setPresenter(this);
    }


    public void onRefresh() {
        page = 0;
        getPageHomeInfo();
    }

    public void getPageHomeInfo() {
        Subscription subscription = RetrofitHelper.getVideoApi().getHomePage()
                .compose(RxUtil.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtil.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(VideoRes videoRes) {

                        if (videoRes != null) {
                            if (mView.isActive()) {
                                mView.showContent(videoRes);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.refreshFailed(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                });
        addSubscribe(subscription);
    }
}
