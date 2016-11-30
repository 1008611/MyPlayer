package com.wildwolf.myplayer.base;

/**
 * Created by ${wild00wolf} on 2016/11/30.
 */
public interface BaseView<T> {
    void setPresenter(T presenter);

    void showError(String msg);
}
