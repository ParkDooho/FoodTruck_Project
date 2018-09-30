package shinhan.ac.kr.foodtruck1.F_Fragment;

import shinhan.ac.kr.foodtruck1.E_Image.UserImage;
import shinhan.ac.kr.foodtruck1.R;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Menu_insert extends AppCompatActivity {
    EditText shopMenu_ET;
    EditText shopPrice_ET;
    EditText shopEvent_ET;
    Button menuSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_insert);


        shopMenu_ET = (EditText)findViewById(R.id.shopMenu_ET);
        shopPrice_ET = (EditText)findViewById(R.id.shopPrice_ET);
        shopEvent_ET = (EditText)findViewById(R.id.shopEvent_ET);
        menuSave = (Button)findViewById(R.id.menuSave);

        menuSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent SaveMenu = new Intent(Menu_insert.this, Menu_insert_Result.class);
                SaveMenu.putExtra("shopMenu",shopMenu_ET.getText().toString());
                SaveMenu.putExtra("shopPrice",shopPrice_ET.getText().toString());
                SaveMenu.putExtra("shopEvent",shopEvent_ET.getText().toString());
                Menu_insert.this.startActivity(SaveMenu);
                finish();

            }

        });





    }
}
