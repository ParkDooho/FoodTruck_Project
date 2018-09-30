package shinhan.ac.kr.foodtruck1.E_Image;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shinhan.ac.kr.foodtruck1.A_Select.MainActivity;
import shinhan.ac.kr.foodtruck1.C_List.SearchActivity;
import shinhan.ac.kr.foodtruck1.D_Gps.ResultGPS_Set;
import shinhan.ac.kr.foodtruck1.F_Fragment.Fragment_Information;
import shinhan.ac.kr.foodtruck1.F_Fragment.Fragment_Menu;
import shinhan.ac.kr.foodtruck1.R;
import shinhan.ac.kr.foodtruck1.Z_Data.GpsInfo;
import shinhan.ac.kr.foodtruck1.Z_Data.ResultGPS_Del;
import shinhan.ac.kr.foodtruck1.Z_Network.RequestHttpURLConnection;
import shinhan.ac.kr.foodtruck1.Z_Network.SeverURL;

public class UserImage extends AppCompatActivity implements Runnable{

    Button  saleBtn;
    Button startBtn;
    private final int REQ_CODE_SELECT_IMAGE = 100;
    private String img_path = new String();
    private String serverURL;  //<<서버주소
    private Bitmap image_bitmap_copy = null;
    private Bitmap image_bitmap = null;
    private String imageName = null;
    final static int GPS_SetData =0;
    final static int GPS_DelData =1;
    String GPS_Check="시작";
    private GpsInfo gps;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    public static Activity UserImage;
    // 1. 변수 선언
    private DrawerLayout mDrawerLayout;
    ImageView readImage;
    Bitmap bitmap; // 비트맵 객체
    public static String ShopID;
    public static String Select_Temp;
    public static String ShopName;
    String ShopPic;
    URL url = null;
    URL url_Del = null;
    TextView textViewRegion;
    // 메인 스레드와 백그라운드 스레드 간의 통신
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 서버에서 받아온 이미지를 핸들러를 경유해 이미지뷰에 비트맵 리소스 연결
            readImage.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_image);
        saleBtn = (Button)findViewById(R.id.saleButton);
        startBtn = (Button)findViewById(R.id.startButton);
        readImage = (ImageView) findViewById(R.id.readImage);
        UserImage = UserImage.this;

        Intent intent = getIntent();
        ShopID=intent.getStringExtra("shopID");
        ShopPic = intent.getStringExtra("shopPic");
        Select_Temp = intent.getStringExtra("select_Temp");

        serverURL= SeverURL.URL()+"?action=insertShop_Image&shopID="+ShopID+"&";
        // 툴바 설정
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        textViewRegion = (TextView) findViewById(R.id.textViewRegion);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        appBarLayout.setBackgroundColor(getResources().getColor(R.color.bar));


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs); // 탭바에 뷰페이거 설정
        tabs.setupWithViewPager(viewPager);
        TabLayout.Tab tabDefault = tabs.getTabAt(0);
        tabDefault.select();

        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch(GPS_Check){
                    case "시작":
                        //GPS_Check="종료";
                        startBtn.setText("영업 종료");
                        gps = new GpsInfo(UserImage.this);
                        // 권한 요청을 해야 함
                        if (!isPermission) {
                            callPermission();
                            return;
                        }
                        // GPS 사용유무 가져오기
                        if (gps.isGetLocation()) {
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            Intent GPS_Set = new Intent(UserImage.this, ResultGPS_Set.class);
                            GPS_Set.putExtra("ShopID",ShopID);
                            GPS_Set.putExtra("GPS_x",latitude);
                            GPS_Set.putExtra("GPS_y",longitude);
                            startActivityForResult(GPS_Set,GPS_SetData);
                        } else {
                            // GPS 를 사용할수 없으므로
                            gps.showSettingsAlert();
                        }break;
                    case "종료":
                        //GPS_Check="시작";
                        startBtn.setText("영업 시작");
                        Intent GPS_Set = new Intent(UserImage.this, ResultGPS_Del.class);
                        GPS_Set.putExtra("ShopID",ShopID);
                        startActivityForResult(GPS_Set,GPS_DelData);break;
                }
            }
        });
        callPermission();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        readImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });

        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoFileUpload(serverURL, img_path);
                Toast.makeText(getApplicationContext(), "이미지 전송 성공",Toast.LENGTH_SHORT).show();
                Log.d("Send", "Success");
            }
        });


        // 2. 위젯 연결
        switch(Select_Temp){
            case "판매자":
                saleBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.VISIBLE);
                break;
            case "소비자":
                saleBtn.setVisibility(View.INVISIBLE);
                startBtn.setVisibility(View.INVISIBLE);
                break;
        }


        // 3. 서버에 이미지를 하나 넣어놓자.
        // /resources/images/이미지.png 처럼 올려 놓자.
        // 그냥... 환경설정에 자원 경로로 등록 된 곳에 올리면 된다.
        String url = SeverURL.URL();
        ContentValues val = new ContentValues();
        val.put("action","shop_Information");
        val.put("shopID",ShopID);
        UserImage.NetworkTask networkTask = new UserImage.NetworkTask(url, val); //여기에 넣기
        networkTask.execute();

        // 백그라운드 스레드 생성
        Thread th = new Thread(UserImage.this);
        // 동작 수행
        th.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Toast.makeText(getBaseContext(), "resultCode : " + data, Toast.LENGTH_SHORT).show();
        switch (requestCode){
            case REQ_CODE_SELECT_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.
                        //Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
                        //이미지를 비트맵형식으로 반환
                        image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        readImage.setImageBitmap(image_bitmap);
                        //사용자 단말기의 width , height 값 반환
                        int reWidth = (int) (getWindowManager().getDefaultDisplay().getWidth());
                        int reHeight = (int) (getWindowManager().getDefaultDisplay().getHeight());

                        //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                        image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
                        ImageView image = (ImageView) findViewById(R.id.imageView);  //이미지를 띄울 위젯 ID값
                        image.setImageBitmap(image_bitmap_copy);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } }
                break;
            case GPS_SetData:
                if(resultCode == Activity.RESULT_OK){
                    GPS_Check = data.getStringExtra("Check");
                }
                break;
            case GPS_DelData:
                if(resultCode == Activity.RESULT_OK){
                    GPS_Check = data.getStringExtra("Check");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }//end of onActivityResult()

    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};

        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        //Toast.makeText(UserImage.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;

        return imgPath;
    }//end of getImagePathToUri()

    public void DoFileUpload(String apiUrl, String absolutePath) {
        HttpFileUpload(apiUrl, "", absolutePath);
    }

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    // 백그라운드 스레드
    @Override
    public void run() {
        // http://192.168.0.127/resources/images/like1.png
        try{
            // 스트링 주소를 url 형식으로 변환
            url_Del = new URL(SeverURL.URL()+"?action=deleteShop_GPS&shopID="+ShopID);
            HttpURLConnection conn2 = (HttpURLConnection)url_Del.openConnection();
            conn2.connect();

            url = new URL(SeverURL.IMG_URL()+ShopPic);
            // url에 접속 시도
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            // 스트림 생성
            InputStream is = conn.getInputStream();
            // 스트림에서 받은 데이터를 비트맵 변환
            // 인터넷에서 이미지 가져올 때는 Bitmap을 사용해야함
            bitmap = BitmapFactory.decodeStream(is);

            // 핸들러에게 화면 갱신을 요청한다.
            handler.sendEmptyMessage(0);
            // 연결 종료
            is.close();
            conn.disconnect();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setupViewPager(ViewPager viewPager) // 탭바에 프레그먼트 추가
    {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Information(), "정보");
        adapter.addFragment(new Fragment_Menu(), "메뉴");
        viewPager.setAdapter(adapter);
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

                    textViewRegion.setText(temp.getString("shopName"));
                    ShopName=temp.getString("shopName");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }


    // ---- 위도 경도 관련 소스
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
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


    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() { // 액티비티 중단 되었을때
        super.onPause();
    }

    @Override
    protected void onResume() { // 액티비티 재개 했을때
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        switch(Select_Temp){
            case "판매자":
                Intent intent = new Intent(UserImage.this, MainActivity.class);
                UserImage.startActivity(intent);
                UserImage.finish();
                break;
            case "소비자":
                Intent intent2 = new Intent(UserImage.this, SearchActivity.class);
                UserImage.startActivity(intent2);
                UserImage.finish();
                break;
        }

    }



    public void HttpFileUpload(String urlString, String params, String fileName) {
        try {

            FileInputStream mFileInputStream = new FileInputStream(fileName);
            URL connectUrl = new URL(urlString);
            Log.d("Test", "mFileInputStream  is " + mFileInputStream);

            // HttpURLConnection 통신
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" +
                    boundary);

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("Test", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("Test", "File is written");
            mFileInputStream.close();
            dos.flush();
            // finish upload...

            // get response
            InputStream is = conn.getInputStream();

            StringBuffer b = new StringBuffer();
            for (int ch = 0; (ch = is.read()) != -1; ) {
                b.append((char) ch);
            }
            is.close();
            Log.e("Test", b.toString());


        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
    } // end of HttpFileUpload()


}