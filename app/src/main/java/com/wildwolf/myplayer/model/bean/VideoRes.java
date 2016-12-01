package com.wildwolf.myplayer.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public class VideoRes {
    public @SerializedName("list")
    List<VideoType> list;
}
