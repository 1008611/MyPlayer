package com.wildwolf.myplayer.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.common.base.Preconditions;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.RootView;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.model.bean.VideoType;
import com.wildwolf.myplayer.presenter.contract.VideoListContract;
import com.wildwolf.myplayer.ui.activity.VideoListActivity;
import com.wildwolf.myplayer.utils.BeanUtil;
import com.wildwolf.myplayer.utils.EventUtil;
import com.wildwolf.myplayer.utils.JumpUtil;
import com.wildwolf.myplayer.utils.ScreenUtil;
import com.wildwolf.myplayer.widget.ColorTextView;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static org.slf4j.MDC.clear;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */

public class VideoListView extends RootView<VideoListContract.Presenter> implements VideoListContract.View,SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener{

    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.title_name)
    ColorTextView mTitleName;

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;

    VideoInfo videoInfo;
    int pageSize = 30;

    public VideoListView(Context context) {
        super(context);
    }

    public VideoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showTitle(String title) {
        mTitleName.setText(title);
    }

    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        mRecyclerView.showError();
    }

    @Override
    public void loadMoreFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        mAdapter.pauseMore();
    }

    @Override
    public void showContent(List<VideoType> list) {
        mAdapter.clear();
        if (list != null && list.size() < pageSize) {
            clearFooter();
        }
        mAdapter.addAll(list);
    }
    public void clearFooter() {
        mAdapter.setMore(new View(mContext), this);
        mAdapter.setError(new View(mContext));
        mAdapter.setNoMore(new View(mContext));
    }

    @Override
    public void showMoreContent(List<VideoType> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void setPresenter(VideoListContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_video_list_view,this);
    }

    @Override
    protected void initView() {
        mRecyclerView.setAdapterWithProgress(mAdapter = new VideoListAdapter(mContext));
        mRecyclerView.setErrorView(R.layout.view_error);
        mAdapter.setMore(R.layout.view_more, this);
        mAdapter.setNoMore(R.layout.view_nomore);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(mContext, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initEvent() {
        mTitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventUtil.isFastDoubleClick()) {
                    mRecyclerView.scrollToPosition(0);
                }
            }
        });

        mRecyclerView.setRefreshListener(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                videoInfo = BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position), videoInfo);
                JumpUtil.go2VideoInfoActivity(getContext(), videoInfo);
            }
        });
        mAdapter.setError(R.layout.view_error_footer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.resumeMore();
            }
        });
        mRecyclerView.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.showProgress();
                onRefresh();
            }
        });
    }

    @OnClick(R.id.rl_back)
    public void back() {
        if (mContext instanceof VideoListActivity) {
            ((VideoListActivity) mContext).finish();
        }
    }

    public void setTitleName(String title) {
        mTitleName.setText(title);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }
}