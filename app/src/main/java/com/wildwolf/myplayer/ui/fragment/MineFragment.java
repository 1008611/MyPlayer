package com.wildwolf.myplayer.ui.fragment;

import android.view.LayoutInflater;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.BaseFragment;
import com.wildwolf.myplayer.presenter.MinePresenter;
import com.wildwolf.myplayer.ui.view.MineView;

import butterknife.BindView;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public class MineFragment extends BaseFragment {
    public static final String SET_THEME = "SET_THEME";

    @BindView(R.id.mine_view)
    MineView mineView;
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter = new MinePresenter(mineView);
    }
}
