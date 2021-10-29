package com.voda.voda_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.voda.voda_admin.Model.Menu;
import com.voda.voda_admin.Model.UserAccount;
import com.voda.voda_admin.R;

public class AccountActivity extends AppCompatActivity {

    //파이어베이스
    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private FirebaseUser firebaseUser;

    //Activity UI
    private Button btn_logout;
    private ImageView iv_picture;
    private TextView tv_store_name,tv_name,tv_email,tv_phone_num,tv_address,tv_business_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();
        getDataFromServer();
    }

    private void init(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("voda_handy");
        firebaseUser = mFirebaseAuth.getCurrentUser();

        btn_logout = findViewById(R.id.account_logout);
        iv_picture = findViewById(R.id.account_picture);
        tv_store_name = findViewById(R.id.account_store_name);
        tv_name = findViewById(R.id.account_name);
        tv_email = findViewById(R.id.account_email);
        tv_phone_num = findViewById(R.id.account_phone_num);
        tv_address = findViewById(R.id.account_address);
        tv_business_num = findViewById(R.id.account_business_num);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(AccountActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getDataFromServer() {
        mDatabaseRef.child("UserAccount").child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Log.d("파이어베이스", "파이어베이스 연동 성공");
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    Log.d("파이어베이스", "파이어베이스 연동 성공2");
                    if(snapshot.getKey().equals(firebaseUser.getUid())){
                        Log.d("파이어베이스", "파이어베이스 연동 성공3");
                        UserAccount user = snapshot.getValue(UserAccount.class);
                        if(user.getImageurl()!=null){
                            Glide.with(AccountActivity.this).load(user.getImageurl()).into(iv_picture);
                        }
                        tv_store_name.setText(user.getStorename());
                        tv_name.setText(user.getName());
                        tv_email.setText(user.getEmail());
                        tv_phone_num.setText(user.getPhonenum());
                        tv_address.setText(user.getStoreaddress());
                        tv_business_num.setText(user.getBusinessnum());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("파이어베이스", "데이터 가져오기 실패");
            }
        });
    }
}