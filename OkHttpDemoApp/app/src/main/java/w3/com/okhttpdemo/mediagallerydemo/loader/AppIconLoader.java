package w3.com.okhttpdemo.mediagallerydemo.loader;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

;

/**
 * Created by shihab on 10/5/2015.
 */
public class AppIconLoader {

    private final Context mContext;
    private LruCache<String, Drawable> mMemoryCache;
    private Bitmap mPlaceHolderBitmap;
    private int dimension;
    private PackageManager pManager;

    public AppIconLoader(Context mContext, int dimension) {
        this.mContext = mContext;
        this.dimension = dimension;
        pManager = mContext.getPackageManager();
        mPlaceHolderBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.empty_photo);
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClassBytes = am.getMemoryClass() * 1024 * 1024;

        mMemoryCache = new LruCache<String, Drawable>(memoryClassBytes/6) {

            protected int sizeOf(String key, Drawable drawable) {
                // The <span class="IL_AD" id="IL_AD5">cache size</span> will be measured in bytes rather than number
                // of items.

                // the drawable (Captain Obvious, to the rescue!!!)
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    return bitmap.getByteCount();
                }
                return 0;
            }

        };

    }

    public void setPlaceHolder(String colorCode) {
        mPlaceHolderBitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888);
        mPlaceHolderBitmap.eraseColor(Color.parseColor(colorCode));
    }


    public void loadBitmapFromPath(String path, ImageView imageView) {

        if (path == null) {
            loadBitmap(null, imageView);
            return;
        }


        PackageInfo pi = pManager.getPackageArchiveInfo(path, 0);
        if (pi != null) {

            pi.applicationInfo.sourceDir = path;
            pi.applicationInfo.publicSourceDir = path;
            loadBitmap(pi.applicationInfo.packageName, imageView);
        }
    }

    public void loadBitmap(String pack, ImageView imageView) {



        if (pack != null) {
            Drawable drawable = getDrawableFromMemCache(pack);


            if (drawable != null) {
                imageView.setImageDrawable(drawable);
                return;
            }
        }

        if (cancelPotentialWork(pack, imageView)) {


            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mContext.getResources(), mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(pack);

            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_app));
        }
    }


    public static boolean cancelPotentialWork(String pack, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.pack;
            if (bitmapData != pack) {

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

    class BitmapWorkerTask extends AsyncTask<String, Void, Drawable> {
        public String pack = null;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage
            // collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Drawable doInBackground(String... params) {
            pack = params[0];
            try {
                /*ApplicationInfo app = pManager.getApplicationInfo(pack, 0);
                Drawable d = app.loadIcon(pManager);
*/
                Drawable d= Util.getIconFromPackageName(pack, mContext);

                addBitmapToMemoryCache(pack, d);
                return d;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


            return null;
        }


        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Drawable drawable) {
            if (imageViewReference != null && drawable != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {

                    imageView.setImageDrawable(drawable);
                }
            }
        }
    }


    private void addBitmapToMemoryCache(String key, Drawable drawable) {


        if (drawable == null || key == null) {
            return;
        }
        if (getDrawableFromMemCache(key) == null) {

            mMemoryCache.put(key, drawable);
        }
    }

    private Drawable getDrawableFromMemCache(String key) {
        return (Drawable) mMemoryCache.get(key);
    }


}
