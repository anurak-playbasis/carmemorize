package com.anint.anurak.carmemorize.activity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.anint.anurak.carmemorize.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEnergy extends AppCompatActivity {

    EditText dateEnnergyShow,station,mile,volume,netPrice;
    private Button saveShowEnergy;
    private Spinner spGass,spLiter,spMoney;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    SQLiteDatabase db;
    public static final String MyPREFERENCES = "shareCar";
    SharedPreferences sharedpreference;
    SharedPreferences.Editor editor;
    String user_carId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_show);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_carId = extras.getString("user_carId");
        }

        Log.e("27777","carId"+user_carId);

        initInstance();
        addDateOfBuy();
        onClickButton();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initInstance(){

        dateEnnergyShow = (EditText)findViewById(R.id.et_dateOfEnergyShow);
        station = (EditText)findViewById(R.id.station);
        spGass = (Spinner)findViewById(R.id.sp_type_gass);
        spLiter = (Spinner)findViewById(R.id.sp_type_liter);
        spMoney = (Spinner)findViewById(R.id.sp_type_bath);
        mile = (EditText)findViewById(R.id.mile);
        volume = (EditText)findViewById(R.id.volume);
        netPrice = (EditText)findViewById(R.id.net_price);
        saveShowEnergy = (Button)findViewById(R.id.btn_save_energy_show);



        spGass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(parent.getContext(),
//                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
//                        Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spLiter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(parent.getContext(),
//                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
//                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMoney.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(parent.getContext(),
//                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
//                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void addDateOfBuy(){
        dateEnnergyShow.setInputType(InputType.TYPE_NULL);
        dateEnnergyShow.setFocusable(false);
        dateEnnergyShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(AddEnergy.this,"click able",Toast.LENGTH_SHORT).show();
                dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                Calendar newCalendar = Calendar.getInstance();
                //separated = age.split("/");
                int year = newCalendar.get(Calendar.YEAR);
                int mm = newCalendar.get(Calendar.MONTH)-1;
                int day = newCalendar.get(Calendar.DAY_OF_MONTH);

                //Log.e("55555",""+year+"/"+mm+"/"+day);

                fromDatePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        dateEnnergyShow.setText(dateFormatter.format(newDate.getTime()));

                    }

                },year,mm,day);

                fromDatePickerDialog.show();
            }
        });
    }

    private void saveDataToBase() {
        Log.e("11111","...into..saveDataBase");

        sharedpreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);

        ContentValues cv = new ContentValues();
        cv.put("car_id",user_carId);
        cv.put("date_energy",dateEnnergyShow.getText().toString());
        cv.put("station",station.getText().toString());
        cv.put("type_gass", sharedpreference.getString("spGass",null) );
        cv.put("mileage", mile.getText().toString());
        cv.put("volume", volume.getText().toString());
        cv.put("type_volume", sharedpreference.getString("spLiter",null) );
        cv.put("net_price", netPrice.getText().toString());
        cv.put("type_netPrice", sharedpreference.getString("spMoney",null) );
        db.insert("energy_car", null, cv);
        db.close();

        Log.e("2car_id",""+ user_carId );
        Log.e("2date_energy",""+dateEnnergyShow.getText().toString() );
        Log.e("2station",""+station.getText().toString() );
        Log.e("2type_gass",""+ sharedpreference.getString("spGass",null) );
        Log.e("2mileage",""+mile.getText().toString() );
        Log.e("2volume",""+volume.getText().toString());
        Log.e("2type_volume",""+sharedpreference.getString("spLiter",null));
        Log.e("2net_price",""+ netPrice.getText().toString());
        Log.e("2type_netPrice",""+sharedpreference.getString("spMoney",null));

    }
    // get the selected dropdown list value
    public void onClickButton() {


        saveShowEnergy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("21111","...onClick..btnSaveEnergy");
                sharedpreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                editor = sharedpreference.edit();
                editor.putString("spGass", String.valueOf(spGass.getSelectedItem()));
                editor.putString("spLiter", String.valueOf(spLiter.getSelectedItem()));
                editor.putString("spMoney", String.valueOf(spMoney.getSelectedItem()));
                editor.commit();

                saveDataToBase();

                Intent intent = new Intent(AddEnergy.this,MainActivity.class);
                startActivity(intent);


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
