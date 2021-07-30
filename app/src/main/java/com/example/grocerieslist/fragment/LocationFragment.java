package com.example.grocerieslist.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.stockdetails.StockDetailsAccess;
import com.example.grocerieslist.db.stockdetails.StockDetailsClass;
import com.example.grocerieslist.init.SlidingMenu;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.cardview.widget.CardView;
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
        sdcs = sda.getStockDetailsByType(Constant.PLACE);
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!names.get(i).equals(Constant.NO_DATA)){
                    displayData(sdcs.get(i),false);
                }
            }
        });
    }


    public void displayData(final StockDetailsClass sdc, boolean flag){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.createstockdetails);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView title = dialog.findViewById(R.id.csd_title);
        ImageView edit = dialog.findViewById(R.id.csd_edit);
        final TextInputEditText name = dialog.findViewById(R.id.csd_name);
        final TextInputEditText address = dialog.findViewById(R.id.csd_address);
        final TextInputEditText state = dialog.findViewById(R.id.csd_state);
        final TextInputEditText number = dialog.findViewById(R.id.csd_number);
        final TextInputEditText pincode = dialog.findViewById(R.id.csd_pincode);
        final TextInputEditText desc = dialog.findViewById(R.id.csd_desc);
        final Spinner status = dialog.findViewById(R.id.csd_status);
        CardView save = dialog.findViewById(R.id.csd_save);
        CardView cancel = dialog.findViewById(R.id.csd_cancel);

        title.setText("Create "+Constant.PLACE);
        if(!flag) {
            save.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            name.setFocusable(false);
            address.setFocusable(false);
            state.setFocusable(false);
            number.setFocusable(false);
            pincode.setFocusable(false);
            desc.setFocusable(false);
            status.setFocusable(false);
            status.setClickable(false);
            status.setEnabled(false);
        }else {
            edit.setVisibility(View.INVISIBLE);
            save.setVisibility(View.VISIBLE);
        }
        name.setText(sdc.getName());
        address.setText(sdc.getAddress());
        state.setText(sdc.getState());
        number.setText(sdc.getNumber());
        pincode.setText(sdc.getPincode());
        desc.setText(sdc.getDesc());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayData(sdc,true);
                dialog.dismiss();
            }
        });
        if(sdc.getStatus().equals(Constant.ACTIVE)){
            status.setSelection(0);
        }else{
            status.setSelection(1);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString().trim();
                String descStr = desc.getText().toString().trim();
                String addStr = address.getText().toString().trim();
                String stateStr = state.getText().toString().trim();
                String pincodeStr = pincode.getText().toString().trim();
                String numStr = number.getText().toString().trim();
                String statusStr = String.valueOf(status.getSelectedItem());

                if(!nameStr.isEmpty()){
                    StockDetailsClass stockDetailsClass = new StockDetailsClass(sdc.getId(),nameStr,addStr,stateStr,pincodeStr,numStr,descStr,Constant.PLACE,statusStr,"");
                    StockDetailsAccess sda = new StockDetailsAccess(getActivity());
                    sda.open();
                    boolean flag = sda.updateStockDetails(stockDetailsClass);
                    sda.close();

                    dialog.dismiss();
                    if(flag)
                        Toast.makeText(getActivity(),"Successfully added...",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(),"Try again...",Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
