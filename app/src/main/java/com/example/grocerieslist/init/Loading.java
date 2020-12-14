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
import androidx.cardview.widget.CardView;

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
                    inputStream = getResources().openRawResource(R.raw.prodb);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        String csvLine;
                        Log.i(TAG,"column1\t\t\tcolumn2\t\t\tcolumn3\t\t\tcolumn4\t\t\tcolumn5\t\t\tcolumn6\t\t\tcolumn7");
                        while ((csvLine = reader.readLine()) != null) {
                            ids=csvLine.split(",");
                            try{
                                /*String name,cPrice,wPrice,rPrice,mrp,caseqty,sheet;
                                name = checkValue(ids[0]);
                                cPrice = checkValue(ids[1]);
                                wPrice = checkValue(ids[2]);
                                rPrice = checkValue(ids[3]);
                                mrp = checkValue(ids[4]);
                                caseqty = checkValue(ids[5]);
                                if(ids.length == 7)
                                    sheet = checkValue(ids[6]);
                                else
                                    sheet = "0";*/

                                String name = checkValue(ids[0]);
                                String group = checkValue(ids[1]);
                                String gst = checkValue(ids[2]);
                                String pPrice = checkValue(ids[3]);
                                String cPrice = checkValue(ids[4]);
                                String mrp = checkValue(ids[5]);
                                String rPrice = checkValue(ids[6]);
                                String wPrice = checkValue(ids[7]);
                                String uom = checkValue(ids[8]);
                                String hsn = checkValue(ids[9]);
                                String caseqty = "NA";
                                String sheet = "NA";

                                if(group.equals(Constant.Toilet)){

                                }else if(group.equals(Constant.Snacks)){

                                }else if(group.equals(Constant.Groceries)){

                                }else if(group.equals(Constant.Groceries)){

                                }else if(group.equals(Constant.Groceries)){

                                }else if(group.equals(Constant.Groceries)){

                                }else if(group.equals(Constant.Groceries)){

                                }else if(group.equals(Constant.Groceries)){

                                }else if(group.equals(Constant.Groceries)){

                                }else if(group.equals(Constant.Groceries)){

                                }else if(group.equals(Constant.Groceries)){

                                }


                                setProduct(name,pPrice,cPrice,wPrice,rPrice,mrp,caseqty,sheet,gst,hsn,uom,group);
                                Log.i(TAG,name+"\t\t\t"+pPrice+"\t\t\t"+cPrice+"\t\t\t"+wPrice+
                                        "\t\t\t"+rPrice +"\t\t\t"+mrp+"\t\t\t"+caseqty+"\t\t\t"+sheet+"" +
                                        "\t\t\t"+gst+"\t\t\t"+hsn+"\t\t\t"+uom+"\t\t\t"+group) ;
                            }catch (Exception e){
                                Log.e("Unknown",e.toString());
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
                           String gst,String hsn,String uom,String group){
        ProductClass pc1 = new ProductClass(name,String.valueOf(System.currentTimeMillis()),"",
                uom,"",mrp,pPrice,cPrice,wPrice,rPrice,caseQty,sheet,
                gst,hsn,group, Constant.Snacks,Constant.ACTIVE);
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
