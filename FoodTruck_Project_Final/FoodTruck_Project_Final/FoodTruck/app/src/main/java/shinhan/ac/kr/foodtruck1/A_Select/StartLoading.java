package shinhan.ac.kr.foodtruck1.A_Select;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import shinhan.ac.kr.foodtruck1.R;

public class StartLoading extends AppCompatActivity {

    ImageView Imageloading;
    Animation loading_start;
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_loading);
        initView();

        new Handler().postDelayed(new Runnable(){
           @Override
            public void run(){
                Intent intent = new Intent(StartLoading.this, MainActivity.class);
                StartLoading.this.startActivity(intent);
                StartLoading.this.finish();
        }
   }, SPLASH_DISPLAY_LENGTH);
   }

    private void initView(){
        Imageloading = (ImageView)findViewById(R.id.Imageloading);
        loading_start = AnimationUtils.loadAnimation(StartLoading.this,R.anim.loading_start);
        Imageloading.setAnimation(loading_start);
    }
}
