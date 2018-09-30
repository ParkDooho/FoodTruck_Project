package shinhan.ac.kr.foodtruck1.D_Gps;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class ResultGPS_Set extends Activity {
    ImageView ImageBus;
    Animation anim;
    String shopId;
    double shopAdr_x=0;
    double shopAdr_y=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_gps__set);
        initView();
        Intent ImageReady = getIntent();
        shopId = ImageReady.getStringExtra("ShopID");
        shopAdr_x = ImageReady.getDoubleExtra("GPS_x",0);
        shopAdr_y = ImageReady.getDoubleExtra("GPS_y",0);
        String url = SeverURL.URL();



        ContentValues val = new ContentValues();
        val.put("action", "insertShop_GPS");
        val.put("shopID", shopId);
        val.put("shopAddr_x", shopAdr_x);
        val.put("shopAddr_y", shopAdr_y);

        ResultGPS_Set.NetworkTask networkTask = new ResultGPS_Set.NetworkTask(url, val); //여기에 넣기
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
            Toast.makeText(getApplication(), "영업 시작!", Toast.LENGTH_SHORT).show();

            String Check="종료";
            Intent returnGPS_Data = new Intent(ResultGPS_Set.this, UserImage.class);
            returnGPS_Data.putExtra("Check", Check);
            setResult(Activity.RESULT_OK, returnGPS_Data);
            finish();
        }


    }


}
