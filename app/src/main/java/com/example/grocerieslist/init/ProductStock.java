package com.example.grocerieslist.init;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.customer.CustomerAccess;
import com.example.grocerieslist.db.customer.CustomerClass;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.db.stock.StockAccess;
import com.example.grocerieslist.db.stock.StockClass;
import com.example.grocerieslist.db.stockdetails.StockDetailsAccess;
import com.example.grocerieslist.db.stockdetails.StockDetailsClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

/**
 * Created by Tejaswi on 22/07/21.
 */
public class ProductStock extends AppCompatActivity {
    String TAG = ProductStock.class.getSimpleName();

    CardView items,save;
    LinearLayout itemsList;
    TextInputEditText dateTime,prodQtytxt,remark;
    MaterialAutoCompleteTextView prodNametxt,custName,location,pickup;
    RadioButton inward,outward;

    AppGlobal global;
    List<ProductClass> pcs;
    List<CustomerClass> ccs;
    List<String> prodName,custList;
    String custId,locId,pupId;
    StockDetailsClass loc,pup;

    StockDetailsAccess sda;
    StockAccess psa;
    CustomerAccess ca;
    ProductAccess pa;

    List<StockDetailsClass> pickUps;
    List<String> pickupList,locationList;
    List<StockDetailsClass> locations;

