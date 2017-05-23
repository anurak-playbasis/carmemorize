package com.carmemorize.app.sql;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.carmemorize.app.component.Constants;
import com.carmemorize.app.constant.CarConstants;
import com.carmemorize.app.model.CarModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by anura on 5/23/2017.
 */

public class HistoryTable {

    public static boolean saveCarHistory(Activity activity,String Action,String carId){

        boolean saveCarHistory = false;

        SQLiteDatabase db = activity.openOrCreateDatabase(CarConstants.CARMEMORIZE_DATABASE, Context.MODE_PRIVATE, null);

       // String carId = getIntent().getStringExtra(Constants.CAR_ID);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        String dateCurrent = sdf.format(c.getTime());

        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        Date resultdate = new Date(milliseconds);
        String timeCurrent = time.format(resultdate);

        // History add
        ContentValues cv = new ContentValues();
        cv.put("car_id",carId);
        cv.put("his_time",dateCurrent);
        cv.put("his_date",timeCurrent);
        cv.put("his_detail", Action);
        db.insert("history_car", null, cv);
        db.close();

        if (db != null){

            saveCarHistory = true;

        }

        return saveCarHistory;
    }
}
