package com.wildwolf.myplayer.model.db;

import io.realm.Realm;

/**
 * Created by ${wild00wolf} on 2016/11/30.
 */
public class RealmHelper {

    public static final String DB_NAME = "my.realm";
    private Realm realm;
    private static RealmHelper instance;

    private RealmHelper(){}

    public static RealmHelper getInstance(){
        if (instance ==null){
            synchronized (RealmHelper.class){
                if (instance == null){
                    instance= new RealmHelper();
                }
            }
        }
        return instance;
    }
}
