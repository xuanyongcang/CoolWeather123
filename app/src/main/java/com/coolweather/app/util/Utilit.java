package com.coolweather.app.util;

import android.text.TextUtils;

import com.coolweather.app.db.FarmDefineDB;
import com.coolweather.app.model.FARMLOG;

/**
 * Created by Administrator on 2015-07-23.
 */
public class Utilit {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvincesResponse(
            FarmDefineDB farmDefineDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    FARMLOG province = new FARMLOG();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    // 将解析出来的数据存储到Province表
                    farmDefineDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
}
