package com.example.grocerieslist.init;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.example.grocerieslist.utilities.ProcessDataAsync;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 16/03/21.
 */
public class SettingsPage extends AppCompatActivity {
    String TAG = SettingsPage.class.getSimpleName();
    String[] ids;

    AppGlobal appGlobal;
    TextView path,importPath;
    LinearLayout importLV;

    public static final int PICKFILE_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        appGlobal = new AppGlobal(this);


/*        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    protected void onResume() {
        super.onResume();

        path = findViewById(R.id.sl_serverpath);
        importLV = findViewById(R.id.sl_import_lv);
        importPath = findViewById(R.id.sl_importpath);

        path.setText(Constant.FB_Account_Path);
        importPath.setText(String.valueOf(appGlobal.getPreference(appGlobal.App_Selected_Path)));
        importLV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(importLV,"Clicked the path... ",Snackbar.LENGTH_SHORT).show();

                Intent mRequestFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mRequestFileIntent.setType("text/*");
                startActivityForResult(mRequestFileIntent, PICKFILE_REQUEST_CODE);
            }
        });
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICKFILE_REQUEST_CODE) {
            String path = data.getData().getPath();

            ProcessDataAsync pda = new ProcessDataAsync(path,SettingsPage.this,data);
            pda.execute();
            appGlobal.setPreference(appGlobal.App_Selected_Path,path,Constant.Pref_String,null);
            Log.i(TAG, "PATH IS " +path);
        }
    }
}
