package com.example.grocerieslist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.grocerieslist.R;
import com.example.grocerieslist.init.NewSales;
import com.example.grocerieslist.init.ProductStock;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {
    String TAG = DashboardFragment.class.getSimpleName();
    CardView share;
    LinearLayout salesLL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.layout_dashboard, container, false);
        //next = rootView.findViewById(R.id.button_first);
        salesLL = rootView.findViewById(R.id.ld_sales);
        share = rootView.findViewById(R.id.ld_dashboard_share);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        salesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newSales = new Intent(getActivity(), NewSales.class);
                startActivity(newSales);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newSales = new Intent(getActivity(), ProductStock.class);
                startActivity(newSales);
            }
        });
    }

}