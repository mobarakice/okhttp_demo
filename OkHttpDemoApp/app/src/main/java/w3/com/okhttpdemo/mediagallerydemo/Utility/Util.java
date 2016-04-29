package w3.com.okhttpdemo.mediagallerydemo.Utility;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import okhttp3.MediaType;
import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.YoCommon;
import w3.com.okhttpdemo.mediagallerydemo.model.FileInfo;
import w3.com.okhttpdemo.mediagallerydemo.model.FileParentItem;
import w3.com.okhttpdemo.mediagallerydemo.model.Image;
import w3.com.okhttpdemo.mediagallerydemo.model.PhotoItem;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;

/**
 * Created by shihab on 10/5/2015.
 */
public class Util {
    public static final int TCP_FILE_SENDING_RECEIVING_PORT = 25023;
    public static final int CHECK_TCP_FILE_SENDING_RECEIVING_PORT = 2523;

    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    public static String getThumbnailPath(int imageId, Context context) {
        if (context == null) {
            return null;
        }
        Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(
                context.getContentResolver(), imageId,
                MediaStore.Images.Thumbnails.MINI_KIND, null);

        if (cursor != null && cursor.moveToFirst()) {
            String thumbPath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Images.Thumbnails.DATA));


            cursor.close();
            return thumbPath;

        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public static List<Image> getListForBucket(Context context, String bucket) {

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri, projection, MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "='" + bucket + "'",
                null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

        List<Image> list = new ArrayList<Image>();
        int columnImageId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int columnActualFilePath = cursor.getColumnIndex(MediaStore.Images.Media.DATA);


        if (cursor != null && cursor.moveToFirst()) {
            do {

                String realPath = cursor.getString(columnActualFilePath);
                File file = new File(realPath);
                if (file == null || !file.exists()) {

                    continue;
                }


                String thumbnailPath = getThumbnailPath(cursor.getInt(columnImageId), context);

                if (thumbnailPath == null) {
                    thumbnailPath = realPath;

                }

                list.add(new Image(thumbnailPath, realPath));

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public static String getFormattedDuration(long millis) {
        long seconds = millis / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        if (h <= 0) {
            return String.format("%02d:%02d", m, s);

        }
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public static String getFileSize(long size) {
        if (size <= 0) {
            return "0B";
        }
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return (long) (size / Math.pow(1024, digitGroups)) + "" + units[digitGroups];


        //return new DecimalFormat("#,##0").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static int dpToPx(float dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    public static boolean validatePhotItem(PhotoItem item, Context context) {

        if (context == null) {
            return false;
        }
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri, projection, MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "='" + item.getCaption() + "'",
                null, null);

        int columnActualFilePath = cursor.getColumnIndex(MediaStore.Images.Media.DATA);


        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String realPath = cursor.getString(columnActualFilePath);
                File file = new File(realPath);
                if (file == null || !file.exists()) {
                    continue;

                }
                count++;
            } while (cursor.moveToNext());
        }

        item.setCount(count);
        if (cursor != null) {
            cursor.close();
        }
        return count > 0;

    }

    public static boolean validateFileItem(FileParentItem item) {
        int count = 0;

        ListIterator<SelectableItem> iterator = item.getFiles().listIterator();

        while (iterator.hasNext()) {
            FileInfo info = (FileInfo) iterator.next();
            File file = new File(info.getPath());
            if (file == null || !file.exists()) {
                iterator.remove();
                continue;

            }
            count++;
        }


        return count > 0;
    }

    public static String getPathFromUri(Uri uri) {
        if (uri == null) {
            return null;
        }
        String scheme = uri.getScheme();
        if (scheme != null && scheme.equals("file")) {
            String filePath = uri.getPath();
            return filePath;
        }
        return null;

    }

    public static String getMediaType(String fileURL) {
        //String originalURL = fileURL;
        if (fileURL == null || fileURL.equals("")) {
            return YoCommon.FILE_TYPE_UNKNOWN;
        }
        String url = fileURL.toLowerCase(Locale.US).trim();

        try {
            if (url.endsWith(".m4p")
                    || url.endsWith(".3gpp")
                    || url.endsWith(".mp3")
                    || url.endsWith(".wma")
                    || url.endsWith(".wav")
                    || url.endsWith(".ogg")
                    || url.endsWith(".m4a")
                    || url.endsWith(".aac")
                    || url.endsWith(".ota")
                    || url.endsWith(".imy")
                    || url.endsWith(".rtx")
                    || url.endsWith(".rtttl")
                    || url.endsWith(".xmf")
                    || url.endsWith(".mid")
                    || url.endsWith(".mxmf")
                    || url.endsWith(".amr")
                    || url.endsWith(".flac")) {
                return YoCommon.FILE_TYPE_MUSIC;
            }
            if (url.endsWith(".jpg")
                    || url.endsWith(".jpeg")
                    || url.endsWith(".png")
                    || url.endsWith(".bmp")) {
                return YoCommon.FILE_TYPE_PHOTO;
            }
            if (url.endsWith(".3gp")
                    || url.endsWith(".mpg")
                    || url.endsWith(".mpeg")
                    || url.endsWith(".mpe")
                    || url.endsWith(".mp4")
                    || url.endsWith(".avi")
                    || url.endsWith(".mov")) {
                return YoCommon.FILE_TYPE_VIDEO;
            }
            if (url.endsWith(".zip") || url.endsWith(".rar")) {
                return YoCommon.FILE_TYPE_ZIP;
            }
            if (url.endsWith(".apk")) {
                return YoCommon.FILE_TYPE_APP;
            }
            //Now that we define for known types let recognize other with their extension, like .pdf is pdf, .doc is doc
            if (url.contains(".")) {

                String ext = url.substring(url.lastIndexOf(".") + 1);
                if (ext != null && !ext.trim().equals("")) {
                    return ext.toUpperCase();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return YoCommon.FILE_TYPE_UNKNOWN;
    }

    public static void lunchTheApp(String pack, Context context) {
        try {
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(pack);
            context.startActivity(LaunchIntent);
        } catch (ActivityNotFoundException e) {

        } catch (Exception e) {
//NULL pointer for context
        }
    }


    public static String getAppNameFromPath(Context context, String path) {
        PackageInfo pi = context.getPackageManager().getPackageArchiveInfo(path, 0);

        pi.applicationInfo.sourceDir = path;
        pi.applicationInfo.publicSourceDir = path;
        return pi.applicationInfo.loadLabel(context.getPackageManager()).toString();
    }

    public static String getPackagNameFromPath(Context context, String path) {
        PackageInfo pi = context.getPackageManager().getPackageArchiveInfo(path, 0);

        if (pi == null) {
            Log.e("null package:", "could not generate package info from path:" + path);
            return null;
        }
        return pi.packageName;
    }


//    public static void deleteFromPublished(String path, Context context) {
//        SharedItem sharedItem = PublishedMediaLoader.getInstance().getMatchItem(path);
//        if (sharedItem != null) {
//            DBManager dbManager = CommonObjectClasss.getDatabase(context);
//            dbManager.deletePublishedMedia(sharedItem.getIdentitiy());
//            PublishedMediaLoader.getInstance().removeItem(sharedItem);
//        }
//
//    }


    private static int getDpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());

    }

//    public static boolean isPublished(String path) {
//        SharedItem sharedItem = PublishedMediaLoader.getInstance().getMatchItem(path);
//        return sharedItem != null;
//    }

    public static Bitmap decodeSampledBitmapFromPath(
            String path, int reqWidth, int reqHeight) {

        if (path == null) {
            return null;
        }


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        File file = new File(path);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        BitmapFactory.decodeStream(in, null, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);


        options.inJustDecodeBounds = false;

        try {
            in = new FileInputStream(file);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return null;
        }


        return BitmapFactory.decodeStream(in, null, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);


            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static PackageInfo getPackageInfo(String path, Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(path, 0);

        pi.applicationInfo.sourceDir = path;
        pi.applicationInfo.publicSourceDir = path;
        return pi;
    }

    public static Drawable getIconFromPackageName(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                Context otherAppCtx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);

                int displayMetrics[] = {DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_MEDIUM, DisplayMetrics.DENSITY_TV};

                for (int displayMetric : displayMetrics) {
                    try {
                        Drawable d = otherAppCtx.getResources().getDrawableForDensity(pi.applicationInfo.icon, displayMetric);
                        if (d != null) {

                            return d;
                        }
                    } catch (Resources.NotFoundException e) {
                    }
                }

            } catch (Exception e) {
                // Handle Error here
            }
        }

        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }

