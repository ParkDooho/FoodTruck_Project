package shinhan.ac.kr.foodtruck1.Z_Data;


import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import shinhan.ac.kr.foodtruck1.B_Login.LoginActivity;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class ResultRegister extends AppCompatActivity {
    String selectChoice;
    String ID;
    String PW;
    String Name;
    String Phone;
    String userNumber;
    ImageView ImageBus;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_register);
        LoginActivity finishLog = (LoginActivity)LoginActivity.LoginActivity;

        Intent intent = getIntent();
        selectChoice = intent.getStringExtra("selectChoice");
        ID=intent.getStringExtra("ID");
        PW=intent.getStringExtra("PW");
        Name=intent.getStringExtra("Name");
        Phone=intent.getStringExtra("Phone");
        userNumber=intent.getStringExtra("Number");
        String url = SeverURL.URL();

        ContentValues val = new ContentValues();
        val.put("action", selectChoice);
        val.put("userID", ID);
        val.put("userPW",PW);
        val.put("userName", Name);
        val.put("userTel", Phone);
        val.put("userNum", userNumber);

        ResultRegister.NetworkTask networkTask = new ResultRegister.NetworkTask(url, val); //여기에 넣기
        networkTask.execute();
        Toast.makeText(getApplication(), "회원가입 완료", Toast.LENGTH_SHORT).show();
        finish();
    }


    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

    public NetworkTask(String url, ContentValues values) {

        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... params) {

        String result; // 요청 결과를 저장할 변수.

        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다


        return result;
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);


    }


}
    private void initView(){
        ImageBus = (ImageView)findViewById(R.id.ImageBus);
        anim = AnimationUtils.loadAnimation(this, R.anim.loading);
        ImageBus.setAnimation(anim);
    }
}

