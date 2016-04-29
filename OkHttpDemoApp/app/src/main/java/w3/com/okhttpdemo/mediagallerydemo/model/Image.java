package w3.com.okhttpdemo.mediagallerydemo.model;

import java.io.File;

/**
 * Created by shihab on 10/2/2015.
 */
public class Image extends SelectableItem {
    private String path, thumbPath, lastModifiedDate;
    private int id;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Image(String thumbPath, String path, String lastModifiedDate) {
        this.path = path;
        this.thumbPath = thumbPath;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Image(String thumbPath, String path) {
        this.path = path;
        this.thumbPath = thumbPath;

    }

    public Image(String path, int id) {
        this.path = path;
        this.id = id;
    }

    public Image(String path) {
        this.path = path;
    }

    public String getThumbPath() {
        return thumbPath;
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
    public long getSize() {
        return 0;
    }


    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCaption() {
        if (path == null) {
            return null;
        }
        return new File(path).getName();
    }
}
