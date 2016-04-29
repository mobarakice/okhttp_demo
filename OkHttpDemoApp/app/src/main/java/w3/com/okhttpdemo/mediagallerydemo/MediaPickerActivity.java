package w3.com.okhttpdemo.mediagallerydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import w3.com.okhttpdemo.R;
import w3.com.okhttpdemo.mediagallerydemo.Utility.ItemSelectionAnimation;
import w3.com.okhttpdemo.mediagallerydemo.adapter.ViewPagerAdapter;
import w3.com.okhttpdemo.mediagallerydemo.model.SelectableItem;

public class MediaPickerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tableLayout;

    protected List<SelectableItem> list;
    private ArrayList<String> allPath;
    private LinearLayout mLinearLayout;
    private SelectableItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_picker);

        //Initialize Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLinearLayout = (LinearLayout) findViewById(R.id.bottom);

        //Initialize view pager and setup adapter
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        //Initialize tablayout for setup tabs
        tableLayout = (TabLayout) findViewById(R.id.tabs);
        tableLayout.setupWithViewPager(viewPager);

        list = new ArrayList<SelectableItem>();

    }

    public void handleSelection(SelectableItem item) {


        if (item.isSelected()) {
            list.add(item);
            mLinearLayout.setVisibility(View.VISIBLE);
        } else {
            list.remove(item);
        }

        handleSelection();


    }

    private ArrayList<String> getAllPath() {
        allPath = new ArrayList<>();

        for (SelectableItem item : list)
            allPath.add(item.getPath());
        return allPath;
    }

    private void handleSelection() {

        EditText editTextView_noOfselection = (EditText) findViewById(R.id.editTextView_noOfselection);
        editTextView_noOfselection.setText(list.size() + "" + " selected");

    }

    public void handleSelection(SelectableItem item, View view) {
        if (item.isSelected()) {
            ItemSelectionAnimation.animatedScaleView(view, 1.0f, 0.9f, false);
            list.add(item);
        } else {
            ItemSelectionAnimation.animatedScaleView(view, 0.9f, 1.0f, true);
            list.remove(item);
        }
        handleSelection();

    }

    public void onSendClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("path_list", getAllPath());
        setResult(RESULT_OK, intent);
        finish();
//        Toast.makeText(this, "" + list.size() + "item is send", Toast.LENGTH_LONG).show();
    }


}
