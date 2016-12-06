package com.wildwolf.myplayer.presenter;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.google.common.base.Preconditions;
import com.wildwolf.myplayer.base.RxPresenter;
import com.wildwolf.myplayer.model.bean.GankItemBean;
import com.wildwolf.myplayer.model.net.GankHttpResponse;
import com.wildwolf.myplayer.model.net.RetrofitHelper;
import com.wildwolf.myplayer.presenter.contract.VideoListContract;
import com.wildwolf.myplayer.presenter.contract.WelfareListContract;
import com.wildwolf.myplayer.utils.RxUtil;
import com.wildwolf.myplayer.utils.StringUtils;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

import static android.R.id.list;
import static com.wildwolf.myplayer.utils.RxUtil.handleResult;
import static com.wildwolf.myplayer.utils.RxUtil.rxSchedulerHelper;

/**
 * Created by ${wild00wolf} on 2016/12/6.
 */
public class WelfareListPresenter extends RxPresenter implements WelfareListContract.Presenter {

    @NonNull
    final WelfareListContract.View mView;
    public static final int NUM_OF_PAGE = 20;
    private int currentPage = 1;

    public WelfareListPresenter(WelfareListContract.View welfareView) {
        mView = Preconditions.checkNotNull(welfareView);
        mView.setPresenter(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        getWelfareList();
    }

    private void getWelfareList() {
        SystemClock.sleep(4000);
        Subscription subscription = RetrofitHelper.getGankApi().getGirlList(NUM_OF_PAGE, currentPage)
                .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankItemBean>>handleGankResult())
                .subscribe(new Action1<List<GankItemBean>>() {
                    @Override
                    public void call(List<GankItemBean> gankItemBeen) {
                        setHeight(gankItemBeen);
                        mView.showContent(gankItemBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("数据加载失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void loadMore() {
        Subscription rxSubscription = RetrofitHelper.getGankApi().getGirlList(NUM_OF_PAGE, ++currentPage)
                .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankItemBean>>handleGankResult())
                .subscribe(new Action1<List<GankItemBean>>() {
                    @Override
                    public void call(List<GankItemBean> gankItemBeen) {
                        setHeight(gankItemBeen);
                        mView.showMoreContent(gankItemBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载更多数据失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscribe(rxSubscription);
    }

    private void setHeight(List<GankItemBean> list) {
        DisplayMetrics dm = mView.getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels / 2;//宽度为屏幕宽度一半
        for (GankItemBean gankItemBean : list) {
            gankItemBean.setHeight(width * StringUtils.getRandomNumber(3, 6) / 3);//随机的高度
        }
    }
}
