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
import w3.com.okhttpdemo.mediagallerydemo.loader.AppIconLoader;
import w3.com.okhttpdemo.mediagallerydemo.loader.ImageLoader;
import w3.com.okhttpdemo.mediagallerydemo.model.RecentItem;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;


public class RecentAdapter extends CommonAdapter {


    private AppIconLoader appIconloader;


    private ImageLoader loader;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iv;
        public final TextView title;
        public final TextView count;
        public final View check, publish;


        public SimpleViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            check = view.findViewById(R.id.check);
            publish = view.findViewById(R.id.publish);
        }
    }

    public RecentAdapter(Context context, List<SelectableItem> list, View emptyView) {
        super(context, list);
        this.emptyView = emptyView;


    }

    public RecentAdapter(Context context, View emptyView) {
        super(context, emptyView);
        loader = new ImageLoader(context, Util.getDeviceWidth(context) / 4);
        appIconloader = new AppIconLoader(context, Util.getDeviceWidth(context) / 4);

        this.emptyView = emptyView;

    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recent_item, parent, false);
        if (itemOnClickListener != null) {
            view.setOnClickListener(itemOnClickListener);
        }

        if (itemLongClickListener != null) {
            view.setOnLongClickListener(itemLongClickListener);
        }

        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, final int position) {

        SimpleViewHolder holder = (SimpleViewHolder) holder2;
        holder.itemView.setTag(position);
        RecentItem item = (RecentItem) mItems.get(position);

        if (item.isSelected()) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.color_media_selected));
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.check.setVisibility(View.GONE);
        }

        if (position == getLongSelectedPosition()) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.color_media_selected));

        }

//
//        if (PublishedMediaLoader.getInstance().containsMedia(item.getPath())) {
//            holder.publish.setVisibility(View.VISIBLE);
//        } else {
//            holder.publish.setVisibility(View.GONE);
//        }
//
//
//        if (Util.getMediaType(item.getPath()).equals(YoCommon.FILE_TYPE_APP)) {
//            appIconloader.loadBitmapFromPath(item.getPath(), (holder).iv);
//        } else {
//            loader.loadBitmap(item.getThumbPath(), holder.iv);
//        }
//
//        //Set default thumb
//        if (item.getThumbPath() == null || item.getThumbPath().equalsIgnoreCase(YoCommon.NO_THUMB_INDICATOR)) {
//            String type = Util.getMediaType(item.getPath());
//            if (type.equals(YoCommon.FILE_TYPE_APP)) {
//                holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_app));
//            } else if (type.equals(YoCommon.FILE_TYPE_MUSIC)) {
//                holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_music));
//            } else if (type.equals(YoCommon.FILE_TYPE_PHOTO)) {
//                holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_image));
//            } else if (type.equals(YoCommon.FILE_TYPE_VIDEO)) {
//                holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_video));
//            } else if (type.equals(YoCommon.FILE_TYPE_ZIP)) {
//                holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_zip));
//            } else {
//                holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_file));
//            }
//        }

        holder.title.setText(item.getCaption());
        holder.count.setText(Util.getFileSize(item.getSize()));


    }


}