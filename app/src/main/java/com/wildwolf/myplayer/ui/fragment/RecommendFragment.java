package com.wildwolf.myplayer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.BaseFragment;
import com.wildwolf.myplayer.presenter.RecommendPresenter;
import com.wildwolf.myplayer.ui.view.RecommendView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public class RecommendFragment extends BaseFragment {

    @BindView(R.id.one_view)
    RecommendView oneView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new RecommendPresenter(oneView);
    }

    @Override
    protected void lazyFetchData() {
        ((RecommendPresenter) mPresenter).onRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        oneView.stopBanner(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        oneView.stopBanner(true);
    }
}
