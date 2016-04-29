package w3.com.okhttpdemo.mediagallerydemo.fragments;

import android.database.Cursor;
import android.net.Uri;
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

public class MusicFragment extends CommonFragment {

    private RecyclerView parent;

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = fragmentView;
        if (v != null) {
            return v;
        }

        v = inflater.inflate(R.layout.fragment_music, container, false);
        fragmentView = v;


        parent = (RecyclerView) v.findViewById(R.id.rv_parent);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        parent.setLayoutManager(layoutManager);
        adapter = new VideoAdapter(getContext());
        adapter.setItemOnClickListener(onClickListener);
        adapter.setItemLongClickListener(longClickListener);

        parent.setAdapter(adapter);


        new Thread(new Runnable() {
            @Override
            public void run() {
                setAudioList();
            }
        }).start();


        return v;

    }

    public void setAudioList() {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.ALBUM_ID};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, MediaStore.Audio.Media.DISPLAY_NAME);

        int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int sizeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        int displayIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        int durationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int albumArtIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int realPathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                String realPath = cursor.getString(realPathIndex);
                File file = new File(realPath);
                if (file == null || !file.exists()) {
                    continue;
                }


                String path = getThumbnail(cursor.getInt(albumArtIndex));


                final VideoItem item = new VideoItem(cursor.getString(displayIndex), cursor.getLong(sizeIndex), cursor.getLong(durationIndex), path, realPath, cursor.getString(artistIndex), cursor.getString(albumIndex));
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

     /*   mediaActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    progressBar.setVisibility(View.GONE);
                    adapter.refreshList(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/


    }


    public String getThumbnail(int albumId) {
        if(getActivity()==null){
            return null;
        }
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART}, MediaStore.Audio.Albums._ID + "=" + albumId, null, null);

        int indexAlbumArt = cursor.getColumnIndex("album_art");
        if (cursor != null && cursor.moveToFirst()) {

            String path = cursor.getString(indexAlbumArt);
            cursor.close();
            return path;

        }
        return null;
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

    @Override
    public void refresh() {

    }
}
