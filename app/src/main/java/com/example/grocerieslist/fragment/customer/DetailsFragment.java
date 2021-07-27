package com.example.grocerieslist.fragment.customer;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.customer.CustomerAccess;
import com.example.grocerieslist.db.customer.CustomerClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 26/07/21.
 */
public class DetailsFragment extends Fragment {
    String TAG = DetailsFragment.class.getSimpleName();
    AppGlobal appGlobal;
    AppGlobal global;

    String custId,cmd;

    CustomerAccess ca;
    CustomerClass cc;

    View editView,viewView;
    TextInputEditText custNameE,addressE,address1E,address2E,placeE,stateE,pincodeE,scodeE,phone1E,phone2E,emailE,gstNumberE;
    TextInputEditText custNameV,addressV,address1V,address2V,placeV,stateV,pincodeV,scodeV,phone1V,phone2V,emailV,gstNumberV;
    CardView save,edit;
    Spinner custTypeV,custTypeE;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_customer_details, container, false);
        appGlobal = new AppGlobal(getActivity());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        editView = rootView.findViewById(R.id.edit);
        viewView = rootView.findViewById(R.id.view);
        linkView(editView,viewView);

        save = rootView.findViewById(R.id.lc_save);
        edit = rootView.findViewById(R.id.lc_edit);

        cmd = getActivity().getIntent().getStringExtra("cmd");
        if(cmd.equals("view")) {
            custId = getActivity().getIntent().getStringExtra("custId");
            if(custId != null){
                Log.i(TAG,"customer id "+custId);
                ca = new CustomerAccess(getActivity());
                ca.open();
                cc = ca.getCustomerDetailsById(custId);
                ca.close();
                Log.i(TAG,"customer selected details "+cc.toString());
                setData();
            }
            setEditable(false);
        }else {
            setEditable(true);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditable(true);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void linkView(View ev,View vv){
        /**
         * Edit view linking
         */
        custNameE = ev.findViewById(R.id.lc_cust_name);
        addressE = ev.findViewById(R.id.lc_address);
        address1E = ev.findViewById(R.id.lc_address1);
        address2E = ev.findViewById(R.id.lc_address2);
        placeE = ev.findViewById(R.id.lc_place);
        stateE = ev.findViewById(R.id.lc_state);
        pincodeE = ev.findViewById(R.id.lc_pincode);
        scodeE = ev.findViewById(R.id.lc_scode);
        phone1E = ev.findViewById(R.id.lc_phone1);
        phone2E = ev.findViewById(R.id.lc_phone2);
        emailE = ev.findViewById(R.id.lc_email);
        gstNumberE = ev.findViewById(R.id.lc_gst);
        custTypeE = ev.findViewById(R.id.lc_cust_type);

        /**
         * View view linking
         */
        custNameV = vv.findViewById(R.id.lc_cust_name);
        addressV = vv.findViewById(R.id.lc_address);
        address1V = vv.findViewById(R.id.lc_address1);
        address2V = vv.findViewById(R.id.lc_address2);
        placeV = vv.findViewById(R.id.lc_place);
        stateV = vv.findViewById(R.id.lc_state);
        pincodeV = vv.findViewById(R.id.lc_pincode);
        scodeV = vv.findViewById(R.id.lc_scode);
        phone1V = vv.findViewById(R.id.lc_phone1);
        phone2V = vv.findViewById(R.id.lc_phone2);
        emailV = vv.findViewById(R.id.lc_email);
        gstNumberV = vv.findViewById(R.id.lc_gst);
        custTypeV = vv.findViewById(R.id.lc_cust_type);
    }




    public void setData(){
        custNameV.setText(cc.getName());
        addressV.setText(cc.getAddress());
        address1V.setText(cc.getAddress1());
        address2V.setText(cc.getAddress2());
        placeV.setText(cc.getPlace());
        stateV.setText(cc.getState());
        pincodeV.setText(cc.getPincode());
        scodeV.setText(cc.getScode());
        phone1V.setText(cc.getPhone1());
        phone2V.setText(cc.getPhone2());
        emailV.setText(cc.getEmail());
        gstNumberV.setText(cc.getGst());


        custNameE.setText(cc.getName());
        addressE.setText(cc.getAddress());
        address1E.setText(cc.getAddress1());
        address2E.setText(cc.getAddress2());
        placeE.setText(cc.getPlace());
        stateE.setText(cc.getState());
        pincodeE.setText(cc.getPincode());
        scodeE.setText(cc.getScode());
        phone1E.setText(cc.getPhone1());
        phone2E.setText(cc.getPhone2());
        emailE.setText(cc.getEmail());
        gstNumberE.setText(cc.getGst());
    }

    public void setEditable(boolean flag){
        if(flag){
            save.setVisibility(View.VISIBLE);
            edit.setVisibility(View.INVISIBLE);
            viewView.setVisibility(View.GONE);
            editView.setVisibility(View.VISIBLE);
        }else{
            save.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            viewView.setVisibility(View.VISIBLE);
            editView.setVisibility(View.GONE);


            custNameV.setFocusable(flag);
            addressV.setFocusable(flag);
            address1V.setFocusable(flag);
            address2V.setFocusable(flag);
            placeV.setFocusable(flag);
            stateV.setFocusable(flag);
            pincodeV.setFocusable(flag);
            scodeV.setFocusable(flag);
            phone1V.setFocusable(flag);
            phone2V.setFocusable(flag);
            emailV.setFocusable(flag);
            gstNumberV.setFocusable(flag);
            custTypeV.setFocusable(flag);
        }
    }


    public void saveData(){
        String custNameStr = custNameE.getText().toString().trim();
        String add = addressE.getText().toString().trim();
        String add1 = address1E.getText().toString().trim();
        String add2 = address2E.getText().toString().trim();
        String place = placeE.getText().toString().trim();
        String state = stateE.getText().toString().trim();
        String scode = scodeE.getText().toString().trim();
        String pincode = pincodeE.getText().toString().trim();
        String phone1 = phone1E.getText().toString().trim();
        String phone2 = phone2E.getText().toString().trim();
        String email = emailE.getText().toString().trim();
        String gst = gstNumberE.getText().toString().trim();
        String custType = String.valueOf(custTypeE.getSelectedItem());

        CustomerClass customerClass;

        CustomerAccess ca = new CustomerAccess(getActivity());
        ca.open();
        long res;
        if(cmd.equals("edit")) {
            customerClass = new CustomerClass(cc.getId(),custNameStr,add,add1,add2,place,state,scode,pincode,phone1,phone2,email,gst,custType, Constant.ACTIVE);
            res = ca.updateCustomerDetails(customerClass);
        }else{
            customerClass = new CustomerClass(custNameStr,add,add1,add2,place,state,scode,pincode,phone1,phone2,email,gst,custType, Constant.ACTIVE);
            res = ca.addCustomerDetails(customerClass);
        }
        ca.close();

        if(res >= 0){
            Snackbar.make(save,"Successfully Updated.....", Snackbar.LENGTH_SHORT).setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setEditable(false);
                    onResume();
                }
            }).setActionTextColor(Color.RED).show();
        }else{
            Snackbar.make(save,"Error while saving the customer details",Snackbar.LENGTH_SHORT).show();
        }

    }

}
