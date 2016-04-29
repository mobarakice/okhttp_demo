package w3.com.okhttpdemo.mediagallerydemo.Utility;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by Monir on 09-Dec-15.
 */
public class ItemSelectionAnimation {

    public static void animatedScaleView(View v, float startScale, float endScale, boolean unSel) {

        if(unSel){
            v.setScaleX(1.0f);
            v.setScaleY(1.0f);
        }

        ScaleAnimation anim = new ScaleAnimation(startScale, endScale, startScale, endScale,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);// Needed to keep the result of the animation
        anim.setDuration(200);
        v.setAnimation(anim);
        v.startAnimation(anim);

//        anim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//              //  notifyDataSetChanged();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }



}
