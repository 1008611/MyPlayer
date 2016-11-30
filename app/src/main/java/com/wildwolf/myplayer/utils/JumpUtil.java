package com.wildwolf.myplayer.utils;

import android.content.Context;
import android.content.Intent;

import com.wildwolf.myplayer.MainActivity;
import com.wildwolf.myplayer.ui.activity.WelcomeActivity;


/**
 * Created by ${wild00wolf} on 2016/11/30.
 */
public class JumpUtil {
    public static void go2MainActivity(Context mContext) {
        jump(mContext, MainActivity.class);
        ((WelcomeActivity) mContext).finish();
    }

    private static void jump(Context mContext, Class<?> clazz) {
        Intent intent = new Intent(mContext,clazz);
        mContext.startActivity(intent);
    }
}
