package com.example.grocerieslist.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.stock.StockClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import java.util.List;

/**
 * Created by Tejaswi on 26/07/21.
 */
public class BillWiseAdapter extends ArrayAdapter<StockClass> {
    String TAG = BillWiseAdapter.class.getSimpleName();

    private Activity act;
    int layoutResourceId;
    List<StockClass> ccs;
    StockClass pc;
    MenuHolder holder;
    AppGlobal global;

    public BillWiseAdapter(Activity act, int resource, List<StockClass> data_list) {
        super(act, resource, data_list);
        this.act = act;
        this.layoutResourceId = resource;
        this.ccs = data_list;

        global = new AppGlobal(act);
    }

    public View getView(final int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(layoutResourceId, parent, false);
        holder = new MenuHolder();


        holder.name = row.findViewById(R.id.csl_name);
        holder.ts = row.findViewById(R.id.csl_ts);
        holder.qty = row.findViewById(R.id.csl_qty);

        pc = ccs.get(pos);

        holder.name.setText(pc.getProName());
        if(!pc.getTs().isEmpty() || !pc.getTs().equals(""))
            holder.ts.setText(global.dateFormat(pc.getTs(),global.DATE_DATE));
        holder.qty.setText(pc.getQty());

        if(pc.getType().equals(Constant.INWARDS))
            holder.qty.setTextColor(Color.GREEN);
        else
            holder.qty.setTextColor(Color.RED);

        return row;
    }


    /**************************************************************************/
    /**************************************************************************/
    static class MenuHolder {
        TextView name, ts, qty;
    }
}
