package com.example.grocerieslist.adapter.listadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.customer.CustomerAccess;
import com.example.grocerieslist.db.customer.CustomerClass;
import com.example.grocerieslist.db.stock.StockClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 10/07/21.
 */
public class StockAdapter extends ArrayAdapter<StockClass> implements Filterable {
    String TAG = StockAdapter.class.getSimpleName();

    private Activity act;
    int layoutResourceId;
    List<StockClass> ccs;
    List<StockClass> filterList;
    StockClass pc;
    CustomFilter filter;
    MenuHolder holder;
    CustomerClass cc;
    boolean flag = false;
    AppGlobal global;

    public StockAdapter(Activity act, int resource, List<StockClass> data_list) {
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


        holder.remark = row.findViewById(R.id.slv_remark);
        holder.name = row.findViewById(R.id.slv_name);
        holder.datetime = row.findViewById(R.id.slv_datetime);
        holder.qty = row.findViewById(R.id.slv_value);

        pc = ccs.get(pos);

        Log.i(TAG,"details "+pc.toString());
        holder.name.setText(pc.getProName());
        holder.qty.setText(pc.getQty());
        if(!pc.getTs().equals("null") || pc.getTs() != null)
            if(pc.getTs().matches("\\d+(?:\\.\\d+)?"))
                holder.datetime.setText(global.dateFormat(pc.getTs(),global.DATE_AMPM));

        if(pc.getType().equals(Constant.INWARDS)) {
            holder.qty.setTextColor(Color.GREEN);
        }else if(pc.getType().equals(Constant.CURRENT)){
            if(pc.getQty().contains("-")) {
                holder.qty.setTextColor(Color.RED);
            }else {
                holder.qty.setTextColor(Color.GREEN);
            }
        }else {
            holder.qty.setTextColor(Color.RED);
        }

        String custName = "";
        if(pc.getCustId() != null){
            if(!pc.getCustId().isEmpty() || !pc.getCustId().equals("")) {
                CustomerAccess ca = new CustomerAccess(act);
                ca.open();
                cc = ca.getCustomerDetailsById(pc.getCustId());
                ca.close();
                custName = cc.getName();
            }
        }

        holder.remark.setText(custName+", "+pc.getRemark());

        return row;
    }

    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter=new CustomFilter();
        }
        return filter;
    }


    /**************************************************************************/
    /**************************************************************************/
    static class MenuHolder {
        TextView name,datetime,remark,qty;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint != null && constraint.length()>0) {
                constraint=constraint.toString().toUpperCase();
                ArrayList<StockClass> filters=new ArrayList<StockClass>();
                for(int i=0;i<filterList.size();i++) {
                    if(filterList.get(i).getProName().toUpperCase().contains(constraint)) {
                        /*ProductClass p=new ProductClass(filterList.get(i).getName(), filterList.get(i).getImg());
                        filters.add(p);*/
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub

            //players=(ArrayList<Player>) results.values;
            notifyDataSetChanged();
        }

    }
}
