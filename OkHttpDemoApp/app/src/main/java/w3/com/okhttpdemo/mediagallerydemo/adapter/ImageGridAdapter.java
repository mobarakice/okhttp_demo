package w3.com.okhttpdemo.mediagallerydemo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;
import w3.com.okhttpdemo.mediagallerydemo.loader.ImageLoader;
import w3.com.okhttpdemo.mediagallerydemo.model.Image;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;


public class ImageGridAdapter extends CommonAdapter {


    private ImageLoader loader;
    static int dimension;




    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iv;
        public final View check, publish;

        public SimpleViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            iv.getLayoutParams().width = dimension;
            iv.getLayoutParams().height = dimension;
            check = view.findViewById(R.id.check);
            publish = view.findViewById(R.id.publish);
        }
    }

    public ImageGridAdapter(Context context, List<SelectableItem> list) {
        super(context,list);
        setDimenstion(context);

        loader = new ImageLoader(context, dimension);

    }


    public ImageGridAdapter(Context context) {
        super(context);
        setDimenstion(context);
        loader = new ImageLoader(context, dimension);
        mItems = new ArrayList<SelectableItem>();


    }

    private void setDimenstion(Context context) {
        dimension = Util.getDeviceWidth(context) / 4;
        int height = Util.getDeviceHeight(context) / 4;
        if (dimension > height) {
            dimension = height;
        }

    }


    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.camera_item, parent, false);

        view.setOnClickListener(itemOnClickListener);
        view.setOnLongClickListener(itemLongClickListener);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, final int position) {
        SimpleViewHolder holder = (SimpleViewHolder)holder2;
        Image item = (Image) mItems.get(position);
        holder.itemView.setTag(position);


        if (item.isSelected()) {

            holder.check.setVisibility(View.VISIBLE);
        } else {

            holder.check.setVisibility(View.GONE);
        }


        loader.loadBitmap(item.getThumbPath(), holder.iv);


    }




}