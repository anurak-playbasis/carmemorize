package com.carmemorize.app.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.carmemorize.app.R;


public class MainActivity extends AppCompatActivity {

    ImageView myCar;
    ImageView energy;
    ImageView tax;
    ImageView service;
    ImageView history;
    ImageView summary;

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Car Memmorize");

        crateDatabase();

        myCar = (ImageView)findViewById(R.id.btn_my_car);
        energy = (ImageView)findViewById(R.id.btn_energy);
        tax = (ImageView)findViewById(R.id.btn_tax);
        service = (ImageView)findViewById(R.id.btn_service);
        history = (ImageView)findViewById(R.id.btn_histoty);
        summary = (ImageView)findViewById(R.id.btn_summaty);


        myCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListCar.class);
                intent.putExtra("number","1");
                startActivity(intent);
            }
        });

        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListCar.class);
                intent.putExtra("number","2");
                startActivity(intent);
            }
        });

        tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListCar.class);
                intent.putExtra("number","3");
                startActivity(intent);
            }
        });


        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListCar.class);
                intent.putExtra("number","4");
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListCar.class);
                intent.putExtra("number","5");
                startActivity(intent);
            }
        });

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListCar.class);
                intent.putExtra("number","6");
                startActivity(intent);
            }
        });

    }

    private void crateDatabase(){

        try {

            db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);

            String car = "create table if not exists car_detail(id INTEGER PRIMARY KEY AUTOINCREMENT,car_id text,date_buy date," +
                         "name_car text,brand text,license_car text,color_car text,photo_car text)";
            db.execSQL(car);

            String energy = "create table if not exists energy_car(id INTEGER PRIMARY KEY AUTOINCREMENT,car_id text,date_energy date," +
                    "station text,type_gass text,mileage text,volume text,type_volume text,net_price text,type_netPrice text)";
            db.execSQL(energy);

            String tax = "create table if not exists tax_car(id INTEGER PRIMARY KEY AUTOINCREMENT,car_id text,expiration_date date," +
                    "price_tax text,alert_tax text,photo_tax text)";
            db.execSQL(tax);

            String history = "create table if not exists history_car(id INTEGER PRIMARY KEY AUTOINCREMENT,car_id text,his_time text,his_date text," +
                    "his_detail text)";
            db.execSQL(history);

            db.close();
        }catch(Exception e)
        {
        }finally {

        }
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
