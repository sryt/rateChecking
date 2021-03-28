package com.example.grocerieslist.init;

import android.os.Bundle;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 16/03/21.
 */
public class SettingsPage extends AppCompatActivity {
    String TAG = SettingsPage.class.getSimpleName();

    AppGlobal appGlobal;
    TextView path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        appGlobal = new AppGlobal(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        path = findViewById(R.id.sl_serverpath);

        path.setText(Constant.FB_Account_Path);
    }
}
