package w3.com.okhttpdemo.mediagallerydemo.model;

/**
 * Created by shihab on 10/7/2015.
 */
public class FileInfo extends SelectableItem {
    private String path;
    private long size;
    private String caption;

    public FileInfo(String path, long size, String caption) {
        this.path = path;
        this.size = size;
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPath() {
        return path;
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



    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
