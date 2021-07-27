package com.example.grocerieslist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.company.CompanyAccess;
import com.example.grocerieslist.db.company.CompanyClass;
import com.example.grocerieslist.init.NewSales;
import com.example.grocerieslist.init.ProductStock;
import com.example.grocerieslist.init.SlidingMenu;
import com.example.grocerieslist.utilities.AppGlobal;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {
    String TAG = DashboardFragment.class.getSimpleName();
    CardView share;
    LinearLayout salesLL;

    AppGlobal global;
    CompanyClass companyClass;

    TextView compName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.layout_dashboard, container, false);

        global = new AppGlobal(getActivity());

        compName = rootView.findViewById(R.id.ld_comp_name);
        salesLL = rootView.findViewById(R.id.ld_sales);
        share = rootView.findViewById(R.id.ld_dashboard_share);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(global.getPreference(global.App_Selected_Company) != null){
            String ccid = String.valueOf(global.getPreference(global.App_Selected_Company));
            CompanyAccess ca = new CompanyAccess(getActivity());
            ca.open();
            companyClass = ca.getCompanyDetails(ccid);
            ca.close();

            compName.setText(companyClass.getCompanyName());
        }
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