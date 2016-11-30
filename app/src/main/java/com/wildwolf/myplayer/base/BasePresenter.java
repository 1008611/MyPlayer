package com.wildwolf.myplayer.base;

/**
 * Created by ${wild00wolf} on 2016/11/30.
 */
public interface BasePresenter<T> {
    void attachView(T view);

    void detachView();
}
