package com.voda.voda_admin.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.voda.voda_admin.R;

public class MainActivity2 extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    LinearLayout Tab1, Tab2, Tab3, Tab4, Tab5;
    TabHost tabHost;
    ImageView i1, i2, i3, i4, i5;
    public static String Tab = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tabHost = getTabHost();
        i1 = findViewById(R.id.iv_main2_1);
        i2 = findViewById(R.id.iv_main2_2);
        i3 = findViewById(R.id.iv_main2_3);
        i4 = findViewById(R.id.iv_main2_4);
        i5 = findViewById(R.id.iv_main2_5);

        i5.setColorFilter(Color.parseColor("#ffffff"),PorterDuff.Mode.SRC_IN);

        Tab1 = findViewById(R.id.Tab1);
        Tab2 = findViewById(R.id.Tab2);
        Tab3 = findViewById(R.id.Tab3);
        Tab4 = findViewById(R.id.Tab4);
        Tab5 = findViewById(R.id.Tab5);

        // Tab for tab1
        TabSpec spec1 = tabHost.newTabSpec("Tab1");
        // setting Title and Icon for the Tab
        spec1.setIndicator("Tab1");
        Intent Intent1 = new Intent(this, OrderActivity.class);
        spec1.setContent(Intent1);

        // Tab for tab2
        TabSpec spec2 = tabHost.newTabSpec("Tab2");
        // setting Title and Icon for the Tab
        spec2.setIndicator("Tab2");
        Intent Intent2 = new Intent(this, MenuActivity.class);
        spec2.setContent(Intent2);

        // Tab for tab3
        TabSpec spec3 = tabHost.newTabSpec("Tab3");
        // setting Title and Icon for the Tab
        spec3.setIndicator("Tab3");
        Intent Intent3 = new Intent(this, CustomerActivity.class);
        spec3.setContent(Intent3);

        // Tab for tab4

        TabSpec spec4 = tabHost.newTabSpec("Tab4");
        // setting Title and Icon for the Tab
        spec4.setIndicator("Tab4");
        Intent Intent4 = new Intent(this, AnalysisActivity.class);
        spec4.setContent(Intent4);

        // Tab for tab5
        TabSpec spec5 = tabHost.newTabSpec("Tab5");
        // setting Title and Icon for the Tab
        spec5.setIndicator("Tab5");
        Intent Intent5 = new Intent(this, SettingsActivity.class);
        spec5.setContent(Intent5);


        // Adding all TabSpec to TabHost

        tabHost.addTab(spec1); // Adding tab1
        tabHost.addTab(spec2); // Adding tab2
        tabHost.addTab(spec3); // Adding tab3
        tabHost.addTab(spec4); // Adding tab4
        tabHost.addTab(spec5); // Adding tab5

        int num = getIntent().getExtras().getInt("num");
        tabHost.setCurrentTab(num);
        if(num==0){
            Tab1.setSelected(true);
            i1.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
        }
        else if(num==1){
            Tab2.setSelected(true);
            i2.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
        }
        else if(num==2){
            Tab3.setSelected(true);
            i3.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
        }
        else if(num==3){
            Tab4.setSelected(true);
            i4.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
        }
        else{
            Tab5.setSelected(true);
            i5.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
        }
//
        Tab1.setOnClickListener(mOnClickListener);
        Tab2.setOnClickListener(mOnClickListener);
        Tab3.setOnClickListener(mOnClickListener);
        Tab4.setOnClickListener(mOnClickListener);
        Tab5.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Tab1:
                    button_init();
                    Tab1.setSelected(true);
                    i1.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
                    tabHost.setCurrentTab(0);
                    break;
                case R.id.Tab2:
                    button_init();
                    Tab2.setSelected(true);
                    i2.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
                    tabHost.setCurrentTab(1);
                    break;
                case R.id.Tab3:
                    button_init();
                    Tab3.setSelected(true);
                    i3.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
                    tabHost.setCurrentTab(2);
                    break;
                case R.id.Tab4:
                    button_init();
                    Tab4.setSelected(true);
                    i4.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
                    tabHost.setCurrentTab(3);
                    break;
                case R.id.Tab5:
                    button_init();
                    Tab5.setSelected(true);
                    i5.setColorFilter(Color.parseColor("#ee8155"),PorterDuff.Mode.SRC_IN);
                    tabHost.setCurrentTab(4);
                    break;
            }
        }
    };

//    public void tabHandler(View target) {
//        if (target.getId() == R.id.Tab1) {
//            Tab = "this is Tab 1";
//
//
//
//        } else if (target.getId() == R.id.Tab2) {
//            Tab = "this is Tab 2";
//
//
//            tabHost.setCurrentTab(1);
//
//        } else if (target.getId() == R.id.Tab3) {
//            Tab = "this is Tab 3";
//
//            tabHost.setCurrentTab(2);
//
//        } else if (target.getId() == R.id.Tab4) {
//            Tab = "this is Tab 4";
//
//            tabHost.setCurrentTab(3);
//
//        } else if (target.getId() == R.id.Tab5) {
//            Tab = "this is Tab 5";
//
//            tabHost.setCurrentTab(4);
//
//        }
//    }

    private void button_init() {
        Tab1.setSelected(false);
        Tab2.setSelected(false);
        Tab3.setSelected(false);
        Tab4.setSelected(false);
        Tab5.setSelected(false);
        i1.setColorFilter(Color.parseColor("#ffffff"),PorterDuff.Mode.SRC_IN);
        i2.setColorFilter(Color.parseColor("#ffffff"),PorterDuff.Mode.SRC_IN);
        i3.setColorFilter(Color.parseColor("#ffffff"),PorterDuff.Mode.SRC_IN);
        i4.setColorFilter(Color.parseColor("#ffffff"),PorterDuff.Mode.SRC_IN);
        i5.setColorFilter(Color.parseColor("#ffffff"),PorterDuff.Mode.SRC_IN);
    }
}