package com.example.grocerieslist.fragment.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.tabpager.ProductTabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Tejaswi on 25/07/21.
 */
public class ProductTabFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    private ProductTabsPagerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_tabview, container, false);


        tabLayout= rootView.findViewById(R.id.tabLayout);
        viewPager= rootView.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("ListData"));
        tabLayout.addTab(tabLayout.newTab().setText("PriceList"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mAdapter = new ProductTabsPagerAdapter(getActivity(),getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mAdapter);

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

        return rootView;
    }
}
