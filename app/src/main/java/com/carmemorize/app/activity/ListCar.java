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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.carmemorize.app.R;
import com.carmemorize.app.adapter.CarAdapter;
import com.carmemorize.app.model.CarModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ListCar extends AppCompatActivity {

    TextView mTitle;
    Toolbar toolbar;
    LinearLayout addCar;
    ImageView noCar;
    ListView carListItem;
    TextView textDeflaut;
    ArrayList<CarModel> CarModelModels;
    CarAdapter carAdapter;
    Boolean carCount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);

        CarModelModels = new ArrayList<CarModel>();
        addCar = (LinearLayout)findViewById(R.id.im_add_car);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText("My Car");


        Bundle extras = getIntent().getExtras();
        String numberPage = null;
        if (extras != null) {
            numberPage = extras.getString("number");
        }

        if (numberPage.equals("2")){
            mTitle.setText("Energy");
            addCar.setVisibility(View.GONE);
        }
        else if (numberPage.equals("3")){
            mTitle.setText("Tax");
            addCar.setVisibility(View.GONE);
        }
        else if (numberPage.equals("4")){
            mTitle.setText("Service");
            addCar.setVisibility(View.GONE);
        }
        else if (numberPage.equals("5")){
            mTitle.setText("Service");
            addCar.setVisibility(View.GONE);
        }
        else if (numberPage.equals("6")){
            mTitle.setText("Summary");
            addCar.setVisibility(View.GONE);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //getSupportActionBar().setTitle(R.string.about);

        textDeflaut = (TextView)findViewById(R.id.text_deflaut);
        carListItem = (ListView) findViewById(R.id.carListItem);
        noCar = (ImageView)findViewById(R.id.no_car);

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCar.this,AddCar.class);
                startActivity(intent);

            }
        });
       // textDeflaut.setVisibility(View.GONE);
        SQLiteDatabase db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);
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

                CarModelModels.add(new CarModel(carId,dateBuy,name, brand, licenseCar, colorCar, photoCar));

            } while (cursor.moveToNext());
            carCount = true;
        } else {
            Log.e("444444"," no data");
            carCount = false;

        }

        if (carCount != true ){

            carListItem.setVisibility(View.GONE);
            noCar.setImageDrawable(getResources().getDrawable(R.drawable.no_car));
            textDeflaut.setText("No list name of car");

        }else {
            Collections.reverse(CarModelModels);
            carAdapter = new CarAdapter(this, R.layout.car_item, CarModelModels);
            carListItem.setAdapter(carAdapter);
        }

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


