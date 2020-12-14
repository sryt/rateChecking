package com.example.grocerieslist.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Tejaswi on 12/12/20.
 */
public class CustomAdapter extends ArrayAdapter<ProductClass> implements Filterable {
    String TAG = CustomAdapter.class.getSimpleName();

    private Activity act;
    int layoutResourceId;
    List<ProductClass> ccs;
    List<ProductClass> filterList;
    ProductClass pc;
    int numChange = 0;
    CustomFilter filter;
    MenuHolder holder;

    public CustomAdapter(Activity act, int resource, List<ProductClass> data_list) {
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

        holder.ll = row.findViewById(R.id.cl_ll);
        holder.img = row.findViewById(R.id.cl_img);
        holder.name = row.findViewById(R.id.cl_prodName);
        holder.desc = row.findViewById(R.id.cl_desc);

        pc = ccs.get(pos);
        String namePack = pc.getPackingsize() + pc.getPackuom();
        holder.name.setText(pc.getName());
        holder.desc.setText(namePack);

        /*if (pc.getGroup().equals(Constant.Groceries)) {
            holder.img.setImageResource(R.mipmap.ic_groceries);
        } else if (pc.getGroup().equals(Constant.Snacks)) {
            holder.img.setImageResource(R.mipmap.ic_snacks);
        } else if (pc.getGroup().equals(Constant.Cleaning)) {
            holder.img.setImageResource(R.mipmap.ic_cleaning);
        } else if (pc.getGroup().equals(Constant.Toilet)) {
            holder.img.setImageResource(R.mipmap.ic_toilet);
        }*/

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
        LinearLayout ll;
        TextView name,desc;
        ImageView img;
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
