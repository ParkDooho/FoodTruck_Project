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

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class ResultGPS_Del extends AppCompatActivity {
    ImageView ImageBus;
    Animation anim;
    String shopId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_gps__del);
        initView();
        Intent ImageReady = getIntent();
        shopId = ImageReady.getStringExtra("ShopID");
        String url = SeverURL.URL();

        ContentValues val = new ContentValues();
        val.put("action", "deleteShop_GPS");
        val.put("shopID", shopId);
        ResultGPS_Del.NetworkTask networkTask = new ResultGPS_Del.NetworkTask(url, val); //여기에 넣기
        networkTask.execute();
    }

    private void initView() {
        ImageBus = (ImageView) findViewById(R.id.ImageBus);
        anim = AnimationUtils.loadAnimation(this, R.anim.loading);
        ImageBus.setAnimation(anim);
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
            Toast.makeText(getApplication(), "영업 종료!", Toast.LENGTH_SHORT).show();
            String Check="시작";
            Intent returnGPS_Data = new Intent(ResultGPS_Del.this, UserImage.class);
            returnGPS_Data.putExtra("Check", Check);
            setResult(Activity.RESULT_OK, returnGPS_Data);
            finish();
        }


    }


}
