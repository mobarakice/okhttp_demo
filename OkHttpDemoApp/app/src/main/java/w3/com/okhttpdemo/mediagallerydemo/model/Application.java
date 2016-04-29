package w3.com.okhttpdemo.mediagallerydemo.model;

import android.content.pm.PackageInfo;

/**
 * Created by shihab on 10/6/2015.
 */
public class Application extends SelectableItem {
    private String caption = null;
    private long size = -1;
    private PackageInfo packageInfo;
    private String pack = null;


    public Application(String appName, long size, PackageInfo pack) {
        this.caption = appName;
        this.size = size;
        this.packageInfo = pack;

    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public Application(String caption, PackageInfo pack) {
        this.caption = caption;
        this.packageInfo = pack;

    }

    public String getPath() {
        return packageInfo.applicationInfo.publicSourceDir;
    }

    @Override
    public String getThumbPath() {
        return null;
    }

    @Override
    public String getAlbum() {
        return null;
    }

    @Override
    public String getArtist() {
        return null;
    }

    @Override
    public long getMediaDuration() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        return !(caption != null ? !caption.equals(that.caption) : that.caption != null);

    }

    @Override
    public int hashCode() {
        return caption != null ? caption.hashCode() : 0;
    }

    public Application(PackageInfo pack) {
        this.packageInfo = pack;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setCaption(String name) {
        this.caption = name;
    }

    public String getCaption() {
        return caption;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
