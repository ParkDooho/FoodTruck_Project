package shinhan.ac.kr.foodtruck1.Z_Data;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class ResultImage extends AppCompatActivity {
    String shopId;
    ImageView ImageBus;
    Animation anim;
    String shopPic;
    String select_Temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_image);

        initView();
        Intent intent = getIntent();
        shopId = intent.getStringExtra("shopId");
        select_Temp = intent.getStringExtra("select_Temp");



        String url = SeverURL.URL();
        ContentValues val = new ContentValues();

        val.put("action", "selectShop_ImageData");
        val.put("shopID", shopId);

        ResultImage.NetworkTask networkTask = new ResultImage.NetworkTask(url, val); //여기에 넣기
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

                for (int i = 0; i < resultArray.length(); i++) {

                    JSONObject temp = resultArray.getJSONObject(i);
                    Data_PictureDTO dp_temp = new Data_PictureDTO();
                    dp_temp.setShopPic(temp.getString("shopPic"));
                    shopPic=dp_temp.getShopPic();


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent ImageIntent = new Intent(ResultImage.this, UserImage.class);
            ImageIntent.putExtra("shopID", shopId );
            ImageIntent.putExtra("shopPic", shopPic );
            ImageIntent.putExtra("select_Temp", select_Temp );
            ResultImage.this.startActivity(ImageIntent);
            finish();


        }



    }




    private void initView(){
        ImageBus = (ImageView)findViewById(R.id.ImageBus);
        anim = AnimationUtils.loadAnimation(this, R.anim.loading);
        ImageBus.setAnimation(anim);
    }
}

