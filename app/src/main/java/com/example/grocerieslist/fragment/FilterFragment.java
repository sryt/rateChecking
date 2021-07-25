package com.example.grocerieslist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.ExpandableListAdapter;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;

public class FilterFragment extends Fragment {
    String TAG = FilterFragment.class.getSimpleName();

    AppGlobal appGlobal;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<HashMap<String,String>> listDataHeader;
    HashMap<HashMap<String,String>, List<ProductClass>> listDataChild;

    List<String> filterHeader;
    List<ProductClass> pcs;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.extended_layout, container, false);
        appGlobal = new AppGlobal(getActivity());
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        filterList();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String groupName = filterHeader.get(i);
                ProductAccess pa = new ProductAccess(getActivity());
                pa.open();
                List<ProductClass> productClasses = pa.getProdCatagoryDetails(groupName);
                pa.close();
                Toast.makeText(getActivity(),"position head is "+groupName+" child is "+productClasses.get(i1).getName(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void filterList(){
        ProductAccess pa = new ProductAccess(getActivity());
        pa.open();
        filterHeader = pa.getProdCatagoryDetails();
        pa.close();

        Collections.sort(filterHeader, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });


        if(filterHeader.size() > 0 ){
            for(int i=0;i<filterHeader.size();i++){
                List<String> nowAdd = new ArrayList<String>();
                String groupName = filterHeader.get(i);

                //Add child to the view
                pa.open();
                pcs = pa.getProdCatagoryDetails(groupName);
                pa.close();

                if(pcs.size() > 0){
                    for(int k=0;k<pcs.size();k++){
                        nowAdd.add(pcs.get(k).getName());
                    }
                }
                HashMap<String,String> headMap = new HashMap<>();
                headMap.put("name",groupName);
                headMap.put("value",String.valueOf(pcs.size()));
                listDataHeader.add(headMap);
                listDataChild.put(listDataHeader.get(i),pcs);

            }
        }
    }

}
