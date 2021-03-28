package com.example.grocerieslist.init;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.db.product.ProductAccess;
import com.example.grocerieslist.db.product.ProductClass;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 11/12/20.
 */
public class Loading extends AppCompatActivity {
    String TAG = Loading.class.getSimpleName();
    Animation coolAnimation;
    ImageView coolAnim;
    AppGlobal global;

    InputStream inputStream;
    String[] ids;

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
                    inputStream = getResources().openRawResource(R.raw.allfields);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        String csvLine;
                        while ((csvLine = reader.readLine()) != null) {
                            Log.i(TAG,"csv line is "+csvLine);
                            ids=csvLine.split(",");
                            try{
                                /**
                                 * 0 - Name                        7 - Selling Rate
                                 * 1 - Group                       8 - Special Rate
                                 * 2 - Company                     9 - Packing Unit
                                 * 3 - GST                         10 - Pcs per Sheet
                                 * 4 - P Rate                      11 - Supplier
                                 * 5 - c Rate                      12 - HSN
                                 * 6 - MRP                         13 - Weight
                                 */

                                String name = checkValue(ids[0]);
                                String group = checkValue(ids[1]);
                                String company = checkValue(ids[2]);
                                String gst = checkValue(ids[3]);
                                String pPrice = checkValue(ids[4]);
                                String cPrice = checkValue(ids[5]);
                                String mrp = checkValue(ids[6]);
                                String rPrice = checkValue(ids[7]);
                                String wPrice = checkValue(ids[8]);
                                String uom = checkValue(ids[9]);
                                String sheet = checkValue(ids[10]);
                                String supplier= "Not Mention";
                                if(ids.length >11 && ids[11] != null){
                                    supplier = checkValue(ids[11]);
                                }
                                String hsn = "Not Mention";
                                if(ids.length > 12 && ids[12] != null){
                                    hsn = checkValue(ids[12]);
                                }

                                String caseqty = "0";
                                if(ids.length > 13 && ids[13] != null)
                                    caseqty = checkValue(ids[13]);

                                String desc = name+ " of brand "+ company +", is supplied by " + supplier+". Product packing weight is "+caseqty+uom;

                                setProduct(name,pPrice,cPrice,wPrice,rPrice,mrp,caseqty,sheet,gst,hsn,uom,company,group,desc);
                            }catch (Exception e){
                                Log.e(TAG,e.toString());
                            }
                        }
                    }
                    catch (IOException ex) {
                        throw new RuntimeException("Error in reading CSV file: "+ex);
                    }
                    sleep(1000);
                }catch(InterruptedException e){

                } finally {
                    Intent splashintent = new Intent(Loading.this,MainActivity.class);
                    startActivity(splashintent);
                    finish();
                }
            }
        };
        splashthread.start();
    }

    public void setProduct(String name,String pPrice,String cPrice,String wPrice,String rPrice,String mrp,String caseQty,String sheet,
                           String gst,String hsn,String uom,String type,String group,String desc){
        ProductClass pc1 = new ProductClass(name,desc,String.valueOf(System.currentTimeMillis()),"","",
                uom,"",mrp,pPrice,cPrice,wPrice,rPrice,caseQty,sheet,
                gst,hsn,type, group,Constant.ACTIVE);

        Log.i(TAG,"saving class "+pc1.toString());
        ProductAccess pa = new ProductAccess(Loading.this);
        pa.open();
        pa.addProdDetails(pc1);
        pa.close();
    }

    public String checkValue(String str){
        if(str.equals(null))
            return "NA";
        else
            return str;
    }

}
