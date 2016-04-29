package w3.com.okhttpdemo.mediagallerydemo.fragments;


import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.MediaPickerActivity;
import w3.com.okhttpdemo.mediagallerydemo.Utility.DateUtil;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;
import w3.com.okhttpdemo.mediagallerydemo.adapter.ImageGridAdapter;
import w3.com.okhttpdemo.mediagallerydemo.adapter.SectionedGridRecyclerViewAdapter;
import w3.com.okhttpdemo.mediagallerydemo.model.Image;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;

public class CameraFragment extends CommonFragment {
    private RecyclerView recyclerView;
    private Map<String, List<Image>> imageMap;
    private ProgressBar progressBar;
    private static final int NUM_COLUMNS = 4;
    private SectionedGridRecyclerViewAdapter sectionedAdapter;


    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();

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


        v = inflater.inflate(R.layout.fragment_camera, container, false);
        fragmentView = v;

        progressBar = (ProgressBar) v.findViewById(R.id.progress_view);

        imageMap = new LinkedHashMap<String, List<Image>>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),NUM_COLUMNS));
        refresh();
        return v;
    }

    public void refresh() {
        imageMap.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {

                generateImageMap();
            }
        }).start();

    }


    public void handleItemClick(int position, View view) {
        adapter.alterOnly(position);
        ((MediaPickerActivity) getActivity()).handleSelection(adapter.getItem(position));

    }


    private void setAdapter() {

        List<SelectableItem> items = new ArrayList<SelectableItem>();

        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<SectionedGridRecyclerViewAdapter.Section>();


        int cumulativeSum = 0;

        for (Map.Entry<String, List<Image>> entry : imageMap.entrySet()) {
            String key = entry.getKey();
            List<Image> list = imageMap.get(key);

            sections.add(new SectionedGridRecyclerViewAdapter.Section(cumulativeSum, key));
            cumulativeSum += list.size();
            items.addAll(list);

        }

        adapter = new ImageGridAdapter(getActivity(), items);
        adapter.setItemOnClickListener(onClickListener);
        adapter.setItemLongClickListener(longClickListener);
        SectionedGridRecyclerViewAdapter sectionedAdapter = new
                SectionedGridRecyclerViewAdapter(getActivity(), R.layout.section, R.id.section_text, recyclerView, adapter);
        setSections(sections);
        //Apply this adapter to the RecyclerView
        recyclerView.setAdapter(sectionedAdapter);

    }


    private void setSections(List<SectionedGridRecyclerViewAdapter.Section> sections) {
        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];

        sectionedAdapter.setSections(sections.toArray(dummy));


    }


    public void generateImageMap() {
        int columnImageId, columnActualFilePath, columnIndexDate;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN};


        if(getActivity()==null){
            return;
        }
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "='Camera'",
                null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");


        columnIndexDate = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
        columnImageId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        columnActualFilePath = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

        if (cursor != null && cursor.moveToFirst()) {
            handlePreference(cursor.getString(columnActualFilePath));
            do {
                String realPath = cursor.getString(columnActualFilePath);
                File file = new File(realPath);
                if (file == null || !file.exists()) {

                    continue;
                }
                String dateString = DateUtil.getDateString(cursor.getLong(columnIndexDate));

                List<Image> list = imageMap.get(dateString);
                if (list == null) {
                    list = new ArrayList<Image>();
                    imageMap.put(dateString, list);
                }


                String thumbnailPath = Util.getThumbnailPath(cursor.getInt(columnImageId), getActivity());

                if (thumbnailPath == null) {
                    thumbnailPath = realPath;

                }
                list.add(new Image(thumbnailPath, realPath, dateString));
            } while (cursor.moveToNext());
        }


        if (cursor != null) {
            cursor.close();
            cursor = null;
        }


        if(getActivity()==null){
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    progressBar.setVisibility(View.GONE);
                    setAdapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void handlePreference(String string) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (preferences.getString("camera_path", null) != null) {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("camera_path", new File(string).getParentFile().getAbsolutePath());
        editor.commit();
    }

    @Override
    public void notifyUpdate() {
        if (adapter != null) {
            sectionedAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void deleteSelectedItem(int position) {

        SelectableItem image = adapter.removeItem(position);
        refreshMap((Image) image);
        refreshSections();


    }

    private void refreshMap(Image image) {
        if (image == null) {
            return;
        }
        List<Image> list = imageMap.get(image.getLastModifiedDate());


        if (list == null || list.size() == 0) {
            imageMap.remove(image.getLastModifiedDate());
            return;
        }
        list.remove(image);

        if (list.size() == 0) {
            imageMap.remove(image.getLastModifiedDate());
        }
    }

    private void refreshSections() {
        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<SectionedGridRecyclerViewAdapter.Section>();
        //Sections


        int cumulativeSum = 0;

        for (Map.Entry<String, List<Image>> entry : imageMap.entrySet()) {
            String key = entry.getKey();
            List<Image> list = imageMap.get(key);
            if (list == null || list.size() == 0) {
                continue;
            }

            sections.add(new SectionedGridRecyclerViewAdapter.Section(cumulativeSum, key));
            cumulativeSum += list.size();

        }
        setSections(sections);

    }

    @Override
    public void setLongSelectedPosition(int longSelectedPosition) {

    }


}