    String cmd = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_prod_stock);

        pa = new ProductAccess(ProductStock.this);
        psa = new StockAccess(ProductStock.this);
        ca = new CustomerAccess(ProductStock.this);

        global = new AppGlobal(ProductStock.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cmd = getIntent().getStringExtra("cmd");
    }

    @Override
    protected void onResume() {
        super.onResume();

        custName = findViewById(R.id.lcs_cust_name);
        dateTime = findViewById(R.id.lcs_date);
        inward = findViewById(R.id.lcs_type_sales);
        outward = findViewById(R.id.lcs_type_purchase);
        itemsList = findViewById(R.id.lcs_add_items);
        remark = findViewById(R.id.lcs_remark);
        prodNametxt = itemsList.findViewById(R.id.li_prodname);
        prodQtytxt = itemsList.findViewById(R.id.li_prodqty);
        items = findViewById(R.id.lcs_items);
        location  = findViewById(R.id.lcs_location);
        pickup = findViewById(R.id.lcs_pickup_person);
        save = findViewById(R.id.lcs_save);

        if(cmd != null){
            if(cmd.equals("edit")){
                setData();
            }
            Log.i(TAG,""+cmd);
        }

        prepare();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ProductStock.this,android.R.layout.simple_list_item_1,prodName);
        prodNametxt.setAdapter(adapter);

        dateTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    setDateTime(dateTime);
            }
        });

        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = prodNametxt.getText().toString().trim();
                String qty = prodQtytxt.getText().toString().trim();
                if(txt.isEmpty()){
                    if(qty.isEmpty()){
                        prodQtytxt.setError("Quantity ?");
                    }else{
                        prodNametxt.setError("Enter the product name.");
                    }
                }else
                    addView(itemsList);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cust = custName.getText().toString().trim();
                String type = Constant.INWARDS;

                if(outward.isChecked())
                    type = Constant.OUTWARDS;

                String remarkStr = remark.getText().toString().trim();
                ca.open();
                CustomerClass cc = ca.getCustomerDetailsByName(cust);
                ca.close();
                if(itemsList.getChildCount() > 0){
                    Calendar c = Calendar.getInstance();
                    String msg = "Eashwar Traders goods details of "+c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                    msg = msg +"\n"+"--------------------------------------------------------------------------------";
                    msg = msg +"\nName"+"&#160;&#160;&#160;&#160;&#160;"+"Type"+"          "+"Quantity"+"          "+"          "+"Remark";
                    for(int i=0;i<itemsList.getChildCount();i++){
                        LinearLayout ll = (LinearLayout) itemsList.getChildAt(i);
                        MaterialAutoCompleteTextView txt = ll.findViewById(R.id.li_prodname);
                        TextInputEditText qty = ll.findViewById(R.id.li_prodqty);
                        Log.i(TAG,"name is "+txt.getText().toString().trim()+"/qty is "+qty.getText().toString().trim());

                        StockClass psc = new StockClass(txt.getText().toString().trim(),cc.getId(),"","",ts,type,qty.getText().toString().trim(),remarkStr);
                        Log.i(TAG,"class value is "+psc.toString());
                        psa.open();
                        psa.addProductStockDetails(psc);
                        psa.close();
                        msg = msg +"\n"+psc.getProName()+"          "+psc.getType()+"          "+psc.getQty()+"          "+psc.getRemark();
                    }
                    msg = msg +"\n"+"--------------------------------------------------------------------------------";
                    global.sendWhatsApp(msg);
                }
                onBackPressed();
            }
        });
    }

    /**
     * Set the data for editing the entry
     */
    StockClass passingData;
    CustomerClass customerClass;
    public void setData(){
        String id = getIntent().getStringExtra("id");

        StockAccess psa = new StockAccess(ProductStock.this);
        psa.open();
        passingData = psa.getProductStockById(id);
        psa.close();

        prodNametxt.setText(passingData.getProName());
        prodQtytxt.setText(passingData.getQty());
        remark.setText(passingData.getRemark());
        dateTime.setText(global.dateFormat(passingData.getTs(),global.DATE_AMPM));
        if(passingData.getType().equals(Constant.OUTWARDS)) {
            outward.setChecked(true);
            inward.setChecked(false);
        }else{
            outward.setChecked(false);
            inward.setChecked(true);
        }

        items.setVisibility(View.GONE);

        CustomerAccess ca = new CustomerAccess(ProductStock.this);
        ca.open();
        customerClass = ca.getCustomerDetailsById(passingData.getCustId());
        ca.close();
        custName.setText(customerClass.getName());

        if(!passingData.getLocationId().isEmpty() || passingData.getLocationId() != null){
            sda = new StockDetailsAccess(ProductStock.this);
            sda.open();
            loc = sda.getStockDetailsById(passingData.getLocationId());
            pup = sda.getStockDetailsById(passingData.getPersonId());
            sda.close();

            pickup.setText(pup.getName());
            location.setText(loc.getName());
        }
    }

    /**
     * Save the content to the database for feature references.
     */
    public void saveStock(){
        String proNameStr = prodNametxt.getText().toString().trim();
        String qtyStr = prodQtytxt.getText().toString().trim();
        String typeStr = Constant.OUTWARDS;
        String remarkStr = remark.getText().toString().trim();

        if(inward.isChecked())
            typeStr = Constant.INWARDS;

        StockClass pcs = new StockClass(proNameStr,custId,locId,pupId,ts,typeStr,qtyStr,remarkStr);
        Log.i(TAG,"product class stock "+pcs.toString());

        StockAccess pca = new StockAccess(ProductStock.this);
        pca.open();
        pca.addProductStockDetails(pcs);
        pca.close();
    }

    /**
     * Update the saved record.
     */
    public void updateStock(){
        String proNameStr = prodNametxt.getText().toString().trim();
        String qtyStr = prodQtytxt.getText().toString().trim();
        String typeStr = Constant.OUTWARDS;
        String locStr = location.getText().toString();
        String pupStr = pickup.getText().toString();
        String remarkStr = remark.getText().toString().trim();

        if(inward.isChecked())
            typeStr = Constant.INWARDS;

        String cusName = custName.getText().toString().trim();
        CustomerAccess ca = new CustomerAccess(ProductStock.this);
        ca.open();
        CustomerClass cc = ca.getCustomerDetailsByName(cusName);
        ca.close();

        sda = new StockDetailsAccess(ProductStock.this);
        sda.open();
        StockDetailsClass sdcLoc = sda.getStockDetailsByName(locStr);
        StockDetailsClass sdcPup = sda.getStockDetailsByName(pupStr);
        sda.close();

        SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yyyy hh:mm a");
        try {
            Date gmt = formatter.parse(dateTime.getText().toString().trim());
            StockClass pcs = new StockClass(passingData.getId(),proNameStr,cc.getId(),sdcLoc.getId(),sdcPup.getId(),String.valueOf(gmt.getTime()),typeStr,qtyStr,remarkStr);
            Log.i(TAG,"product class stock "+pcs.toString());

            StockAccess pca = new StockAccess(ProductStock.this);
            pca.open();
            boolean res = pca.updateStock(pcs);
            pca.close();

            if(res){
                Snackbar.make(custName,"Successfully updated the record",Snackbar.LENGTH_SHORT).show();
                onBackPressed();
            }else{
                Snackbar.make(custName,"Error while updating the record. Try later....",Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }).setActionTextColor(Color.BLUE).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * Clean all the input field.
     */
    public void clearForm(){
        prodNametxt.setText("");
        dateTime.setText("");
        prodQtytxt.setText("");
        remark.setText("");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void prepare(){
        pa.open();
        pcs = pa.getProdDetails();
        pa.close();

        if(pcs.size() > 0){
            prodName = new ArrayList<>();
            for(int i=0;i<pcs.size();i++){
                prodName.add(pcs.get(i).getName());
            }
        }

        ca.open();
        List<CustomerClass> ccs = ca.getCustomerDetails();
        ca.close();
        if(ccs.size() > 0){
            custList = new ArrayList<>();
            for(int i=0;i<ccs.size();i++){
                custList.add(ccs.get(i).getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ProductStock.this,android.R.layout.simple_list_item_1,custList);
            custName.setAdapter(adapter);
        }

        sda = new StockDetailsAccess(ProductStock.this);
        sda.open();
        pickUps = sda.getStockDetailsByType(Constant.PERSON);
        locations = sda.getStockDetailsByType(Constant.PLACE);
        sda.close();

        if(pickUps.size() > 0){
            pickupList = new ArrayList<>();
            for(int i=0;i<pickUps.size();i++)
                pickupList.add(pickUps.get(i).getName());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ProductStock.this,android.R.layout.simple_list_item_1,pickupList);
            pickup.setAdapter(adapter);
        }

        if(locations.size() > 0){
            locationList = new ArrayList<>();
            for(int i=0;i<locations.size();i++)
                locationList.add(locations.get(i).getName());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ProductStock.this,android.R.layout.simple_list_item_1,locationList);
            location.setAdapter(adapter);
        }
    }

    String ts;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    public void setDateTime(final TextInputEditText tiet){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ProductStock.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myYear = i;
                myday = i2;
                myMonth = i1;
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProductStock.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int i1) {
                        myHour = hourOfDay;
                        myMinute = i1;

                        Calendar selected = Calendar.getInstance();
                        selected.set(Calendar.YEAR,myYear);
                        selected.set(Calendar.MONTH,myMonth);
                        selected.set(Calendar.DAY_OF_MONTH,myday);
                        selected.set(Calendar.HOUR_OF_DAY,myHour);
                        selected.set(Calendar.MINUTE,myMinute);

                        ts = String.valueOf(selected.getTimeInMillis());
                        tiet.setText(global.dateFormat(ts,global.DATE_DATETIME));
                    }
                }, hour, minute, DateFormat.is24HourFormat(getApplicationContext()));
                timePickerDialog.show();
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void addView(LinearLayout ll){
        LinearLayout debugLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_item, ll, false);
        MaterialAutoCompleteTextView name = debugLayout.findViewById(R.id.li_prodname);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductStock.this,android.R.layout.simple_list_item_1,prodName);
        name.setAdapter(adapter);
        ll.addView(debugLayout);
        Log.i(TAG,"child id is "+ll.getChildCount());
    }
}
