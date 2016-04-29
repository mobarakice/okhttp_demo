package w3.com.okhttpdemo.mediagallerydemo.model;

/**
 * Created by shihab on 10/5/2015.
 */
public class PhotoItem {
    private String caption;
    private int count;
    private String path;

    public PhotoItem(String caption, int count, String thumbnailPath) {
        this.caption = caption;
        this.count = count;
        this.path = thumbnailPath;
    }

    public PhotoItem(String caption) {
        this.caption = caption;
    }

    public String getPath() {
        return path;
    }

    public String getCaption() {
        return caption;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoItem photoItem = (PhotoItem) o;

        return !(caption != null ? !caption.equals(photoItem.caption) : photoItem.caption != null);

    }

    @Override
    public int hashCode() {
        return caption != null ? caption.hashCode() : 0;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
