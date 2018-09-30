package shinhan.ac.kr.foodtruck1.F_Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class Menu_update extends AppCompatActivity {
    String shopID;
    String shopName;
    String shopMenu;
    TextView shopMenu_ET;
    TextView shopPrice_ET;
    TextView shopEvent_ET;
    Button menuSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_update);
        shopMenu_ET = (TextView)findViewById(R.id.shopMenu_ET);
        shopPrice_ET = (TextView)findViewById(R.id.shopPrice_ET);
        shopEvent_ET = (TextView)findViewById(R.id.shopEvent_ET);
        menuSave = (Button)findViewById(R.id.menuSave);

        Intent intent = getIntent();
        shopID = intent.getStringExtra("shopID");
        shopMenu = intent.getStringExtra("shopMenu");

        String url = SeverURL.URL();
        ContentValues val = new ContentValues();
        val.put("action", "shop_MenuSelect");
        val.put("shopID", shopID);
        val.put("shopMenu", shopMenu);

        menuSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SaveMenu = new Intent(Menu_update.this, Menu_update_Result.class);
                SaveMenu.putExtra("shopMenu",shopMenu_ET.getText().toString());
                Menu_update.this.startActivity(SaveMenu);
                finish();

            }

        });


        Menu_update.NetworkTask networkTask = new Menu_update.NetworkTask(url, val); //여기에 넣기
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
                    JSONObject temp = resultArray.getJSONObject(0);
                    shopMenu_ET.setText(temp.getString("shopMenu"));
                    shopPrice_ET.setText(temp.getString("shopPrice"));
                    shopEvent_ET.setText(temp.getString("shopEvent"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


    }

}
