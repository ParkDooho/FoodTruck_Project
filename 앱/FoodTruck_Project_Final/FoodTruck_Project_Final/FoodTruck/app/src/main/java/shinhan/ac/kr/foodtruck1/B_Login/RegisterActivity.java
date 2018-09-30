package shinhan.ac.kr.foodtruck1.B_Login;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Data.ResultRegister;
import shinhan.ac.kr.foodtruck1.Z_Data.ResultRegister_ID_Check;

public class RegisterActivity extends AppCompatActivity {
    EditText userId;
    EditText userPw;
    EditText userTel;
    EditText userPwAgain;
    EditText userName;
    EditText userNum1;
    EditText userNum2;
    Button ok;
    Button back;
    Button idCheck;
    String selectChoice;
    final static int Register_ResultData =0;
    int check=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userId = (EditText) findViewById(R.id.userId);
        userPw = (EditText) findViewById(R.id.userPw);
        userName = (EditText) findViewById(R.id.userName);
        userPwAgain = (EditText) findViewById(R.id.userPwAgain);
        userTel = (EditText) findViewById(R.id.userTel);
        userNum1 = (EditText)findViewById(R.id.userNum1);
        userNum2 = (EditText)findViewById(R.id.userNum2);
        ok = (Button) findViewById(R.id.ok);
        back = (Button) findViewById(R.id.back);
        idCheck = (Button) findViewById(R.id.idCheck);
        Intent intent = getIntent();
        selectChoice = intent.getStringExtra("selectChoice");

        idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = userId.getText().toString().trim();
                if(ID.equals("")){
                    Toast.makeText(getApplication(), "ID를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                Intent LogCheck = new Intent(RegisterActivity.this, ResultRegister_ID_Check.class);
                LogCheck.putExtra("selectChoice",selectChoice);
                LogCheck.putExtra("ID",ID);
                startActivityForResult(LogCheck,Register_ResultData);}
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = userId.getText().toString();
                String PW = userPw.getText().toString();
                String PwAgain = userPwAgain.getText().toString();
                String Name = userName.getText().toString();
                String Phone = userTel.getText().toString();
                String Num1 = userNum1.getText().toString();
                String Num2 = userNum2.getText().toString();

                if(check ==1){
                    Toast.makeText(getApplication(), "아이디 중복 체크 확인", Toast.LENGTH_SHORT).show();
                }
                else if(!PW.equals(PwAgain)){
                    Toast.makeText(getApplication(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
                else if(Name.equals("")){
                    Toast.makeText(getApplication(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(Phone.equals("")){
                    Toast.makeText(getApplication(), "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(Num1.equals("")){
                    Toast.makeText(getApplication(), "주민등록번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(Num2.equals("")){
                    Toast.makeText(getApplication(), "주민등록번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                if(check ==0 && PW.equals(PwAgain) && !Name.equals("") && !Phone.equals("") && !Num1.equals("") && !Num2.equals("")){
                        String userNumber= Num1 + Num2;
                Intent LogCheck = new Intent(RegisterActivity.this, ResultRegister.class);
                LogCheck.putExtra("selectChoice",selectChoice);
                LogCheck.putExtra("ID",ID);
                LogCheck.putExtra("PW",PW);
                LogCheck.putExtra("Name",Name);
                LogCheck.putExtra("Phone",Phone);
                LogCheck.putExtra("Number",userNumber);
                RegisterActivity.this.startActivity(LogCheck);
                finish();}
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    } protected void onActivityResult (int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case Register_ResultData:
                if(resultCode == Activity.RESULT_OK){
                    String ID=data.getStringExtra("ID");
                    check = data.getIntExtra("Check",1);
                    userId.setText(ID);
                }
                break;
        }

    }
}