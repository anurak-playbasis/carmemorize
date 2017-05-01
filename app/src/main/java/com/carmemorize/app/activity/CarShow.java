package com.carmemorize.app.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carmemorize.app.R;
import com.carmemorize.app.component.Constants;
import com.carmemorize.app.component.RoundedImageView;
import com.carmemorize.app.component.Utils;
import com.carmemorize.app.model.CarModel;

import org.parceler.Parcels;


public class CarShow extends AppCompatActivity {

    private TextView showName, showBrand, showLicense, showColor, showDateOfDay;

    private Button btnShowSaveCar;

    private String carId;

    private Toolbar toolbar;

    private ImageView showCarPhoto;

    private LinearLayout btnEditCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_show);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        carId = getIntent().getStringExtra(Constants.CAR_ID);

        showName = (TextView) findViewById(R.id.show_name_car);

        showBrand = (TextView) findViewById(R.id.show_brand_car);

        showLicense = (TextView) findViewById(R.id.show_license_car);

        showColor = (TextView) findViewById(R.id.show_sp_color);

        showDateOfDay = (TextView) findViewById(R.id.show_et_dateOfBuy);

        showCarPhoto = (ImageView) findViewById(R.id.show_car_photo);

        btnShowSaveCar = (Button) findViewById(R.id.btn_show_save_car);

        btnEditCar = (LinearLayout) findViewById(R.id.img_edit_car);

        SQLiteDatabase db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);

        if (db != null) {

            Cursor cursor = db.rawQuery("select * from car_detail where car_id = '" + carId + "'", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {

                    String dateBuy = cursor.getString(2);

                    String name = cursor.getString(3);

                    String brand = cursor.getString(4);

                    String licenseCar = cursor.getString(5);

                    String colorCar = cursor.getString(6);

                    String photo_car = cursor.getString(7);

                    showName.setText(name);

                    showBrand.setText( brand.isEmpty() ? "-" : brand );

                    showLicense.setText( licenseCar.isEmpty() ? "-" : licenseCar );

                    showColor.setText(colorCar);

                    showDateOfDay.setText( dateBuy.isEmpty() ? "-" : dateBuy );

                    Bitmap pictureBitmap = Utils.getImageInInterStorage(CarShow.this, photo_car);

                    if (pictureBitmap != null) {

                        showCarPhoto.setImageBitmap(RoundedImageView.getCroppedBitmap(pictureBitmap, Constants.IMAGE_RADIUS));

                    }

                } while (cursor.moveToNext());

            } else {

            }
        }

        btnShowSaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEditCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openEditCar();

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void openEditCar() {

        Intent intent = new Intent(CarShow.this, EditCarActivity.class);

        intent.putExtra(Constants.CAR_ID, carId);

        startActivity(intent);

        finish();
    }
}
