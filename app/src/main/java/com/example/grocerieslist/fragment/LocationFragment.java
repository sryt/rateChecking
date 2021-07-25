package com.example.grocerieslist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.stockdetails.StockDetailsAccess;
import com.example.grocerieslist.db.stockdetails.StockDetailsClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 24/07/21.
 */
public class LocationFragment extends Fragment {

    AppGlobal appGlobal;
    SearchView searchView;
    ListView lv;

    StockDetailsAccess sda;
    List<StockDetailsClass> sdcs;
    List<String> names;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homefragment, container, false);
        appGlobal = new AppGlobal(getActivity());

        searchView = rootView.findViewById(R.id.search);
        lv = rootView.findViewById(R.id.listview);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        sda = new StockDetailsAccess(getActivity());
        sda.open();
        sdcs = sda.getStockDetails(Constant.PLACE);
        sda.close();

        names = new ArrayList<>();
        if(sdcs.size() > 0){
            for(int i=0;i<sdcs.size();i++)
                names.add(sdcs.get(i).getName());
        }else{
            names.add(Constant.NO_DATA);
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
