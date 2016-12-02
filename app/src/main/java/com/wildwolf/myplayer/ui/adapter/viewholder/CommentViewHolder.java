package com.wildwolf.myplayer.ui.adapter.viewholder;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wildwolf.myplayer.R;
import com.wildwolf.myplayer.component.ImageLoader;
import com.wildwolf.myplayer.model.bean.VideoType;

/**
 * Created by ${wild00wolf} on 2016/12/2.
 */
public class CommentViewHolder extends BaseViewHolder<VideoType> {

    RoundedImageView avatar;
    TextView tv_nick;
    TextView tv_time;
    TextView tv_like;
    TextView tv_comment;

    public CommentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_comment);

        avatar = $(R.id.avatar);
        tv_nick = $(R.id.tv_nick);
        tv_time = $(R.id.tv_time);
        tv_like = $(R.id.tv_like);
        tv_comment = $(R.id.tv_comment);
    }

    @Override
    public void setData(VideoType data) {
        tv_nick.setText(data.phoneNumber);
        tv_time.setText(data.time);
        tv_like.setText(data.likeNum);
        tv_comment.setText(data.msg);
        if (!TextUtils.isEmpty(data.userPic))
            ImageLoader.load(getContext(), data.userPic, avatar);
    }
}
