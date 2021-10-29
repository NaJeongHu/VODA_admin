package com.voda.voda_admin.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.voda.voda_admin.Model.StoreInfo;
import com.voda.voda_admin.Model.UserAccount;
import com.voda.voda_admin.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    // 이미지
    private static final int IMAGE_REQUEST = 0;
    private Uri imageUri;

    //파이어베이스
    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private FirebaseUser mFirebaseUser;
    private StorageTask uploadTask;
    private StorageReference storageReference;

    //UI
    private EditText mEtEmailId, mEtPassword,mEtName,mEtEmail,mEtPhoneNum,mEtStoreName,mEtStoreAddress,mEtBusinessNum,mEtCategory;
    private Button mBtnRegister;
    private ImageView mIvPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("voda_handy");
        storageReference = FirebaseStorage.getInstance().getReference("admin_account_pictures");

        mEtEmailId = findViewById(R.id.et_emailId);
        mEtPassword = findViewById(R.id.et_password);
        mEtName = findViewById(R.id.et_name);
        mEtEmail = findViewById(R.id.et_email);
        mEtPhoneNum = findViewById(R.id.et_phonenum);
        mEtStoreName = findViewById(R.id.et_storename);
        mEtStoreAddress = findViewById(R.id.et_storeaddress);
        mEtBusinessNum = findViewById(R.id.et_businessnum);
        mEtCategory = findViewById(R.id.et_category);
        mIvPicture = findViewById(R.id.iv_picture);

        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(this);
        mIvPicture.setOnClickListener(this);
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
                            mFirebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            StoreInfo storeInfo = new StoreInfo();
                            account.setIdToken(mFirebaseUser.getUid());
                            account.setEmailId(mFirebaseUser.getEmail());
                            account.setPassword(strPassword);
                            account.setName(strName);
                            account.setEmail(strEmail);
                            account.setPhonenum(strPhoneNum);
                            account.setStorename(strStoreName);
                            account.setStoreaddress(strStoreAddress);
                            account.setBusinessnum(strBusinessNum);
                            account.setCategory(strCategory);

                            storeInfo.setStorename(strStoreName);
                            storeInfo.setStar(3);       // 추후 DB에서 직접 수정
                            storeInfo.setWaiting(40);   // 추후 DB에서 직접 수정
                            // setValue : database에 insert 행위위
                            mDatabaseRef.child("UserAccount").child("admin").child(mFirebaseUser.getUid()).setValue(account);
                            mDatabaseRef.child("Store").child(strCategory).child(mFirebaseUser.getUid()).child("StoreInfo").setValue(storeInfo);

                            uploadImage();

                            Toast.makeText(RegisterActivity.this,"회원가입 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this,task.getException()+"회원가입 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case R.id.iv_picture:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                //set Image to mIvPicture
                if (imageUri != null) {
                    mIvPicture.setImageURI(imageUri);
                }
            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImage() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                + "." + getFileExtension(imageUri));

        uploadTask = fileReference.putFile(imageUri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageurl", mUri);
                    mDatabaseRef.child("UserAccount").child("admin").child(mFirebaseUser.getUid()).updateChildren(map);
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = RegisterActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}