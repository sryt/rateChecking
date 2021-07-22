package com.example.grocerieslist.init;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.grocerieslist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent create = new Intent(MainActivity.this,CreateProduct.class);
                startActivity(create);
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
            Intent create = new Intent(MainActivity.this,SettingsPage.class);
            startActivity(create);
            return true;
        }else if (id == R.id.action_price) {
            Intent create = new Intent(MainActivity.this,PriceDetials.class);
            startActivity(create);
            return true;
        }else if(id == R.id.action_filterview){
            Intent filterView = new Intent(MainActivity.this,FilterView.class);
            startActivity(filterView);
            return true;
        }else if(id == R.id.action_company){
            Intent filterView = new Intent(MainActivity.this,CompanyList.class);
            startActivity(filterView);
            return true;
        }else if(id == R.id.action_cart){
            Intent filterView = new Intent(MainActivity.this,FilterView.class);
            startActivity(filterView);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}