package w3.com.okhttpdemo.mediagallerydemo.model;

/**
 * Created by shihab on 10/5/2015.
 */
public class VideoItem extends SelectableItem {
    private String caption;
    private long size;
    private long duration;
    private String thumbPath;
    private String path;
    private String artist;
    private String album;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public VideoItem(String caption, long size, long duration, String thumbnailPath, String path,String artist,String album) {
        this.caption = caption;
        this.size = size;
        this.duration = duration;
        this.thumbPath = thumbnailPath;
        this.path = path;
        this.artist=artist;
        this.album=album;
    }

    public VideoItem(String caption) {
        this.caption = caption;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public String getCaption() {
        return caption;
    }

    public long getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoItem photoItem = (VideoItem) o;

        return !(caption != null ? !caption.equals(photoItem.caption) : photoItem.caption != null);

    }

    @Override
    public int hashCode() {
        return caption != null ? caption.hashCode() : 0;
    }

    public long getMediaDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }
}
