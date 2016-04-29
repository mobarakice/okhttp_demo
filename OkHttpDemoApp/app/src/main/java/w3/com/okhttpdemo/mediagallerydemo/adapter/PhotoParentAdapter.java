package w3.com.okhttpdemo.mediagallerydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;
import w3.com.okhttpdemo.mediagallerydemo.loader.ImageLoader;
import w3.com.okhttpdemo.mediagallerydemo.model.PhotoItem;


public class PhotoParentAdapter extends RecyclerView.Adapter<PhotoParentAdapter.SimpleViewHolder> {
    private static final String YO_CAPTURED = "YO! Captured Images";
    private static final String YO_RECEIVED = "YO! Received Images";


    private final Context mContext;
    private List<PhotoItem> mItems;
    private View.OnClickListener itemOnClickListener;

    private ImageLoader loader;

    public void setItemOnClickListener(View.OnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public void addItemToList(PhotoItem item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iv;
        public final TextView title;
        public final TextView count;

        public SimpleViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
        }
    }

    public PhotoParentAdapter(Context context, List<PhotoItem> list) {
        loader = new ImageLoader(context, Util.getDeviceWidth(context) / 4);
        mContext = context;
        mItems = list;


    }

    public void movePriorityItemToTop() {
        moveToTop(YO_RECEIVED);
        moveToTop(YO_CAPTURED);
        notifyDataSetChanged();
    }

    public PhotoParentAdapter(Context context) {
        loader = new ImageLoader(context, Util.getDeviceWidth(context) / 4);
        mContext = context;
        mItems = new ArrayList<PhotoItem>();

    }

    public void refreshList(List<PhotoItem> list) {
        mItems = list;
        notifyDataSetChanged();
    }


    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false);
        view.setOnClickListener(itemOnClickListener);
        return new SimpleViewHolder(view);
    }

    private void moveToTop(String yo) {


        int index = mItems.indexOf(new PhotoItem(yo));

        if (index != -1) {
            mItems.add(0, mItems.remove(index));
        }


    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {

        holder.itemView.setTag(position);
        PhotoItem item = mItems.get(position);
        Log.e("Image", " Path = " + item.getPath());
        if(item.getPath() != null && new File(item.getPath()).exists()){
            loader.loadBitmap(item.getPath(), holder.iv);
        }else{
            holder.iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_image));
        }

        holder.title.setText(item.getCaption());
        holder.count.setText(item.getCount() + " " + getString(item.getCount()));


    }

    private String getString(int count) {
        if (count == 1) {
            return "Photo";
        }
        return "Photos";
    }




    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {

        return mItems == null ? 0 : mItems.size();
    }

    public PhotoItem getItem(int pos) {
        return mItems.get(pos);
    }


}