package com.wildwolf.myplayer.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.BaseFragment;
import com.wildwolf.myplayer.presenter.ClassificationPresenter;
import com.wildwolf.myplayer.presenter.DiscoverPresenter;
import com.wildwolf.myplayer.ui.view.ClassificationView;
import com.wildwolf.myplayer.ui.view.DiscoverView;

import butterknife.BindView;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.three_view)
    DiscoverView three_View;

    @Override
    protected int getLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new DiscoverPresenter(three_View);
    }

    @Override
    protected void lazyFetchData() {
        ((DiscoverPresenter) mPresenter).getData();
    }

}
