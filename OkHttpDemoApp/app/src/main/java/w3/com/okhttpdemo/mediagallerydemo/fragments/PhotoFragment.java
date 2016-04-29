package w3.com.okhttpdemo.mediagallerydemo.fragments;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.MediaPickerActivity;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;
import w3.com.okhttpdemo.mediagallerydemo.adapter.ImageGridAdapter;
import w3.com.okhttpdemo.mediagallerydemo.adapter.PhotoParentAdapter;
import w3.com.okhttpdemo.mediagallerydemo.model.Image;
import w3.com.okhttpdemo.mediagallerydemo.model.PhotoItem;


public class PhotoFragment extends CommonFragment implements View.OnClickListener {
    private static final int FOLDERS_ALL = 0;
    private static final int FOLDERS_SUB = 1;
    private int currentFolderHieararchy = FOLDERS_ALL;
    private RecyclerView parent, child;
    private static final int NUM_COLUMNS = 4;
    private ProgressBar progressBar;

    private static final int POSITION_UNDEFINED = -1;
    private int parentPostion = POSITION_UNDEFINED;

    private PhotoParentAdapter parentAdapter;
    TextView textViewFolderName;
    TextView textViewSubFolderName;

    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
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
        v = inflater.inflate(R.layout.fragment_photos, container, false);
        fragmentView = v;

        textViewFolderName = (TextView) v.findViewById(R.id.textView_folder_name);
        textViewSubFolderName = (TextView) v.findViewById(R.id.folder_sub);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_view);

        parent = (RecyclerView) v.findViewById(R.id.rv_parent);
        child = (RecyclerView) v.findViewById(R.id.rv_child);

        parent.setLayoutManager(new LinearLayoutManager(getContext()));
        parentAdapter = new PhotoParentAdapter(getContext());
        parentAdapter.setItemOnClickListener(parentClickListener);
        parent.setAdapter(parentAdapter);

        child.setHasFixedSize(true);
        child.setLayoutManager(new GridLayoutManager(getContext(), NUM_COLUMNS));
        textViewFolderName.setOnClickListener(this);


        // pView.start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                refreshParent();
            }
        }).start();


        return v;


    }


    private View.OnClickListener parentClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            parentPostion = (Integer) view.getTag();
            refreshClild();


        }
    };


    public void refresh() {
        parentAdapter.clear();
        refreshParent();

        if (child.getVisibility() == View.VISIBLE) {

            refreshClild();
        }
    }


    private void refreshClild() {
        adapter = new ImageGridAdapter(getContext());
        adapter.setItemOnClickListener(onClickListener);
        parent.setVisibility(View.GONE);
        child.setAdapter(adapter);
        child.setVisibility(View.VISIBLE);
        // getView().findViewById(R.id.arrow).setVisibility(View.VISIBLE);
        textViewFolderName.setBackgroundResource(R.drawable.all_folder_bg);
        textViewFolderName.setTextColor(getResources().getColor(R.color.message_content_text_color));

        textViewSubFolderName.setText(parentAdapter.getItem(parentPostion).getCaption());
        // progressBar.setVisibility(View.VISIBLE);
        adapter.setItemLongClickListener(longClickListener);
        adapter.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                generateListForBucket(parentAdapter.getItem(parentPostion).getCaption());


            }
        }).start();
    }

    public void generateListForBucket(String bucket) {

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "='" + bucket + "'",
                null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");


        int columnImageId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int columnActualFilePath = cursor.getColumnIndex(MediaStore.Images.Media.DATA);


        if (cursor != null && cursor.moveToFirst()) {
            do {

                String realPath = cursor.getString(columnActualFilePath);
                File file = new File(realPath);
                if (file == null || !file.exists()) {

                    continue;
                }


                String thumbnailPath = Util.getThumbnailPath(cursor.getInt(columnImageId), getActivity());

                if (thumbnailPath == null) {
                    thumbnailPath = realPath;

                }

                final Image image = new Image(thumbnailPath, realPath);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addItem(image);
                    }
                });

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

    }


    public void adaptAllFolderClick() {
        if (child.getVisibility() == View.VISIBLE) {
            child.setVisibility(View.GONE);
            parent.setVisibility(View.VISIBLE);
        }


//        getView().findViewById(R.id.arrow).setVisibility(View.GONE);
        textViewFolderName.setBackgroundResource(0);
        textViewFolderName.setTextColor(getResources().getColor(R.color.fab_color));
        textViewSubFolderName.setText("");
        if (parentPostion != POSITION_UNDEFINED) {
            PhotoItem item = parentAdapter.getItem(parentPostion);
            int count = item.getCount();
            if (!Util.validatePhotItem(item, getActivity())) {
                parentAdapter.removeItem(parentPostion);
            } else if (count != item.getCount()) {
                parentAdapter.notifyItemChanged(parentPostion);
            }
            parentPostion = POSITION_UNDEFINED;
        }

    }


    public boolean handleBackPressed() {
        if (child.getVisibility() == View.VISIBLE) {
            adaptAllFolderClick();
            return true;
        }
        return false;
    }


    public void refreshParent() {


        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String selection = "1) GROUP BY (" + MediaStore.Images.Media.BUCKET_DISPLAY_NAME;

        String[] projection = {
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, "count(" + MediaStore.Images.Media._ID + ") as _count", MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};


        Cursor cursor = getActivity().getContentResolver().query(uri, projection, selection,
                null, MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " COLLATE NOCASE asc," + MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

        int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int columnActualFilePath = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        int columnBucketIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int countIndex = cursor.getColumnIndex("_count");


        if (cursor != null && cursor.moveToFirst()) {

            do {
                String realPath = cursor.getString(columnActualFilePath);

                String folder = cursor.getString(columnBucketIndex);

                String thumbnailPath = Util.getThumbnailPath(cursor.getInt(idIndex), getContext());

                if (thumbnailPath == null) {
                    thumbnailPath = realPath;

                }


                final PhotoItem item = new PhotoItem(folder, cursor.getInt(countIndex), thumbnailPath);
                if (Util.validatePhotItem(item, getContext())) {
                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parentAdapter.addItemToList(item);
                        }
                    });


                }


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
                    parentAdapter.movePriorityItemToTop();


                    //  progressBar.setVisibility(View.GONE);
                    //  parentAdapter.refreshList(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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
    public void onClick(View v) {
        adaptAllFolderClick();
    }
}
