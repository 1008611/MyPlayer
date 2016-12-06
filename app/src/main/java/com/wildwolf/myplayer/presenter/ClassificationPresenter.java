package com.wildwolf.myplayer.presenter;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.wildwolf.myplayer.base.RxPresenter;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.model.net.RetrofitHelper;
import com.wildwolf.myplayer.model.net.VideoHttpResponse;
import com.wildwolf.myplayer.presenter.contract.ClassificationContract;
import com.wildwolf.myplayer.utils.RxUtil;
import com.wildwolf.myplayer.utils.StringUtils;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class ClassificationPresenter extends RxPresenter implements ClassificationContract.Presenter {

    ClassificationContract.View mView;
    int page = 0;

    public ClassificationPresenter(@NonNull ClassificationContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void onRefresh() {
        page = 0;
        getPageHomeInfo();
    }

    private void getPageHomeInfo() {
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
                        mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                });
        addSubscribe(subscription);
    }


}
