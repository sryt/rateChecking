package com.example.grocerieslist.init;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 26/07/21.
 */
public class ProductDetails extends AppCompatActivity {
    String TAG = ProductDetails.class.getSimpleName();

    AppGlobal global;

    ImageView proImg;
    TextView prodName,proDesc,pPrice,cPrice,wPrice,rPrice,mrp,pSize,pUnit,caseQty,hsn,gst,sheetNo,uom;
    boolean visFlag=false;
    ProductClass selectedProd;
    Button edit;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsfragment);

        global = new AppGlobal(this);
        proImg = findViewById(R.id.proImg);
        prodName = findViewById(R.id.prodName);
        proDesc = findViewById(R.id.prodDesc);
        pPrice = findViewById(R.id.pPrice);
        cPrice = findViewById(R.id.cPrice);
        wPrice = findViewById(R.id.wPrice);
        rPrice = findViewById(R.id.rPrice);
        mrp = findViewById(R.id.mrp);
        pSize = findViewById(R.id.packingSize);
        pUnit = findViewById(R.id.packingUnit);
        uom = findViewById(R.id.uom);
        caseQty = findViewById(R.id.caseQty);
        hsn = findViewById(R.id.hsnCode);
        gst = findViewById(R.id.gst);
        sheetNo = findViewById(R.id.sheetBundal);
        edit = findViewById(R.id.prodEdit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        String value = String.valueOf(global.getPreference(global.App_Selected_Prod));
        Log.i(TAG,"values is "+value);

        ProductAccess pa = new ProductAccess(this);
        pa.open();
        selectedProd = pa.getProdDetails(value);
        pa.close();

        prodName.setText(selectedProd.getName());
        proDesc.setText(selectedProd.getDesc());
        pPrice.setText(selectedProd.getPurRate()+ Constant.PriceSym);
        cPrice.setText(selectedProd.getCost()+Constant.PriceSym);
        wPrice.setText(selectedProd.getSpecial()+Constant.PriceSym);
        rPrice.setText(selectedProd.getRetail()+Constant.PriceSym);
        mrp.setText(selectedProd.getMrp());
        pSize.setText(selectedProd.getPackingsize());
        pUnit.setText(selectedProd.getPackuom());
        uom.setText(selectedProd.getUom());
        caseQty.setText(selectedProd.getCaseqty());
        hsn.setText(selectedProd.getHsn());
        gst.setText(selectedProd.getGst());
        sheetNo.setText(selectedProd.getSheetNo());

        Log.i(TAG,"product class is "+selectedProd.toString());

        String storedPass = String.valueOf(global.getPreference(global.App_Pass_Code));
        Log.i(TAG,"stored pass is "+storedPass);
        if(storedPass.equals("NA") || storedPass == null ||
                storedPass.equals("null"))
            visFlag = false;
        else {
            visFlag = true;
            pPrice.setInputType(InputType.TYPE_CLASS_TEXT);
            cPrice.setInputType(InputType.TYPE_CLASS_TEXT);
            wPrice.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        pPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!visFlag){
                    alertVisible();
                }
            }
        });

        cPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!visFlag){
                    alertVisible();
                }
            }
        });

        wPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!visFlag){
                    alertVisible();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(visFlag)
                    rateUpdation();
                else {
                    Snackbar.make(edit,"Enter the pass code first",Snackbar.LENGTH_SHORT).setAction("Pass Code", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertVisible();
                        }
                    }).show();
                }
            }
        });
    }

    EditText pCost,cCost,wCost,rCost;
    public void rateUpdation(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rate_layout);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);

        pCost = dialog.findViewById(R.id.ru_pRate);
        cCost = dialog.findViewById(R.id.ru_cRate);
        wCost = dialog.findViewById(R.id.ru_wRate);
        rCost = dialog.findViewById(R.id.ru_rRate);

        Button cancel = dialog.findViewById(R.id.ru_cancel);
        Button save = dialog.findViewById(R.id.ru_save);

        focusNext(pCost,wCost,5);
        focusNext(wCost,rCost,5);
        focusNext(rCost,null,5);

        pCost.setText(selectedProd.getPurRate());
        cCost.setText(selectedProd.getCost());
        wCost.setText(selectedProd.getSpecial());
        rCost.setText(selectedProd.getRetail());

        cCost.setFocusable(false);
        cCost.setClickable(false);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pStr =  pCost.getText().toString().trim();
                String cStr =  cCost.getText().toString().trim();
                String wStr =  wCost.getText().toString().trim();
                String rStr =  rCost.getText().toString().trim();

                Log.i(TAG,"updated values are "+pStr+","+cStr+","+wStr+","+rStr);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    StringBuilder sb=new StringBuilder();
    public void alertVisible(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.verification);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);

        TextView desc = dialog.findViewById(R.id.verifyDesc);
        final EditText v1 = dialog.findViewById(R.id.verify1);
        final EditText v2 = dialog.findViewById(R.id.verify2);
        final EditText v3 = dialog.findViewById(R.id.verify3);
        final EditText v4 = dialog.findViewById(R.id.verify4);

        focusNext(v1,v2,1);
        focusNext(v2,v3,1);
        focusNext(v3,v4,1);
        focusNext(v4,null,1);

        desc.setText(selectedProd.getDesc());

        FloatingActionButton fab = dialog.findViewById(R.id.verifySave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1Str = v1.getText().toString().trim();
                String v2Str = v2.getText().toString().trim();
                String v3Str = v3.getText().toString().trim();
                String v4Str = v4.getText().toString().trim();

                if(!v1Str.isEmpty()){
                    if(!v2Str.isEmpty()){
                        if(!v3Str.isEmpty()){
                            if(!v4Str.isEmpty()){
                                pPrice.setInputType(InputType.TYPE_CLASS_TEXT);
                                cPrice.setInputType(InputType.TYPE_CLASS_TEXT);
                                wPrice.setInputType(InputType.TYPE_CLASS_TEXT);

                                visFlag = true;
                                pass = v1Str+v2Str+v3Str+v4Str;
                                Log.i(TAG,"passcode values are "+pass);
                                if(pass.equals("1205")){
                                    global.setPreference(global.App_Pass_Code,pass, Constant.Pref_String,null);
                                    dialog.dismiss();
                                }else{
                                    v1.setError("wrong key");
                                    v2.setError("wrong key");
                                    v3.setError("wrong key");
                                    v4.setError("wrong key");
                                }

                            }
                        }
                    }
                }
            }
        });
        dialog.show();
    }

    public void focusNext(final EditText editText1, final EditText editText2, final int lenght){
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(sb.length()==lenght) {
                    sb.deleteCharAt(0);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(sb.length()==0& editText1.length()==1) {
                    sb.append(charSequence);
                    editText1.clearFocus();

                    if(editText2 ==null){
                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText1.getWindowToken(), 0);
                    }else {
                        editText2.requestFocus();
                        editText2.setCursorVisible(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(sb.length()==0) {
                    editText1.requestFocus();
                }
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

}
