package w3.com.okhttpdemo.mediagallerydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;
import w3.com.okhttpdemo.mediagallerydemo.loader.ImageLoader;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;

public class VideoAdapter extends CommonAdapter {

    private ImageLoader loader;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iv;
        public final TextView title;
        public final TextView size;
        public final TextView duration;
        public final View check, publish;

        public SimpleViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            title = (TextView) view.findViewById(R.id.title);
            size = (TextView) view.findViewById(R.id.size);
            duration = (TextView) view.findViewById(R.id.duration);
            check = view.findViewById(R.id.check);
            publish = view.findViewById(R.id.publish);
        }
    }

    public VideoAdapter(Context context, List<SelectableItem> list) {
        super(context, list);
        loader = new ImageLoader(context, Util.getDeviceWidth(context) / 4);
        mItems = list;


    }


    public VideoAdapter(Context context) {
        super(context);
        loader = new ImageLoader(context, Util.getDeviceWidth(context) / 4);
        mItems = new ArrayList<SelectableItem>();


    }


    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.video_item, parent, false);
        view.setOnClickListener(itemOnClickListener);
        view.setOnLongClickListener(itemLongClickListener);
        return new SimpleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, final int position) {

        SimpleViewHolder holder = (SimpleViewHolder) holder2;

        holder.itemView.setTag(position);

        SelectableItem item = mItems.get(position);
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
//        if (PublishedMediaLoader.getInstance().containsMedia(item.getPath())) {
//            holder.publish.setVisibility(View.VISIBLE);
//        } else {
//            holder.publish.setVisibility(View.GONE);
//        }
//        loader.loadBitmap(item.getThumbPath(), holder.iv);
//
//        if (item.getThumbPath() == null || item.getThumbPath().trim().equals("")
//                || item.getThumbPath().trim().equals(YoCommon.NO_THUMB_INDICATOR)) {
//            String type = Util.getMediaType(item.getPath());
//            if (type.equals(YoCommon.FILE_TYPE_MUSIC)) {
//                holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_music));
//            } else if (type.equals(YoCommon.FILE_TYPE_VIDEO)) {
//                holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_video));
//            }
//        }
//
//        if(item.getThumbPath() == null && Util.getMediaType(item.getPath()).equals(YoCommon.FILE_TYPE_MUSIC)){
//            holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_music));
//        }else {
//            holder.iv.setImageDrawable(holder.iv.getContext().getResources().getDrawable(R.drawable.default_video));
//        }

        holder.title.setText(item.getCaption());
        holder.duration.setText(Util.getFormattedDuration(item.getMediaDuration()));
        holder.size.setText(Util.getFileSize(item.getSize()));


    }

    private String getString(int count) {
        if (count == 0) {
            return "Photo";
        }
        return "Photos";
    }


}