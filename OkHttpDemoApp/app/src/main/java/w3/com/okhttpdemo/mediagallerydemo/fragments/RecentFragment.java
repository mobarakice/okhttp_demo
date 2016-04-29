package w3.com.okhttpdemo.mediagallerydemo.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.MediaPickerActivity;
import w3.com.okhttpdemo.mediagallerydemo.adapter.RecentAdapter;

public class RecentFragment extends CommonFragment {
//    private DBManager dbManager;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public static RecentFragment newInstance() {
        RecentFragment fragment = new RecentFragment();
        return fragment;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = fragmentView;
        if (view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_recent, container, false);
        fragmentView = view;

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = (ProgressBar) view.findViewById(R.id.progress_view);


//        dbManager = CommonObjectClasss.getDatabase(mediaActivity);
        adapter = new RecentAdapter(getContext(), view.findViewById(R.id.view_empty));
        adapter.setItemOnClickListener(onClickListener);
        adapter.setItemLongClickListener(longClickListener);

        recyclerView.setAdapter(adapter);

        refresh();

        return view;
    }
    @Override
    protected void handleItemClick(int position, View view) {
        adapter.alterSelection(position);
        ((MediaPickerActivity) getActivity()).handleSelection(adapter.getItem(position));

    }

    @Override
    protected void deleteSelectedItem(int position) {
//        dbManager.deleteRecent(((RecentItem) adapter.getItem(position)).getFileIdentity());
        adapter.removeItem(position);
    }

    @Override
    public void refresh() {
//        if (dbManager == null) {
//            return;
//        }
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                final List<SelectableItem> list = dbManager.getAllRecentItems();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            progressBar.setVisibility(View.GONE);
//                            adapter.refreshList(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }).start();


    }

}
