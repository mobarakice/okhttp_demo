package w3.com.okhttpdemo.mediagallerydemo.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.MediaPickerActivity;
import w3.com.okhttpdemo.mediagallerydemo.adapter.FileChildAdapter;
import w3.com.okhttpdemo.mediagallerydemo.adapter.FileParentAdapter;
import w3.com.okhttpdemo.mediagallerydemo.model.FileInfo;
import w3.com.okhttpdemo.mediagallerydemo.model.FileParentItem;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;


public class FilesFragment extends CommonFragment implements View.OnClickListener {

    private RecyclerView parent, child;


    private FileParentAdapter parentAdapter;

    private List<SelectableItem> docs, apks, ebooks, zips;

    private static final int INDEX_APK = 0;
    private static final int INDEX_DOC = 1;
    private static final int INDEX_EBOOK = 2;
    private static final int INDEX_ZIP = 3;

    private static final int POSITION_UNDEFINED = -1;
    private int parentPostion = POSITION_UNDEFINED;

    TextView textViewFolderName;
    TextView textViewSubFolderName;


    public static FilesFragment newInstance() {
        FilesFragment fragment = new FilesFragment();
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
        v = inflater.inflate(R.layout.fragment_files, container, false);
        fragmentView = v;

        textViewFolderName = (TextView) v.findViewById(R.id.textView_folder_name);
        textViewSubFolderName = (TextView) v.findViewById(R.id.folder_sub);

        textViewFolderName.setOnClickListener(this);


        parent = (RecyclerView) v.findViewById(R.id.rv_parent);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parent.setLayoutManager(layoutManager);
        child = (RecyclerView) v.findViewById(R.id.rv_child);


        final LinearLayoutManager layoutManagerChild = new LinearLayoutManager(getContext());
        layoutManagerChild.setOrientation(LinearLayoutManager.VERTICAL);

        child.setLayoutManager(layoutManagerChild);


        List<FileParentItem> list = new ArrayList<FileParentItem>();

        docs = new ArrayList<SelectableItem>();
        apks = new ArrayList<SelectableItem>();
        ebooks = new ArrayList<SelectableItem>();
        zips = new ArrayList<SelectableItem>();
        list.add(new FileParentItem("APK", apks, ".apk"));
        list.add(new FileParentItem("Documents", docs, ".doc, .docx, .ppt, .xls, .xlsx"));

        list.add(new FileParentItem("eBooks", ebooks, ".pdf"));
        list.add(new FileParentItem("ZIP Files", zips, ".zip, .rar"));


        parentAdapter = new FileParentAdapter(getActivity(), list);
        parentAdapter.setItemOnClickListener(parentClickListener);
        parent.setAdapter(parentAdapter);


        new Thread(new Runnable() {

            @Override
            public void run() {

                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                listFile(dir);

            }
        }).start();


        return v;


    }

    private View.OnClickListener parentClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            parentPostion = (Integer) view.getTag();
            adapter = new FileChildAdapter(getActivity(), parentAdapter.getItem(parentPostion).getFiles());
            adapter.setItemOnClickListener(onClickListener);
            adapter.setItemLongClickListener(longClickListener);
            parent.setVisibility(View.GONE);
            child.setAdapter(adapter);
            child.setVisibility(View.VISIBLE);
            //   getView().findViewById(R.id.arrow).setVisibility(View.VISIBLE);
            textViewFolderName.setBackgroundResource(R.drawable.all_folder_bg);
            textViewFolderName.setTextColor(getResources().getColor(R.color.message_content_text_color));

            textViewSubFolderName.setText(parentAdapter.getItem(parentPostion).getCaption());


        }
    };


    public void adaptAllFilesClick() {
        child.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
        // getView().findViewById(R.id.arrow).setVisibility(View.GONE);
        textViewFolderName.setBackgroundResource(0);
        textViewFolderName.setTextColor(getResources().getColor(R.color.fab_color));

        textViewSubFolderName.setText("");

        if (parentPostion != POSITION_UNDEFINED) {

            parentAdapter.notifyItemChanged(parentPostion);

        }
        parentPostion = POSITION_UNDEFINED;


    }

    public boolean handleBackPressed() {
        if (child.getVisibility() == View.VISIBLE) {
            adaptAllFilesClick();
            return true;
        }
        return false;
    }


    public void listFile(File file) {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            if (!file.canRead()) {
                return;
            }
            File files[] = file.listFiles();
            if (files == null) {
                return;
            }
            for (File f : files) {
                listFile(f);
            }

        }
        String name = file.getName().toLowerCase();

        if (name.endsWith(".doc") || name.endsWith(".docx") || name.endsWith(".ppt") || name.endsWith(".pptx") || name.endsWith("xls") || name.endsWith(".xlsx")) {

            docs.add(new FileInfo(file.getAbsolutePath(), file.length(), file.getName()));
            notifyUpdate(INDEX_DOC);

            return;

        }

        if (name.endsWith(".apk")) {

            apks.add(new FileInfo(file.getAbsolutePath(), file.length(), file.getName()));
            notifyUpdate(INDEX_APK);
            return;

        }

        if (name.endsWith(".pdf")) {

            ebooks.add(new FileInfo(file.getAbsolutePath(), file.length(), file.getName()));
            notifyUpdate(INDEX_EBOOK);
            return;

        }

        if (name.endsWith(".zip") || name.endsWith(".rar")) {

            zips.add(new FileInfo(file.getAbsolutePath(), file.length(), file.getName()));
            notifyUpdate(INDEX_ZIP);
            return;

        }


    }

    private void notifyUpdate(final int indexDoc) {
        if(getActivity()==null){
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parentAdapter.notifyItemChanged(indexDoc);
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
    public void refresh() {

    }

    @Override
    public void onClick(View v) {
        adaptAllFilesClick();
    }
}
