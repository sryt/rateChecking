package com.example.grocerieslist.init;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.ExpandableListAdapter;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 29/06/21.
 */
public class FilterView extends AppCompatActivity {
    String TAG = FilterView.class.getSimpleName();

    AppGlobal appGlobal;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<HashMap<String,String>> listDataHeader;
    HashMap<HashMap<String,String>, List<ProductClass>> listDataChild;

    List<String> filterHeader;
    List<ProductClass> pcs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extended_layout);

        appGlobal = new AppGlobal(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        filterList();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void filterList(){
        ProductAccess pa = new ProductAccess(FilterView.this);
        pa.open();
        filterHeader = pa.getProdCatagoryDetails();
        pa.close();

        Collections.sort(filterHeader, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });


        if(filterHeader.size() > 0 ){
            for(int i=0;i<filterHeader.size();i++){
                List<String> nowAdd = new ArrayList<String>();
                String groupName = filterHeader.get(i);

                //Add child to the view
                pa.open();
                pcs = pa.getProdCatagoryDetails(groupName);
                pa.close();

                if(pcs.size() > 0){
                    for(int k=0;k<pcs.size();k++){
                        nowAdd.add(pcs.get(k).getName());
                    }
                }
                HashMap<String,String> headMap = new HashMap<>();
                headMap.put("name",groupName);
                headMap.put("value",String.valueOf(pcs.size()));
                listDataHeader.add(headMap);
                listDataChild.put(listDataHeader.get(i),pcs);

            }
        }
    }

}