        return appInfo.loadIcon(pm);
    }

    public static void setAppIcon(String path, ImageView imageView, Context context) {
        PackageInfo packageInfo = getPackageInfo(path, context);
        Drawable drawable = getIconFromPackageName(packageInfo.packageName, context);
        // imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        int padding = Util.dpToPx(80, context);
        imageView.setPadding(padding, padding, padding, padding);
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.default_app));
        }

    }

    /**
     * Monir Zzaman
     * Purpose to blur the orginal Image
     * Date: 21-DEC-2015
     */

    private static final float BITMAP_SCALE = 0.2f;
    private static final float BLUR_RADIUS = 4.0f;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(Context context, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    private static final float BITMAP_SCALE_LIGHT = 0.1f;
    private static final float BLUR_RADIUS_LIGHT = 1.0f;


    public static Bitmap blurLight(Context context, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE_LIGHT);
        int height = Math.round(image.getHeight() * BITMAP_SCALE_LIGHT);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS_LIGHT);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static MediaType getMediaFileType(String path) {
        if (path == null) {
            return MediaType.parse("unknown");
        }
        String type = getMediaType(path);
        if (type == YoCommon.FILE_TYPE_PHOTO) {
            return MediaType.parse("image/*");
        } else if (type == YoCommon.FILE_TYPE_VIDEO) {
            return MediaType.parse("video/*");
        } else if (type == YoCommon.FILE_TYPE_MUSIC) {
            return MediaType.parse("audio/*");
        } else if (type == YoCommon.FILE_TYPE_APP) {
            return MediaType.parse("application/vnd.android.package-archive");
        } else if (type == YoCommon.FILE_TYPE_ZIP) {
            return MediaType.parse("application/msword, application/pdf, application/vnd.ms-powerpoint, application/x-wav");
        } else {
            return MediaType.parse("unknown");
        }
    }

}
