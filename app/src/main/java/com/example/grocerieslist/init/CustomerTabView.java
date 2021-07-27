package com.example.grocerieslist.init;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.tabpager.CustTabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Tejaswi on 25/07/21.
 */
public class CustomerTabView extends AppCompatActivity {
    TextView title;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tabview);

        tabLayout= findViewById(R.id.tabLayout);
        viewPager= findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Bill Wise"));
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final CustTabsPagerAdapter adapter = new CustTabsPagerAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
