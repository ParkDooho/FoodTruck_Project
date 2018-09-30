package shinhan.ac.kr.foodtruck1.Z_Data;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class ResultRegister_ID_Check_shop extends AppCompatActivity {
    String selectChoice;
    String ID;
    ImageView ImageBus;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_register__id__check_shop);
        initView();
        Intent intent = getIntent();
        selectChoice = intent.getStringExtra("selectChoice");
        ID=intent.getStringExtra("ID");
        String url = SeverURL.URL();
        ContentValues val = new ContentValues();
        val.put("action", "shopCheck");
        val.put("shopID",ID);
        ResultRegister_ID_Check_shop.NetworkTask networkTask = new ResultRegister_ID_Check_shop.NetworkTask(url, val); //여기에 넣기
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
                    if ( ID.equals(temp.getString("shopID"))) {
                        check = 1;
                    }
                }
                if(check == 1){
                    Toast.makeText(getApplication(), "사용 할 수 없는 아이디입니다.", Toast.LENGTH_SHORT).show();}
                else if(check == 0){
                    Toast.makeText(getApplication(), "사용 할 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show();}


                Intent returnData = new Intent(ResultRegister_ID_Check_shop.this, ResultRegister.class);
                returnData.putExtra("ID", ID);
                returnData.putExtra("Check", check);
                setResult(Activity.RESULT_OK, returnData);
                finish();

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