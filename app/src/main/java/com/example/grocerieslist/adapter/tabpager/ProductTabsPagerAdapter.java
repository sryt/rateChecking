package com.example.grocerieslist.adapter.tabpager;

import android.content.Context;

import com.example.grocerieslist.fragment.SettingsFragment;
import com.example.grocerieslist.fragment.product.PriceListFragment;
import com.example.grocerieslist.fragment.product.ProductFragment;
import com.example.grocerieslist.fragment.stock.CurrentStockFragment;
import com.example.grocerieslist.fragment.stock.LatestStockFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProductTabsPagerAdapter extends FragmentPagerAdapter {

	private Context myContext;
	int totalTabs;

	public ProductTabsPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
		super(fm);
		myContext = context;
		this.totalTabs = totalTabs;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new ProductFragment();
		case 1:
			return new PriceListFragment();
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
