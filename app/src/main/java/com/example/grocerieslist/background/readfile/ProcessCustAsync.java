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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by Tejaswi on 13/07/21.
 */
public class ProcessCustAsync extends AsyncTask<Void,Void,Void> {
        String TAG = ProcessDataAsync.class.getSimpleName();
        String path;
        Activity act;
        Intent data;
        private ProgressDialog dialog;
        String[] ids;

    public ProcessCustAsync(String path, Activity act,Intent data) {
        this.path = path;
        this.act = act;
        this.data = data;
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
                         * 0 - Name                     7 - State code
                         * 1 - Address                  8 - Phone1
                         * 2 - Place                    9 - Email
                         * 3 - aad1                     10 - GST Number
                         * 4 - add2                     11 - Type
                         * 5 - phone                    12 - Status
                         * 6 - pin                      13 - State
                         **/

                        String name = checkValue(ids[0]);
                        String address = checkValue(ids[1]);
                        String place = checkValue(ids[2]);
                        String add1 = checkValue(ids[3]);
                        String add2 = checkValue(ids[4]);
                        String phone = checkValue(ids[5]);
                        String pin = checkValue(ids[6]);
                        String stateCode = checkValue(ids[7]);
                        String phone1 = checkValue(ids[8]);
                        String email = checkValue(ids[9]);
                        String gst = checkValue(ids[10]);
                        String type= checkValue(ids[11]);
                        String status = checkValue(ids[12]);
                        String state = checkValue(ids[13]);

                        CustomerClass cc = new CustomerClass(name,address,add1,add2,place,state,stateCode,pin,phone,phone1,email,gst,type,status);
                        Log.i(TAG,"customer class is "+cc.toString());
                        CustomerAccess ca = new CustomerAccess(act);
                        ca.open();
                        ca.addCustomerDetails(cc);
                        ca.close();
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