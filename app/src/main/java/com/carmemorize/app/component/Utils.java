package com.carmemorize.app.component;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.carmemorize.app.R;
import com.carmemorize.app.activity.AddCar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String saveImageInInterStorage(Activity activity, Bitmap finalBitmap) {

        File mediaStorageDir = activity.getDir(Environment.DIRECTORY_PICTURES, activity.MODE_PRIVATE);

        if (!mediaStorageDir.exists()) {

            if (mediaStorageDir.mkdirs()) {

                Log.d("--------- ", "Directory is created.");

            } else {

                Log.d("--------- ", "Failed to create directory.");
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String photoName = "IMG_" + timeStamp + ".jpg";

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + photoName);

        if (mediaFile.exists()) mediaFile.delete();

        try {
            FileOutputStream out = new FileOutputStream(mediaFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("--------- ", "FileNotFoundException java.io.FileNotFoundException: " + mediaFile.getPath());

        return photoName;

    }

    public static Bitmap getImageInInterStorage(Activity activity, String photoName) {

        Bitmap bitmap = null;

        try {

            File imageFile = new File(activity.getDir(Environment.DIRECTORY_PICTURES, activity.MODE_PRIVATE), photoName);
            Log.d("--------- ", "imageFile " + imageFile);

            bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
            Log.d("--------- ", "bitmap " + bitmap);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            Log.d("--------- ", "FileNotFoundException " + e);
        }

        return bitmap;
    }
}
