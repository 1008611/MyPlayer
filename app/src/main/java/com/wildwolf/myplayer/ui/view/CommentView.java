package com.wildwolf.myplayer.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.RootView;
import com.wildwolf.myplayer.model.bean.VideoType;
import com.wildwolf.myplayer.presenter.VideoInfoPresenter;
import com.wildwolf.myplayer.presenter.contract.CommentContract;
import com.wildwolf.myplayer.ui.adapter.CommentAdapter;
import com.wildwolf.myplayer.utils.EventUtil;
import com.wildwolf.myplayer.utils.ScreenUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ${wild00wolf} on 2016/12/2.
 */

public class CommentView extends RootView<CommentContract.Presenter> implements CommentContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {


    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    TextView tv_empty;

    CommentAdapter adapter;

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentView(Context context) {
        super(context);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        recyclerView.showError();
    }

    @Override
    public void showContent(List<VideoType> list) {
        adapter.clear();
        if (list != null && list.size() < 30) {
            clearFooter();
        }
        adapter.addAll(list);
    }

    public void clearFooter() {
        adapter.setMore(new View(mContext), this);
        adapter.setError(new View(mContext));
        adapter.setNoMore(new View(mContext));
    }


    @Override
    public void showMoreContent(List<VideoType> list) {
        adapter.addAll(list);
    }

    @Override
    public void setPresenter(CommentContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_comment_view, this);
    }

    @Override
    protected void initView() {
        recyclerView.setAdapterWithProgress(adapter = new CommentAdapter(mContext));
        recyclerView.setErrorView(R.layout.view_error);
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
        tv_empty = (TextView) recyclerView.getEmptyView();
        tv_empty.setText("暂无评论");
    }

    @Override
    protected void initEvent() {
        recyclerView.setRefreshListener(this);
        adapter.setError(R.layout.view_error_footer).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.resumeMore();
            }
        });
        recyclerView.getErrorView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.showProgress();
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Subscriber(tag = VideoInfoPresenter.Put_DataId)
    public void setData(String dataId) {
        mPresenter.setMediaId(dataId);
        mPresenter.onRefresh();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }
}
