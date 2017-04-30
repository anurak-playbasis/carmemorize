package com.anint.anurak.carmemorize.sql;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by Pack.A on 8/8/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_MY_CAR= "car_detail";
    public static final String ENERGY_CAR= "energy_car";
    public static final String TAX_CAR= "tax_car";

    private static final String DATABASE_NAME = "CARMEMORIZE.db";
    private static final String TAG = "MySQLiteHelper";
    private boolean databaseLogs = false;
    public static final int DATABASE_VERSION = 15;

    private Context context;
    public static final String COLUMN_ID = "_id";
    public static final String KEY = "key";

    // employee data
    public static final String CAR_ID = "car_id";
    public static final String DATEOFBUY = "date_buy";
    public static final String NAMECAR = "name_car";
    public static final String BRAND = "brand";
    public static final String LICENSECAR = "license_car";
    public static final String COLORCAR = "color_car";
    public static final String PHOTOCAR = "photo_car";

    public static final String DATEOFENERGY = "date_energy";
    public static final String STATION = "station";
    public static final String TYPEGASS = "type_gass";
    public static final String MILEAGE = "mileage";
    public static final String VOLUME = "volume";
    public static final String TYPE_VOLUME = "type_volume";
    public static final String NETPRICE = "net_price";
    public static final String TYPE_NETPRICE = "type_netPrice";

    public static final String EXPIRATIONDATE = "expiration_date";
    public static final String PRICE_TAX = "price_tax";
    public static final String ALERT_TAX = "alert_tax";
    public static final String PHOTOTAX = "photo_tax";



    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


         String CAR = "CREATE TABLE "
                + TABLE_MY_CAR + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + KEY + " TEXT, "
                + CAR_ID + " TEXT, "+ DATEOFBUY + " DATE, " + NAMECAR + " TEXT, " + BRAND + " TEXT, "
                + LICENSECAR + " TEXT, " + COLORCAR + " TEXT, "+ PHOTOCAR + " TEXT" + ");";
        db.execSQL(CAR);

        String ENERGY = "CREATE TABLE "
                + ENERGY_CAR + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + KEY + " TEXT, "
                + CAR_ID + " TEXT, "+ DATEOFENERGY + " DATE, " + STATION + " TEXT, " + TYPEGASS + " TEXT, "+ MILEAGE + " TEXT, "
                + VOLUME + " TEXT, "+ TYPE_VOLUME + " TEXT, "+ NETPRICE + " TEXT, " + TYPE_NETPRICE + " TEXT" + ");";
        db.execSQL(ENERGY);

        String TAX = "CREATE TABLE "
                + TAX_CAR + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + KEY + " TEXT, "
                + CAR_ID + " TEXT, "+ EXPIRATIONDATE + " DATE, " + PRICE_TAX + " TEXT, " + ALERT_TAX + " TEXT, "+  PHOTOTAX + " TEXT" + ");";
        db.execSQL(TAX);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_MY_CAR);
        db.execSQL("DROP TABLE IF EXISTS " +ENERGY_CAR);
        db.execSQL("DROP TABLE IF EXISTS " +TAX_CAR);

        // Create tables again
        onCreate(db);
    }
}
