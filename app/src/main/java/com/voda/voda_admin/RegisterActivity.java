package com.voda.voda_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmailId, mEtPassword,mEtName,mEtEmail,mEtPhoneNum,mEtStoreName,mEtStoreAddress,mEtBusinessNum,mEtCategory;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("voda_handy");
    }

    private void init() {
        mEtEmailId = findViewById(R.id.et_emailId);
        mEtPassword = findViewById(R.id.et_password);
        mEtName = findViewById(R.id.et_name);
        mEtEmail = findViewById(R.id.et_email);
        mEtPhoneNum = findViewById(R.id.et_phonenum);
        mEtStoreName = findViewById(R.id.et_storename);
        mEtStoreAddress = findViewById(R.id.et_storeaddress);
        mEtBusinessNum = findViewById(R.id.et_businessnum);
        mEtCategory = findViewById(R.id.et_category);

        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String strEmailId = mEtEmailId.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                String strName = mEtName.getText().toString();
                String strEmail = mEtEmail.getText().toString();
                String strPhoneNum = mEtPhoneNum.getText().toString();
                String strStoreName = mEtStoreName.getText().toString();
                String strStoreAddress = mEtStoreAddress.getText().toString();
                String strBusinessNum = mEtBusinessNum.getText().toString();
                String strCategory = mEtCategory.getText().toString();
                // Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmailId,strPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPassword);
                            account.setName(strName);
                            account.setEmail(strEmail);
                            account.setPhonenum(strPhoneNum);
                            account.setStorename(strStoreName);
                            account.setStoreaddress(strStoreAddress);
                            account.setBusinessnum(strBusinessNum);
                            account.setCategory(strCategory);

                            // setValue : database에 insert 행위위
                           mDatabaseRef.child("UserAccount").child("admin").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegisterActivity.this,"회원가입 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this,task.getException()+"회원가입 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            default:
                break;
        }
    }
}