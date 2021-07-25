package com.example.grocerieslist.init;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.listadapter.PriceDetailsAdapter;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 17/01/21.
 */
public class PriceDetials extends AppCompatActivity {
    String TAG = PriceDetials.class.getSimpleName();
    AppGlobal global;
    ListView lv;
    SearchView txt;
    Button filterBtn;
    PriceDetailsAdapter priceDetailsAdapter;
    List<ProductClass> pcs;
    List<String> proNameFilter;
    List<ProductClass> pcFilter;

    boolean filterFlag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pricelayout);

        global = new AppGlobal(PriceDetials.this);

        lv = findViewById(R.id.listview);
        txt = findViewById(R.id.search);
        filterBtn = findViewById(R.id.filter);

        /**
         * back button in tool bar
         */
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_settings) {
            Intent create = new Intent(PriceDetials.this,SettingsPage.class);
            startActivity(create);
            return true;
        }else if (id == R.id.action_price) {
            Intent create = new Intent(PriceDetials.this,Dashboard.class);
            startActivity(create);
            return true;
        }else if(id == R.id.action_filterview){
            Intent filterView = new Intent(PriceDetials.this,FilterView.class);
            startActivity(filterView);
            return true;
        }else if(id == R.id.action_company){
            Intent filterView = new Intent(PriceDetials.this,CompanyList.class);
            startActivity(filterView);
            return true;
        }else if(id == R.id.action_cart){
            Intent filterView = new Intent(PriceDetials.this,MainActivity.class);
            startActivity(filterView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getProductName();

        priceDetailsAdapter = new PriceDetailsAdapter(PriceDetials.this,R.layout.pricedetials,pcs);

        txt.setQueryHint("Search over "+pcs.size());
        priceDetailsAdapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(priceDetailsAdapter);

        txt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchFlow(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchFlow(s);
                return false;
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(PriceDetials.this);
                dialog.setContentView(R.layout.homefragment);

                dialog.setTitle("Select the category.");
                SearchView searchView = dialog.findViewById(R.id.search);
                searchView.setVisibility(View.GONE);

                ListView lv = dialog.findViewById(R.id.listview);

                ProductAccess pa = new ProductAccess(PriceDetials.this);
                pa.open();
                final List<String> category = pa.getProdCatagoryDetails();
                pa.close();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(PriceDetials.this,android.R.layout.simple_list_item_1,category);
                lv.setAdapter(adapter);

                adapter.sort(new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                });

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        rearrangeList(category.get(i));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    public void rearrangeList(String str){
        pcs = new ArrayList<>();
        Toast.makeText(getApplicationContext(),"Selected category is "+str,Toast.LENGTH_SHORT).show();

        ProductAccess pa = new ProductAccess(PriceDetials.this);
        pa.open();
        pcs = pa.getProdCatagoryDetails(str);
        pa.close();

        for(int i=0;i<pcs.size();i++)
            Log.i(TAG,i+" "+pcs.get(i).getName());

        priceDetailsAdapter = new PriceDetailsAdapter(PriceDetials.this,R.layout.pricedetials,pcs);
        priceDetailsAdapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(priceDetailsAdapter);
    }

    public void searchFlow(String s){
        filterFlag = true;
        pcFilter = new ArrayList<>();
        proNameFilter = new ArrayList<>();
        for(ProductClass pc : pcs){
            if(pc.getName().toLowerCase().contains(s.toLowerCase())){
                pcFilter.add(pc);
                proNameFilter.add(pc.getName());
            }
        }

        priceDetailsAdapter = new PriceDetailsAdapter(PriceDetials.this,R.layout.pricedetials,pcFilter);
        priceDetailsAdapter.sort(new Comparator<ProductClass>() {
            @Override
            public int compare(ProductClass lhs, ProductClass rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        lv.setAdapter(priceDetailsAdapter);
    }

    public void getProductName(){
        ProductAccess pa = new ProductAccess(PriceDetials.this);

        pa.open();
        pcs = pa.getProdDetails();
        pa.close();

        Log.i(TAG,"product list size is "+pcs.size());
    }


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
