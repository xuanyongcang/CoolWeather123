package com.coolweather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.Province;
import com.coolweather.app.model.FARMLOG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-07-23.
 */
public class FarmDefineDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "FARMLOG";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static FarmDefineDB FarmDefineDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private FarmDefineDB(Context context) {
        FarmDefine dbHelper = new FarmDefine(context,
                DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取farmdefineDB的实例。
     */
    public synchronized static FarmDefineDB getInstance(Context context) {
        if (FarmDefineDB == null) {
            FarmDefineDB = new FarmDefineDB(context);
        }
        return FarmDefineDB;
    }

    /**
     * 将farmdefineDB实例存储到数据库。
     */
    public void saveProvince(FARMLOG province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * 从数据库读取全国所有的省份信息。
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor
                        .getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor
                        .getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }


}
