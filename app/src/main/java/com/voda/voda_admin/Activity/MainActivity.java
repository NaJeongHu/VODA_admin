package com.voda.voda_admin.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.voda.voda_admin.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView card_open,card_menu,card_customer,card_analysis,card_settings;
    ImageView iv_private;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){

        card_open = findViewById(R.id.card_open);
        card_menu = findViewById(R.id.card_menu);
        card_customer = findViewById(R.id.card_customer);
        card_analysis = findViewById(R.id.card_analysis);
        card_settings = findViewById(R.id.card_settings);
        iv_private = findViewById(R.id.iv_private);

        card_open.setOnClickListener(this);
        card_menu.setOnClickListener(this);
        card_customer.setOnClickListener(this);
        card_analysis.setOnClickListener(this);
        card_settings.setOnClickListener(this);
        iv_private.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_open:
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("num",0);
                startActivity(intent);
                break;
            case R.id.card_menu:
                Intent intent1 = new Intent(MainActivity.this,MainActivity2.class);
                intent1.putExtra("num",1);
                startActivity(intent1);
                break;
            case R.id.card_customer:
                Intent intent2 = new Intent(MainActivity.this,MainActivity2.class);
                intent2.putExtra("num",2);
                startActivity(intent2);
                break;
            case R.id.card_analysis:
                Intent intent3 = new Intent(MainActivity.this,MainActivity2.class);
                intent3.putExtra("num",3);
                startActivity(intent3);
                break;
            case R.id.card_settings:
                Intent intent4 = new Intent(MainActivity.this,MainActivity2.class);
                intent4.putExtra("num",4);
                startActivity(intent4);
                break;
            case R.id.iv_private:
                Intent intent5 = new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent5);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setMessage("VODA를 종료하시겠습니까?");
        d.setPositiveButton("예", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // 앱 종료
                moveTaskToBack(true);						// 태스크를 백그라운드로 이동
                finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        d.show();
    }
}