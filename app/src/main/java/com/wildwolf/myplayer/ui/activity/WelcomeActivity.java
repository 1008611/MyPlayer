package com.wildwolf.myplayer.ui.activity;

import android.os.Bundle;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.BaseActivity;
import com.wildwolf.myplayer.presenter.WelcomePresenter;
import com.wildwolf.myplayer.ui.view.WelcomeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${wild00wolf} on 2016/11/30.
 */

public class WelcomeActivity  extends BaseActivity {

    @BindView(R.id.welcome_view)
    WelcomeView welcomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        unbinder = ButterKnife.bind(this);
        mPresenter = new WelcomePresenter(welcomeView);
    }
}