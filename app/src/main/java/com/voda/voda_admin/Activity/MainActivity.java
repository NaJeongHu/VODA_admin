package com.voda.voda_admin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
                startActivity(intent);
                break;
            case R.id.card_menu:
                break;
            case R.id.card_customer:
                break;
            case R.id.card_analysis:
                break;
            case R.id.card_settings:
                break;
            case R.id.iv_private:
                Intent intent1 = new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent1);
                break;
        }
    }
}