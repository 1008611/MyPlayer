package com.wildwolf.myplayer.presenter;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.wildwolf.myplayer.base.RxPresenter;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.model.net.RetrofitHelper;
import com.wildwolf.myplayer.model.net.VideoHttpResponse;
import com.wildwolf.myplayer.presenter.contract.CommentContract;
import com.wildwolf.myplayer.ui.view.CommentView;
import com.wildwolf.myplayer.utils.RxUtil;
import com.wildwolf.myplayer.utils.StringUtils;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ${wild00wolf} on 2016/12/2.
 */
public class CommentPresenter extends RxPresenter implements CommentContract.Presenter {

    @NonNull
    final CommentContract.View mView;

    int page = 1;
    String mediaId = "";

    public CommentPresenter(@NonNull CommentContract.View addTaskView) {
        mView = Preconditions.checkNotNull(addTaskView);
        mView.setPresenter(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (mediaId != null && !mediaId.equals("")) {
            getComment(mediaId);
        }
    }

    private void getComment(String mediaId) {
        Subscription rxSubscription = RetrofitHelper.getVideoApi().getCommentList(mediaId, page + "")
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
        if (mediaId != null && mediaId.equals("")) {
            getComment(mediaId);
        }
    }

    @Override
    public void setMediaId(String id) {
        this.mediaId = id;
    }
}
