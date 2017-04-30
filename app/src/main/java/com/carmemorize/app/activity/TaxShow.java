package com.carmemorize.app.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;


import com.carmemorize.app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TaxShow extends AppCompatActivity {

    EditText expiration,price;
    Switch alert;
    ImageView showPhoto, addPhoto;
    Button saveDetail;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private static final int CAMERA_REQUEST = 1888;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_show);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initInstance();
        addDateExpiration();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initInstance() {
        expiration = (EditText)findViewById(R.id.et_date_expiration);
        price = (EditText)findViewById(R.id.price);
        alert = (Switch)findViewById(R.id.switch1);
        showPhoto = (ImageView)findViewById(R.id.show_image_camera);
        addPhoto = (ImageView)findViewById(R.id.select_image);
        saveDetail = (Button)findViewById(R.id.btn_save_detail);

        addPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!hasPermissions(TaxShow.this, PERMISSIONS)){
                    ActivityCompat.requestPermissions(TaxShow.this, PERMISSIONS, PERMISSION_ALL);

                }
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });



}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            showPhoto.setImageBitmap(photo);
        }
    }

    public void addDateExpiration(){
        expiration.setInputType(InputType.TYPE_NULL);
        expiration.setFocusable(false);
        expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(TaxShow.this,"click able",Toast.LENGTH_SHORT).show();
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

                        expiration.setText(dateFormatter.format(newDate.getTime()));

                    }

                },year,mm,day);

                fromDatePickerDialog.show();
            }
        });
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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
