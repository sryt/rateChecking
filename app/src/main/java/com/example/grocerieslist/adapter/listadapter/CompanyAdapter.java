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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.company.CompanyClass;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejaswi on 25/07/21.
 */
public class CompanyAdapter extends ArrayAdapter<CompanyClass> implements Filterable {
        String TAG = CompanyAdapter.class.getSimpleName();

        private Activity act;
        int layoutResourceId;
        List<CompanyClass> ccs;
        List<CompanyClass> filterList;
        CompanyClass pc;
        AppGlobal global;
        CustomFilter filter;
        MenuHolder holder;

        public CompanyAdapter(Activity act, int resource, List<CompanyClass> data_list) {
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

            holder.ll = row.findViewById(R.id.cl_ll);
            holder.img = row.findViewById(R.id.cl_img);
            holder.name = row.findViewById(R.id.cl_prodName);
            holder.desc = row.findViewById(R.id.cl_desc);

            pc = ccs.get(pos);
            Log.i(TAG,"company details is "+pc.toString());
            holder.name.setText(pc.getCompanyName());
            holder.desc.setText(pc.getPhoneNumber());

            if(pc.getId().equals(String.valueOf(global.getPreference(global.App_Selected_Company))))
                holder.name.setTextColor(Color.GREEN);

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
                    if(filterList.get(i).getCompanyName().toUpperCase().contains(constraint)) {
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
