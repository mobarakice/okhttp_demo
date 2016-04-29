package w3.com.okhttpdemo.mediagallerydemo.Utility;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import w3.com.okhttpdemo.mediagallerydemo.YoCommon;

public class MediaCaptureUtil {

    private static File getRootFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + YoCommon.cam_directory);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(YoCommon.icon_location, "Oops! Failed create "
                        + YoCommon.cam_directory + " directory");
                return null;
            }
        }
        return mediaStorageDir;
    }

    public static File getCameraOutputFile() {
        return getOutputFile("YO_IMG_", ".jpg");


    }

    public static File getVideoOutputFile() {
        return getOutputFile("VID_", ".mp4");


    }

    private static File getOutputFile(String folder, String extension) {

        File mediaStorageDir = getRootFile();
        // Create the storage directory if it does not exist

        if (mediaStorageDir == null) {
            Log.d(YoCommon.icon_location, "Oops! Failed create "
                    + YoCommon.cam_directory + " directory");
            return null;
        }


        String timeStamp = getTImeStamp();
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + folder + timeStamp + extension);
        return mediaFile;

    }


    private static String getTImeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }


    public static boolean hasCameraFeature(Context context) {

        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    /*
     * Creating file uri to store image/video
     */
    public static Uri getOutputMediaFileUri(File file) {
        return Uri.fromFile(file);
    }


    public static Intent buildVideoIntent(Context context, Uri uri) {

        String title ="Choose photo";

        //Build galleryIntent
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);

        galleryIntent.setType("video/*");
        //Create chooser
        Intent chooser = Intent.createChooser(galleryIntent, title);

        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        Intent[] extraIntents = {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        return chooser;

    }

    public static Intent buildCameraIntent() {
        Intent intent = getCameraIntent();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri(getCameraOutputFile()));
        return intent;
    }

    public static Intent buildCameraIntent(Uri uri) {
        Intent intent = getCameraIntent();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
              intent.putExtra("return-data", true);
        return intent;
    }


    public static Intent getCameraIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }
}



