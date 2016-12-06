package com.wildwolf.myplayer.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.wildwolf.myplayer.model.bean.GankItemBean;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.model.bean.VideoType;
import com.wildwolf.myplayer.presenter.WelcomePresenter;
import com.wildwolf.myplayer.presenter.WelfareListPresenter;
import com.wildwolf.myplayer.presenter.contract.WelfareListContract;
import com.wildwolf.myplayer.ui.activity.WelfareActivity;
import com.wildwolf.myplayer.ui.adapter.WelfareListAdapter;
import com.wildwolf.myplayer.utils.BeanUtil;
import com.wildwolf.myplayer.utils.EventUtil;
import com.wildwolf.myplayer.utils.JumpUtil;
import com.wildwolf.myplayer.utils.ScreenUtil;
import com.wildwolf.myplayer.widget.ColorTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${wild00wolf} on 2016/12/6.
 */
public class WelfareListView extends RootView< WelfareListContract.Presenter> implements  WelfareListContract.View,SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;

    WelfareListAdapter mAdapter;

    public WelfareListView(Context context) {
        super(context);
    }

    public WelfareListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
            inflate(mContext, R.layout.activity_welfare_list_view,this);
    }

    @Override
    protected void initView() {
        titleName.setText("福利");
        mRecyclerView.setAdapterWithProgress(mAdapter = new WelfareListAdapter(mContext));
        mRecyclerView.setErrorView(R.layout.view_error);
        mAdapter.setMore(R.layout.view_more, this);
        mAdapter.setNoMore(R.layout.view_nomore);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(mContext, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initEvent() {
        mRecyclerView.setRefreshListener(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                videoInfo = BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position), videoInfo);
//                JumpUtil.go2VideoInfoActivity(getContext(), videoInfo);
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


    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }

    @Override
    public boolean isActive() {
        return mActive;
    }


    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        mRecyclerView.showError();
    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        if (mContext instanceof WelfareActivity) {
            ((WelfareActivity) mContext).finish();
        }
    }

    @Override
    public void loadMoreFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        mAdapter.pauseMore();
    }

    @Override
    public void showContent(List<GankItemBean> list) {
        mAdapter.clear();
        if (list != null && list.size() < WelfareListPresenter.NUM_OF_PAGE) {
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
    public void showMoreContent(List<GankItemBean> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void setPresenter(WelfareListContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }
}
