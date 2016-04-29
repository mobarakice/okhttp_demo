package w3.com.okhttpdemo.mediagallerydemo.Utility;


import org.json.JSONObject;

import w3.com.okhttpdemo.mediagallerydemo.YoCommon;

/**
 * Created by Aziz on 18-Dec-15.
 */
public class ContentParser {
    private final String CONTENT_NAME_JSON_KEY = "file_name";
    private final String CONTENT_DURATION_JSON_KEY = "duration";
    private final String CONTENT_ARTIST_JSON_KEY = "artist";
    private final String CCONTENT_ALBUM_JSON_KEY = "album";

    private JSONObject jsonObject = null;

    public String encodeContentDataToJsonString(String fileName, String mediaDuration, String artist, String album) {

        if (album == null) {
            album = YoCommon.EMPTY_STRING;
        }
        if (artist == null) {
            artist = YoCommon.EMPTY_STRING;
        }

        JSONObject jo = null;
        try {
            jo = new JSONObject();
            jo.put(CONTENT_NAME_JSON_KEY, fileName);
            jo.put(CONTENT_DURATION_JSON_KEY, mediaDuration);
            jo.put(CONTENT_ARTIST_JSON_KEY, artist);
            jo.put(CCONTENT_ALBUM_JSON_KEY, album);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    public void setContentJsonData(String metadata) {
        try {
            jsonObject = new JSONObject(metadata);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getContentName() {
        String name = "";
        try {
            if (jsonObject != null && jsonObject.has(CONTENT_NAME_JSON_KEY)) {
                name = jsonObject.getString(CONTENT_NAME_JSON_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    public String getContentArtist() {
        String artist = "";
        try {
            if (jsonObject != null && jsonObject.has(CONTENT_ARTIST_JSON_KEY)) {
                artist = jsonObject.getString(CONTENT_ARTIST_JSON_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return artist;
    }

    public String getContentAlbum() {
        String album = "";
        try {
            if (jsonObject != null && jsonObject.has(CCONTENT_ALBUM_JSON_KEY)) {
                album = jsonObject.getString(CCONTENT_ALBUM_JSON_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return album;
    }

    public String getContentDuration() {
        String duration = "";
        try {
            if (jsonObject != null && jsonObject.has(CONTENT_DURATION_JSON_KEY)) {
                duration = jsonObject.getString(CONTENT_DURATION_JSON_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return duration;
    }


}
