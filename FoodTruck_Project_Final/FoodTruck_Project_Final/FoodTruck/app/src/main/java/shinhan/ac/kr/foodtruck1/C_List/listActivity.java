package shinhan.ac.kr.foodtruck1.C_List;


import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Adapter.listview_item;
import shinhan.ac.kr.foodtruck1.Z_Data.Data_TextDTO;
import shinhan.ac.kr.foodtruck1.Z_Data.ResultImage;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class listActivity extends AppCompatActivity {

    ArrayList<Data_TextDTO> Result = new ArrayList<Data_TextDTO>();
    String[] x = Result.toArray(new String[Result.size()]);
    ListView listview;
    ArrayList<Data_TextDTO> items = new ArrayList();
    pjAdapter adapter = new pjAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listview = (ListView) findViewById(R.id.listview1);
        Intent intent = getIntent();
        String text = intent.getStringExtra("String");
        String selectMenu = intent.getStringExtra("Menu");
        String selectNenu = intent.getStringExtra("menu");

        String url = SeverURL.URL();
        ContentValues val= new ContentValues();
        val.put("action",selectMenu);
        val.put(selectNenu,text);

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, val); //여기에 넣기
        networkTask.execute();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Data_TextDTO adapterName =  (Data_TextDTO)adapter.getItem(position);
                String adapterResultName = adapterName.getShopID();
                Intent intent = new Intent(listActivity.this, ResultImage.class);
                intent.putExtra("shopId", adapterResultName);
                intent.putExtra("select_Temp", "소비자");
                startActivity(intent);
            }
        });

    }

    class pjAdapter extends BaseAdapter {

        ArrayList<Data_TextDTO> items = new ArrayList();


        @Override
        public int getCount() {
            return items.size();    // 아이템의 갯수 (배열의 갯수)
        }

        // 클래스 밖에서 item data 추가하는 메소드 정의
        public void addItem(Data_TextDTO item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) { // 아이템 선택
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {   // id값이 있다면 넘겨줘라.
            return position;                   // 특정 아이디를 만들어서 넘겨줘도 됨.
        }


        // SingerItemView(아이템뷰)를 리턴하는 메소드
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            listview_item view = new listview_item(getApplicationContext()); // 어떤 뷰든 안드로이드는 context객체를 받음*
            // 뷰의 몇번째 아이템인지 - position(index값)
            Data_TextDTO item = items.get(position);
            view.setshopID(item.getShopID());       // name 설정
            view.setshopName(item.getShopName());   // mobile 설정
            view.setshopMenu(item.getShopMenu());     // ResId 설정
            view.setshopPrice(item.getShopPrice());
            view.setshopEvent(item.getShopEvent());




            return view;
        }
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            final ListView listview = (ListView) findViewById(R.id.listview1);
            try {
                JSONObject resultData = new JSONObject(s);
                JSONArray resultArray = new JSONArray(resultData.getString("success"));

                for (int i = 0; i < resultArray.length(); i++) {

                    JSONObject temp = resultArray.getJSONObject(i);
                    adapter.addItem(new Data_TextDTO(temp.getString("shopID"), temp.getString("shopName"), temp.getString("shopMenu"), temp.getInt("shopPrice"), temp.getString("shopEvent")));
                    listview.setAdapter(adapter);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }}