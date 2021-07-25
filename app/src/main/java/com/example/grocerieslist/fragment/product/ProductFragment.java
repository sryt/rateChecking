package com.example.grocerieslist.fragment.product;

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
import com.example.grocerieslist.adapter.listadapter.ProductAdapter;
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

        search.setQueryHint("Search over "+pcs.size());
        Log.i(TAG,"Product details size "+pcs.size());
        if(pcs.size() > 0){
            for(int i=0;i<pcs.size();i++)
                names.add(pcs.get(i).getName());
        }else{
            names.add("No Data......");
        }

        adapter = new ProductAdapter(getActivity(),R.layout.list_items_img_product,pcs);
        adapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductClass productClass;
                if(filterFlag)
                    productClass = pcFilter.get(i);
                else
                    productClass = pcs.get(i);

                Toast.makeText(getActivity(),""+productClass.toString(),Toast.LENGTH_SHORT).show();
            }
        });
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
    }

    boolean filterFlag = false;
    List<String> proNameFilter;
    List<ProductClass> pcFilter;
    ProductAdapter adapter;

    public void searchFlow(String s){
        filterFlag = true;
        pcFilter = new ArrayList<>();
        proNameFilter = new ArrayList<>();
        for(ProductClass pc : pcs){
            if(pc.getName().toLowerCase().contains(s.toLowerCase())){
                pcFilter.add(pc);
                proNameFilter.add(pc.getName());
            }
        }

        adapter = new ProductAdapter(getActivity(),R.layout.list_items_img_product,pcFilter);
        adapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(adapter);
    }
}
