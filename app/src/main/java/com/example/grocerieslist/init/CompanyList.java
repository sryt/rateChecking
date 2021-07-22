package com.example.grocerieslist.init;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.company.CompanyAccess;
import com.example.grocerieslist.db.company.CompanyClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 30/06/21.
 */
public class CompanyList extends AppCompatActivity {
    String TAG = CompanyList.class.getSimpleName();

    AppGlobal appGlobal;
    SearchView search;
    ListView listview;

    List<CompanyClass> ccs;
    List<String> compNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homefragment);

        appGlobal = new AppGlobal(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        search = findViewById(R.id.search);
        search.setVisibility(View.GONE);

        listview = findViewById(R.id.listview);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = ccs.get(i).getCompanyName();

                if(!str.equals(getResources().getString(R.string.company_adding))){
                    Snackbar.make(listview,getResources().getString(R.string.company_adding)+" "+str,Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(listview,getResources().getString(R.string.company_adding),Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        setData();
    }

    private void setData() {
        CompanyAccess ca = new CompanyAccess(CompanyList.this);
        ca.open();
        ccs = ca.getCompaniesDetails();
        ca.close();

        Log.i(TAG,"size is "+ccs.size());
        if(ccs.size()>0){
            compNames = new ArrayList<>();
            for(int i=0;i<ccs.size();i++)
                compNames.add(ccs.get(i).getCompanyName());
        }else {
            compNames = new ArrayList<>();
            compNames.add(getResources().getString(R.string.company_adding));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CompanyList.this,android.R.layout.simple_list_item_1,compNames);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_company, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }else if (id == R.id.action_add) {
            Intent create = new Intent(CompanyList.this,CompanyDetails.class);
            startActivity(create);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
