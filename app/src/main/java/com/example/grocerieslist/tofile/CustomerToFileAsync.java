package com.example.grocerieslist.tofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.grocerieslist.db.customer.CustomerClass;
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
public class CustomerToFileAsync  extends AsyncTask<Void,Void,Void> {
    String TAG = StockAdjToFileAsync.class.getSimpleName();
    private BufferedWriter file;
    private ProgressDialog dialog;
    List<CustomerClass> pcs;
    AppGlobal global;
    Activity act;

    public CustomerToFileAsync(Activity act,List<CustomerClass> obj) {
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
            CustomerClass psc = pcs.get(i);
            write(TAG,new String[]{psc.getName(),psc.getAddress(),psc.getAddress1(),psc.getAddress2(),psc.getPlace(),psc.getState(),psc.getScode(),psc.getPincode(),psc.getPhone1(),psc.getPhone2(),psc.getEmail(),psc.getGst(),psc.getType()});
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
        String name = "MyStock_Customer_"+ sdf.format(currentdate) + ".csv";
        filename = new File(directory, name);
        try {
            file = new BufferedWriter(new FileWriter(filename));
            write(TAG,new String[]{"Name","Address","Address 1","Address 2","Place","State","State Code","Pincode","Phone 1","phone 2","Email","GST Number","Type"});
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

    private void stopRecording() {
        try {
            file.close();
            if(filename != null)
                global.setPreference(global.App_Customer_Path_Lastest,filename.getAbsolutePath(), Constant.Pref_String,null);
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
