package shinhan.ac.kr.foodtruck1.F_Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Adapter.listview_User_Image;
import shinhan.ac.kr.foodtruck1.Z_Data.Data_TextDTO;
import shinhan.ac.kr.foodtruck1.Z_Data.Data_Text_ImageDTO;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class Fragment_Menu extends  Fragment{
    pjAdapter adapter = new pjAdapter();
    private View view;
    private ListView listImage;
    private Button insertBtn;
    public Fragment_Menu() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // xml 로 만들어준 프레그먼트를 자바 단에서 만들어줌
        String ShopID = UserImage.ShopID;
        String Select_Temp = UserImage.Select_Temp;

        view = inflater.inflate(R.layout.fragment_menu, null);
        listImage = view.findViewById(R.id.listImage);
        insertBtn = view.findViewById(R.id.insertBtn);
        if(Select_Temp.equals("판매자")){
        listImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                String ShopID = UserImage.ShopID;
                Data_Text_ImageDTO adapterMenu =  (Data_Text_ImageDTO)adapter.getItem(position);
                String adapterResultMenu = adapterMenu.getShopMenu();
                Intent intent = new Intent(view.getContext (), Menu_update.class);
                intent.putExtra("shopID", ShopID);
                intent.putExtra("shopMenu", adapterResultMenu);
                view.getContext ().startActivity(intent);
            }
        });}


        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext (), Menu_insert.class);
                view.getContext ().startActivity(intent);
            }
        });

        switch(Select_Temp){
            case "판매자":
                insertBtn.setVisibility(View.VISIBLE);
                break;
            case "소비자":
                insertBtn.setVisibility(View.INVISIBLE);
                break;
        }

        String url = SeverURL.URL();
        ContentValues val = new ContentValues();
        val.put("action","select_Menu");
        val.put("shopID",ShopID);

        Fragment_Menu.NetworkTask networkTask = new Fragment_Menu.NetworkTask(url, val); //여기에 넣기
        networkTask.execute();

        return  view;

    }



    class pjAdapter extends BaseAdapter {

        ArrayList<Data_Text_ImageDTO> items = new ArrayList();


        @Override
        public int getCount() {
            return items.size();    // 아이템의 갯수 (배열의 갯수)
        }

        // 클래스 밖에서 item data 추가하는 메소드 정의
        public void addItem(Data_Text_ImageDTO item) {
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

            listview_User_Image view = new listview_User_Image(getContext()); // 어떤 뷰든 안드로이드는 context객체를 받음*
            // 뷰의 몇번째 아이템인지 - position(index값)
            Data_Text_ImageDTO item = items.get(position);
            // mobile 설정
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

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //final ListView listview = view.findViewById(R.id.listImage);
            try {
                JSONObject resultData = new JSONObject(s);
                JSONArray resultArray = new JSONArray(resultData.getString("success"));

                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject temp = resultArray.getJSONObject(i);
                        adapter.addItem(new Data_Text_ImageDTO(temp.getString("shopMenu"), temp.getInt("shopPrice"), temp.getString("shopEvent")));
                    listImage.setAdapter(adapter);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }
}