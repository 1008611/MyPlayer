package com.wildwolf.myplayer.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeFrameLayout;
import com.google.common.base.Preconditions;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.app.Constants;
import com.wildwolf.myplayer.base.RootView;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.model.bean.VideoType;
import com.wildwolf.myplayer.presenter.contract.DiscoverContract;
import com.wildwolf.myplayer.ui.adapter.SwipeDeckAdapter;
import com.wildwolf.myplayer.utils.EventUtil;
import com.wildwolf.myplayer.utils.PreUtils;
import com.wildwolf.myplayer.utils.ScreenUtil;
import com.wildwolf.myplayer.widget.ColorTextView;
import com.wildwolf.myplayer.widget.LVGhost;
import com.wildwolf.myplayer.widget.SwipeDeck;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.PREPEND;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class DiscoverView extends RootView<DiscoverContract.Presenter> implements DiscoverContract.View {

    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.swipe_deck)
    SwipeDeck swipeDeck;
    @BindView(R.id.swipeLayout)
    SwipeFrameLayout swipeLayout;
    @BindView(R.id.loading)
    LVGhost loading;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;

    private SwipeDeckAdapter adapter;
    private List<VideoType> videos = new ArrayList<>();

    public DiscoverView(Context context) {
        super(context);
    }

    public DiscoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(VideoRes videoRes) {
        if (videoRes != null) {
            videos.getClass();
            videos.addAll(videoRes.list);

            swipeDeck.removeAllViews();
            swipeDeck.removeAllViews();
            adapter = new SwipeDeckAdapter(videos, getContext());
            swipeDeck.setAdapter(adapter);
            tvNomore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refreshFaild(String msg) {
        hidLoading();
        if (!TextUtils.isEmpty(msg)) {
            showError(msg);
        }
    }

    @Override
    public void hidLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public int getLastPage() {
        return PreUtils.getInt(getContext(), Constants.DISCOVERlASTpAGE,1);
    }

    @Override
    public void setLastPage(int page) {
        PreUtils.putInt(getContext(), Constants.DISCOVERlASTpAGE, page);
    }

    @Override
    public void setPresenter(DiscoverContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_discover_view, this);
    }

    @Override
    protected void initView() {
        titleName.setText("发现");
        ViewGroup.LayoutParams lp = swipeDeck.getLayoutParams();
        lp.height = ScreenUtil.getScreenHeight(getContext()) / 3 * 2;
        swipeDeck.setLayoutParams(lp);
        swipeDeck.setHardwareAccelerationEnabled(true);
    }

    @Override
    protected void initEvent() {
        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {

            }

            @Override
            public void cardSwipedRight(int position) {

            }

            @Override
            public void cardsDepleted() {
                swipeDeck.setVisibility(View.GONE);
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });
    }

    private void nextVideos() {
        swipeDeck.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        tvNomore.setVisibility(View.GONE);
        mPresenter.getData();
    }

    @OnClick({R.id.btn_next, R.id.tv_nomore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
            case R.id.tv_nomore:
                nextVideos();
                break;
        }
    }
}
