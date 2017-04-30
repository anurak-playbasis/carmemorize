package com.carmemorize.app.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carmemorize.app.R;
import com.carmemorize.app.adapter.ServiceAdapter;

public class Service extends AppCompatActivity {

    TabLayout serviceTab;
    ViewPager serviceViewPager;
    ServiceAdapter serviceAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_show);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        serviceTab       =   (TabLayout)findViewById(R.id.service_tab);
        serviceViewPager =   (ViewPager)findViewById(R.id.service_viewPager);

        serviceAdapter = new ServiceAdapter(getSupportFragmentManager());
        serviceViewPager.setAdapter(serviceAdapter);
        serviceTab.setupWithViewPager(serviceViewPager);

        serviceTab.getTabAt(0).setText("Radiater");
        serviceTab.getTabAt(1).setText("Tire");
        serviceTab.getTabAt(2).setText("Break");
        serviceTab.getTabAt(3).setText("Oil Change");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
