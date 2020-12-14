package com.example.grocerieslist.init;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {
    String TAG = DetailsFragment.class.getSimpleName();
    AppGlobal global;

    TextView prodName,pPrice,cPrice,wPrice,rPrice,mrp,pSize,pUnit,caseQty,hsn,gst,sheetNo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.detailsfragment, container, false);
        global = new AppGlobal(getActivity());

        prodName = rootView.findViewById(R.id.prodName);
        pPrice = rootView.findViewById(R.id.pPrice);
        cPrice = rootView.findViewById(R.id.cPrice);
        wPrice = rootView.findViewById(R.id.wPrice);
        rPrice = rootView.findViewById(R.id.rPrice);
        mrp = rootView.findViewById(R.id.mrp);
        pSize = rootView.findViewById(R.id.packingSize);
        pUnit = rootView.findViewById(R.id.packingUnit);
        caseQty = rootView.findViewById(R.id.caseQty);
        hsn = rootView.findViewById(R.id.hsnCode);
        gst = rootView.findViewById(R.id.gst);
        sheetNo = rootView.findViewById(R.id.sheetBundal);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        String value = String.valueOf(global.getPreference(global.App_Selected_Prod));
        Log.i(TAG,"values is "+value);

        ProductAccess pa = new ProductAccess(getActivity());
        pa.open();
        ProductClass pc = pa.getProdDetails(value);
        pa.close();

        prodName.setText(pc.getName());
        pPrice.setText(pc.getPurRate()+"/-");
        cPrice.setText(pc.getCost()+"/-");
        wPrice.setText(pc.getSpecial()+"/-");
        rPrice.setText(pc.getRetail()+"/-");
        mrp.setText(pc.getMrp());
        pSize.setText(pc.getPackingsize());
        pUnit.setText(pc.getPackuom());
        caseQty.setText(pc.getCaseqty());
        hsn.setText(pc.getHsn());
        gst.setText(pc.getGst());
        sheetNo.setText(pc.getSheetNo());


        Log.i(TAG,"product class is "+pc.toString());
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DetailsFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }
}