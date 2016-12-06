package com.wildwolf.myplayer.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.NewBaseActivity;
import com.wildwolf.myplayer.widget.SwipeBackLayout;

import butterknife.ButterKnife;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public abstract class NewSwipeBackActivity extends NewBaseActivity {
    protected SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.base, null);
        layout.attachToActivity(this);
        unbinder = ButterKnife.bind(this);
        onBaseCreate();
    }

    protected abstract int setLayoutResourceID();

    protected abstract void onBaseCreate();
}
