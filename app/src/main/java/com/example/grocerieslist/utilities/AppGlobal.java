package com.example.grocerieslist.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tejaswi on 09/12/20.
 */
public class AppGlobal {
    String TAG = AppGlobal.class.getSimpleName();
    Activity act;
    Context ctx;

    /**
     * Shared Preference of the user details
     */
    public String App_UserId = "userid";
    public String App_Name = "name";
    public String App_Username = "userName";
    public String App_Password = "password";
    public String App_Role = "role";
    public String App_Mobile_Number = "mobileNumber";
    public String App_Email = "email";
    public String App_Gender = "gender";
    public String App_WhatsApp_Number = "whatsAppNumber";
    public String App_Pass_Code = "passCode";

    public String App_Selected_Prod = "prodSelected";
    public String App_Selected_Company = "compSelected";

    public String App_Selected_Path = "prodPath";

    public String App_Product_Path = "productsPath";
    public String App_Customer_Path = "cusomterPath";
    public String App_Stock_Path = "stockPath";

    public String App_Product_Path_Lastest = "prodPathLatest";
    public String App_Customer_Path_Lastest = "customerPathLatest";
    public String App_Stock_Path_Lastest = "stockPathLatest";

    /**************************************************************************/
    public static final int DATE_FULL = 1 ;
    public static final int DATE_DATE_TIME = 2;
    public static final int DATE_DATE = 3;
    public static final int DATE_TIME = 4;
    public static final int DATE_AMPM = 5;
    public static final int TIME_DAY = 6;
    public static final int DATE_DATE_HASH = 7;
    public static final int DATE_DATETIME = 8;
    public static final int DATE_DATEMONTH = 9;
    public static final int TIME_TIME = 10;

    public AppGlobal(Context ctx){
        this.ctx = ctx;
    }

    public AppGlobal(Activity act){
        this.act = act;
        ctx = act.getApplicationContext();
    }

    public boolean isDebuggable(){
        boolean debuggable = false;

        PackageManager pm = ctx.getPackageManager();
        try{
            ApplicationInfo appinfo = pm.getApplicationInfo(ctx.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        }catch(PackageManager.NameNotFoundException e) {
            /*debuggable variable will remain false*/
        }
        return debuggable;
    }

    public void setPreference(String key, String obj, int type, Set<String> res){
        SharedPreferences shared;
        if(act != null){
            shared = act.getSharedPreferences(act.getApplicationContext().getPackageName(),0);
        }else {
            shared = ctx.getSharedPreferences(ctx.getApplicationContext().getPackageName(),0);
        }
        SharedPreferences.Editor edit = shared.edit();
        switch (type){
            case 1:
                edit.putBoolean(key,Boolean.parseBoolean(obj));
                break;
            case 2:
                edit.putFloat(key,Float.parseFloat(obj));
                break;
            case 3:
                edit.putInt(key,Integer.parseInt(obj));
                break;
            case 4:
                edit.putLong(key,Long.parseLong(obj));
                break;
            case 5:
                edit.putString(key,obj);
                break;
            case 6:
                edit.putStringSet(key,res);
                break;
        }
        edit.commit();
    }

    /**************************************************************************/
    /**************************************************************************/
    public Object getPreference(String key){
        SharedPreferences shared;
        if(act != null){
            shared = act.getSharedPreferences(act.getApplicationContext().getPackageName(),0);
        }else {
            shared = ctx.getSharedPreferences(ctx.getApplicationContext().getPackageName(),0);
        }
        HashMap<String,String> map = (HashMap<String, String>) shared.getAll();
        if(map.containsKey(key))
            return map.get(key);//.toString();
        return null;
    }

    public void removePreference(String key){
        SharedPreferences shared;
        if(act != null){
            shared = act.getSharedPreferences(act.getApplicationContext().getPackageName(),0);
        }else {
            shared = ctx.getSharedPreferences(ctx.getApplicationContext().getPackageName(),0);
        }
        SharedPreferences.Editor edit = shared.edit();
        edit.remove(key);
        edit.commit();
    }

    public void removerAllPerference(){
        SharedPreferences shared;
        if(act != null){
            shared = act.getSharedPreferences(act.getApplicationContext().getPackageName(),0);
        }else {
            shared = ctx.getSharedPreferences(ctx.getApplicationContext().getPackageName(),0);
        }
        SharedPreferences.Editor edit = shared.edit();
        edit.clear();
        edit.commit();
    }

    public void displayPreference(){
        Log.i(TAG,"Stared the share preference func");
        SharedPreferences shared;
        if(act != null){
            shared = act.getSharedPreferences(act.getApplicationContext().getPackageName(),0);
        }else {
            shared = ctx.getSharedPreferences(ctx.getApplicationContext().getPackageName(),0);
        }
        Map<String, ?> allEntries = shared.getAll();
        Log.i(TAG,"size is "+allEntries.size());
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.i(TAG, entry.getKey() + ": " + entry.getValue().toString());
        }
        Log.i(TAG,"Completed the shared preference display");
    }

