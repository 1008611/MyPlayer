package com.wildwolf.myplayer.presenter;

import com.google.common.base.Preconditions;
import com.wildwolf.myplayer.base.RxPresenter;
import com.wildwolf.myplayer.model.bean.Record;
import com.wildwolf.myplayer.model.bean.VideoType;
import com.wildwolf.myplayer.model.db.RealmHelper;
import com.wildwolf.myplayer.presenter.contract.MineContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${wild00wolf} on 2016/12/5.
 */
public class MinePresenter extends RxPresenter implements MineContract.Presenter  {

    MineContract.View mView;
    public static final int maxSize = 30;

    public MinePresenter(MineContract.View oneView) {
        mView = Preconditions.checkNotNull(oneView);
        mView.setPresenter(this);
        getHistoryData();
    }


    @Override
    public void getHistoryData() {
        List<Record> records = RealmHelper.getInstance().getRecordList();
        List<VideoType> list = new ArrayList<>();
        VideoType videoType;
        int maxlinth = records.size()<=3?records.size():3;
        for (int i=0;i<maxlinth;i++){
            Record record = records.get(i);
            videoType = new VideoType();
            videoType.title = record.title;
            videoType.pic = record.pic;
            videoType.dataId = record.getId();
            list.add(videoType);
        }
        mView.showContent(list);
    }


    @Override
    public void delAllHistory() {
        RealmHelper.getInstance().deleteAllRecord();
    }
}
