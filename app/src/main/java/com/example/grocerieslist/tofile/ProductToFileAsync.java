package com.example.grocerieslist.tofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Tejaswi on 20/07/21.
 */
public class ProductToFileAsync extends AsyncTask<Void,Void,Void> {
    String TAG = StockAdjToFileAsync.class.getSimpleName();
    private BufferedWriter file;
    private ProgressDialog dialog;
    List<ProductClass> pcs;
    AppGlobal global;
    Activity act;

    public ProductToFileAsync(Activity act,List<ProductClass> obj) {
        this.act = act;
        this.pcs = obj;
        global = new AppGlobal(act);
        dialog = new ProgressDialog(act);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Please wait, writing the data to the file...");
        dialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        startRecording();
        for(int i=0;i<pcs.size();i++) {
            ProductClass psc = pcs.get(i);
            String ts = global.dateFormat(psc.getTs(),global.DATE_AMPM);
            //write(TAG,new String[]{psc.getName(),psc.getDesc(),ts,psc.getPackuom(),psc.getUom(),psc.getPackingsize(),psc.getMrp(),psc.getPurRate(),psc.getCost(),psc.getSpecial(),psc.getRetail(),psc.getGst(),psc.getHsn(),psc.getType(),psc.getGroup(),psc.getCaseqty(),psc.getSheetNo()});
            write(psc);
        }
        return null;
    }

    File filename;
    private void startRecording() {
        // Prepare data storage
        File directory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMddyyyyHHmm");
        Date currentdate = new Date(System.currentTimeMillis());
        String name = "MyStock_Product_"+ sdf.format(currentdate) + ".csv";
        filename = new File(directory, name);
        try {
            file = new BufferedWriter(new FileWriter(filename));
            write(TAG,new String[]{"Name","Description","DateTime","PackingUint","UnitofMeasurement","PackingSize","MRP","PurchaseRate","CostRate","WholeSale","RetailSale","GSTPercentage","HSNCode","Type","Group","CaseQuantity","PerSheet"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String tag, String[] values) {
        if (file == null) {
            return;
        }
        String line = "";
        if (values != null) {
            for (String value : values) {
                if(line.isEmpty())
                    line = value;
                else
                    line += "," + value;
            }

        }
        line = line + "\n";
        Log.i(TAG,tag+" "+line);
        try {
            file.write(line);
            Log.i(TAG,"Write completed....");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void write(ProductClass psc) {
        if (file == null) {
            return;
        }
        String ts = global.dateFormat(psc.getTs(),global.DATE_AMPM);
        String line = "";
        if(psc != null)
            line = psc.getName()+","+" "+","+ts+","+psc.getPackuom()+","+psc.getUom()+","+psc.getPackingsize()+","+psc.getMrp()+","+psc.getPurRate()+","+psc.getCost()+","+psc.getSpecial()+","+psc.getRetail()+","+psc.getGst()+","+psc.getHsn()+","+psc.getType()+","+psc.getGroup()+","+psc.getCaseqty()+","+psc.getSheetNo();
        line = line + "\n";
        Log.i(TAG,line);
        try {
            file.write(line);
            Log.i(TAG,"Write completed....");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        try {
            file.close();
            if(filename != null)
                global.setPreference(global.App_Product_Path_Lastest,filename.getAbsolutePath(), Constant.Pref_String,null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i(TAG,"post execute called");
        stopRecording();
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(act,"Completed the file parsing...",Toast.LENGTH_SHORT).show();
    }
}
