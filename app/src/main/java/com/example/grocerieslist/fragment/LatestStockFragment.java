package com.example.grocerieslist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.listadapter.StockAdapter;
import com.example.grocerieslist.db.stock.StockAccess;
import com.example.grocerieslist.db.stock.StockClass;
import com.example.grocerieslist.init.ProductStock;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 25/07/21.
 */
public class LatestStockFragment extends Fragment {
    String TAG = LatestStockFragment.class.getSimpleName();

    ListView lv;
    StockAccess psa;
    List<StockClass> psas;
    AppGlobal global;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.homefragment, container, false);
        lv = root.findViewById(R.id.listview);
        global = new AppGlobal(getActivity());

        psa = new StockAccess(getActivity());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        psa.open();
        psas = psa.getProductStock();
        psa.close();

        if(psas.size() > 0){
            StockAdapter adapter = new StockAdapter(getActivity(),R.layout.stock_list_view,psas);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent details = new Intent(getContext(), ProductStock.class);
                    details.putExtra("cmd","edit");
                    details.putExtra("id",psas.get(i).getId());
                    details.putExtra("name",psas.get(i).getProName());
                    details.putExtra("ts",psas.get(i).getTs());
                    details.putExtra("type",psas.get(i).getType());
                    details.putExtra("qty",psas.get(i).getQty());
                    details.putExtra("remark",psas.get(i).getRemark());
                    details.putExtra("stockClass",psas.get(i).toString());
                    getActivity().startActivity(details);
                }
            });
        }
    }

}
