package com.carmemorize.app.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carmemorize.app.R;
import com.carmemorize.app.component.Constants;
import com.carmemorize.app.component.RoundedImageView;
import com.carmemorize.app.component.Utils;
import com.carmemorize.app.model.CarModel;
import com.carmemorize.app.sql.CarDetailTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditCarActivity extends AppCompatActivity {
    
    private Toolbar toolbar;
    
    private String carId;

    private Spinner spColor;
    
    private Button btnSave;
    
    private EditText dateOfBuy, nameCar, brandCar, licenseCar;
    
    private ImageView takePhoto;

    private final int REQUEST_CAMERA = 1;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;

    public static final String ALLOW_KEY = "ALLOWED";

    public static final String CAMERA_PREF = "camera_pref";

    SQLiteDatabase db;

    public static final String MyPREFERENCES = "shareCar";

    SharedPreferences sharedpreference;

    SharedPreferences.Editor editor;

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog fromDatePickerDialog;

    private String photoName;

    private String oldCarPhoto;

    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_my_car);

        intToolBar();

        carId = getIntent().getStringExtra(Constants.CAR_ID);

        initInstance();
        
        addColorSelection();
        
        addDateOfBuy();
        
        onClickButton();

    }

    private void intToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        toolbarTitle.setText(getString(R.string.text_edit_my_car));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initInstance() {

        nameCar = (EditText)findViewById(R.id.name_car);

        brandCar = (EditText)findViewById(R.id.brand_car);

        licenseCar = (EditText)findViewById(R.id.license_car);

        spColor = (Spinner) findViewById(R.id.sp_color);

        btnSave = (Button) findViewById(R.id.btn_save_car);

        dateOfBuy = (EditText)findViewById(R.id.et_dateOfBuy);

        takePhoto = (ImageView)findViewById(R.id.im_take_photo);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getOpenCamera();

            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        displayCarData();
    }

    private void displayCarData(){

        SQLiteDatabase db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);

        if (db != null) {

            Cursor cursor = db.rawQuery("select * from car_detail where car_id = '" + carId + "'", null);

            if (cursor != null && cursor.moveToFirst()) {

                do {

                    String dateBuy = cursor.getString(2);

                    String name = cursor.getString(3);

                    String brand = cursor.getString(4);

                    String license = cursor.getString(5);

                    String photo_car = cursor.getString(7);

                    oldCarPhoto = photo_car;

                    nameCar.setText(name);

                    brandCar.setText(brand);

                    licenseCar.setText(license);

                    dateOfBuy.setText(dateBuy);

                    String[] colorArray = getResources().getStringArray(R.array.color_arrays);

                    for (int index = 0; index <colorArray.length; index++){

                        if (colorArray[index].equals(cursor.getString(6))){

                            spColor.setSelection(index);

                        }
                    }


                    Bitmap pictureBitmap = Utils.getImageInInterStorage(EditCarActivity.this, photo_car);

                    if (pictureBitmap != null) {

                        takePhoto.setImageBitmap(RoundedImageView.getCroppedBitmap(pictureBitmap, Constants.IMAGE_RADIUS));

                    }

                } while (cursor.moveToNext());

            } else {

            }
        }
    }
    
    public void addColorSelection() {
        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    // get the selected dropdown list value
    public void onClickButton() {
        
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("1111","...onClick..btnSave");
                sharedpreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                editor = sharedpreference.edit();
                editor.putString("spColor", String.valueOf(spColor.getSelectedItem()));
                editor.commit();

                verifyValue();

            }

        });
    }

    private void verifyValue(){

        String carName = nameCar.getText().toString();

        String carBrand = brandCar.getText().toString();

        if (carName.isEmpty()){

            Toast.makeText(EditCarActivity.this, getString(R.string.toast_enter_car_name), Toast.LENGTH_SHORT).show();

            nameCar.requestFocus();

            return;
        }

        if (carBrand.isEmpty()){

            Toast.makeText(EditCarActivity.this, getString(R.string.toast_enter_car_name), Toast.LENGTH_SHORT).show();

            brandCar.requestFocus();

            return;
        }

        editCarOnDataBase();

    }

    public void addDateOfBuy(){
        dateOfBuy.setInputType(InputType.TYPE_NULL);
        dateOfBuy.setFocusable(false);
        dateOfBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(EditCarActivity.this,"click able",Toast.LENGTH_SHORT).show();
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

                        dateOfBuy.setText(dateFormatter.format(newDate.getTime()));

                    }

                },year,mm,day);

                fromDatePickerDialog.show();
            }
        });
    }

    public void getOpenCamera(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(this, ALLOW_KEY)) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)

                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        } else {

            takeFromCamera();
        }

    }

    private void takeFromCamera() {

        try {

            Intent callCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(callCamera, REQUEST_CAMERA);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK ){

            Bitmap photo = (Bitmap) data.getExtras().get("data");

            photoName = Utils.saveImageInInterStorage(EditCarActivity.this, photo);

            updatePicture();

        }
    }

    public void updatePicture(){

        Bitmap pictureBitmap = Utils.getImageInInterStorage(EditCarActivity.this, photoName);

        if (pictureBitmap != null){

            takePhoto.setImageBitmap(RoundedImageView.getCroppedBitmap(pictureBitmap, Constants.IMAGE_RADIUS));

        }
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }
    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(EditCarActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(EditCarActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(EditCarActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(EditCarActivity.this);
                    }
                });

        alertDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        this, permission);

                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {

                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }


    private void editCarOnDataBase() {

        CarModel carModel = new CarModel(
                carId,
                dateOfBuy.getText().toString(),
                nameCar.getText().toString(),
                brandCar.getText().toString(),
                licenseCar.getText().toString(),
                sharedpreference.getString("spColor",null),
                photoName != null ? photoName : oldCarPhoto
        );

        if(CarDetailTable.updateCarDetail(EditCarActivity.this, carModel)){

            Toast.makeText(EditCarActivity.this, getString(R.string.toast_edit_data_successfully), Toast.LENGTH_SHORT).show();

            openMain();

        }

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


    private void openMain(){

        Intent intent = new Intent(EditCarActivity.this,MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        finish();
    }

}

