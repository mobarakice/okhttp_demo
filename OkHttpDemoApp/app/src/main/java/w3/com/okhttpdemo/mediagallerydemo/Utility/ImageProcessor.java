package w3.com.okhttpdemo.mediagallerydemo.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;


public class ImageProcessor extends AsyncTask<Void, Integer, Void> {

    ProcessUpdater updater;
    String inputPath, outputPath;
    Context context;
    private int requiredWidth, requiredHeight;


    public interface ProcessUpdater {
        public void onPreExecute();

        public void onPostExecute();

    }


    public ImageProcessor(Context context, ProcessUpdater updater, String inputPath, String outputPath, int requiredWidth, int requiredHeight) {

        this(context, inputPath, outputPath, updater);
        this.requiredWidth = requiredWidth;
        this.requiredHeight = requiredHeight;

    }

    public ImageProcessor(Context context, String inputPath, String outputPath, ProcessUpdater updater) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.updater = updater;
        this.context = context;

    }

    private void setDefaultWidthHeight() {
/*
    If no width and height is passed then setting device width as width and height*/

        requiredWidth = Util.getDeviceWidth(context);
        requiredHeight = requiredWidth;
    }

    @Override
    protected void onPreExecute() {
        updater.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params1) {
        try {
            if (requiredWidth == 0) {
                setDefaultWidthHeight();
            }

            Bitmap bitmap = Util.decodeSampledBitmapFromPath(inputPath, requiredWidth, requiredHeight);
            if (!new File(outputPath).getParentFile().exists()) {
                new File(outputPath).getParentFile().mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(outputPath);
            if (bitmap != null && fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
            fos.flush();
            fos.close();

        } catch (Exception e) {
            new File(outputPath).delete();
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        updater.onPostExecute();
    }
}
