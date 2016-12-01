package com.wildwolf.myplayer.base;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildwolf.myplayer.MainActivity;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.utils.ColorUiUtil;
import com.wildwolf.myplayer.utils.L;
import com.wildwolf.myplayer.utils.ScreenUtil;
import com.wildwolf.myplayer.widget.ColorRelativeLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;



/**
 * Created by ${wild00wolf} on 2016/12/1.
 */

public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment {

    private final String TAG = getClass().getSimpleName();
    protected T mPresenter;
    protected Context mContext;
    protected View rootView;
    protected Unbinder unbinder;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.d(this.getClass(), getName() + "------>onAttach");
        if (context != null) {
            this.mContext = context;
        } else {
            this.mContext = getActivity();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(this.getClass(), getName() + "------>onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d(this.getClass(), getName() + "------>onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(getLayout(), container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView(inflater);
        EventBus.getDefault().register(this);
        setTitleHeight(rootView);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.d(this.getClass(), getName() + "------>onActivityCreated");
        initEvent();
    }

    @Override
    public void onStart() {
        super.onStart();
        L.d(this.getClass(), getName() + "------>onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d(this.getClass(), getName() + "------>onResume");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.d(this.getClass(), getName() + "------>onViewCreated");
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onPause() {
        super.onPause();
        L.d(this.getClass(), getName() + "------>onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.d(this.getClass(), getName() + "------>onStop");
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        L.d(this.getClass(), getName() + "------>onDestroyView");
        hasFetchData = false;
        isViewPrepared = false;
        mPresenter = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(this.getClass(), getName() + "------>onDestroy");
        if (unbinder != null)
            unbinder.unbind();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        L.d(this.getClass(), getName() + "------>onDetach");
    }

    public String getName() {
        return BaseFragment.class.getName();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(TAG, getClass().getName() + "------>isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser){
            lazyFetchDataIfPrepared();
        }
    }

    private void lazyFetchDataIfPrepared() {
        if (getUserVisibleHint() && !hasFetchData&& isViewPrepared){
            hasFetchData=true;
            lazyFetchData();
        }
    }

    protected void lazyFetchData() {
        Log.v(TAG, getClass().getName() + "------>lazyFetchData");
    }
    protected abstract int getLayout();

    protected void initView(LayoutInflater inflater) {
    }

    protected void initEvent() {
    }

    @Subscriber(tag= MainActivity.Set_Theme_Color)
    public void setTheme(String tag){
        final View rootView = getActivity().getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);

        if (null!= localBitmap && rootView instanceof ViewGroup){
            final View tmpView = new View(getContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(),localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView,params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    ColorUiUtil.changeTheme(rootView,getContext().getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
        }
    }

    private void setTitleHeight(View view) {
        if (view!= null){
            ColorRelativeLayout title = (ColorRelativeLayout) view.findViewById(R.id.title);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                if (title != null) {
                    ViewGroup.LayoutParams lp = title.getLayoutParams();
                    lp.height = ScreenUtil.dip2px(getContext(), 48);
                    title.setLayoutParams(lp);
                    title.setPadding(0, 0, 0, 0);
                }
//                if (TAG.equals(MineFragment.class.getSimpleName())) {
//                    Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//                    ViewGroup.LayoutParams lp = toolbar.getLayoutParams();
//                    lp.height = ScreenUtil.dip2px(getContext(), 48);
//                    toolbar.setLayoutParams(lp);
//                }
            }
        }
    }

}
