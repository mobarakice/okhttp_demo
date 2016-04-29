package w3.com.okhttpdemo.mediagallerydemo.model;

/**
 * Created by shihab on 11/10/2015.
 */
public class EditablePublishItem {
    private String fullPath, thumbPath, title, description,artist,album,identity;
    private long lastModifiedDate, mediaDuration;



    private boolean isDescriptionOnFocussed;

    public boolean isTitleOnFocussed() {
        return titleOnFocussed;
    }

    public void setTitleOnFocussed(boolean titleOnFocussed) {
        this.titleOnFocussed = titleOnFocussed;
    }

    public boolean titleOnFocussed;

    public boolean isDescriptionOnFocussed() {
        return isDescriptionOnFocussed;
    }

    public void setIsDescriptionOnFocussed(boolean isDescriptionOnFocussed) {
        this.isDescriptionOnFocussed = isDescriptionOnFocussed;
    }

    public String getIdentity() {
        return identity;
    }

    public EditablePublishItem(String fullPath, String thumbPath, long lastModifiedDate, String title, String description, long mediaDuration, String identity,String artist,String album) {
        this.fullPath = fullPath;
        this.thumbPath = thumbPath;
        this.lastModifiedDate = lastModifiedDate;
        this.title = title;
        this.description = description;

        this.mediaDuration = mediaDuration;
        this.identity = identity;
        this.artist=artist;
        this.album=album;
    }

    public long getMediaDuration() {
        return mediaDuration;
    }

    public void setMediaDuration(long mediaDuration) {
        this.mediaDuration = mediaDuration;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }
}
