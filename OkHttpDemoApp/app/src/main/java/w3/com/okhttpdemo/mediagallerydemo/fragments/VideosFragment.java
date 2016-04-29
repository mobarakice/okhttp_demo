package w3.com.okhttpdemo.mediagallerydemo.fragments;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.MediaPickerActivity;
import w3.com.okhttpdemo.mediagallerydemo.adapter.VideoAdapter;
import w3.com.okhttpdemo.mediagallerydemo.model.VideoItem;

public class VideosFragment extends CommonFragment {

    private RecyclerView parent;


    public static VideosFragment newInstance() {
        VideosFragment fragment = new VideosFragment();
        return fragment;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = fragmentView;
        if (v != null) {
            return v;
        }
        v = inflater.inflate(R.layout.fragment_videos, container, false);
        fragmentView = v;
        parent = (RecyclerView) v.findViewById(R.id.rv_parent);

        parent.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new VideoAdapter(getContext());
        adapter.setItemOnClickListener(onClickListener);
        adapter.setItemLongClickListener(longClickListener);
        parent.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                refreshVideos();
            }
        }).start();

        return v;


    }


    public void refresh() {
        adapter.clear();
        refreshVideos();

    }

    public void refreshVideos() {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.ALBUM, MediaStore.Video.Media.ARTIST};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, MediaStore.Video.Media.DISPLAY_NAME);
        String thumb[] = new String[]{MediaStore.Video.Thumbnails.DATA};
        int idIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID);
        int sizeIndex = cursor.getColumnIndex(MediaStore.Video.Media.SIZE);
        int displayIndex = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
        int durationIndex = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);
        int realPathIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
        int albumIndex = cursor.getColumnIndex(MediaStore.Video.Media.ALBUM);
        int artistIndex = cursor.getColumnIndex(MediaStore.Video.Media.ARTIST);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String realPath = cursor.getString(realPathIndex);
                File file = new File(realPath);
                if (file == null || !file.exists()) {
                    continue;
                }
                String thumbPath = getThumbnail(cursor.getInt(idIndex), thumb);
                final VideoItem item = new VideoItem(cursor.getString(displayIndex), cursor.getLong(sizeIndex), cursor.getLong(durationIndex), thumbPath, realPath, cursor.getString(artistIndex), cursor.getString(albumIndex));

                if (getActivity() == null) {
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addItem(item);
                    }
                });


            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }


    }

    public String getThumbnail(int id, String thumb[]) {

        if(getActivity()==null){
            return null;
        }
        MediaStore.Video.Thumbnails.getThumbnail(getActivity().getContentResolver(),
                id, MediaStore.Video.Thumbnails.MICRO_KIND, null);

        Cursor thumbCursor = null;
        try {


            if (getActivity() == null) {
                return null;
            }
            thumbCursor = getActivity().getContentResolver().query(
                    MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                    thumb, MediaStore.Video.Thumbnails.VIDEO_ID + " = "
                            + id, null, null);

            if (thumbCursor != null && thumbCursor.moveToFirst()) {
                String thumbPath = thumbCursor.getString(thumbCursor
                        .getColumnIndex(MediaStore.Video.Thumbnails.DATA));


                return thumbPath;
            }

        } finally {
            if (thumbCursor != null) {
                thumbCursor.close();
            }
        }

        return null;
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    protected void handleItemClick(int position, View view) {
        adapter.alterSelection(position);
        ((MediaPickerActivity) getActivity()).handleSelection(adapter.getItem(position));
    }

    @Override
    protected void deleteSelectedItem(int position) {
        adapter.removeItem(position);

    }

}
