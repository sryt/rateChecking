package com.example.grocerieslist.tofile;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.customer.CustomerAccess;
import com.example.grocerieslist.db.customer.CustomerClass;
import com.example.grocerieslist.db.stock.StockClass;
import com.example.grocerieslist.init.Loading;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.core.app.NotificationCompat;


/**
 * Created by Tejaswi on 19/07/21.
 */
public class StockAdjToFileAsync extends AsyncTask<Void,Void,Void> {
        String TAG = StockAdjToFileAsync.class.getSimpleName();
        private BufferedWriter file;
        private ProgressDialog dialog;
        List<StockClass> pscs;
        AppGlobal global;
        Activity act;

    public StockAdjToFileAsync(Activity act, List<StockClass> obj) {
        this.act = act;
        this.pscs = obj;
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
        CustomerAccess ca =new CustomerAccess(act);
        for(int i=0;i<pscs.size();i++) {
            StockClass psc = pscs.get(i);
            String ts = global.dateFormat(psc.getTs(),global.DATE_AMPM);
            ca.open();
            CustomerClass cc = ca.getCustomerDetailsById(psc.getCustId());
            ca.close();
            if(cc != null)
                write(TAG,new String[]{psc.getProName(),cc.getName(),ts,psc.getQty(),psc.getType(),psc.getRemark()});
            else
                write(TAG,new String[]{psc.getProName(),psc.getCustId(),ts,psc.getQty(),psc.getType(),psc.getRemark()});
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
        String name = "MyStock_StockAdjustment_"+ sdf.format(currentdate) + ".csv";
        filename = new File(directory, name);
        try {
            file = new BufferedWriter(new FileWriter(filename));
            write(TAG,new String[]{"Product Name","Customer Name","Date & Time","Quantity","Type","Remark"});
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
                global.setPreference(global.App_Stock_Path_Lastest,filename.getAbsolutePath(), Constant.Pref_String,null);
            global.displayPreference();
            addNotification(act.getResources().getString(R.string.app_name),filename.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void addNotification(String title,String msg) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(act.getApplicationContext(), "notify_001");
        Intent ii = new Intent(act.getApplicationContext(), Loading.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(act, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(msg); //detail mode is the "expanded" notification
        bigText.setBigContentTitle(msg);
        bigText.setSummaryText("Downloaded"); //small text under notification

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.logo); //notification icon
        mBuilder.setContentTitle(title); //main title
        mBuilder.setContentText(msg); //main text when you "haven't expanded" the notification yet
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager = (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        if (mNotificationManager != null) {
            mNotificationManager.notify(0, mBuilder.build());
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
