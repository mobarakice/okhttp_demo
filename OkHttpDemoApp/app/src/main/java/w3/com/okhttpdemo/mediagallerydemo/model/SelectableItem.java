package w3.com.okhttpdemo.mediagallerydemo.model;


import w3.com.okhttpdemo.mediagallerydemo.interfaces.Selectable;

/**
 * Created by Shihab on 10/14/2015.
 */
public abstract class SelectableItem implements Selectable {
    protected boolean isSelected;

    public abstract String getPath();

    public abstract String getCaption();

    public abstract String getThumbPath();

    public abstract String getAlbum();

    public abstract String getArtist();

    public abstract long getMediaDuration();

    public abstract long getSize();



    @Override
    public void setSelection(boolean value) {
        isSelected = value;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void alterSelection() {
        isSelected = !isSelected;
    }
}
