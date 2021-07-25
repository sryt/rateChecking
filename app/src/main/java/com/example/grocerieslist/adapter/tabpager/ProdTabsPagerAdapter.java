package com.example.grocerieslist.adapter.tabpager;

import android.content.Context;

import com.example.grocerieslist.fragment.CurrentStockFragment;
import com.example.grocerieslist.fragment.LatestStockFragment;
import com.example.grocerieslist.fragment.SettingsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProdTabsPagerAdapter extends FragmentPagerAdapter {

	private Context myContext;
	int totalTabs;

	public ProdTabsPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
		super(fm);
		myContext = context;
		this.totalTabs = totalTabs;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			//Product wise stock details
			return new LatestStockFragment();
		case 1:
			//Product details
			return new CurrentStockFragment();
		}

		return null;
	}

	// this counts total number of tabs
	@Override
	public int getCount() {
		return totalTabs;
	}

}
