package w3.com.okhttpdemo.mediagallerydemo.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.Utility.Util;
import w3.com.okhttpdemo.mediagallerydemo.loader.AppIconLoader;
import w3.com.okhttpdemo.mediagallerydemo.model.Application;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;

public class ApplicationAdapter extends CommonAdapter {


    private PackageManager pManager;
    private static int dimension;
    private AppIconLoader loader;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iv;
        public final TextView title;
        public final TextView size;
        public final TextView duration;
        public final View check, publish;

        public SimpleViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            iv.getLayoutParams().width = dimension;
            iv.getLayoutParams().height = dimension;
            title = (TextView) view.findViewById(R.id.title);
            size = (TextView) view.findViewById(R.id.size);
            duration = (TextView) view.findViewById(R.id.duration);
            check = view.findViewById(R.id.check);
            publish = view.findViewById(R.id.publish);
        }
    }

    public ApplicationAdapter(Context context, List<SelectableItem> list) {
        super(context, list);
        setDimenstion(context);
        loader = new AppIconLoader(context, dimension);
        pManager = context.getPackageManager();

    }

    private void setDimenstion(Context context) {
        dimension = Util.getDeviceWidth(context) / 4;
        int height = Util.getDeviceHeight(context) / 4;
        if (dimension > height) {
            dimension = height;
        }
        dimension = dimension - Util.dpToPx(20, context);
    }


    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.app_item, parent, false);
        view.setOnClickListener(itemOnClickListener);
        view.setOnLongClickListener(itemLongClickListener);
        return new SimpleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, final int position) {

        SimpleViewHolder holder = (SimpleViewHolder) holder2;
        holder.itemView.setTag(position);

        Application app = (Application) mItems.get(position);
        if (app.isSelected()) {

            holder.check.setVisibility(View.VISIBLE);
        } else {


            holder.check.setVisibility(View.GONE);
        }
        if (app.getCaption() == null) {
            app.setCaption(app.getPackageInfo().applicationInfo.loadLabel(pManager).toString());
        }
        if (app.getPack() == null) {
            app.setPack(app.getPackageInfo().packageName);
        }

        if (app.getSize() == -1) {
            try {
                app.setSize(new File(app.getPackageInfo().applicationInfo.publicSourceDir).length());
            } catch (Exception e) {
                app.setSize(0);
            }
        }

        holder.title.setText(app.getCaption());

        holder.size.setText(Util.getFileSize(app.getSize()));
        loader.loadBitmap(app.getPack(), holder.iv);


    }


}