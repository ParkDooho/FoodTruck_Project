package shinhan.ac.kr.foodtruck1.A_Select;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import shinhan.ac.kr.foodtruck1.B_Login.LoginActivity;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Data.BackPressCloseHandler;

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;
    String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start1 = (Button)findViewById(R.id.start1);
        Button start2 = (Button)findViewById(R.id.start2);

        backPressCloseHandler = new BackPressCloseHandler(this);  //  뒤로 가기 두번 눌러야 종료

        verifyStoragePermissions(this);

        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = "userLog";
                Intent selectIntent = new Intent(MainActivity.this, LoginActivity.class);
                selectIntent.putExtra("select",select);
                MainActivity.this.startActivity(selectIntent);

            }
        });

        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = "shopLog";
                Intent selectIntent = new Intent(MainActivity.this, LoginActivity.class);
                selectIntent.putExtra("select",select);
                MainActivity.this.startActivity(selectIntent);

            }
        });
    }

    @Override public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    // 이미지 폴더 권한 요청
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    public static String[] PERMISSIONS_STRORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE ,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.ACCESS_FINE_LOCATION ,Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static void verifyStoragePermissions(Activity activity) {
        int writePermission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int finePermission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int coarsePermission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if(writePermission != PackageManager.PERMISSION_GRANTED|| readPermission != PackageManager.PERMISSION_GRANTED||
                 finePermission!= PackageManager.PERMISSION_GRANTED|| coarsePermission!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STRORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



}
