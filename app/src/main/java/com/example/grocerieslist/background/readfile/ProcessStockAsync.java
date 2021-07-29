package com.example.grocerieslist.background.readfile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.grocerieslist.db.customer.CustomerAccess;
import com.example.grocerieslist.db.customer.CustomerClass;
import com.example.grocerieslist.db.stock.StockAccess;
import com.example.grocerieslist.db.stock.StockClass;
import com.example.grocerieslist.utilities.AppGlobal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by Tejaswi on 21/07/21.
 */
public class ProcessStockAsync extends AsyncTask<Void,Void,Void> {
    String TAG = ProcessDataAsync.class.getSimpleName();
    String path;
    Activity act;
    AppGlobal global;
    Intent data;
    private ProgressDialog dialog;
    String[] ids;

    public ProcessStockAsync(String path, Activity act, Intent data) {
        this.path = path;
        this.act = act;
        this.data = data;
        global = new AppGlobal(act);
        dialog = new ProgressDialog(act);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Processing the file and saving the data.");
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Uri content_describer = data.getData();

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        try {
            InputStream inputStream = act.getContentResolver().openInputStream(content_describer);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String csvLine;
                while ((csvLine = reader.readLine()) != null) {
                    Log.i(TAG,"csv line is "+csvLine);
                    ids=csvLine.split(",");
                    try{
                        /**
                         * 0 - Name
                         * 1 - Customer Name
                         * 2 - Date Time
                         * 3 - Quantity
                         * 4 - Type
                         * 5 - Remark
                         **/

                        String name = checkValue(ids[0]);
                        String custName = checkValue(ids[1]);
                        String ts = checkValue(ids[2]);
                        String qty = checkValue(ids[3]);
                        String type = checkValue(ids[4]);
                        String remark = checkValue(ids[5]);

                        CustomerAccess ca = new CustomerAccess(act);
                        ca.open();
                        CustomerClass cc = ca.getCustomerDetailsByName(custName);
                        ca.close();

                        if(cc != null){
                            StockClass psc = new StockClass(name,cc.getId(),"","",ts,type,qty,remark);
                            StockAccess psa = new StockAccess(act);
                            psa.open();
                            psa.addProductStockDetails(psc);
                            psa.close();
                        }else{
                            Toast.makeText(act,"Kindly add the customer "+custName+". And try again to add missing stock",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Log.e(TAG,e.toString());
                    }
                }
            }
            catch (IOException ex) {
                throw new RuntimeException("Error in reading CSV file: "+ex);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(act,"Completed the file parsing...",Toast.LENGTH_SHORT).show();
    }

    public String checkValue(String str){
        if(str.equals(null))
            return "NA";
        else
            return str;
    }
}
