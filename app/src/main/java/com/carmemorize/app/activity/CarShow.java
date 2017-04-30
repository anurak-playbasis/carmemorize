package com.carmemorize.app.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carmemorize.app.R;


public class CarShow extends AppCompatActivity {

    TextView showCarId,showName,showBrand,showLicense,showColor,showDateOfDay;
    Button btnShowSaveCar;
    String user_carId;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_show);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        showCarId = (TextView)findViewById(R.id.show_car_id);
        showName = (TextView)findViewById(R.id.show_name_car);
        showBrand = (TextView)findViewById(R.id.show_brand_car);
        showLicense = (TextView)findViewById(R.id.show_license_car);
        showColor = (TextView)findViewById(R.id.show_sp_color);
        showDateOfDay = (TextView)findViewById(R.id.show_et_dateOfBuy);
        btnShowSaveCar = (Button)findViewById(R.id.btn_show_save_car);


            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                user_carId = extras.getString("user_carId");
            }

        Log.e("7777","carId"+user_carId);

        SQLiteDatabase db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select car_id,date_buy,name_car,brand,license_car,color_car from car_detail where car_id = "+user_carId+"", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String carId = cursor.getString(0);
                String dateBuy = cursor.getString(1);
                String name = cursor.getString(2);
                String brand = cursor.getString(3);
                String licenseCar = cursor.getString(4);
                String colorCar = cursor.getString(5);
                // String photo_car = cursor.getString(5);
                Log.e("222","carId"+cursor.getString(0));
                Log.e("2222","dateBuy"+cursor.getString(1));
                Log.e("22222","name"+cursor.getString(2));
                Log.e("222222","brand"+cursor.getString(3));
                Log.e("2222222","license_car"+cursor.getString(4));
                Log.e("22222222","color_car"+cursor.getString(5));

                showCarId.setText(carId);
                showName.setText(name);
                showBrand.setText(brand);
                showLicense.setText(licenseCar);
                showColor.setText(colorCar);
                showDateOfDay.setText(dateBuy);

            } while (cursor.moveToNext());

        } else {

        }

        btnShowSaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarShow.this,MainActivity.class);
                startActivity(intent);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
