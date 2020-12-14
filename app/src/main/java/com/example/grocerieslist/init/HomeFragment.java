package com.example.grocerieslist.init;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.CustomAdapter;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends Fragment {
    String TAG = HomeFragment.class.getSimpleName();
    ListView lv;
    Button next;
    SearchView txt;
    ArrayAdapter<String> adapter;
    CustomAdapter customAdapter;
    AppGlobal global;
    InputStream inputStream;
    String[] ids;

    List<ProductClass> pcs;
    List<String> proName;
    List<String> proNameFilter;
    List<ProductClass> pcFilter;

    boolean filterFlag = false;
    NavHostFragment navHostFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.homefragment, container, false);
        //next = rootView.findViewById(R.id.button_first);
        lv = rootView.findViewById(R.id.listview);
        txt = rootView.findViewById(R.id.search);

        global = new AppGlobal(getActivity());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getProductName();

        customAdapter = new CustomAdapter(getActivity(),R.layout.custlayout,pcs);

        customAdapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(customAdapter);


        //adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,proName);
        //lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(filterFlag){
                    Log.i(TAG,"selected product is "+proNameFilter.get(i));
                    Toast.makeText(getActivity(),"selected product is ",Toast.LENGTH_SHORT).show();
                    global.setPreference(global.App_Selected_Prod,pcFilter.get(i).getId(),Constant.Pref_String,null);
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                }else{
                    Log.i(TAG,"selected product is "+proName.get(i));
                    Toast.makeText(getActivity(),"selected product is ",Toast.LENGTH_SHORT).show();
                    global.setPreference(global.App_Selected_Prod,pcs.get(i).getId(),Constant.Pref_String,null);
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                }
            }
        });

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
    }

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

        customAdapter = new CustomAdapter(getActivity(),R.layout.custlayout,pcFilter);
        customAdapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(customAdapter);
    }

    public void getProductName(){
        proName = new ArrayList<>();
        ProductAccess pa = new ProductAccess(getActivity());

        pa.open();
        pcs = pa.getProdDetails();
        pa.close();

        Log.i(TAG,"product list size is "+pcs.size());
        for(int i=0;i<pcs.size();i++){
            String name = pcs.get(i).getName()+" "+pcs.get(i).getPackingsize()+pcs.get(i).getPackuom();
            proName.add(name);
        }
    }

}