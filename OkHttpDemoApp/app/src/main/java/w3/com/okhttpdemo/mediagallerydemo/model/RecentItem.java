package w3.com.okhttpdemo.mediagallerydemo.model;

/**
 * Created by shihab on 10/28/2015.
 */
public class RecentItem extends SelectableItem {
    private String title, thumbnailPath, path;
    long mediadDuration, fileSize;
    String fileIdentity;

    public RecentItem(String fileIdentity, String title, String thumbnailPath, long fileSize, String path, long mediadDuration) {
        this.title = title;
        this.thumbnailPath = thumbnailPath;
        this.fileSize = fileSize;
        this.path = path;
        this.mediadDuration = mediadDuration;
        this.fileIdentity = fileIdentity;
    }

    public String getFileIdentity() {
        return fileIdentity;
    }

    public String getPath() {
        return path;
    }


    public RecentItem(String title, String thumbnailPath, long fileSize) {
        this.title = title;
        this.thumbnailPath = thumbnailPath;
        this.fileSize = fileSize;
    }

    public long getMediaDuration() {
        return mediadDuration;
    }

    @Override
    public long getSize() {
        return fileSize;
    }


    public String getCaption() {
        return title;
    }

    public void setCaption(String title) {
        this.title = title;
    }

    public String getThumbPath() {
        return thumbnailPath;
    }

    @Override
    public String getAlbum() {
        return null;
    }

    @Override
    public String getArtist() {
        return null;
    }


    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
