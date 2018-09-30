package shinhan.ac.kr.foodtruck1.C_List;



import android.content.ContentValues;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.content.pm.PackageManager;
import android.view.ViewGroup;
import android.Manifest;
import android.os.Build;
import android.widget.TextView;

import java.util.ArrayList;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shinhan.ac.kr.foodtruck1.A_Select.MainActivity;
import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Adapter.AdapterSpinner1;
import shinhan.ac.kr.foodtruck1.Z_Data.GpsInfo;
import shinhan.ac.kr.foodtruck1.Z_Data.ResultImage;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class SearchActivity extends AppCompatActivity implements MapView.POIItemEventListener {
    private Spinner spinner;
    private EditText edit;
    AdapterSpinner1 adapterSpinner1;

    private Button btnShowLocation;

    private MapView mapView;
    private MapPOIItem userMarker;
    private MapPOIItem shopMarker;

    double latitude;
    double longitude;

    String selectMenu;
    String selectmenu;
    ArrayList<String> menu;

    String shopId;

    double GPS_x=0;
    double GPS_y=0;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    protected LocationManager locationManager;

    // GPSTracker class
    private GpsInfo gps;


    // CalloutBalloonAdapter 인터페이스 구현
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {

            if(poiItem.getTag()==0){
                ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.gps);
                ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            }else{
                ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.bus);
                ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            }
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spinner = (Spinner)findViewById(R.id.spinner1);
        edit = (EditText)findViewById(R.id.edit1);

        Intent ImageReady = getIntent();
        shopId=ImageReady.getStringExtra("ID");
        GPS_x=ImageReady.getDoubleExtra("GPS_x",0);
        GPS_y=ImageReady.getDoubleExtra("GPS_y",0);
        String url = SeverURL.URL();
        ContentValues val = new ContentValues();
        val.put("action", "selectShop_GPS");
        val.put("userAddr_x", GPS_x);
        val.put("userAddr_y", GPS_y);

        SearchActivity.NetworkTask networkTask = new SearchActivity.NetworkTask(url, val); //여기에 넣기
        networkTask.execute();


        String[] TextList = {"메뉴","트럭 이름"};
        menu = new ArrayList<String>();
        for(int i = 0; i< TextList.length; i++)
            menu.add(TextList[i]);
        //UI생성
        spinner = (Spinner)findViewById(R.id.spinner1);
        //Adapter
        adapterSpinner1 = new AdapterSpinner1(this, menu);
        //Adapter 적용
        spinner.setAdapter(adapterSpinner1);
        //Spinner();
        spinner.setOnItemSelectedListener(ItemSelectedListener);


        // ---- 다음 지도
        mapView = new MapView(this);
        mapView.setPOIItemEventListener(this);

        // 구현한 CalloutBalloonAdapter 등록
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


        btnShowLocation = (Button) findViewById(R.id.btn_start);  // 좌표 측정 버튼

        edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Actiond
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    String data = edit.getText().toString().trim();
                    Intent registerIntent = new Intent(SearchActivity.this, listActivity.class);
                    registerIntent.putExtra("String",data);
                    registerIntent.putExtra("Menu",selectMenu);
                    registerIntent.putExtra("menu",selectmenu);
                    SearchActivity.this.startActivity(registerIntent);
                    return true;
                }
                return false;
            }
        });


        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 권한 요청을 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }

                gps = new GpsInfo(SearchActivity.this);

                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    if(userMarker != null)
                        mapView.removePOIItem(userMarker);

                    // ---- 위도 경도 포인터 찍기
                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);  // 위도 경도 측정
                    mapView.setMapCenterPoint(mapPoint, true);
                    userMarker = new MapPOIItem();
                    userMarker.setItemName("현재 위치");  // 마커 클릭 시 나오는 아이템 이름
                    userMarker.setTag(0);  // 마커의 번호 지정
                    userMarker.setMapPoint(mapPoint);
                    // 기본으로 제공하는 BluePin 마커 모양.
                    userMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                    // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    userMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                    mapView.addPOIItem(userMarker);

                    /*Toast.makeText(getApplicationContext(),"당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,Toast.LENGTH_LONG).show();*/
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });
        callPermission();  // 권한 요청을 해야 함
    }

    public void onClickSearch(View v) {
        switch (v.getId()) {
            case R.id.search1 :
                String data = edit.getText().toString();
                Intent registerIntent = new Intent(SearchActivity.this, listActivity.class);
                registerIntent.putExtra("String",data);
                registerIntent.putExtra("Menu",selectMenu);
                registerIntent.putExtra("menu",selectmenu);
                SearchActivity.this.startActivity(registerIntent);
        }
    }

    /*public void Spinner(){
        String[] TextList = {"메뉴","트럭이름"};
        menu = new ArrayList<String>();
        for(int i = 0; i< TextList.length; i++)
            menu.add(TextList[i]);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,menu);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }*/

    AdapterView.OnItemSelectedListener ItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (menu.get(i)){
                case "메뉴":
                    selectMenu = "selectData_text_Menu";
                    selectmenu = "shopMenu";
                    break;
                case "트럭이름":
                    selectMenu = "selectData_text_Name";
                    selectmenu = "shopName";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    // ---- 위도 경도 관련 소스
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isAccessFineLocation = true;
        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            isAccessCoarseLocation = true;
        }
        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
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
            double GPS_Result_x = 0;
            double GPS_Result_y = 0;

            try {
                JSONObject resultData = new JSONObject(s);
                JSONArray resultArray = new JSONArray(resultData.getString("success"));


                for (int i = 0; i < resultArray.length(); i++) {
                    shopMarker = new MapPOIItem();
                    JSONObject temp = resultArray.getJSONObject(i);
                    shopMarker.setItemName(temp.getString("shopID"));
                    MapPoint resultPoint = MapPoint.mapPointWithGeoCoord(temp.getDouble("shopAddr_x"),temp.getDouble("shopAddr_y"));  // 위도 경도 측정
                    shopMarker.setTag(i+1);  // 마커의 번호 지정
                    shopMarker.setMapPoint(resultPoint);
                    // 기본으로 제공하는 BluePin 마커 모양.
                    shopMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    shopMarker.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin);
                    mapView.addPOIItem(shopMarker);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
       if(mapPOIItem.getTag()!=0){
            Intent intent = new Intent(SearchActivity.this, ResultImage.class);
            intent.putExtra("shopId",mapPOIItem.getItemName());
            intent.putExtra("select_Temp","소비자");
            SearchActivity.this.startActivity(intent);

        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        SearchActivity.this.startActivity(intent);
        SearchActivity.this.finish();
    }
}