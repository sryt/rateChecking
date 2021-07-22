package com.example.grocerieslist.init;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.company.CompanyAccess;
import com.example.grocerieslist.db.company.CompanyClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

/**
 * Created by Tejaswi on 30/06/21.
 */
public class CompanyDetails extends AppCompatActivity {
    String TAG = CompanyDetails.class.getSimpleName();

    AppGlobal appGlobal;
    TextInputEditText name,phonenumber,owner,compDesc,emailId,address,place,pincode,gst,bankName,bankBranch,ifsc,acc;
    Button cancel,save;
    CardView compDetails,bankDetails;
    ImageView compIV,bankIV;
    boolean compFlag=false,bankFlag=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_details);

        appGlobal = new AppGlobal(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        emailId = findViewById(R.id.lcd_company_email);
        compDetails = findViewById(R.id.lcd_company_card);
        bankDetails = findViewById(R.id.lcd_bank_card);
        compIV = findViewById(R.id.lcd_company);
        bankIV = findViewById(R.id.lcd_bank);
        owner = findViewById(R.id.lcd_company_owner);
        compDesc = findViewById(R.id.lcd_company_desc);
        name = findViewById(R.id.lcd_company_name);
        phonenumber = findViewById(R.id.lcd_company_number);
        address = findViewById(R.id.lcd_company_address);
        place = findViewById(R.id.lcd_company_place);
        pincode = findViewById(R.id.lcd_company_pincode);
        gst = findViewById(R.id.lcd_company_gst);
        bankName = findViewById(R.id.lcd_bank_name);
        bankBranch = findViewById(R.id.lcd_bank_branch);
        ifsc = findViewById(R.id.lcd_bank_ifsc);
        acc = findViewById(R.id.lcd_bank_acc);

        cancel = findViewById(R.id.lcd_btn_cancel);
        save = findViewById(R.id.lcd_btn_save);


        compIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compFlag = showHide(compFlag,compIV,compDetails);
            }
        });

        bankIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankFlag = showHide(bankFlag,bankIV,bankDetails);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString().trim();
                String numberStr = phonenumber.getText().toString().trim();
                String compDescStr = compDesc.getText().toString().trim();
                String ownerStr = owner.getText().toString().trim();
                String emailStr = emailId.getText().toString().trim();
                String addreStr = address.getText().toString().trim();
                String placeStr = place.getText().toString().trim();
                String pincodeStr = pincode.getText().toString().trim();
                String gstNoStr = gst.getText().toString().trim();
                String bankNameStr = bankName.getText().toString().trim();
                String bankBranchStr = bankBranch.getText().toString().trim();
                String accStr = acc.getText().toString().trim();
                String ifscStr = ifsc.getText().toString().trim();

                if(!nameStr.isEmpty()){
                    CompanyClass cc = new CompanyClass(nameStr,ownerStr,compDescStr,addreStr,placeStr,pincodeStr,numberStr,emailStr,gstNoStr,accStr,bankNameStr,ifscStr,bankBranchStr,Constant.ACTIVE, Constant.FALSE);

                    Log.i(TAG,"Company class details "+cc.toString());
                    CompanyAccess ca = new CompanyAccess(CompanyDetails.this);
                    ca.open();
                    long res = ca.addCompDetails(cc);
                    ca.close();
                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Successfully added the company",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Kindly update the details correctly.",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    name.setError("Kindly enter the name.");
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    /**
     * Show and hide the layout based on the details provided by calling the function.
     * @param flag
     * @param iv
     * @param cv
     * @return
     */
    public boolean showHide(boolean flag,ImageView iv,CardView cv){
        if(flag) {
            iv.setImageResource(R.mipmap.arrow_down);
            cv.setVisibility(View.VISIBLE);
        }else {
            iv.setImageResource(R.mipmap.arrow_up);
            cv.setVisibility(View.GONE);
        }
        flag = !flag;

        return flag;
    }

}
