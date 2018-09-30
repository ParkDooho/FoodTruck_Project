package shinhan.ac.kr.foodtruck1.B_Login;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Data.ResultRegister_ID_Check_shop;
import shinhan.ac.kr.foodtruck1.Z_Data.ResultRegister_shop;



public class Register_shop extends AppCompatActivity {
    EditText shopId;
    EditText shopPw;
    EditText shopTel;
    EditText shopPwAgain;
    EditText shopName;
    EditText shopOwner;
    Button ok;
    Button back;
    Button idCheck2;
    String selectChoice;
    final static int Register_ResultData_shop = 0;
    int check = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        shopId = (EditText) findViewById(R.id.shopId);
        shopPw = (EditText) findViewById(R.id.shopPw);
        shopName = (EditText) findViewById(R.id.shopName);
        shopPwAgain = (EditText) findViewById(R.id.shopPwAgain);
        shopTel = (EditText) findViewById(R.id.shopTel);
        shopOwner = (EditText) findViewById(R.id.shopOwner);
        ok = (Button) findViewById(R.id.ok);
        idCheck2 = (Button) findViewById(R.id.idCheck2);
        back = (Button) findViewById(R.id.back);
        Intent intent = getIntent();
        selectChoice = intent.getStringExtra("selectChoice");

        idCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = shopId.getText().toString();
                if(ID.equals("")){
                    Toast.makeText(getApplication(), "ID를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                Intent LogCheck = new Intent(Register_shop.this, ResultRegister_ID_Check_shop.class);
                LogCheck.putExtra("selectChoice",selectChoice);
                LogCheck.putExtra("ID",ID);
                startActivityForResult(LogCheck,Register_ResultData_shop);}
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = shopId.getText().toString();
                String PW = shopPw.getText().toString();
                String PwAgain = shopPwAgain.getText().toString();
                String Name = shopName.getText().toString();
                String Phone = shopTel.getText().toString();
                String Owner = shopOwner.getText().toString();

                if(check ==1){
                    Toast.makeText(getApplication(), "아이디 중복 체크 확인", Toast.LENGTH_SHORT).show();
                }
                else if(!PW.equals(PwAgain)){
                    Toast.makeText(getApplication(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
                else if(Name.equals("")){
                    Toast.makeText(getApplication(), "트럭 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(Phone.equals("")){
                    Toast.makeText(getApplication(), "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(Owner.equals("")){
                    Toast.makeText(getApplication(), "사업자 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }


                if(check ==0 && PW.equals(PwAgain) && !Name.equals("") && !Phone.equals("") && !Owner.equals("")){
                    Intent LogCheck = new Intent(Register_shop.this, ResultRegister_shop.class);
                    LogCheck.putExtra("selectChoice",selectChoice);
                    LogCheck.putExtra("ID",ID);
                    LogCheck.putExtra("PW",PW);
                    LogCheck.putExtra("Name",Name);
                    LogCheck.putExtra("Phone",Phone);
                    LogCheck.putExtra("Owner",Owner);
                    Register_shop.this.startActivity(LogCheck);
                    finish();}
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Register_ResultData_shop:
                if (resultCode == Activity.RESULT_OK) {
                    String ID = data.getStringExtra("ID");
                    check = data.getIntExtra("Check", 1);
                    shopId.setText(ID);
                }
                break;
        }

    }
}
