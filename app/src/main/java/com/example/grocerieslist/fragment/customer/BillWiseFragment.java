package com.example.grocerieslist.fragment.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.BillWiseAdapter;
import com.example.grocerieslist.db.customer.CustomerAccess;
import com.example.grocerieslist.db.customer.CustomerClass;
import com.example.grocerieslist.db.stock.StockAccess;
import com.example.grocerieslist.db.stock.StockClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 26/07/21.
 */
public class BillWiseFragment extends Fragment {

    AppGlobal appGlobal;
    MaterialAutoCompleteTextView search;
    ListView lv;
    String custId;
    CustomerClass customerClass;
    List<StockClass> pscs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bill_wise, container, false);
        appGlobal = new AppGlobal(getActivity());

        custId = getActivity().getIntent().getStringExtra("custId");
        search = rootView.findViewById(R.id.lcsl_search);
        lv = rootView.findViewById(R.id.lcsl_lv);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!custId.isEmpty()){
            fetchCustomer();
            search.setText(customerClass.getName());
            prepare();
        }
    }

    public void fetchCustomer(){
        CustomerAccess ca = new CustomerAccess(getActivity());
        ca.open();
        customerClass = ca.getCustomerDetailsById(custId);
        ca.close();
    }

    public void prepare(){
        List<String> str = new ArrayList<>();
        pscs = new ArrayList<>();
        StockAccess psc = new StockAccess(getActivity());
        psc.open();
        pscs = psc.getProductStockByCustId(custId);
        psc.close();

        if(pscs.size() > 0){
            for(int i=0;i<pscs.size();i++) {
                StockClass stockClass = pscs.get(i);
                str.add(stockClass.getTs()+"-"+ stockClass.getProName()+"-"+ stockClass.getQty());
            }
            BillWiseAdapter adapter = new BillWiseAdapter(getActivity(),R.layout.list_item_bill_wise,pscs);
            adapter.sort(new Comparator<StockClass>() {
                @Override
                public int compare(StockClass lhs, StockClass rhs) {
                    return rhs.getTs().compareTo(lhs.getTs());
                }
            });
            lv.setAdapter(adapter);
        }else{
            str.add("No data..");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,str);
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
