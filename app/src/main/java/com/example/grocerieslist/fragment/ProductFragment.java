package com.example.grocerieslist.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.customer.CustomerAccess;
import com.example.grocerieslist.db.customer.CustomerClass;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 24/07/21.
 */
public class ProductFragment extends Fragment {
    String TAG = ProductFragment.class.getSimpleName();

    AppGlobal appGlobal;
    ListView lv;
    SearchView search;

    ProductAccess pa;
    List<ProductClass> pcs;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homefragment, container, false);

        appGlobal = new AppGlobal(getActivity());
        pa = new ProductAccess(getActivity());
        lv = rootView.findViewById(R.id.listview);
        search = rootView.findViewById(R.id.search);


        return rootView;
    }

    List<String> names;
    @Override
    public void onResume() {
        super.onResume();

        pa.open();
        pcs = pa.getProdDetails();
        pa.close();
        names = new ArrayList<>();

        Log.i(TAG,"Product details size "+pcs.size());
        if(pcs.size() > 0){
            for(int i=0;i<pcs.size();i++)
                names.add(pcs.get(i).getName());
        }else{
            names.add("No Data......");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,names);
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        lv.setAdapter(adapter);
    }
}
