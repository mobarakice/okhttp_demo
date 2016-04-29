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
import w3.com.okhttpdemo.mediagallerydemo.model.FileInfo;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;

public class FileChildAdapter extends CommonAdapter {


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

    public FileChildAdapter(Context context, List<SelectableItem> list) {
        super(context,list);


    }


    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.file_item, parent, false);
        view.setOnClickListener(itemOnClickListener);
        view.setOnLongClickListener(itemLongClickListener);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, final int position) {


        SimpleViewHolder holder = (SimpleViewHolder)holder2;
        holder.itemView.setTag(position);
        FileInfo item = (FileInfo) mItems.get(position);

        if (item.isSelected()) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.off_white));
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.check.setVisibility(View.GONE);
        }

        if (position == getLongSelectedPosition()) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.color_media_selected));

        }

        //loader.loadBitmap(null, holder.iv);
        holder.title.setText(item.getCaption());
        holder.count.setText(Util.getFileSize(item.getSize()));


    }

}