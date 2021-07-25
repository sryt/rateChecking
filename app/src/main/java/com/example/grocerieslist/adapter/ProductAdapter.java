package com.example.grocerieslist.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.product.ProductClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 25/07/21.
 */
public class ProductAdapter extends ArrayAdapter<ProductClass> implements Filterable {
    private Activity act;
    int layoutResourceId;
    List<ProductClass> ccs;
    List<ProductClass> filterList;
    ProductClass pc;
    int numChange = 0;
    CustomFilter filter;
    MenuHolder holder;

    public ProductAdapter(Activity act, int resource, List<ProductClass> data_list) {
        super(act, resource, data_list);
        this.act = act;
        this.layoutResourceId = resource;
        this.ccs = data_list;
    }

    public View getView(final int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(layoutResourceId, parent, false);
        holder = new MenuHolder();

        holder.name = row.findViewById(R.id.pd_name);
        holder.cPrice = row.findViewById(R.id.pd_cPrice);
        holder.wPrice = row.findViewById(R.id.pd_wPrice);
        holder.rPrice = row.findViewById(R.id.pd_rPrice);
        holder.mrp = row.findViewById(R.id.pd_mPrice);

        if(pos%2 != 0)
            row.setBackgroundColor(act.getResources().getColor(android.R.color.darker_gray));

        pc = ccs.get(pos);
        String namePack = pc.getPackingsize() + pc.getPackuom();
        holder.name.setText(pc.getName()+" "+namePack);
        holder.cPrice.setText(pc.getCost());
        holder.wPrice.setText(pc.getSpecial());
        holder.rPrice.setText(pc.getRetail());
        holder.mrp.setText(pc.getMrp());

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
        TextView name,cPrice,wPrice,rPrice,mrp;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint != null && constraint.length()>0) {
                constraint=constraint.toString().toUpperCase();
                ArrayList<ProductClass> filters=new ArrayList<ProductClass>();
                for(int i=0;i<filterList.size();i++) {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint)) {
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
