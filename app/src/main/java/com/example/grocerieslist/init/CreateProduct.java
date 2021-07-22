package com.example.grocerieslist.init;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.grocerieslist.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

/**
 * Created by Tejaswi on 15/12/20.
 */
public class CreateProduct extends AppCompatActivity {
    String TAG = CreateProduct.class.getSimpleName();

    CardView rateCard,packingCard,mfrCard;
    ImageView rateIV,packingIV,mfrIV,proIV;

    boolean rateBoolean = false;
    boolean packingBoolean = false;
    boolean mfrBoolean = false;

    public static final int PICK_IMAGE = 1;

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
        proIV = findViewById(R.id.cp_pro_img);
        Log.i(TAG,"Completed");
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

        proIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Log.i(TAG,"picture path "+data.getData().getPath());
            proIV.setImageURI(selectedImage);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            proIV.setImageBitmap(bitmap);

            if (bitmap != null) {
                String imgFile = new File(String.valueOf(data.getData())).getAbsolutePath();
                Snackbar.make(proIV,"Image clicked from the "+imgFile,Snackbar.LENGTH_SHORT).show();
                Log.i(TAG,"File path is "+imgFile);
            }

        }
    }


    /**
     * Show or hide the layout based on the image clicking.
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
