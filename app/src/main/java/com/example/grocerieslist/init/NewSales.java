package com.example.grocerieslist.init;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

/**
 * Created by Tejaswi on 01/07/21.
 */
public class NewSales extends AppCompatActivity {
    String TAG = NewSales.class.getSimpleName();

    TextInputEditText billNo,dateCur,custName,phoneNumber;
    TextView totalAmt;
    CardView save;
    View items;

    EditText sino,qty,rate,amt;
    AutoCompleteTextView proname;

    List<ProductClass> pcs;
    List<String> proString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_sales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        billNo = findViewById(R.id.lns_no);
        dateCur = findViewById(R.id.lns_date);
        custName = findViewById(R.id.lns_name);
        phoneNumber =findViewById(R.id.lns_phone);
        items = findViewById(R.id.lns_items);
        totalAmt = findViewById(R.id.lns_total_amt);
        save = findViewById(R.id.lns_save);

        sino = items.findViewById(R.id.ii_sino);
        proname = items.findViewById(R.id.ii_pName);
        qty = items.findViewById(R.id.ii_qty);
        rate = items.findViewById(R.id.ii_rate);
        amt = items.findViewById(R.id.ii_amt);

        preProductList();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void preProductList(){
        pcs = new ArrayList<>();
        proString = new ArrayList<>();
        ProductAccess pa = new ProductAccess(NewSales.this);
        pa.open();
        pcs = pa.getProdDetails();
        pa.close();

        if(pcs != null){
            if(pcs.size() > 0){
                for(int i=0;i<pcs.size();i++)
                    proString.add(pcs.get(i).getName());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,proString);
                proname.setThreshold(1);
                proname.setAdapter(adapter);
                Log.i(TAG,"products are "+proString.toString());
            }
        }else
            Snackbar.make(proname,"Kindly add the products first",Snackbar.LENGTH_SHORT).show();

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

}
