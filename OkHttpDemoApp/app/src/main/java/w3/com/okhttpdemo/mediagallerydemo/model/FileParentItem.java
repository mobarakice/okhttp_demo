package w3.com.okhttpdemo.mediagallerydemo.model;

import java.util.List;

/**
 * Created by shihab on 10/7/2015.
 */
public class FileParentItem {
    private String caption;
    private List<SelectableItem> files;
    private String extension;

    public FileParentItem(String caption, List<SelectableItem> files, String extension) {
        this.caption = caption;
        this.files = files;
        this.extension = extension;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<SelectableItem> getFiles() {
        return files;
    }

    public void setFiles(List<SelectableItem> files) {
        this.files = files;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
