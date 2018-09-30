package shinhan.ac.kr.foodtruck1.Z_Data;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shinhan.ac.kr.foodtruck1.B_Login.LoginActivity;
import shinhan.ac.kr.foodtruck1.C_List.SearchActivity;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class Resultlogin extends AppCompatActivity {
    String select;
    String ID;
    String PW;
    double GPS_x=0;
    double GPS_y=0;
    ImageView ImageBus;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlogin);
        initView();
        Intent intent = getIntent();
        select = intent.getStringExtra("select");
        ID=intent.getStringExtra("ID");
        PW=intent.getStringExtra("PW");
        GPS_x=intent.getDoubleExtra("GPS_x",0);
        GPS_y=intent.getDoubleExtra("GPS_y",0);
        String url = SeverURL.URL();
        ContentValues val = new ContentValues();

        switch (select){
            case "userLog":
                val.put("action", select);
                val.put("userID",ID);
                val.put("userPW",PW);
                break;

            case "shopLog":
                val.put("action", select);
                val.put("shopID",ID);
                val.put("shopPW",PW);
                break;
        }

        Resultlogin.NetworkTask networkTask = new Resultlogin.NetworkTask(url, val); //여기에 넣기
        networkTask.execute();

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


            try {
                JSONObject resultData = new JSONObject(s);
                JSONArray resultArray = new JSONArray(resultData.getString("success"));
                int check = 0;
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject temp = resultArray.getJSONObject(0);
                if (select.equals("shopLog") && ID.equals(temp.getString("shopID")) && PW.equals(temp.getString("shopPW"))) {check=1;}
                if (select.equals("userLog") && ID.equals(temp.getString("userID")) && PW.equals(temp.getString("userPW"))) {check=2;}}
                if(check == 1){
                    Intent GoData = new Intent(Resultlogin.this, ResultImage.class);
                    GoData.putExtra("shopId", ID);
                    GoData.putExtra("select_Temp", "판매자");
                    Resultlogin.this.startActivity(GoData);
                    LoginActivity finishLog = (LoginActivity)LoginActivity.LoginActivity;
                    finishLog.finish();
                    finish();
                }
                else if (check == 2){
                    Intent GoData = new Intent(Resultlogin.this, SearchActivity.class);
                    GoData.putExtra("ID", ID);
                    GoData.putExtra("GPS_x", GPS_x);
                    GoData.putExtra("GPS_y", GPS_y);
                    Resultlogin.this.startActivity(GoData);
                    LoginActivity finishLog = (LoginActivity)LoginActivity.LoginActivity;
                    finishLog.finish();
                    finish();
                }
                else {
                    Toast.makeText(getApplication(),"아이디 혹은 비밀번호를 확인바랍니다.",Toast.LENGTH_SHORT).show();
                    Intent returnData = new Intent(Resultlogin.this, LoginActivity.class);
                    returnData.putExtra("ID", ID);
                    returnData.putExtra("PW", PW);
                    setResult(Activity.RESULT_OK, returnData);
                    finish();}

            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }


    private void initView(){
        ImageBus = (ImageView)findViewById(R.id.ImageBus);
        anim = AnimationUtils.loadAnimation(this, R.anim.loading);
        ImageBus.setAnimation(anim);
    }


}
