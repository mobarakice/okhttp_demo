package w3.com.okhttpdemo.mediagallerydemo.Utility;

import android.os.Build;

/**
 * Created by Roman on 5/26/2015.
 */
public class OnControl {
    private OnControl() {}
    private static OnControl mOnControl;

    public static OnControl init() {
        return mOnControl = mOnControl == null ? new OnControl() : mOnControl;
    }

    public boolean getIsLollipopMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public boolean getIsLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
