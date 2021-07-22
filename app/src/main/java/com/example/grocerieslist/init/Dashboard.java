package com.example.grocerieslist.init;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.grocerieslist.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

/**
 * Created by Tejaswi on 30/06/21.
 */
public class Dashboard extends AppCompatActivity {
    String TAG = Dashboard.class.getSimpleName();

    CardView share;
    LinearLayout salesLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
    }

    @Override
    protected void onResume() {
        super.onResume();

        salesLL = findViewById(R.id.ld_sales);
        share = findViewById(R.id.ld_dashboard_share);

        salesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newSales = new Intent(Dashboard.this,NewSales.class);
                startActivity(newSales);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newSales = new Intent(Dashboard.this,ProductStock.class);
                startActivity(newSales);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent create = new Intent(Dashboard.this,SettingsPage.class);
            startActivity(create);
            return true;
        }else if (id == R.id.action_price) {
            Intent create = new Intent(Dashboard.this,PriceDetials.class);
            startActivity(create);
            return true;
        }else if(id == R.id.action_filterview){
            Intent filterView = new Intent(Dashboard.this,FilterView.class);
            startActivity(filterView);
            return true;
        }else if(id == R.id.action_company){
            Intent filterView = new Intent(Dashboard.this,CompanyList.class);
            startActivity(filterView);
            return true;
        }else if(id == R.id.action_cart){
            Intent filterView = new Intent(Dashboard.this,MainActivity.class);
            startActivity(filterView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Double back press to close the app...
     */

   /*boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }*/
    
}
