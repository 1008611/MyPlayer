package com.wildwolf.myplayer.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.base.Preconditions;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.RootView;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.model.bean.VideoRes;
import com.wildwolf.myplayer.presenter.contract.ClassificationContract;
import com.wildwolf.myplayer.ui.adapter.ClassificationAdapter;
import com.wildwolf.myplayer.utils.EventUtil;
import com.wildwolf.myplayer.utils.JumpUtil;
import com.wildwolf.myplayer.utils.ScreenUtil;
import com.wildwolf.myplayer.utils.StringUtils;
import com.wildwolf.myplayer.widget.ColorTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */

public class ClassificationView extends RootView<ClassificationContract.Presenter>  implements ClassificationContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    ClassificationAdapter adapter;

    public ClassificationView(Context context) {
        super(context);
    }

    public ClassificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(VideoRes videoRes) {
        if (videoRes != null) {
            adapter.clear();
            List<VideoInfo> videoInfos = new ArrayList<>();
            for (int i = 1; i < videoRes.list.size(); i++) {
                if (!TextUtils.isEmpty(videoRes.list.get(i).moreURL) && !TextUtils.isEmpty(videoRes.list.get(i).title)) {
                    VideoInfo videoInfo = videoRes.list.get(i).childList.get(0);
                    videoInfo.title = videoRes.list.get(i).title;
                    videoInfo.moreURL = videoRes.list.get(i).moreURL;
                    videoInfos.add(videoInfo);
                }
            }
            adapter.addAll(videoInfos);
        }
    }

    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        recyclerView.showError();
    }

    @Override
    public void setPresenter(ClassificationContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    protected void getLayout() {
      inflate(mContext, R.layout.fragment_class_view,this);
    }

    @Override
    protected void initView() {
        titleName.setText("专题");
        recyclerView.setAdapterWithProgress(adapter = new ClassificationAdapter(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setErrorView(R.layout.view_error);

        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initEvent() {
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                JumpUtil.go2VideoListActivity(mContext, StringUtils.getCatalogId(adapter.getItem(position).moreURL), adapter.getItem(position).title);
            }
        });
        recyclerView.getErrorView().setOnClickListener(new View.OnClickListener() {
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
}
