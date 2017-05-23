package com.carmemorize.app.activity;

import android.content.Context;
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
import com.carmemorize.app.adapter.HistoryAdapter;
import com.carmemorize.app.component.Constants;
import com.carmemorize.app.model.EnergyModel;
import com.carmemorize.app.model.HistoryModel;

import java.util.ArrayList;


public class History extends AppCompatActivity {

    TextView mTitle;
    Toolbar toolbar;
    ImageView noEnergy;
    ListView historyListItem;
    TextView textDeflaut;
    String carId;
    ArrayList<HistoryModel> HisModelModels;
    CarAdapter carAdapter;
    HistoryAdapter hisAdapter;
    Boolean energyStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        HisModelModels = new ArrayList<HistoryModel>();

        carId = getIntent().getStringExtra(Constants.CAR_ID);
        historyListItem = (ListView) findViewById(R.id.history_ListItem);
        textDeflaut = (TextView)findViewById(R.id.text_deflaut);
        noEnergy = (ImageView)findViewById(R.id.no_energy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("History");

        SQLiteDatabase db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select car_id,his_time,his_date,his_detail from history_car where car_id = '"+ carId +"'", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String carId = cursor.getString(0);
                String hisDate = cursor.getString(1);
                String hisTime = cursor.getString(2);
                String hisDetail = cursor.getString(3);


                HisModelModels.add(new HistoryModel(carId,hisTime,hisDate,hisDetail));

            } while (cursor.moveToNext());
            energyStatus = true;
        } else {
            Log.e("444444"," no data");
            energyStatus = false;

        }

        if (energyStatus == true ){
            Log.e("energyStatus"," true");
            hisAdapter = new HistoryAdapter(this, R.layout.history_item, HisModelModels);
            historyListItem.setAdapter(hisAdapter);

        }else {

            historyListItem.setVisibility(View.GONE);
            noEnergy.setImageDrawable(getResources().getDrawable(R.drawable.gas_station));
            textDeflaut.setText("No list of history");

        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
