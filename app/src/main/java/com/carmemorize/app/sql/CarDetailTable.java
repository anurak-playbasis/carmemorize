package com.carmemorize.app.sql;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.carmemorize.app.model.CarModel;
import java.util.ArrayList;
import java.util.Collections;


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
}
