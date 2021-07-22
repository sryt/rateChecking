package com.example.grocerieslist.init;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.utilities.AppGlobal;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Tejaswi on 11/12/20.
 */
public class Loading extends AppCompatActivity {
    String TAG = Loading.class.getSimpleName();
    Animation coolAnimation;
    ImageView coolAnim;
    AppGlobal global;

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
                    sleep(1000);
                }catch(InterruptedException e){

                } finally {
                    Intent splashintent = new Intent(Loading.this,Dashboard.class);
                    startActivity(splashintent);
                    finish();
                }
            }
        };
        splashthread.start();
    }

}
