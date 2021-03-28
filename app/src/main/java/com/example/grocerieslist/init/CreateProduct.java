package com.example.grocerieslist.init;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.grocerieslist.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

/**
 * Created by Tejaswi on 15/12/20.
 */
public class CreateProduct extends AppCompatActivity {
    String TAG = CreateProduct.class.getSimpleName();

    CardView rateCard,packingCard,mfrCard;
    ImageView rateIV,packingIV,mfrIV;

    boolean rateBoolean = false;
    boolean packingBoolean = false;
    boolean mfrBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createprod);

        rateCard = findViewById(R.id.cp_card_rate);
        packingCard = findViewById(R.id.cp_card_packing);
        mfrCard = findViewById(R.id.cp_card_mfr);

        rateIV = findViewById(R.id.cp_iv_rate);
        packingIV = findViewById(R.id.cp_iv_packing);
        mfrIV = findViewById(R.id.cp_iv_mfr);

    }

    @Override
    protected void onResume() {
        super.onResume();

        rateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateBoolean = showHide(rateBoolean,rateIV,rateCard);
            }
        });

        packingIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packingBoolean = showHide(packingBoolean,packingIV,packingCard);
            }
        });

        mfrIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mfrBoolean = showHide(mfrBoolean,mfrIV,mfrCard);
            }
        });
    }

    /**
     *
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
