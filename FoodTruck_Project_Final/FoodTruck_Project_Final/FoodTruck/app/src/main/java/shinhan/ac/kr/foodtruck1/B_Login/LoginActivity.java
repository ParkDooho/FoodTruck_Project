package shinhan.ac.kr.foodtruck1.B_Login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Data.GpsInfo;
import shinhan.ac.kr.foodtruck1.Z_Data.Resultlogin;

public class LoginActivity extends AppCompatActivity {
    String select;
    String selectChoice;
    EditText idText;
    EditText pwText;
    Button loginButton;
    Button registerButton;
    String ID="";
    String PW="";
    final static int Login_ResultData =0;

    private GpsInfo gps;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    double latitude;
    double longitude;
    public static Activity LoginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idText = (EditText) findViewById(R.id.userId);
        pwText = (EditText) findViewById(R.id.userPw);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        LoginActivity = LoginActivity.this;


        Intent intent = getIntent();
        select = intent.getStringExtra("select");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(select){
                    case "userLog":
                        selectChoice  = "userInsert";
                        break;
                    case "shopLog":
                        selectChoice  = "shopInsert";
                        break;
                }
                switch(selectChoice) {
                    case "userInsert":
                        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                        registerIntent.putExtra("selectChoice",selectChoice);
                        LoginActivity.this.startActivity(registerIntent);

                        break;
                    case "shopInsert":
                        Intent registerIntent_shop = new Intent(LoginActivity.this, Register_shop.class);
                        registerIntent_shop.putExtra("selectChoice",selectChoice);
                        LoginActivity.this.startActivity(registerIntent_shop);

                        break;
                }
            }
        });

        pwText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    gps = new GpsInfo(LoginActivity.this);
                    // 권한 요청을 해야 함
                    if (!isPermission) {
                        callPermission();
                    }
                    // GPS 사용유무 가져오기
                    if (gps.isGetLocation()) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }

                    callPermission();
                    ID = idText.getText().toString().trim();
                    PW = pwText.getText().toString().trim();
                    Intent LogCheck = new Intent(LoginActivity.this, Resultlogin.class);
                    LogCheck.putExtra("select",select);
                    LogCheck.putExtra("ID",ID);
                    LogCheck.putExtra("PW",PW);
                    LogCheck.putExtra("GPS_x",latitude);
                    LogCheck.putExtra("GPS_y",longitude);
                    startActivityForResult(LogCheck,Login_ResultData);

                    return true;
                }
                return false;
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPermission) {
                    callPermission();
                    return;
                }else {
                    gps = new GpsInfo(LoginActivity.this);
                    // 권한 요청을 해야 함
                    // GPS 사용유무 가져오기
                    if (gps.isGetLocation()) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }
                    callPermission();
                    ID = idText.getText().toString();
                    PW = pwText.getText().toString();
                    Intent LogCheck = new Intent(LoginActivity.this, Resultlogin.class);
                    LogCheck.putExtra("select", select);
                    LogCheck.putExtra("ID", ID);
                    LogCheck.putExtra("PW", PW);
                    LogCheck.putExtra("GPS_x", latitude);
                    LogCheck.putExtra("GPS_y", longitude);
                    startActivityForResult(LogCheck, Login_ResultData);
                }
            }
        });

    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case Login_ResultData:
                if(resultCode == Activity.RESULT_OK){
                    ID=data.getStringExtra("ID");
                    PW=data.getStringExtra("PW");
                }
                break;
        }

    }


    // ---- 위도 경도 관련 소스
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isAccessFineLocation = true;
        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            isAccessCoarseLocation = true;
        }
        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}
