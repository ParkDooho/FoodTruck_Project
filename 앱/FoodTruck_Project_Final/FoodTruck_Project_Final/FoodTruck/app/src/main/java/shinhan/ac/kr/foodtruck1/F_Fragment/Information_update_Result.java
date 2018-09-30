package shinhan.ac.kr.foodtruck1.F_Fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Data.ResultImage;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

import static shinhan.ac.kr.foodtruck1.E_Image.UserImage.Select_Temp;
import static shinhan.ac.kr.foodtruck1.E_Image.UserImage.ShopID;

public class Information_update_Result extends AppCompatActivity {
    String openTime;
    String openPlace;
    ImageView ImageBus;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_information_update__result);
        initView();
        String ShopID = UserImage.ShopID;
        String Select_Temp = UserImage.Select_Temp;
        Intent intent = getIntent();
        openTime = intent.getStringExtra("openTime");
        openPlace = intent.getStringExtra("openPlace");
        String url = SeverURL.URL();
        ContentValues val = new ContentValues();
        val.put("action", "shop_OpenUpdate");
        val.put("shopOpenTime", openTime);
        val.put("shopOpenPlace", openPlace);
        val.put("shopID", ShopID);
        Information_update_Result.NetworkTask networkTask = new Information_update_Result.NetworkTask(url, val); //여기에 넣기
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
            UserImage finishImage = (UserImage)UserImage.UserImage;
            finishImage.finish();
            Intent SaveFinish = new Intent(Information_update_Result.this, ResultImage.class);
            SaveFinish.putExtra("shopId",ShopID);
            SaveFinish.putExtra("select_Temp",Select_Temp);
            Information_update_Result.this.startActivity(SaveFinish);
            finish();

        }


    }


    private void initView(){
        ImageBus = (ImageView)findViewById(R.id.ImageBus);
        anim = AnimationUtils.loadAnimation(this, R.anim.loading);
        ImageBus.setAnimation(anim);
    }
}

