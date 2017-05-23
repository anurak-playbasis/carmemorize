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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carmemorize.app.R;
import com.carmemorize.app.component.Constants;
import com.carmemorize.app.component.RoundedImageView;
import com.carmemorize.app.component.Utils;
import com.carmemorize.app.model.CarModel;
import com.carmemorize.app.sql.CarDetailTable;
import com.carmemorize.app.sql.HistoryTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCar extends AppCompatActivity {

    private static final String TAG = AddCar.class.getSimpleName();
    private final int REQUEST_CAMERA = 1;

    private Spinner spColor;
    private Button btnSave;
    EditText dateOfBuy,nameCar,brandCar,licenseCar;
    ImageView takePhoto;
    Toolbar toolbar;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    SQLiteDatabase db;
    public static final String MyPREFERENCES = "shareCar";
    public static final String MyHISTORY = "historyCar";
    SharedPreferences sharedpreference;
    SharedPreferences.Editor editor;
    //String[] separated;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private TextView toolbarTitle;

    private String carId;

    private String photoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_car);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.text_add_my_car));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initInstance();
        addColorSelection();
        addDateOfBuy();
        onClickButton();



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

            Toast.makeText(AddCar.this, getString(R.string.toast_enter_car_name), Toast.LENGTH_SHORT).show();

            nameCar.requestFocus();

            return;
        }

        if (carBrand.isEmpty()){

            Toast.makeText(AddCar.this, getString(R.string.toast_enter_car_name), Toast.LENGTH_SHORT).show();

            brandCar.requestFocus();

            return;
        }

        saveDataToBase();

    }

    public void addDateOfBuy(){
        dateOfBuy.setInputType(InputType.TYPE_NULL);
        dateOfBuy.setFocusable(false);
        dateOfBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(AddCar.this,"click able",Toast.LENGTH_SHORT).show();
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

            photoName = Utils.saveImageInInterStorage(AddCar.this, photo);

            updatePicture();

        }
    }

    public void updatePicture(){

        Bitmap pictureBitmap = Utils.getImageInInterStorage(AddCar.this, photoName);

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
        AlertDialog alertDialog = new AlertDialog.Builder(AddCar.this).create();
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
                        ActivityCompat.requestPermissions(AddCar.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(AddCar.this).create();
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
                        startInstalledAppDetailsActivity(AddCar.this);
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


    private void saveDataToBase() {

        CarModel carModel = new CarModel(
                carId,
                dateOfBuy.getText().toString(),
                nameCar.getText().toString(),
                brandCar.getText().toString(),
                licenseCar.getText().toString(),
                sharedpreference.getString("spColor",null),
                photoName != null ? photoName : ""
        );

        if(CarDetailTable.saveCarDetail(AddCar.this, carModel)){

            Toast.makeText(AddCar.this, getString(R.string.toast_add_data_successfully), Toast.LENGTH_SHORT).show();

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

        Intent intent = new Intent(AddCar.this,MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        finish();
    }

}
