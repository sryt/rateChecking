package com.example.grocerieslist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.tabpager.StockTabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class StockFragment extends Fragment{

	TabLayout tabLayout;
	ViewPager viewPager;
	private StockTabsPagerAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout_tabview, container, false);


		tabLayout= rootView.findViewById(R.id.tabLayout);
		viewPager= rootView.findViewById(R.id.viewPager);

		tabLayout.addTab(tabLayout.newTab().setText("Last"));
		tabLayout.addTab(tabLayout.newTab().setText("Current"));
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

		mAdapter = new StockTabsPagerAdapter(getActivity(),getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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
