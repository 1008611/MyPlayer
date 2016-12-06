package com.wildwolf.myplayer.model.db;


import com.wildwolf.myplayer.model.bean.Collection;
import com.wildwolf.myplayer.model.bean.Record;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ${wild00wolf} on 2016/11/30.
 */
public class RealmHelper {

    public static final String DB_NAME = "my.realm";
    private Realm realm;
    private static RealmHelper instance;

    private RealmHelper() {
    }

    public static RealmHelper getInstance() {
        if (instance == null) {
            synchronized (RealmHelper.class) {
                if (instance == null) {
                    instance = new RealmHelper();
                }
            }
        }
        return instance;
    }

    public boolean queryCollectionId(String dataId) {
        RealmResults<Collection> results = getRealm().where(Collection.class).findAll();
        for (Collection item : results) {
            if (item.getId().equals(dataId)) {
                return true;
            }
        }
        return false;
    }

    private Realm getRealm() {
        if (realm == null || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    public void deleteCollection(String dataId) {
        Collection data = getRealm().where(Collection.class).equalTo("id", dataId).findFirst();
        getRealm().beginTransaction();
        data.deleteFromRealm();
        getRealm().commitTransaction();
    }

    public void insertCollection(Collection bean) {
        getRealm().beginTransaction();
        getRealm().copyToRealm(bean);
        getRealm().commitTransaction();
    }

    public boolean queryRecordId(String id) {
        RealmResults<Record> results = getRealm().where(Record.class).findAll();
        for (Record item : results) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void deleteAllRecord() {
        getRealm().beginTransaction();
        getRealm().delete(Record.class);
        getRealm().commitTransaction();
    }

    public List<Record> getRecordList() {
        RealmResults<Record> records = getRealm().where(Record.class).findAllSorted("time", Sort.DESCENDING);
        return getRealm().copyFromRealm(records);
    }
}
