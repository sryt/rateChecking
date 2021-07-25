package com.example.grocerieslist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.stock.StockAccess;
import com.example.grocerieslist.db.stock.StockClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;


public class ProductStockFragment extends Fragment {
    String TAG = ProductStockFragment.class.getSimpleName();
    ListView lv;
    StockAccess sa;
    List<StockClass> scs;
    List<String> names;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product_stock, container, false);
        lv = view.findViewById(R.id.fds_lv);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        sa = new StockAccess(getActivity());
        sa.open();
        scs = sa.getProductStock();
        sa.close();

        if(scs.size() > 0){
            names = new ArrayList<>();
            for(int i=0;i<scs.size();i++)
                names.add(scs.get(i).getProName());

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
}
