package com.example.grocerieslist.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.listadapter.PriceDetailsAdapter;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;

public class PriceListFragment extends Fragment {
    String TAG = PriceListFragment.class.getSimpleName();
    AppGlobal global;
    ListView lv;
    SearchView txt;
    Button filterBtn;
    PriceDetailsAdapter priceDetailsAdapter;
    List<ProductClass> pcs;
    List<String> proNameFilter;
    List<ProductClass> pcFilter;

    public PriceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.pricelayout, container, false);
        global = new AppGlobal(getActivity());

        lv = rootView.findViewById(R.id.listview);
        txt = rootView.findViewById(R.id.search);
        filterBtn = rootView.findViewById(R.id.filter);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        getProductName();

        priceDetailsAdapter = new PriceDetailsAdapter(getActivity(),R.layout.pricedetials,pcs);

        txt.setQueryHint("Search over "+pcs.size());
        priceDetailsAdapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(priceDetailsAdapter);

        txt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.homefragment);

                dialog.setTitle("Select the category.");
                SearchView searchView = dialog.findViewById(R.id.search);
                searchView.setVisibility(View.GONE);

                ListView lv = dialog.findViewById(R.id.listview);

                ProductAccess pa = new ProductAccess(getActivity());
                pa.open();
                final List<String> category = pa.getProdCatagoryDetails();
                pa.close();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,category);
                lv.setAdapter(adapter);

                adapter.sort(new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                });

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        rearrangeList(category.get(i));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    public void rearrangeList(String str){
        pcs = new ArrayList<>();
        Toast.makeText(getActivity(),"Selected category is "+str,Toast.LENGTH_SHORT).show();

        ProductAccess pa = new ProductAccess(getActivity());
        pa.open();
        pcs = pa.getProdCatagoryDetails(str);
        pa.close();

        for(int i=0;i<pcs.size();i++)
            Log.i(TAG,i+" "+pcs.get(i).getName());

        priceDetailsAdapter = new PriceDetailsAdapter(getActivity(),R.layout.pricedetials,pcs);
        priceDetailsAdapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(priceDetailsAdapter);
    }

    boolean filterFlag = false;
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

        priceDetailsAdapter = new PriceDetailsAdapter(getActivity(),R.layout.pricedetials,pcFilter);
        priceDetailsAdapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(priceDetailsAdapter);
    }

    public void getProductName(){
        ProductAccess pa = new ProductAccess(getActivity());

        pa.open();
        pcs = pa.getProdDetails();
        pa.close();

        Log.i(TAG,"product list size is "+pcs.size());
    }

}
