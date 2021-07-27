package com.example.grocerieslist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.listadapter.CustomAdapter;
import com.example.grocerieslist.db.customer.CustomerAccess;
import com.example.grocerieslist.db.customer.CustomerClass;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.fragment.product.ProductTabFragment;
import com.example.grocerieslist.init.CustomerTabView;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 24/07/21.
 */
public class CustomerFragment extends Fragment {
    String TAG = CustomerFragment.class.getSimpleName();

    AppGlobal appGlobal;
    ListView lv;
    SearchView search;

    CustomerAccess ca;
    List<CustomerClass> ccs;
    CustomerClass customerClass;

    public CustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homefragment, container, false);

        appGlobal = new AppGlobal(getActivity());
        ca = new CustomerAccess(getActivity());
        lv = rootView.findViewById(R.id.listview);
        search = rootView.findViewById(R.id.search);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ca.open();
        ccs = ca.getCustomerDetails();
        ca.close();

        Log.i(TAG,"Customer details size "+ccs.size());
        if(ccs.size() > 0){
            adapter = new CustomAdapter(getActivity(),R.layout.custlayout,ccs);
            adapter.sort(new Comparator<CustomerClass>() {
                @Override
                public int compare(CustomerClass s, CustomerClass t1) {
                    return s.getName().compareTo(t1.getName());
                }
            });
            lv.setAdapter(adapter);
        }

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchFlow(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchFlow(s);
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductClass productClass;
                if(filterFlag)
                    customerClass = pcFilter.get(i);
                else
                    customerClass = ccs.get(i);

                Toast.makeText(getActivity(),""+customerClass.toString(),Toast.LENGTH_SHORT).show();
                Intent det = new Intent(getActivity(), CustomerTabView.class);
                det.putExtra("custId",customerClass.getId());
                det.putExtra("cmd","view");
                getActivity().startActivity(det);
            }
        });

    }

    boolean filterFlag = false;
    List<String> proNameFilter;
    List<CustomerClass> pcFilter;
    CustomAdapter adapter;

    public void searchFlow(String s){
        filterFlag = true;
        pcFilter = new ArrayList<>();
        proNameFilter = new ArrayList<>();
        for(CustomerClass pc : ccs){
            if(pc.getName().toLowerCase().contains(s.toLowerCase())){
                pcFilter.add(pc);
                proNameFilter.add(pc.getName());
            }
        }

        adapter = new CustomAdapter(getActivity(),R.layout.custlayout,pcFilter);
        adapter.sort(new Comparator<CustomerClass>() {
            @Override
            public int compare(CustomerClass lhs, CustomerClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(adapter);
    }
}
