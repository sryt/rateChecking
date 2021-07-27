package com.example.grocerieslist.adapter.tabpager;

import android.content.Context;

import com.example.grocerieslist.fragment.customer.BillWiseFragment;
import com.example.grocerieslist.fragment.customer.DetailsFragment;
import com.example.grocerieslist.fragment.stock.CurrentStockFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CustTabsPagerAdapter extends FragmentPagerAdapter {

	private Context myContext;
	int totalTabs;

	public CustTabsPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
		super(fm);
		myContext = context;
		this.totalTabs = totalTabs;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new BillWiseFragment();
		case 1:
			return new DetailsFragment();
		}

		return null;
	}

	// this counts total number of tabs
	@Override
	public int getCount() {
		return totalTabs;
	}

}
