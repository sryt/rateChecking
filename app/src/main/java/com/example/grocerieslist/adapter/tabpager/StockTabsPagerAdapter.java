package com.example.grocerieslist.adapter.tabpager;

import android.content.Context;

import com.example.grocerieslist.fragment.CurrentStockFragment;
import com.example.grocerieslist.fragment.LatestStockFragment;
import com.example.grocerieslist.fragment.SettingsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StockTabsPagerAdapter extends FragmentPagerAdapter {

	private Context myContext;
	int totalTabs;

	public StockTabsPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
		super(fm);
		myContext = context;
		this.totalTabs = totalTabs;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new LatestStockFragment();
		case 1:
			return new CurrentStockFragment();
		case 2:
			return new SettingsFragment();
		}

		return null;
	}

	// this counts total number of tabs
	@Override
	public int getCount() {
		return totalTabs;
	}

}
