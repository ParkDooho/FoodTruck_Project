package shinhan.ac.kr.foodtruck1.F_Fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Data.Resultlogin;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class Fragment_Information extends  Fragment{
    private View view;
    private TextView txt_shopTel;
    private TextView txt_shopOwner;
    private TextView txt_shopTime;
    private TextView txt_shopPlace;

    private Button updateBtn;
    public Fragment_Information() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        String ShopID = UserImage.ShopID;
        String Select_Temp = UserImage.Select_Temp;
        view = inflater.inflate(R.layout.fragment_information, null);
        String url = SeverURL.URL();
        ContentValues val = new ContentValues();
        val.put("action","shop_Information");
        val.put("shopID",ShopID);

        Fragment_Information.NetworkTask networkTask = new Fragment_Information.NetworkTask(url, val); //여기에 넣기
        networkTask.execute();

        txt_shopTel = view.findViewById(R.id.txt_shopTel);
        txt_shopOwner = view.findViewById(R.id.txt_shopOwner);
        txt_shopTime = view.findViewById(R.id.txt_shopTime);
        txt_shopPlace = view.findViewById(R.id.txt_shopPlace);
        updateBtn=view.findViewById(R.id.updateBtn);

        switch(Select_Temp){
            case "판매자":
                updateBtn.setVisibility(View.VISIBLE);
                break;
            case "소비자":
                updateBtn.setVisibility(View.INVISIBLE);
                break;
        }




        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openShop = new Intent(view.getContext (), Information_update.class);
                //openShop.putExtra("ShopID",ShopID);
                view.getContext ().startActivity(openShop);
            }
        });


        // ViewGroup rootGroup =(ViewGroup)inflater.inflate(R.layout.fragment_information,container,false);
        return  view;

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

                    txt_shopTel.setText(temp.getString("shopTel"));
                    txt_shopOwner.setText(temp.getString("shopOwner"));
                    txt_shopTime.setText(temp.getString("shopOpenTime"));
                    txt_shopPlace.setText(temp.getString("shopOpenPlace"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }

}