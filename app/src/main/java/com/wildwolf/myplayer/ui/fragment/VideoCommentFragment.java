package com.wildwolf.myplayer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.BaseFragment;
import com.wildwolf.myplayer.presenter.CommentPresenter;
import com.wildwolf.myplayer.ui.view.CommentView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${wild00wolf} on 2016/12/2.
 */
public class VideoCommentFragment extends BaseFragment {


    @BindView(R.id.comment_view)
    CommentView commentView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mPresenter = new CommentPresenter(commentView);
    }

    @Override
    protected void lazyFetchData() {
        ((CommentPresenter)mPresenter).onRefresh();
    }
}
