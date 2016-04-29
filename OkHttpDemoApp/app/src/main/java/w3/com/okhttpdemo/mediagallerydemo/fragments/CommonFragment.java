package w3.com.okhttpdemo.mediagallerydemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.io.File;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.adapter.CommonAdapter;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;

public abstract class CommonFragment extends Fragment {
    protected View fragmentView;

    protected CommonAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (fragmentView != null) {
            ViewParent vg = fragmentView.getParent();
            if (vg != null) {
                ((ViewGroup) vg).removeView(fragmentView);
            }
        }
    }

    public View getCachedView() {
        return fragmentView;
    }

    public void notifyUpdate() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();

        }
    }

    public void showFabIcon() {
        View view = getFabIcon();
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            if (!exists(position)) {
                return;
            }

            handleItemClick(position, view);


        }
    };

    private boolean exists(int position) {
        File file = new File(getPath(position));
        if (file == null || !file.exists()) {
            deleteItemFromPos(position);
            return false;
        }
        return true;
    }

    protected boolean isChecked(int position) {
        return adapter.getSelection(position);
    }

    protected abstract void handleItemClick(int position, View view);

    public View getFabIcon() {
        if (fragmentView == null) {
            return null;
        }
        return fragmentView.findViewById(R.id.fab_icon);

    }

    public void hideFabIcon() {
        View view = getFabIcon();
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    protected View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (Integer) v.getTag();
            if (!exists((Integer) v.getTag())) {
                return true;
            }
//            mediaActivity.showItemSelectionPopup(v);
//            getActivity().showDialog(position);
            setLongSelectedPosition(position);
            return true;
        }
    };

    public String getPath(int position) {
        return adapter.getItem(position).getPath();
    }

    public String getThumbPath(int position) {
        return adapter.getItem(position).getThumbPath();
    }

    public SelectableItem getSelectedItem(int position) {
        return adapter.getItem(position);
    }

    public void deleteItemFromPos(int position) {
        deleteSelectedItem(position);
        notifyUpdate();
    }


    protected abstract void deleteSelectedItem(int position);

    public abstract void refresh();

    public long getMediaDuration(int selectedItemPosition) {
        return adapter.getItem(selectedItemPosition).getMediaDuration();
    }

    public String getAlbum(int selectedItemPosition) {
        return adapter.getItem(selectedItemPosition).getAlbum();
    }

    public String getArtist(int selectedItemPosition) {
        return adapter.getItem(selectedItemPosition).getArtist();
    }


    public String getCaption(int selectedItemPosition) {
        return adapter.getItem(selectedItemPosition).getCaption();
    }

    public void setLongSelectedPosition(int position) {
        int prevPosition = adapter.getLongSelectedPosition();
        adapter.setLongSelectedPosition(position);

        if (position == -1) {
            adapter.notifyItemChanged(prevPosition);
        } else {
            adapter.notifyItemChanged(position);
        }

    }


}
