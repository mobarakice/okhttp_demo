package w3.com.okhttpdemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import w3.com.okhttpdemo.mediagallerydemo.MediaPickerActivity;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;
import w3.com.okhttpdemo.progresslistener.CustomRequestBodyToTrackProgress;

public class FileUploadingActivity extends AppCompatActivity implements CustomRequestBodyToTrackProgress.ProgressListener {

    private TextView tv;
    private ImageView iv;
    private String name = null;
    //    private String downUrl = "http://192.168.1.139/uploads/images/images(56).jpg";
    private String SERVER_URL = "http://192.168.0.101/uploads/upload_receiver_php.php";
    public static final int PERMISSIONS_REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_PICK_MEDIA = 100;
    List<String> pickedFiles;
    private ProgressDialog mDialog;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_uploading);
        tv = (TextView) findViewById(R.id.tv_result);
        iv = (ImageView) findViewById(R.id.imageView);

        dialog();

        isPermissionGrantNeeded();
    }

    private void dialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Uploading.....");
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setProgress(0);
        mDialog.setMax(100);
    }

    public void onPickClick(View view) {
        startActivityForResult(new Intent(this, MediaPickerActivity.class), REQUEST_PICK_MEDIA);
//        fileDownload(url);
    }


    public void onUploadClick(View view) {

        if (pickedFiles == null || pickedFiles.size() == 0) {
            Toast.makeText(this, "Please pick at least one file", Toast.LENGTH_LONG).show();
            return;
        }
        count = pickedFiles.size();
        showDialog();
        for (int i = 0; i < pickedFiles.size(); i++) {
            MediaType mediaType = Util.getMediaFileType(pickedFiles.get(i));
            File file = new File(pickedFiles.get(i));
            uploadFile(SERVER_URL, file, mediaType);
        }


    }

    private int decrementCounter() {
        return count--;
    }

    public void uploadFile(String url, File path, MediaType mediaType) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        client.connectTimeoutMillis();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userFile", path.getName(), RequestBody.create(mediaType, path))
                .addFormDataPart("userFile", "tempFile")
                .addFormDataPart("userFile", String.valueOf(mediaType))
                .build();

        CustomRequestBodyToTrackProgress body = new CustomRequestBodyToTrackProgress(requestBody, FileUploadingActivity.this);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                int i = decrementCounter();
                Log.d("decrementCounter", "count: " + i);
                Log.d("TAG", "" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("Upload failed");
                        hideDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int i = decrementCounter();
                Log.d("decrementCounter", "count: " + i);
                if (response.isSuccessful()) {
//                    Log.d("onResponse", "" + response.body().string());
                    final String s = response.body().string();
                    final int c = response.code();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText("Code: " + c + "\n" + s);
                            hideDialog();
                        }
                    });
                } else {
                    final int code = response.code();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText("Upload unsuccesfull. Code: " + code);
                            hideDialog();

                        }
                    });
                }
            }
        });

    }


    public boolean isPermissionGrantNeeded() {
        //TODO need to sorted out how we will handle permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CODE_READ_EXTERNAL_STORAGE);

            return true;

        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {


        if (requestCode == PERMISSIONS_REQUEST_CODE_READ_EXTERNAL_STORAGE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_MEDIA && resultCode == RESULT_OK) {

            List<String> paths = data.getStringArrayListExtra("path_list");
            if (paths == null || paths.size() == 0) {
                return;
            }

            pickedFiles = paths;
            tv.setText(pickedFiles.size() + " file(s) selected");

        }
    }

    public String getPath(Uri uri) {
        String res = null;
        String[] projection = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndex(projection[0]);
            res = cursor.getString(column_index);
        }
        cursor.close();

        return res;
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }

    private void showDialog() {
        if (!mDialog.isShowing()) {// && count > 0) {
            mDialog.show();
        }
    }

    private void hideDialog() {
        if (mDialog.isShowing()) {// && count == 0) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onRequestProgress(long bytesWritten, long contentLength) {
        float percentage = 100f * bytesWritten / contentLength;
        mDialog.setProgress((int) percentage);


    }

    public class OkHttpHandler extends AsyncTask<String, Integer, byte[]> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            hideDialog();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mDialog.setIndeterminate(false);
            mDialog.setMax(100);
            mDialog.setProgress(values[0]);
        }

        @Override
        protected byte[] doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    byte[] b = response.body().bytes();
                    publishProgress(b.length / 100);
                    return response.body().bytes();
                } else {
                    throw new IOException("Unexpected code " + response);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void downloadFile(String url) {
        if (url == null) {
            return;
        }

        OkHttpHandler handler = new OkHttpHandler();

        byte[] image = new byte[0];
        try {
            image = handler.execute(url).get();
            if (image != null && image.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                Bitmap bmp = getResizedBitmap(bitmap, 1024, 720);
                iv.setImageBitmap(bmp);
                tv.setText("Total btytes download: " + image.length);
            }
        } catch (Exception e) {
            tv.setText("sorry, something went wrong!");
        }

    }

    /**
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


}



