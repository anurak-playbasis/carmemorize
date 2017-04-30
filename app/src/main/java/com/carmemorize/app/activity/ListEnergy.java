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
import com.carmemorize.app.adapter.EnergyAdapter;
import com.carmemorize.app.model.EnergyModel;

import java.util.ArrayList;

public class ListEnergy extends AppCompatActivity {

    TextView mTitle;
    Toolbar toolbar;
    LinearLayout addEnergy;
    ImageView noEnergy;
    ListView energyListItem;
    TextView textDeflaut;
    String userCarId ;
    String carName ;
    ArrayList<EnergyModel> EnergyModelModels;
    CarAdapter carAdapter;
    EnergyAdapter energyAdapter;
    Boolean energyStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_energy);

        EnergyModelModels = new ArrayList<EnergyModel>();
        addEnergy = (LinearLayout)findViewById(R.id.im_add_enegy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userCarId = extras.getString("user_carId");
            carName = extras.getString("car_name");

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTitle.setText(carName);
        textDeflaut = (TextView)findViewById(R.id.text_deflaut);
        energyListItem = (ListView) findViewById(R.id.energy_ListItem);
        noEnergy = (ImageView)findViewById(R.id.no_energy);

        addEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEnergy.this, AddEnergy.class);
                intent.putExtra("user_carId",userCarId);
                startActivity(intent);
            }
        });
        Log.e("--------userCarId",""+userCarId);

        SQLiteDatabase db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select car_id,date_energy,station,type_gass,mileage,volume," +
                "type_volume,net_price,type_netPrice from energy_car where car_id = "+userCarId+"", null);
     //   "type_volume,net_price,type_netPrice from energy_car where car_id = " + userCarId , null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String carId = cursor.getString(0);
                String dateEnergy = cursor.getString(1);
                String station = cursor.getString(2);
                String typeGass = cursor.getString(3);
                String mileage = cursor.getString(4);
                String volume = cursor.getString(5);
                String typeVolume = cursor.getString(6);
                String netPrice = cursor.getString(7);
                String typeNetPrice = cursor.getString(8);

                Log.e("--carId",""+cursor.getString(0));
                Log.e("--dateEnergy",""+cursor.getString(1));
                Log.e("--station",""+cursor.getString(2));
                Log.e("--typeGass",""+cursor.getString(3));
                Log.e("--mileage",""+cursor.getString(4));
                Log.e("--volume",""+cursor.getString(5));
                Log.e("--typeVolume",""+cursor.getString(6));
                Log.e("--netPrice",""+cursor.getString(7));
                Log.e("--typeNetPrice",""+cursor.getString(8));

                EnergyModelModels.add(new EnergyModel(carId,dateEnergy,station, typeGass, mileage, volume,typeVolume,netPrice,typeNetPrice));

            } while (cursor.moveToNext());
            energyStatus = true;
        } else {
            Log.e("444444"," no data");
            energyStatus = false;

        }

        if (energyStatus == true ){
            Log.e("energyStatus"," true");
            energyAdapter = new EnergyAdapter(this, R.layout.energy_item, EnergyModelModels);
            energyListItem.setAdapter(energyAdapter);

        }else {

            energyListItem.setVisibility(View.GONE);
            noEnergy.setImageDrawable(getResources().getDrawable(R.drawable.gas_station));
            textDeflaut.setText("No list of energy");

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
