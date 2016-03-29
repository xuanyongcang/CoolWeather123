package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015-07-23.
 */
public  class FarmDefine extends SQLiteOpenHelper {
    public static final String CREATE_FARMLOG = "create table FARMLOG ("
            + "text integer primary key autoincrement, "
            + "province_name text, "
            + "province_na text, "
            + "province_nam text, "
            + "province_nam1 text, "
            + "province_code text)";

    public FarmDefine(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FARMLOG);  // 创建Farmlog表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists FARMLOG");
        onCreate(db);
    }
}