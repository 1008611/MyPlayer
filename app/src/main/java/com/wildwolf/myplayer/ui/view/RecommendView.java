package com.wildwolf.myplayer.ui.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.jude.rollviewpager.hintview.IconHintView;
import com.wildwolf.myplayer.MainActivity;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.RootView;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.presenter.contract.RecommendContract;
import com.wildwolf.myplayer.ui.adapter.BannerAdapter;
import com.wildwolf.myplayer.ui.adapter.RecommendAdapter;
import com.wildwolf.myplayer.utils.EventUtil;
import com.wildwolf.myplayer.utils.JumpUtil;
import com.wildwolf.myplayer.utils.ScreenUtil;
import com.wildwolf.myplayer.widget.ColorRelativeLayout;
import com.wildwolf.myplayer.widget.ColorTextView;
import com.wildwolf.myplayer.widget.RollPagerView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */

public class RecommendView extends RootView<RecommendContract.Presenter> implements RecommendContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Nullable
    @BindView(R.id.title)
    ColorRelativeLayout title;
    @BindView(R.id.title_name)
    ColorTextView titleName;

    RollPagerView banner;
    View headerView;
    RecommendAdapter adapter;
    TextView etSearchKey;
    RelativeLayout rlGoSearch;
    List<VideoInfo> recommend;

    public RecommendView(Context context) {
        super(context);
    }

    public RecommendView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_recommend_view, this);
    }

    @Override
    protected void initView() {
        title.setVisibility(View.GONE);
        titleName.setText("精选");
        headerView = LayoutInflater.from(mContext).inflate(R.layout.fragment_header, null);
        banner = ButterKnife.findById(headerView, R.id.banner);
        rlGoSearch = ButterKnife.findById(headerView, R.id.rlGoSearch);
        etSearchKey = ButterKnife.findById(headerView, R.id.etSearchKey);
        banner.setPlayDelay(2000);

        recyclerView.setAdapterWithProgress(adapter = new RecommendAdapter(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setErrorView(R.layout.view_error);

        SpaceDecoration decoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        decoration.setPaddingEdgeSide(true);
        decoration.setPaddingStart(true);
        decoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    protected void initEvent() {
        title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EventUtil.isFastDoubleClick())
                    recyclerView.scrollToPosition(0);
            }
        });
        recyclerView.setRefreshListener(this);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (getHeaderScroll() <= ScreenUtil.dip2px(mContext, 150)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            float precentage = getHeaderScroll() / ScreenUtil.dip2px(mContext, 150);
                            title.setAlpha(precentage);
                            if (precentage > 0) {
                                title.setVisibility(View.VISIBLE);
                            } else {
                                title.setVisibility(View.GONE);
                            }
                        }
                    }, 300);
                } else {
                    title.setAlpha(1.0f);
                    title.setVisibility(View.VISIBLE);
                }
            }
        });

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                JumpUtil.go2VideoInfoActivity(mContext, adapter.getItem(position));
            }
        });
        recyclerView.getEmptyView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.showProgress();
                onRefresh();
            }
        });
        rlGoSearch.setOnClickListener(this);
    }

    private int getHeaderScroll() {
        if (headerView == null) {
            return 0;
        }
        int distance = headerView.getTop();
        distance = Math.abs(distance);
        return distance;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(final VideoRes videoRes) {
        if (videoRes != null) {
            adapter.clear();
            List<VideoInfo> videoInfos;
            for (int i = 1; i < videoRes.list.size(); i++) {
                if (videoRes.list.get(i).title.equals("精彩推荐")) {
                    videoInfos = videoRes.list.get(i).childList;
                    Log.e("TAG-video", videoInfos.get(i).title);
                    adapter.addAll(videoInfos);
                    break;
                }
            }
            for (int i = 1; i < videoRes.list.size(); i++) {
                if (videoRes.list.get(i).title.equals("免费推荐")) {
                    recommend = videoRes.list.get(i).childList;
                    break;
                }
            }
            if (adapter.getHeaderCount() == 0) {
                adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                    @Override
                    public View onCreateView(ViewGroup parent) {
                        banner.setHintView(new IconHintView(getContext(), R.mipmap.ic_page_indicator_focused, R.mipmap.ic_page_indicator));
                        banner.setHintPadding(0, 0, 0, ScreenUtil.dip2px(getContext(), 8));
                        banner.setAdapter(new BannerAdapter(getContext(), videoRes.list.get(0).childList));
                        return headerView;
                    }

                    @Override
                    public void onBindView(View headerView) {

                    }
                });
            }
        }
    }

    @Override
    public void refreshFailed(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            showError(msg);
        }
        recyclerView.showError();
    }

    @Subscriber(tag = MainActivity.Banner_Stop)
    public void stopBanner(boolean stop) {
       if (stop){
           banner.pause();
       }else {
           banner.resume();
       }
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext,msg);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rlGoSearch:
//                Intent intent = new Intent(mContext, SearchActivity.class);
//                intent.putExtra("recommend", (Serializable) recommend);
//                mContext.startActivity(intent);
                break;

        }
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
}