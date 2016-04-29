package w3.com.okhttpdemo.mediagallerydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;

public abstract class CommonAdapter extends RecyclerView.Adapter {


    protected final Context mContext;
    protected List<SelectableItem> mItems = null;
    protected View.OnClickListener itemOnClickListener;
    protected View.OnLongClickListener itemLongClickListener;
    protected View emptyView;
    private int longSelectedPosition = -1;


    public CommonAdapter(Context context, List<SelectableItem> list) {
        mContext = context;
        mItems = list;
    }

    public CommonAdapter(Context context, View emptyView) {
        mContext = context;
        this.emptyView = emptyView;
    }

    public CommonAdapter(Context context) {
        mContext = context;
    }


    public void setItemOnClickListener(View.OnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public void setItemLongClickListener(View.OnLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public int getLongSelectedPosition() {
        return longSelectedPosition;
    }

    public void setLongSelectedPosition(int longSelectedPosition) {
        this.longSelectedPosition = longSelectedPosition;
    }


    public void delete(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public boolean getSelection(int position) {
        return mItems.get(position).isSelected();
    }

    public List<SelectableItem> getList() {
        return mItems;
    }


    public void addItem(SelectableItem item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public SelectableItem removeItem(int position) {
        SelectableItem item = mItems.remove(position);
        notifyItemRemoved(position);
        return item;
    }


    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void alterSelection(int pos) {
        getItem(pos).alterSelection();
        notifyItemChanged(pos);
    }

    @Override
    public int getItemCount() {

        int count = mItems == null ? 0 : mItems.size();
        handleEmptyView(count);
        return count;
    }

    private void handleEmptyView(int count) {
        if (emptyView == null) {
            return;
        }
        if (count <= 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    public SelectableItem getItem(int pos) {
        return mItems.get(pos);
    }

    public void refreshList(List<SelectableItem> list) {
        mItems = list;
        notifyDataSetChanged();
    }


    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void alterOnly(int pos) {
        getItem(pos).alterSelection();

    }

}