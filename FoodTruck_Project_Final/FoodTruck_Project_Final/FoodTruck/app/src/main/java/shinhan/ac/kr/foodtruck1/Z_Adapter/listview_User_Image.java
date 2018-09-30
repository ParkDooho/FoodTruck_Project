package shinhan.ac.kr.foodtruck1.Z_Adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import shinhan.ac.kr.foodtruck1.R;

public class listview_User_Image extends LinearLayout {
    TextView textView0;
    TextView textView1;
    TextView textView2;


    public listview_User_Image(Context context) {
        super(context);
        init(context);
    }

    public listview_User_Image(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    private void init(Context context){
        //inflate
        // system service에서 LayoutInflater를 가져다 사용하겠다.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // singer_item xml을 inflate함
        inflater.inflate(R.layout.activity_listview__user__image,this, true);

        textView0 = (TextView) findViewById(R.id.textView0);
        textView1= (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);




    }

    public void setshopMenu(String shopMenu){textView0.setText(shopMenu);}
    public void setshopPrice(int shopPrice){
        textView1.setText(String.valueOf(shopPrice));
    }
    public void setshopEvent(String shopEvent){
        textView2.setText(shopEvent);
    }



}
