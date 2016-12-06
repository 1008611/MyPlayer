package com.wildwolf.myplayer.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.app.App;
import com.wildwolf.myplayer.utils.L;
import com.wildwolf.myplayer.utils.PreUtils;
import com.wildwolf.myplayer.utils.ScreenUtil;
import com.wildwolf.myplayer.widget.ColorRelativeLayout;
import com.wildwolf.myplayer.widget.Theme;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public abstract  class NewBaseActivity<T extends BasePresenter> extends SupportActivity {

    protected Unbinder unbinder;
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(this.getClass(), this.getClass().getName() + "------>onCreate");
        init();
    }

    protected void init() {
        setTranslucentStatus(true);
        onPreCreate();
        App.getInstance().registerActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d(this.getClass(), this.getClass().getName() + "------>onStart");
        setTitleHeight(getRootView(this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.d(this.getClass(), this.getClass().getName() + "------>onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d(this.getClass(), this.getClass().getName() + "------>onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d(this.getClass(), this.getClass().getName() + "------>onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d(this.getClass(), this.getClass().getName() + "------>onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d(this.getClass(), this.getClass().getName() + "------>onDestroy");
        App.getInstance().unregisterActivity(this);
        if (unbinder != null)
            unbinder.unbind();
        mPresenter = null;
    }

    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Brown:
                setTheme(R.style.BrownTheme);
                break;
            case Green:
                setTheme(R.style.GreenTheme);
                break;
            case Purple:
                setTheme(R.style.PurpleTheme);
                break;
            case Teal:
                setTheme(R.style.TealTheme);
                break;
            case Pink:
                setTheme(R.style.PinkTheme);
                break;
            case DeepPurple:
                setTheme(R.style.DeepPurpleTheme);
                break;
            case Orange:
                setTheme(R.style.OrangeTheme);
                break;
            case Indigo:
                setTheme(R.style.IndigoTheme);
                break;
            case LightGreen:
                setTheme(R.style.LightGreenTheme);
                break;
            case Lime:
                setTheme(R.style.LimeTheme);
                break;
            case DeepOrange:
                setTheme(R.style.DeepOrangeTheme);
                break;
            case Cyan:
                setTheme(R.style.CyanTheme);
                break;
            case BlueGrey:
                setTheme(R.style.BlueGreyTheme);
                break;
            case Black:
                setTheme(R.style.BlackTheme);
                break;
        }

    }

    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    private void setTitleHeight(View view) {
        if (view != null) {
            ColorRelativeLayout title = (ColorRelativeLayout) view.findViewById(R.id.title);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                if (title != null) {
                    ViewGroup.LayoutParams lp = title.getLayoutParams();
                    lp.height = ScreenUtil.dip2px(this, 48);
                    title.setLayoutParams(lp);
                    title.setPadding(0, 0, 0, 0);
                }
            }
        }
    }

    protected static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
}