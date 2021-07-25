package com.example.grocerieslist.fragment.stock;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.listadapter.StockAdapter;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.db.stock.StockAccess;
import com.example.grocerieslist.db.stock.StockClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 25/07/21.
 */
public class CurrentStockFragment extends Fragment {
    String TAG = CurrentStockFragment.class.getSimpleName();
    ListView lv;

    StockAccess psa;
    List<StockClass> psas;

    ProductAccess pa;
    List<ProductClass> pcs;

    List<StockClass> currentPro = new ArrayList<>();
    AppGlobal global;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.homefragment, container, false);
        lv = root.findViewById(R.id.listview);
        global = new AppGlobal(getActivity());

        psa = new StockAccess(getActivity());
        pa = new ProductAccess(getActivity());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        pa.open();
        pcs = pa.getProdDetails();
        pa.close();


        if(pcs.size() > 0){
            currentPro = new ArrayList<>();
            for(int i=0;i<pcs.size();i++){
                psa.open();
                psas = psa.getProductStockByName(pcs.get(i).getName());
                psa.close();
                if(psas.size()>0){
                    ProductClass productSC =  pcs.get(i);
                    Log.i(TAG,"product "+productSC.getName()+" is "+psas.size());
                    int sum = 0;

                    for(int z=0;z<psas.size();z++){
                        StockClass psc = psas.get(z);
                        Log.i(TAG," "+psc.toString());
                        if(!psc.getQty().isEmpty()){
                            if(psc.getType().equals(Constant.INWARDS)){
                                sum = sum + Integer.parseInt(psc.getQty());
                            }else {
                                sum = sum - Integer.parseInt(psc.getQty());
                            }
                        }
                    }
                    Log.i(TAG,"sum is "+sum);
                    StockClass newPSC = new StockClass(productSC.getId(),productSC.getName(),psas.get(psas.size()-1).getCustId(),psas.get(psas.size()-1).getLocationId(),psas.get(psas.size()-1).getPersonId(),psas.get(psas.size()-1).getTs(),Constant.CURRENT,String.valueOf(sum),psas.get(psas.size()-1).getRemark());
                    Log.i(TAG,"current stock is "+newPSC.toString());
                    currentPro.add(newPSC);
                }else{
                    Log.i(TAG,"product "+pcs.get(i).getName()+" is empty");
                }

            }
            StockAdapter adapter = new StockAdapter(getActivity(),R.layout.stock_list_view,currentPro);
            adapter.sort(new Comparator<StockClass>() {
                @Override
                public int compare(StockClass lhs, StockClass rhs) {
                    return rhs.getTs().compareTo(lhs.getTs());
                }
            });
            lv.setAdapter(adapter);
        }
    }
}
