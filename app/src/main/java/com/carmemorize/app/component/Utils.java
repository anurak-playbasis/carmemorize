package com.carmemorize.app.component;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.carmemorize.app.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static void saveImageInInterStorage(Activity activity, Bitmap finalBitmap){
        String appName = activity.getString(R.string.app_name);
        /** Create a File for saving an image or video */

        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), appName);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (mediaStorageDir.mkdirs()) {
                Log.d("--------- ","Directory is created.");
            } else {
                Log.d("--------- ","Failed to create directory.");
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        if (mediaFile.exists()) mediaFile.delete();
        try {
            FileOutputStream out = new FileOutputStream(mediaFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("--------- ","mediaFile.getPath()"+mediaFile.getPath());

    }
}
