package com.wildwolf.myplayer.ui.fragment;

import android.view.LayoutInflater;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.BaseFragment;
import com.wildwolf.myplayer.presenter.ClassificationPresenter;
import com.wildwolf.myplayer.ui.view.ClassificationView;

import butterknife.BindView;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class ClassificationFragment extends BaseFragment {

    @BindView(R.id.two_view)
    ClassificationView twoView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_class;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new ClassificationPresenter(twoView);
    }

    @Override
    protected void lazyFetchData() {
        ((ClassificationPresenter) mPresenter).onRefresh();
    }

}
