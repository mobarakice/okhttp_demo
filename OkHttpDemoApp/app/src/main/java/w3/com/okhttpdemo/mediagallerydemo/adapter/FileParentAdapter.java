package w3.com.okhttpdemo.mediagallerydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;
import w3.com.okhttpdemo.mediagallerydemo.loader.ImageLoader;
import w3.com.okhttpdemo.mediagallerydemo.model.FileParentItem;

public class FileParentAdapter extends RecyclerView.Adapter<FileParentAdapter.SimpleViewHolder> {


    private final Context mContext;
    private final List<FileParentItem> mItems;
    private View.OnClickListener itemOnClickListener;

    private ImageLoader loader;

    public void setItemOnClickListener(View.OnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
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

    public FileParentAdapter(Context context, List<FileParentItem> list) {
        loader = new ImageLoader(context, Util.getDeviceWidth(context) / 4);
        mContext = context;
        mItems = list;


    }


    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.file_parent_item, parent, false);
        view.setOnClickListener(itemOnClickListener);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {

        holder.itemView.setTag(position);
        FileParentItem item = mItems.get(position);
        loader.loadBitmap(null, holder.iv);

        //Set default thumb
        if(item.getCaption().equalsIgnoreCase("APK")){
            holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_app));
        }else if(item.getCaption().equalsIgnoreCase("ZIP Files")){
            holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_zip));
        }else if(item.getCaption().equalsIgnoreCase("eBooks") || item.getCaption().equalsIgnoreCase("Documents")){
            holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_file));
        }
        holder.title.setText(item.getCaption() + " (" + item.getFiles().size() + ")");
        holder.count.setText(item.getExtension());


    }



    public void addItem(int position) {

        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {

        return mItems == null ? 0 : mItems.size();
    }

    public FileParentItem getItem(int pos) {
        return mItems.get(pos);
    }


}