package com.wildwolf.myplayer.ui.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.wildwolf.myplayer.MainActivity;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.base.RootView;
import com.wildwolf.myplayer.presenter.contract.MainContract;
import com.wildwolf.myplayer.ui.adapter.ContentPageAdapter;
import com.wildwolf.myplayer.ui.fragment.MineFragment;
import com.wildwolf.myplayer.ui.fragment.RecommendFragment;
import com.wildwolf.myplayer.utils.EventUtil;
import com.wildwolf.myplayer.utils.StringUtils;
import com.wildwolf.myplayer.widget.ResideLayout;
import com.wildwolf.myplayer.widget.UnScrollViewPager;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wildwolf.myplayer.MainActivity.Banner_Stop;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */

public class MainView extends RootView<MainContract.Presenter> implements MainContract.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_mydown)
    TextView tvMydown;
    @BindView(R.id.tv_fuli)
    TextView tvFuli;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.theme)
    TextView theme;
    @BindView(R.id.tab_rg_menu)
    RadioGroup tabRgMenu;
    @BindView(R.id.vp_content)
    UnScrollViewPager vpContent;
    @BindView(R.id.resideLayout)
    ResideLayout mResideLayout;


    final int WAIT_TIME = 200;
    MainActivity activity;
    ContentPageAdapter pageAdapter;

    public MainView(Context context) {
        super(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_main_view, this);
    }

    @Override
    protected void initView() {
        activity = (MainActivity) mContext;
        List<Fragment> fragments = initFragments();
        vpContent.setScrollable(false);
        pageAdapter = new ContentPageAdapter(activity.getSupportFragmentManager(), fragments);
        vpContent.setAdapter(pageAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());

        StringUtils.setIconDrawable(mContext, tvCollect, MaterialDesignIconic.Icon.gmi_collection_bookmark, 16, 10);
        StringUtils.setIconDrawable(mContext, tvMydown, MaterialDesignIconic.Icon.gmi_download, 16, 10);
        StringUtils.setIconDrawable(mContext, tvFuli, MaterialDesignIconic.Icon.gmi_mood, 16, 10);
        StringUtils.setIconDrawable(mContext, tvShare, MaterialDesignIconic.Icon.gmi_share, 16, 10);
        StringUtils.setIconDrawable(mContext, tvFeedback, MaterialDesignIconic.Icon.gmi_android, 16, 10);
        StringUtils.setIconDrawable(mContext, tvSetting, MaterialDesignIconic.Icon.gmi_settings, 16, 10);
        StringUtils.setIconDrawable(mContext, about, MaterialDesignIconic.Icon.gmi_account, 16, 10);
        StringUtils.setIconDrawable(mContext, theme, MaterialDesignIconic.Icon.gmi_palette, 16, 10);

    }

    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        Fragment fragment = new RecommendFragment();
        Fragment mainFragment = new MineFragment();
        fragments.add(fragment);
        fragments.add(mainFragment);
        return fragments;
    }

    @Override
    protected void initEvent() {
        tabRgMenu.setOnCheckedChangeListener(this);
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) tabRgMenu.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mResideLayout.setPanelSlideListener(new ResideLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                postBannerState(true);
            }

            @Override
            public void onPanelOpened(View panel) {
                postBannerState(true);
            }

            @Override
            public void onPanelClosed(View panel) {
                postBannerState(false);
            }
        });
    }

//    @Subscriber(tag = MainFragment.SET_THEME)
//    public void setTheme(String content) {
//        new ColorChooserDialog.Builder(activity, R.string.theme)
//                .customColors(R.array.colors, null)
//                .doneButton(R.string.done)
//                .cancelButton(R.string.cancel)
//                .allowUserColorInput(false)
//                .allowUserColorInputAlpha(false)
//                .show();
//    }

    private void postBannerState(final boolean stop) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(stop, Banner_Stop);
            }
        }, WAIT_TIME);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.tab_rb_1:
                vpContent.setCurrentItem(0, false);
                break;
            case R.id.tab_rb_2:
                vpContent.setCurrentItem(1, false);
                break;
            case R.id.tab_rb_3:
                vpContent.setCurrentItem(2, false);
                break;
            case R.id.tab_rb_4:
                vpContent.setCurrentItem(3, false);
                break;
        }
    }

    @OnClick({R.id.tv_collect, R.id.tv_mydown, R.id.tv_fuli, R.id.tv_share, R.id.tv_feedback, R.id.tv_setting, R.id.about, R.id.theme})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.tv_mydown:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.tv_fuli:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.tv_share:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.tv_feedback:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.tv_setting:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.about:
                EventUtil.showToast(mContext, "敬请期待");
                break;
            case R.id.theme:
                EventUtil.showToast(mContext, "敬请期待");
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
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public ResideLayout getResideLayout() {
        return mResideLayout;
    }
}
