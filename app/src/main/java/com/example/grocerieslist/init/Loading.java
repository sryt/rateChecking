package com.example.grocerieslist.init;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.company.CompanyAccess;
import com.example.grocerieslist.db.company.CompanyClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 11/12/20.
 */
public class Loading extends AppCompatActivity {
    String TAG = Loading.class.getSimpleName();
    Animation coolAnimation;
    ImageView coolAnim;
    AppGlobal global;
    boolean flag = true;

    CompanyAccess ca;
    List<CompanyClass> ccs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        global = new AppGlobal(Loading.this);

        coolAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        coolAnim = findViewById(R.id.imageView1);
        coolAnim.setAnimation(coolAnimation);
        coolAnim.startAnimation(coolAnimation);

        Thread splashthread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){

                } finally {
                    ca = new CompanyAccess(Loading.this);
                    ca.open();
                    ccs = ca.getCompaniesDetails();
                    ca.close();

                    Log.i(TAG,"company size is "+ccs.size());
                    if(ccs.size() > 0) {
                        flag = false;
                        if(ccs.size() == 1) {
                            global.setPreference(global.App_Selected_Company, ccs.get(0).getId(), Constant.Pref_String, null);
                        }else{
                            selectCompany();
                        }
                    }

                    if(flag){
                        Intent cc = new Intent(Loading.this,CompanyDetails.class);
                        startActivity(cc);
                    }else {
                        Intent splashintent = new Intent(Loading.this, SlidingMenu.class);
                        startActivity(splashintent);
                        finish();
                    }
                }
            }
        };
        splashthread.start();
    }

    AlertDialog.Builder builderSingle;
    public void selectCompany(){
         builderSingle = new AlertDialog.Builder(this);
        builderSingle.setIcon(R.mipmap.logo);
        builderSingle.setTitle("Select Company....");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        for(int i=0;i<ccs.size();i++)
            arrayAdapter.add(ccs.get(i).getCompanyName());

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                global.setPreference(global.App_Selected_Company,ccs.get(which).getId(),Constant.Pref_String,null);
                Snackbar.make(coolAnim,"Selected company is "+strName,Snackbar.LENGTH_SHORT).show();
            }
        });
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                builderSingle.show();
            }
        });
    }
}