    /**************************************************************************/
    /**************************************************************************/
    public void sendWhatsApp(String msg){
        /*String phone = String.valueOf(getPreference(App_WhatsApp_Number));
        if(phone.equals("null")){
            phone = "7397186661";
        }*/
        String phone ="7397186661";
        PackageManager packageManager = ctx.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (i.resolveActivity(ctx.getPackageManager()) == null) {
            Log.i(TAG,"whats app not installed");
            Toast.makeText(ctx, "Please install WhatsApp first.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }else {
            Log.i(TAG,"whats app installed");
            try {
                String url = "https://api.whatsapp.com/send?phone=+91" + phone + "&text=" + URLEncoder.encode(msg, "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                Log.i(TAG,"completed the whatsapp sending.");
                if (i.resolveActivity(packageManager) != null) {
                    ctx.startActivity(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**************************************************************************/
    /**************************************************************************/
    public String dateFormat(String epoch,int type){
        String date = "";

        switch(type){
            case DATE_FULL:
				/*
				example of the format is
				 */
                SimpleDateFormat full = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                date = full.format(new Date(Long.parseLong(epoch)));
                break;
            case DATE_DATE_TIME :
                /*
				example of the format is
				 */
                SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                date = datetime.format(new Date(Long.parseLong(epoch)));
                break;
            case DATE_DATE:
                /*
				example of the format is 9-Dec-2015
				 */
                SimpleDateFormat dateformat = new SimpleDateFormat("d-MMM-yyyy");
                date = dateformat.format(new Date(Long.parseLong(epoch)));
                break;
            case DATE_TIME:
                /*
				example of the format is 12:25:42
				 */
                SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
                date = timeformat.format(new Date(Long.parseLong(epoch)));
                break;
            case DATE_AMPM:
			    /*
				example of the format is 12:25 pm 9-Dec-2015
				 */
                SimpleDateFormat ampm = new SimpleDateFormat("HH:mm a d-MMM-yyyy");
                date = ampm.format(new Date(Long.parseLong(epoch)));
                break;
            case TIME_DAY:
                /*
				example of the format is THU, 04:20
				 */
                SimpleDateFormat day = new SimpleDateFormat("EEE, HH:mm");
                date = day.format(new Date(Long.parseLong(epoch)));
                break;
            case TIME_TIME:
                /*
				example of the format is THU, 04:20
				 */
                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                date = time.format(new Date(Long.parseLong(epoch)));
                break;
            case DATE_DATE_HASH:
                /*
				example of the format is 9/12/2015
				 */
                SimpleDateFormat dateformathash = new SimpleDateFormat("dd/MM/yyyy");
                date = dateformathash.format(new Date(Long.parseLong(epoch)));
                break;
            case DATE_DATETIME:
				/*
				example of the format is 9-Dec-2015 12:25 pm
				 */
                SimpleDateFormat datetimes = new SimpleDateFormat("d-MMM-yyyy hh:mm a");
                date = datetimes.format(new Date(Long.parseLong(epoch)));
                break;
            case DATE_DATEMONTH:
				/*
				example of the format is 9 Dec
				 */
                SimpleDateFormat datemonth = new SimpleDateFormat("d MMM");
                date = datemonth.format(new Date(Long.parseLong(epoch)));
                break;
            default:
                break;
        }
        return date;
    }

    /**************************************************************************/
    /**************************************************************************/
    /*public String storeToFB(String path,Object obj){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        String ids = reference.push().getKey();
        ProductClass pfb = (ProductClass) obj;
        pfb.setFid(ids);
        try {
            reference.child(ids).setValue(pfb);
            Log.i(TAG,"Product keys :"+ids);
        }catch (Exception e){
            Log.i(TAG,"exception "+e.getStackTrace());
        }
        return ids;
    }*/

    /************************************************************************/
    /************************************************************************/
    /*public String updateToFB(String path,Object obj){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        ProductClass pfb = (ProductClass) obj;
        String ids = pfb.getFid();
        pfb.setFid(ids);
        reference.child(ids).setValue(pfb);

        return ids;
    }

    public void deleteFB(String path){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.removeValue();
    }*/

}
