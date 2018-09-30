package shinhan.ac.kr.foodtruck1.Z_Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import shinhan.ac.kr.foodtruck1.R;

public class listview_item extends LinearLayout {
    TextView textView0;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;


    public listview_item(Context context) {
        super(context);
        init(context);
    }

    public listview_item(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    private void init(Context context){
        //inflate
        // system service에서 LayoutInflater를 가져다 사용하겠다.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // singer_item xml을 inflate함
        inflater.inflate(R.layout.activity_listview_item,this, true);

        textView0 = (TextView) findViewById(R.id.textView0);
        textView1= (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4= (TextView) findViewById(R.id.textView4);




    }

    public void setshopID(String shopID) { textView0.setText(shopID);  }
    public void setshopName(String shopName){
        textView1.setText(shopName);
    }
    public void setshopMenu(String shopMenu){
        textView2.setText(shopMenu);
    }
    public void setshopPrice(int shopPrice){
        textView3.setText(String.valueOf(shopPrice));
    }
    public void setshopEvent(String shopEvent){
        textView4.setText(shopEvent);
    }



}
