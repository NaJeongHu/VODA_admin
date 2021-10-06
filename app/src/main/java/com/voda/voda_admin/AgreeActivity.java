package com.voda.voda_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgreeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        init();
    }

    private void init(){
        btn_continue = findViewById(R.id.btn_continue);

        btn_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                Intent intent = new Intent(AgreeActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}