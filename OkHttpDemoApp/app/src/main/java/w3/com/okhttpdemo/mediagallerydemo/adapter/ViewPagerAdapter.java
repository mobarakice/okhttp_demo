package w3.com.okhttpdemo.mediagallerydemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import w3.com.okhttpdemo.mediagallerydemo.fragments.AppsFragment;
import w3.com.okhttpdemo.mediagallerydemo.fragments.CameraFragment;
import w3.com.okhttpdemo.mediagallerydemo.fragments.FilesFragment;
import w3.com.okhttpdemo.mediagallerydemo.fragments.MusicFragment;
import w3.com.okhttpdemo.mediagallerydemo.fragments.PhotoFragment;
import w3.com.okhttpdemo.mediagallerydemo.fragments.RecentFragment;
import w3.com.okhttpdemo.mediagallerydemo.fragments.VideosFragment;


/**
 * Created by W3E-55 on 12/9/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();

        switch (position) {
            case 0:
                f = RecentFragment.newInstance();
                break;
            case 1:
                f = CameraFragment.newInstance();
                break;
            case 2:
                f = PhotoFragment.newInstance();
                break;
            case 3:
                f = VideosFragment.newInstance();
                break;
            case 4:
                f = MusicFragment.newInstance();
                break;
            case 5:
                f = AppsFragment.newInstance();
                break;
            case 6:
                f = FilesFragment.newInstance();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = "";
        if (position == 0) {
            title = "RECENT";
        } else if (position == 1) {
            title = "CAMERA";
        } else if (position == 2) {
            title = "PHOTOS";
        } else if (position == 3) {
            title = "VIDEOS";
        } else if (position == 4) {
            title = "MUSIC";
        } else if (position == 5) {
            title = "APPS";
        } else {
            title = "FILES";
        }
        return title;
    }
}
