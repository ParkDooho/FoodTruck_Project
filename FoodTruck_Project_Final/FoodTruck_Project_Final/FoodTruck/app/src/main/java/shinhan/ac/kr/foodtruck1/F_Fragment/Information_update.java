package shinhan.ac.kr.foodtruck1.F_Fragment;

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Information_update extends AppCompatActivity {
    EditText openTime;
    EditText openPlace;
    Button openSave;

    public static Activity Information_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_update);
        Information_update = Information_update.this;
        String ShopID = UserImage.ShopID;
        openTime = (EditText)findViewById(R.id.openTime);
        openPlace = (EditText)findViewById(R.id.openPlace);
        openSave = (Button)findViewById(R.id.openSave);

        String url = SeverURL.URL();
        ContentValues val = new ContentValues();
        val.put("action", "shop_OpenSelect");
        val.put("shopID", ShopID);

        openSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SaveOpen = new Intent(Information_update.this, Information_update_Result.class);
                SaveOpen.putExtra("openTime",openTime.getText().toString());
                SaveOpen.putExtra("openPlace",openPlace.getText().toString());
                Information_update.this.startActivity(SaveOpen);
                finish();

            }

        });

        Information_update.NetworkTask networkTask = new Information_update.NetworkTask(url, val); //여기에 넣기
        networkTask.execute();

    } public class NetworkTask extends AsyncTask<Void, Void, String> {

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
                JSONObject temp = resultArray.getJSONObject(0);
                openTime.setText(temp.getString("shopOpenTime"));
                openPlace.setText(temp.getString("shopOpenPlace"));
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }

}
