package com.carmemorize.app.sql;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.carmemorize.app.constant.CarConstants;
import com.carmemorize.app.model.CarModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class CarDetailTable {

    public static ArrayList<CarModel> getCarData(Activity activity){

        ArrayList<CarModel> carModelModels = new ArrayList<>();

        SQLiteDatabase db = activity.openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("select * from car_detail ", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String carId = cursor.getString(1);
                String dateBuy = cursor.getString(2);
                String name = cursor.getString(3);
                String brand = cursor.getString(4);
                String licenseCar = cursor.getString(5);
                String colorCar = cursor.getString(6);;
                String photoCar = cursor.getString(7);;

                carModelModels.add(new CarModel(carId,dateBuy,name, brand, licenseCar, colorCar, photoCar));

            } while (cursor.moveToNext());

        }

        Collections.reverse(carModelModels);

        return carModelModels;
    }

    public static boolean saveCarDetail(Activity activity, CarModel carModel){

        boolean savedCarDetail = false;

       SQLiteDatabase db = activity.openOrCreateDatabase(CarConstants.CARMEMORIZE_DATABASE, Context.MODE_PRIVATE, null);

        String carId = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        ContentValues contentValues = new ContentValues();
        contentValues.put(CarConstants.carTable.CAR_ID, carId);
        contentValues.put(CarConstants.carTable.DATE_BUY, carModel.getDateBuy() );
        contentValues.put(CarConstants.carTable.NAME_CAR, carModel.getName() );
        contentValues.put(CarConstants.carTable.BRAND, carModel.getBrand() );
        contentValues.put(CarConstants.carTable.LICENSE_CAR, carModel.getLicenseCar() );
        contentValues.put(CarConstants.carTable.COLOR_CAR, carModel.getColorCar() );
        contentValues.put(CarConstants.carTable.PHOTO_CAR, carModel.getPhotoCar() );
        db.insert(CarConstants.CAR_DETAIL_TABLE, null, contentValues);
        db.close();

        if (db != null){

            savedCarDetail = true;

        }

        return savedCarDetail;
    }


    public static boolean updateCarDetail(Activity activity, CarModel carModel){

        boolean updatedCarDetail = false;

        SQLiteDatabase db = activity.openOrCreateDatabase(CarConstants.CARMEMORIZE_DATABASE, Context.MODE_PRIVATE, null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(CarConstants.carTable.CAR_ID, carModel.getCarId());
        contentValues.put(CarConstants.carTable.DATE_BUY, carModel.getDateBuy() );
        contentValues.put(CarConstants.carTable.NAME_CAR, carModel.getName() );
        contentValues.put(CarConstants.carTable.BRAND, carModel.getBrand() );
        contentValues.put(CarConstants.carTable.LICENSE_CAR, carModel.getLicenseCar() );
        contentValues.put(CarConstants.carTable.COLOR_CAR, carModel.getColorCar() );
        contentValues.put(CarConstants.carTable.PHOTO_CAR, carModel.getPhotoCar() );

        db.update(CarConstants.CAR_DETAIL_TABLE, contentValues, CarConstants.carTable.CAR_ID + " = "+carModel.getCarId(), null);
        db.close();

        if (db != null){

            updatedCarDetail = true;

        }

        return updatedCarDetail;
    }
}
