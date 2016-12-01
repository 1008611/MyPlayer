package com.wildwolf.myplayer.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/12/1.
 */
public class VideoType {
    public String title;
    public String moreURL;
    public String pic;
    public String dataId;
    public String airTime;
    public String score;
    public String description;
    public String msg;
    public String phoneNumber;
    public String userPic;
    public String time;
    public String likeNum;
    public
    @SerializedName("childList")
    List<VideoInfo> childList;
}
