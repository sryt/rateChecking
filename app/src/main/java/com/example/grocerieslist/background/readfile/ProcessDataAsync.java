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

import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by Tejaswi on 27/06/21.
 */
public class ProcessDataAsync extends AsyncTask<Void,Void,Void> {
    String TAG = ProcessDataAsync.class.getSimpleName();
    String path;
    Activity act;
    Intent data;
    private ProgressDialog dialog;
    String[] ids;

    public ProcessDataAsync(String path, Activity act,Intent data) {
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
                             * 0 - Name                        7 - Selling Rate
                             * 1 - Group                       8 - Special Rate
                             * 2 - Company                     9 - Packing Unit
                             * 3 - GST                         10 - Pcs per Sheet
                             * 4 - P Rate                      11 - Supplier
                             * 5 - c Rate                      12 - HSN
                             * 6 - MRP                         13 - Weight
                            **/

                            String name = checkValue(ids[0]);
                            String group = checkValue(ids[1]);
                            String company = checkValue(ids[2]);
                            String gst = checkValue(ids[3]);
                            String pPrice = checkValue(ids[4]);
                            String cPrice = checkValue(ids[5]);
                            String mrp = checkValue(ids[6]);
                            String rPrice = checkValue(ids[7]);
                            String wPrice = checkValue(ids[8]);
                            String uom = checkValue(ids[9]);
                            String sheet = checkValue(ids[10]);
                            String supplier= "Not Mention";
                            if(ids.length >11 && ids[11] != null){
                                supplier = checkValue(ids[11]);
                            }
                            String hsn = "Not Mention";
                            if(ids.length > 12 && ids[12] != null){
                                hsn = checkValue(ids[12]);
                            }

                            String caseqty = "0";
                            if(ids.length > 13 && ids[13] != null)
                                caseqty = checkValue(ids[13]);

                            String desc = name+ " of brand "+ company +", is supplied by " + supplier+". Product packing weight is "+caseqty+uom;

                            setProduct(name,pPrice,cPrice,wPrice,rPrice,mrp,caseqty,sheet,gst,hsn,uom,company,group,desc);
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

    public void setProduct(String name,String pPrice,String cPrice,String wPrice,String rPrice,String mrp,String caseQty,String sheet,
                           String gst,String hsn,String uom,String type,String group,String desc){
        ProductClass pc1 = new ProductClass(name,desc,String.valueOf(System.currentTimeMillis()),"","",
                uom,"",mrp,pPrice,cPrice,wPrice,rPrice,caseQty,sheet,
                gst,hsn,type, group, Constant.ACTIVE);

        Log.i(TAG,"saving class "+pc1.toString());
        ProductAccess pa = new ProductAccess(act);
        pa.open();
        pa.addProdDetails(pc1);
        pa.close();
    }

    public String checkValue(String str){
        if(str.equals(null))
            return "NA";
        else
            return str;
    }


}
