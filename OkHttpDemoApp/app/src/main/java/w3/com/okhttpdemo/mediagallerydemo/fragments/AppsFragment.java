package w3.com.okhttpdemo.mediagallerydemo.fragments;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.MediaPickerActivity;
import w3.com.okhttpdemo.mediagallerydemo.adapter.ApplicationAdapter;
import w3.com.okhttpdemo.mediagallerydemo.model.Application;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;


public class AppsFragment extends CommonFragment {
    private RecyclerView recyclerView;
    private static final int NUM_COLUMNS = 4;

    private PackageManager pm;


    public static AppsFragment newInstance() {
        AppsFragment fragment = new AppsFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = fragmentView;
        if (view != null) {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_apps, container, false);

        fragmentView = view;

        pm = getActivity().getPackageManager();
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), NUM_COLUMNS);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ApplicationAdapter(getActivity(), getInstalledApps());
        recyclerView.setAdapter(adapter);
        adapter.setItemOnClickListener(onClickListener);

        adapter.setItemLongClickListener(longClickListener);
        return view;
    }

    @Override
    protected void handleItemClick(int position, View view) {
        adapter.alterSelection(position);
        ((MediaPickerActivity) getActivity()).handleSelection(adapter.getItem(position));


    }

    public String getPack(int position) {
        return ((Application) adapter.getItem(position)).getPack();
    }


    @Override
    protected void deleteSelectedItem(int position) {
        adapter.delete(position);

    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }


    private List<SelectableItem> getInstalledApps() {


        List<SelectableItem> apps = new ArrayList<SelectableItem>();
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);
        int size = packs.size();

        for (int i = 0; i < size; i++) {
            PackageInfo p = packs.get(i);
            if (isSystemPackage(p)) {
                continue;
            }

            apps.add(new Application(p.applicationInfo.loadLabel(pm).toString(), p));


        }
        Collections.sort(apps, new Comparator<SelectableItem>() {
            @Override
            public int compare(SelectableItem o1, SelectableItem o2) {
                return o1.getCaption().compareToIgnoreCase(o2.getCaption());
            }
        });
        return apps;
    }

    private boolean isSystemPackage(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public int getPositionOfYo() {
        List<SelectableItem> list = adapter.getList();
        if (list == null) {
            return -1;
        }
        String pack = getActivity().getPackageName();
        for (int i = 0; i < list.size(); i++) {
            Application app = (Application) list.get(i);
            if (pack.equals(app.getPackageInfo().packageName)) {
                return i;
            }
        }
        return -1;
    }

}
