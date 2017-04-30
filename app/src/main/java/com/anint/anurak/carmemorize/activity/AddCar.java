package com.anint.anurak.carmemorize.activity;

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
import android.net.Uri;
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
import android.widget.Toast;

import com.anint.anurak.carmemorize.R;
import com.anint.anurak.carmemorize.component.Permission;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddCar extends AppCompatActivity {

    private Spinner spColor;
    private Button btnSave;
    EditText dateOfBuy,carId,nameCar,brandCar,licenseCar;
    ImageView takePhoto;
    Toolbar toolbar;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    SQLiteDatabase db;
    public static final String MyPREFERENCES = "shareCar";
    SharedPreferences sharedpreference;
    SharedPreferences.Editor editor;
    //String[] separated;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_car);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initInstance();
        addColorSelection();
        addDateOfBuy();
        onClickButton();



    }

    private void initInstance() {


        carId = (EditText)findViewById(R.id.car_id);
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

                if (Permission.isEnabledCamera(AddCar.this)){

                    getOpenCamera();

                }
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

                saveDataToBase();

                Intent intent = new Intent(AddCar.this,MainActivity.class);
                startActivity(intent);

            }

        });
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
            openCamera();
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

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    private void saveDataToBase() {
        Log.e("11111","...into..saveDataBase");

        sharedpreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db = openOrCreateDatabase("CARMEMORIZE", Context.MODE_PRIVATE, null);

        ContentValues cv = new ContentValues();
        cv.put("car_id",carId.getText().toString());
        cv.put("date_buy",dateOfBuy.getText().toString());
        cv.put("name_car",nameCar.getText().toString());
        cv.put("brand", brandCar.getText().toString() );
        cv.put("license_car", licenseCar.getText().toString());
        cv.put("color_car", sharedpreference.getString("spColor",null));
        //cv.put("photo_car", sprefs.getString("lateMinutes",null));
        db.insert("car_detail", null, cv);
        db.close();

        Log.e("111111",""+carId.getText().toString() );
        Log.e("1111111",""+dateOfBuy.getText().toString() );
        Log.e("11111111",""+nameCar.getText().toString() );
        Log.e("111111111",""+brandCar.getText().toString() );
        Log.e("1111111111",""+licenseCar.getText().toString() );
        Log.e("11111111111",""+sharedpreference.getString("spColor",null));

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
