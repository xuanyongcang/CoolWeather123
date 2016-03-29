package com.coolweather.app.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.app.R;
import com.coolweather.app.db.FarmDefine;
import com.coolweather.app.model.FARMLOG;
import com.coolweather.app.service.AutoUpdateService;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import java.net.ContentHandler;
import java.sql.SQLDataException;

/**
 * Created by Administrator on 2015-07-24.
 */
public class FarmLogActivity extends Activity {
    private LinearLayout weatherInfoLayout;
    /**
     * 用于显示城市名
     */
    private TextView cityNameText;
    /**
     * 用于显示发布时间
     */
    private TextView publishText;
    /**
     * 用于显示天气描述信息
     */
    private TextView weatherDespText;
    /**
     * 用于显示气温1
     */
    private TextView temp1Text;
    /**
     * 用于显示气温2
     */
    private TextView temp2Text;
    /**
     * 用于显示当前日期
     */
    private TextView currentDateText;
    /**
     * 切换城市按钮
     */
    private Button switchCity;
    /**
     * 更新天气按钮
     */
    private Button refreshWeather;
    private Button button10;

    private Button search12;
    public TextView xuan;
    private FarmDefine dbHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.farmlog_layout);
        dbHelp = new FarmDefine(this, "farm.db", null, 2);
        Intent intent = getIntent();
        String data1 = intent.getStringExtra("extra_data1");
        Log.d("SecondeActivity", data1);
        // 初始化各控件
//        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout1);
        cityNameText = (TextView) findViewById(R.id.city_name1);
        publishText = (TextView) findViewById(R.id.publish_text1);
        weatherDespText = (TextView) findViewById(R.id.textView6);
        temp1Text = (TextView) findViewById(R.id.textView2);
        temp2Text = (TextView) findViewById(R.id.textView4);
        currentDateText = (TextView) findViewById(R.id.current_date12);
        switchCity = (Button) findViewById(R.id.switch_city1);
        button10=(Button)findViewById(R.id.button10);
        xuan = (TextView) findViewById(R.id.define_1);
        xuan.setText(data1);
        String countyCode = getIntent().getStringExtra("county_code");
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put("autoincrement", "小麦是我国重要的粮食产物");
        values.put("province_na","小麦");
        values.put("province_name","危害小麦的病害有：小麦条锈病、叶锈病、秆锈病、腥黑穗病、散黑穗病、黄矮病、红矮病、全蚀病、赤霉病、叶斑病等。虫害有小麦蚜虫、麦种蝇、吸浆虫、红蜘蛛、叶蝉、蛴螬、金针虫、蝼蛄、麦叶蜂、麦秆蝇等。");
        values.put("province_nam1","山东省德州市2015年本地产师栾02-1中等优质小麦收购价格2720元/吨，2015年二级白小麦收购价格2340元/吨，特一粉出厂价3250元 /吨。临沂市场2015年产2级白小麦收购价格2392元/吨，3级白小麦收购价格2352元/吨，特制一级小麦粉出厂价格3130元/吨。济南2015 年产3等普通白小麦进厂价格2420元/吨。济宁2014年产济南 17平均出库价格2860元/吨");
        values.put("province_code", "小麦是小麦系植物的统称，是一种在世界各地广泛种植的禾本科植物，起源于中东新月沃土(Levant)地区，是世界上最早栽培的农作物之一，小麦的颖果是人类的主食之一，磨成面粉后可制作面包、馒头、饼干、面条等食物；发酵后可制成啤酒、酒精、伏特加，或生质燃料。小麦富含淀粉、蛋白质、脂肪、矿物质、钙、铁、硫胺素、核黄素、烟酸、维生素A及维生素C等。");
        values.put("province_nam", "小麦");
        //db.insert("Farmlog", null, values);
          db.insert("FARMLOG", null, values);
//        String sql = "select * from FARMLOG where province_na ='&data1&'";
//        Cursor cursor = db.rawQuery(sql,new String[0]);
        //String sql = "select * from FARMLOG where province_na = '" + data + "'" ;
        Cursor cursor = db.rawQuery("select * from FARMLOG where province_na =?", new String[]{data1});
        if (cursor.moveToFirst()) {
                do {
                    currentDateText.setText(cursor.getString(cursor.getColumnIndex("province_nam")));
                    temp1Text.setText(cursor.getString(cursor.getColumnIndex("province_name")));
                    weatherDespText.setText(cursor.getString(cursor.getColumnIndex("province_nam1")));
                    temp2Text.setText(cursor.getString(cursor.getColumnIndex("province_code")));
                } while (cursor.moveToNext());
        }
        else {

            Toast.makeText(this, "请返回从新输入需要的农作物信息,数据库中尚未拥有你所输入的农作物信息", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(FarmLogActivity.this, WeatherActivity.class);
            startActivity(intent2);
        }
//        cursor.close();
//        currentDateText.setText("小白鼠");
//        currentDateText.setText(cursor.getString(cursor.getColumnIndex("province_nam")));
//        temp1Text.setText(cursor.getString(cursor.getColumnIndex("province_name")));
//        weatherDespText.setText(cursor.getString(cursor.getColumnIndex("province_nam1")));
//        temp2Text.setText(cursor.getString(cursor.getColumnIndex("province_code")));
//        if(TextUtils.equals(data,"小麦"))
//        {
//            currentDateText.setText("小麦是我国重要的粮食产物");
//            temp1Text.setText("危害小麦的病害有：小麦条锈病、叶锈病、秆锈病、腥黑穗病、散黑穗病、黄矮病、红矮病、全蚀病、赤霉病、叶斑病等。虫害有小麦蚜虫、麦种蝇、吸浆虫、红蜘蛛、叶蝉、蛴螬、金针虫、蝼蛄、麦叶蜂、麦秆蝇等。");
//            weatherDespText.setText("山东省德州市2015年本地产师栾02-1中等优质小麦收购价格2720元/吨，2015年二级白小麦收购价格2340元/吨，特一粉出厂价3250元 /吨。临沂市场2015年产2级白小麦收购价格2392元/吨，3级白小麦收购价格2352元/吨，特制一级小麦粉出厂价格3130元/吨。济南2015 年产3等普通白小麦进厂价格2420元/吨。济宁2014年产济南 17平均出库价格2860元/吨");
//            temp2Text.setText("小麦是小麦系植物的统称，是一种在世界各地广泛种植的禾本科植物，起源于中东新月沃土(Levant)地区，是世界上最早栽培的农作物之一，小麦的颖果是人类的主食之一，磨成面粉后可制作面包、馒头、饼干、面条等食物；发酵后可制成啤酒、酒精、伏特加，或生质燃料。小麦富含淀粉、蛋白质、脂肪、矿物质、钙、铁、硫胺素、核黄素、烟酸、维生素A及维生素C等。");
//        }

            showFarm();

        switchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(FarmLogActivity.this, WeatherActivity.class);
                startActivity(intent2);
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(FarmLogActivity.this,MainActivity.class);
                startActivity(intent4);
            }
        });
    }

    private void showFarm() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText( prefs.getString("city_name", ""));
//        temp1Text.setText(prefs.getString("temp1", ""));
//        temp2Text.setText(prefs.getString("temp2", ""));
//        weatherDespText.setText(prefs.getString("weather_desp", ""));
        publishText.setText("每天" + prefs.getString("publish_time", "") + "更新数据库");
//        currentDateText.setText("小麦是我国重要的粮食产物");
//        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

}
