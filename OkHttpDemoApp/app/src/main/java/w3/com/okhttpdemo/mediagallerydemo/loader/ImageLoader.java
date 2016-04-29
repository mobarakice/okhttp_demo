package w3.com.okhttpdemo.mediagallerydemo.loader;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;

/**
 * Created by shihab on 10/5/2015.
 */
public class ImageLoader {

    private final Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;
    private Bitmap mPlaceHolderBitmap;
    private int dimension;

    public ImageLoader(Context mContext, int dimension) {
        this.mContext = mContext;
        this.dimension = dimension;
        mPlaceHolderBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.empty_photo);


        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClassBytes = am.getMemoryClass() * 1024 * 1024;

        mMemoryCache = new LruCache<String, Bitmap>(memoryClassBytes / 4) {

            protected int sizeOf(String key, Bitmap bitmap) {
                // The <span class="IL_AD" id="IL_AD5">cache size</span> will be measured in bytes rather than number
                // of items.
                return bitmap.getByteCount();
            }

        };
    }

    public void setPlaceHolder(String colorCode) {
        mPlaceHolderBitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888);
        mPlaceHolderBitmap.eraseColor(Color.parseColor(colorCode));
    }

    public void loadBitmap(String path, ImageView imageView) {


        if (path != null) {

            Bitmap btmp = getBitmapFromMemCache(path);


            if (btmp != null) {
                imageView.setImageBitmap(btmp);
                return;
            }
        }


        if (cancelPotentialWork(path, imageView)) {


            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mContext.getResources(), mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);

            task.execute(path);
        }
    }


    public static boolean cancelPotentialWork(String path, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.data;
            if (bitmapData != path) {

                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was
        // cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable == null) {
                return null;
            }
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
                    bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        public String data = null;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage
            // collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            data = params[0];
            final Bitmap bitmap = Util.decodeSampledBitmapFromPath(
                    data, dimension, dimension);

            addBitmapToMemoryCache(params[0], bitmap);


            return bitmap;
        }


        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {

                    imageView.setImageBitmap(bitmap);
                    imageView.setTag(bitmap.getHeight());
                }
            }
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {


        if (bitmap == null || key == null) {
            return;
        }
        if (getBitmapFromMemCache(key) == null) {

            mMemoryCache.put(key, bitmap);


        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return  mMemoryCache.get(key);
    }


}
