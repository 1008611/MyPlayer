package com.wildwolf.myplayer.utils;

import android.content.Context;
import android.content.Intent;

import com.wildwolf.myplayer.MainActivity;
import com.wildwolf.myplayer.model.bean.VideoInfo;
import com.wildwolf.myplayer.ui.activity.VideoInfoActivity;
import com.wildwolf.myplayer.ui.activity.VideoListActivity;
import com.wildwolf.myplayer.ui.activity.WelcomeActivity;


/**
 * Created by ${wild00wolf} on 2016/11/30.
 */
public class JumpUtil {
    public static void go2MainActivity(Context mContext) {
        jump(mContext, MainActivity.class);
        ((WelcomeActivity) mContext).finish();
    }

    public static void go2VideoListActivity(Context context, String catalogId, String title) {
        Intent intent = new Intent(context, VideoListActivity.class);
        intent.putExtra("catalogId", catalogId);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    private static void jump(Context mContext, Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
    }

    public static void go2VideoInfoActivity(Context mContext, VideoInfo info) {
        Intent intent = new Intent(mContext, VideoInfoActivity.class);
        intent.putExtra("videoInfo", info);
        mContext.startActivity(intent);
    }
}
