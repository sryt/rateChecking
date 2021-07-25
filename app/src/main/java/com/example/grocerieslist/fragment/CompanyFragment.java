package com.example.grocerieslist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerieslist.R;
import com.example.grocerieslist.utilities.AppGlobal;

import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 24/07/21.
 */
public class CompanyFragment extends Fragment {

    AppGlobal appGlobal;

    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settingslayout, container, false);
        appGlobal = new AppGlobal(getActivity());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
